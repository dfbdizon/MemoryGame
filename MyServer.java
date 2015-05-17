import java.io.*;
import java.net.*;
import java.util.*;

public class MyServer {
	public ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
			
    public static void main(String args[]) {
		MyServer server = new MyServer();
		server.start();
    }
	
	public void start(){
		int clientNumber = 1;
		
		try{
			System.out.println("S: Starting server...");
			ServerSocket ssocket = new ServerSocket(8888);
			System.out.println("S: Waiting for connections...");
						
			while(true){
				Socket s = ssocket.accept();
				String clientName = "Client" + clientNumber;
				clientNumber++;
				
				ClientHandler ch = new ClientHandler(s, clientName, this);
				ch.start();
				clientList.add(ch);
	//			announce(clientName + " has connected.");
	//			updateClientList();
			}
			
		} catch(Exception e) {
            System.out.println("S: Encountered a problem.");
            e.printStackTrace();
        }
	}
	
/*	public void announce(String message){
		for(ClientHandler ch: clientList){
			ch.sendMessage(message);
		}
	}
	
	public void whisper(String recipient, String message, String sender){
		boolean sent = false;
		for(ClientHandler ch: clientList){
			if(ch.clientName.equals(recipient)){
				ch.sendMessage("[" + sender + " whispers]:" + " " + message);
				sent = true;
				
				for(ClientHandler client: clientList){
					if(client.clientName.equals(sender)){
						client.sendMessage("[You whispered to " + recipient + "]:" + " " + message);
					}
				}
			}
		}
		if(sent == false){
			for(ClientHandler ch: clientList){
				if(ch.clientName.equals(sender)){
					ch.sendMessage("Server message: User " + recipient + " is not found.");
				}
			}
		}
	}
	
	public void removeClient(ClientHandler ch){
		clientList.remove(ch);
		announce(ch.clientName + " has disconnected.");	
		updateClientList();
	}
	
	public void updateClientList(){
		String clients = "";
		for(ClientHandler ch: clientList){
			clients = clients + ch.clientName;
			if(ch.clientStatus != null){
				clients = clients + " - " + ch.clientStatus + "*";
			}
			else{
				clients = clients + "*";
			}
		}
		String updatedClientList = "<clients> " + clients;
		announce(updatedClientList);
	} */
}