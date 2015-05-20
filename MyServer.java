import java.io.*;
import java.net.*;
import java.util.*;

public class MyServer {
	public ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
	public int numberOfPlayers;
	public int numberOfReadyPlayers;
	public int numberOfSessionPlayers;
			
    public static void main(String args[]) {
		MyServer server = new MyServer();
		server.start();
    }
	
	public void start(){
		int clientNumber = 1;
		numberOfPlayers = 0;
		numberOfReadyPlayers = 0;
		
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
			}
			
		} catch(Exception e) {
            System.out.println("S: Encountered a problem.");
            e.printStackTrace();
        }
	}
	
	public void announce(String message){
		for(ClientHandler ch: clientList){
			ch.sendMessage(message);
		}
	}
	
	public void addPlayer(){
		numberOfPlayers++;
		
		if(numberOfPlayers >= 2){
			announce("show play button");
		}
	}
	
	public void addReadyPlayer(){
		numberOfReadyPlayers++;
		
		if(numberOfReadyPlayers == 1){
			setSessionPlayers();
		}
		
		updateClientList();
	}
	
	public void setSessionPlayers(){
		numberOfSessionPlayers = numberOfPlayers;
	}
	
	public void removeClient(ClientHandler ch){
		clientList.remove(ch);
		announce(ch.clientName + " has disconnected.");	
		updateClientList();
		numberOfPlayers--;
		numberOfReadyPlayers--;
	}
	
	public void updateClientList(){
		String clients = "";
		int counter = 1;
		for(ClientHandler ch: clientList){
			if(ch.isReady){
				clients = clients + ch.clientName + " - " + ch.clientScore;
				if(counter < clientList.size()){
					clients = clients + "-new-";
				}
			}
			counter++;
		}
		String updatedClientList = "<clients> " + clients;
		announce(updatedClientList);
	} 
}