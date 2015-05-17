import java.io.*;
import java.net.*;

public class MyConnection {
	Socket mySocket;
	OutputStream os;
	OutputStreamWriter osw;
	PrintWriter out;
	InputStream is;
	InputStreamReader isr;
	BufferedReader in;
	
	public MyConnection(Socket s){
		mySocket = s;
		try{
			os = mySocket.getOutputStream();
			osw = new OutputStreamWriter(os);
			out = new PrintWriter(osw);
			is = mySocket.getInputStream();
			isr = new InputStreamReader(is);
			in = new BufferedReader(isr);
		} catch (Exception e) {
			System.out.println("Encountered a problem.");
			e.printStackTrace();
		}
	}
   
	public boolean sendMessage(String msg){
		try{
			out.println(msg);
			out.flush();	
			
		} catch (Exception e) {
			System.out.println("Encountered a problem.");
			e.printStackTrace();
			return false;
		}
		return true;
     }
	 
	public String getMessage(){
		String msg = "";
		
		try{
			msg = in.readLine();

		} catch (Exception e) {
			System.out.println("Encountered a problem.");
			e.printStackTrace();
		}
		return msg;
	}
}
	 