import java.io.*;
import java.net.*;
import java.util.*;

public class MyClient {
	
	public static void main(String args[]) {
		try{
			boolean ongoing = true;
			Socket s = new Socket("127.0.0.1", 8888);
			MyConnection mc = new MyConnection(s);
			ListeningThread lt = new ListeningThread(mc);
			SendingThread st = new SendingThread(mc);
			final GameWindow gamewindow = new GameWindow(lt, st);
			lt.setGameWindow(gamewindow);
			lt.start();
			st.start();
						
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					gamewindow.setVisible(true);
				}
			});
									
			while(ongoing){
				if(!st.isAlive()){
					ongoing = false;
				}
			}
			
		} catch(Exception e) {
            System.out.println("Encountered a problem.");
            e.printStackTrace();
        }
	}

}