import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientController extends UnicastRemoteObject implements ChatClient {
    private static final long serialVersionUID = 1L;
    private String username;

    public ChatClientController(String username) throws RemoteException {
        super();
        this.username = username;
    }

    // Method to receive messages
    // public void receiveMessage(String msg) throws RemoteException {
    //     System.out.println(msg); // Display message in the console
    // }

    // Optionally, you could add more methods like getting user status (online,
    // offline, etc.)
}
