import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.io.*;
// import java.util.Timer;
// import java.util.TimerTask;

public class ChatServerController extends UnicastRemoteObject implements ChatServer, Runnable {
    private static final long serialVersionUID = 1L;

    private Map<String, ChatClient> clients = new HashMap<>();
    private ArrayList<String> history = new ArrayList<>();
    private Map<String, Integer> clientMessageIndex = new HashMap<>();
    private File backupFile;

    @Override
    public void run() {
        try {
            ArrayList<String> messages = getMessages("username", 0);
            for (String msg : messages) {
                System.out.println(msg);
            }
        } catch (Exception e) {
            System.err.println("Error polling messages: " + e.getMessage());
        }
    }

    public ChatServerController(File f1) throws RemoteException {
        super();
        this.backupFile = f1;
    }

    public synchronized void join(String username) throws RemoteException {
        System.out.println("Client " + username + " joining...");
        clientMessageIndex.put(username, history.size());
        history.add("Server: " + username + " joined the chat!");
        backupMessages("Server", username + " joined the chat!");
    }

    public synchronized void sendMessage(String username, String msg) throws RemoteException {
        history.add(username + ": " + msg);
        backupMessages(username, msg);
    }

    public synchronized void leave(String username) throws RemoteException {
        history.add("Server: " + username + " left the chat!");
        clientMessageIndex.remove(username);
        backupMessages("Server", username + " left the chat!");
    }

    public synchronized ArrayList<String> getMessages(String username, int lastIndex) throws RemoteException {
        ArrayList<String> newMessages = new ArrayList<>();
        if (lastIndex < history.size()) {
            newMessages.addAll(history.subList(lastIndex, history.size()));
            clientMessageIndex.put(username, history.size());
        }
        return newMessages;
    }

    public ArrayList<String> showHistory() throws RemoteException {
        return history;
    }

    private void backupMessages(String sender, String msg) throws RemoteException {
        System.out.println("Backing up message...");
        try (FileWriter fw = new FileWriter(backupFile, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(sender + ": " + msg);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
