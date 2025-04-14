// package interfaces;

import java.rmi.*;
import java.util.*;
public interface ChatServer extends Remote{
	void join(String username) throws RemoteException;
    void sendMessage(String username, String msg) throws RemoteException;
    void leave(String username) throws RemoteException;
    ArrayList<String> showHistory() throws RemoteException;
    ArrayList<String> getMessages(String username, int lastIndex) throws RemoteException;

    // void backupMessages(String sender, String msg)throws RemoteException;
    
}


