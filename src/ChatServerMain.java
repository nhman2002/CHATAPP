import java.rmi.registry.*;
import java.io.*;

public class ChatServerMain {
    public static void main(String[] args) {

        try {
            System.setProperty("java.rmi.server.hostname", "34.1.7.20");
            // System.setProperty("java.rmi.server.hostname", "192.168.1.13");

            if (args.length < 1) {
                System.out.println("Usage: java ChatServerMain <port>");
                return;
            }

            Integer port = Integer.parseInt(args[0]);
            System.out.println("You are on port: " + port);

            System.out.println("RMI hostname: " + System.getProperty("java.rmi.server.hostname"));

            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("Registry bound to: " + registry);
            File f1 = new File("portnb.txt");

            ChatServerController server = new ChatServerController(f1);

            registry.rebind("ChatService", server);

            System.out.println("Chat Server ready!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error on server: " + e);
        }
    }
}
