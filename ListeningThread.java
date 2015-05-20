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
			reply = mc.getMessage();
			if(reply.equals("end")){
				gw.stopNow();
				ongoing = false;
			}
			else if(reply.equals("show play button")){
				System.out.println("Show play button");
				if(!gw.showReadyButton){
					gw.ReadyButton.setVisible(true);
				}
				gw.showReadyButton = true;
			}
			else if(reply.startsWith("<clients> ")){
				String clientList = reply.substring(10);
				gw.setOpponentsList(clientList);
			}
			else if(reply.startsWith("<windowtitle> ")){
				String player = reply.substring(14);
				gw.clientName = player;
				System.out.println("Im " + player);
			}
		}

	}
	
	public void stopNow(){
		ongoing = false;
	}
}