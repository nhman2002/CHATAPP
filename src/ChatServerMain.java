import java.rmi.registry.*;
import java.rmi.server.RMIServerSocketFactory;
// import java.rmi.server.UnicastRemoteObject;
import java.io.*;
// import java.rmi.server.UnicastRemoteObject;
// import interfaces.ChatServer;
import java.net.InetAddress;
import java.net.ServerSocket;

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

            // String hostIP = InetAddress.getLocalHost().getHostAddress(); // should print
            // 34.1.7.20
            // System.out.println("Chat Server IP:" + hostIP);
            // System.setProperty("java.rmi.server.hostname", hostIP);

            // Start or connect to RMI registry (use your port or default)
            // Registry registry = LocateRegistry.createRegistry(port, null, new RMIServerSocketFactory() {
            //     @Override
            //     public ServerSocket createServerSocket(int port) throws IOException {
            //         return new ServerSocket(port, 0, InetAddress.getByName("0.0.0.0"));
            //     }
            // });

            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("Registry bound to: " + registry);
            File f1 = new File("portnb.txt");

            ChatServerController server = new ChatServerController(f1);

            // ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(server, 0);
            // registry.rebind("ChatService", stub);
            registry.rebind("ChatService", server);

            System.out.println("Chat Server ready!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error on server: " + e);
        }
    }
}
