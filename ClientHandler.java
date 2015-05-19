import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread{
	MyConnection mc;
	String clientName;
	int clientScore;
	MyServer server;
	
	public ClientHandler(Socket socket, String clientName, MyServer server){
		this.mc = new MyConnection(socket);
		this.clientName = clientName;
		this.server = server;
//		changeClientWindow(clientName);
	}
	
	public void changeClientName(String newName){
		clientName = newName;
		server.updateClientList();
	}
	
	public void changeClientScore(int score){
		clientScore = score;
		server.updateClientList();
	}
	
	public void run(){
		boolean ongoing = true;
		
		while(ongoing){
			String msg = mc.getMessage();
			
			if(msg.equals("/quit")){
				ongoing = false;
				mc.sendMessage("end");
			//	server.removeClient(this);
			}
			else{
				evaluate(msg);
			}
		}
	}
	
	public void evaluate(String mesg){
		String msg = mesg.trim();
		if(msg.startsWith("Name: ")){
			String newName = msg.substring(7);
			changeClientName(newName);
		}
		else if(msg.equals("Client: Start")){
			server.addPlayer();
		}
		else if(msg.startsWith("score: ")){
			int clientScore = msg.substring(8);
			changeClientScore(clientScore);
		}
		else{
			//server.announce(clientName + ": " + msg);
		}
	/*	else if(msg.startsWith("/changestatus ")){
			if(msg.length() == 14){
				sendMessage("Server message: No status found");
			}
			else{
				String newStatus = msg.substring(14);
				server.announce("Server message: " + clientName + " has changed status to \"" + newStatus + "\"");
				changeClientStatus(newStatus);
			}
		}*/
	}
	
	public void changeName(String msg){
		char[] newName = msg.toCharArray();
		
	}
	
	public void sendMessage(String message){
		mc.sendMessage(message);
	}

}