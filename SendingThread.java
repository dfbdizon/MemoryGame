public class SendingThread extends Thread{
	MyConnection mc;
	boolean ongoing;
	public SendingThread(MyConnection mc){
		this.mc = mc;
		ongoing = true;
	}
	
	public void run(){	
		while(ongoing){
		}
	}
	
	public void sendMessage(String msg){
		mc.sendMessage(msg);
		if(msg.equals("/quit")){
			ongoing = false;
		}
	}
	
	public void stopNow(){
		sendMessage("/quit");
		ongoing = false;
	}
}