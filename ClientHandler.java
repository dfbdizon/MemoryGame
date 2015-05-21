import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread{
	MyConnection mc;
	String clientName;
	int clientScore;
	MyServer server;
	boolean isReady;
	
	public ClientHandler(Socket socket, String clientName, MyServer server){
		this.mc = new MyConnection(socket);
		this.clientName = clientName;
		this.server = server;
		clientScore = 0;
		changeClientWindow(clientName);
		isReady = false;
	}
	
	public void changeClientName(String newName){
		clientName = newName;
		server.updateClientList();
	}
	
	public void changeClientScore(int score){
		clientScore = score;
		server.updateClientList();
	}
	
	public void changeClientWindow(String client){
		mc.sendMessage("<windowtitle> " + client);
	}
	
	public void run(){
		boolean ongoing = true;
		
		while(ongoing){
			String msg = mc.getMessage();
			
			if(msg.equals("/quit")){
				ongoing = false;
				mc.sendMessage("end");
				server.removeClient(this);
			}
			else{
				evaluate(msg);
			}
		}
	}
	
	public void evaluate(String mesg){
		String msg = mesg.trim();
		if(msg.startsWith("Name: ")){
			String newName = msg.substring(6);
			changeClientName(newName);
		}
		else if(msg.equals("Client: Start")){
			server.addPlayer();
		}
		else if(msg.equals("Client: Ready")){
			isReady = true;
			server.updateClientList();
		}
		else if(msg.equals("Client: Not Ready")){
			isReady = false;
			clientScore = 0;
		}
		else if(msg.startsWith("score: ")){
			int clientScore = Integer.valueOf(msg.substring(7));
			changeClientScore(clientScore);
		}
		else if(msg.startsWith("winner: ")){
			server.announce(msg);
		}
	}
	
	public void changeName(String msg){
		char[] newName = msg.toCharArray();
		
	}
	
	public void sendMessage(String message){
		mc.sendMessage(message);
	}

}