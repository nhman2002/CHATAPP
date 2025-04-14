import java.rmi.registry.*;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Timer;

public class ChatClientMain {
    public static void main(String[] args) {
        try {
            if (args.length < 3) {
                System.out.println("Usage: java ChatClientMain <username> <hostname> <port>");
                return;
            }

            String username = args[0];
            String hostname = args[1];
            int port = Integer.parseInt(args[2]);

            System.out.println("Welcome, " + username + "!");
            System.out.println("Connecting to chat server at " + hostname + " on port " + port + "...");

            Registry registry;
            ChatServer server;
            try {
                registry = LocateRegistry.getRegistry(hostname, port);
                System.out.println("Looking up ChatService...");
                server = (ChatServer) registry.lookup("ChatService");
                System.out.println("Found server!");
            } catch (java.rmi.ConnectException e) {
                System.err.println("Error: Cannot connect to RMI registry at " + hostname + ":" + port);
                e.printStackTrace();
                return;
            } catch (java.rmi.NotBoundException e) {
                System.err.println("Error: ChatService not found in RMI registry");
                e.printStackTrace();
                return;
            } catch (Exception e) {
                System.err.println("Error: Unexpected issue connecting to server: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            ChatClientController client = new ChatClientController(username);
            server.join(username);
            System.out.println("Joined server successfully!");

            Timer timer = new Timer();
            final int[] lastIndex = { 0 };
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        ArrayList<String> messages = server.getMessages(username, lastIndex[0]);
                        for (String msg : messages) {
                            System.out.println(msg);
                        }
                        lastIndex[0] = lastIndex[0] + messages.size();
                    } catch (Exception e) {
                        System.err.println("Error polling messages: " + e.getMessage());
                    }
                }
            }, 0, 1000);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Type messages, 'history' to show previous messages, or 'exit' to quit:");
            while (true) {
                String msg = scanner.nextLine();
                if ("exit".equalsIgnoreCase(msg)) {
                    server.leave(username);
                    timer.cancel();
                    break;
                } else if ("history".equalsIgnoreCase(msg)) {
                    ArrayList<String> history = server.showHistory();
                    for (String str : history) {
                        System.out.println(str);
                    }
                } else {
                    try {
                        server.sendMessage(username, msg);
                    } catch (Exception e) {
                        System.err.println("Error: Unable to send message. Server might be down.");
                        break;
                    }
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error on client: " + e);
        }

    }
}
