public class ListeningThread extends Thread{
	MyConnection mc;
	boolean ongoing;
	GameWindow gw;
	
	public ListeningThread(MyConnection mc){
		this.mc = mc;
		ongoing = true;
	}
	
	public void setGameWindow(GameWindow game){
		gw = game;
	}
	
	public void run(){
		String reply;
		while(ongoing){
	/*		reply = mc.getMessage();
			if(reply.equals("end")){
				gw.stopNow();
				ongoing = false;
			}
			else if(reply.startsWith("<windowtitle> ")){
				String title = reply.substring(14);
				gw.setTitle(title);
			}
			else if(reply.startsWith("<clients> ")){
				String clientList = reply.substring(10);
				gw.setClientList(clientList);
			}
			else{
				gw.displayMessages(reply);
			}*/
		}

	}
	
	public void stopNow(){
		ongoing = false;
	}
}