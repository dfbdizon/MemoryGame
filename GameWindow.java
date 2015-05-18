import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.*;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.sound.sampled.*;
import java.io.*;
@SuppressWarnings("unchecked")
	
public class GameWindow extends JFrame {
	                  
    private JFrame GameFrame;
    private JButton HelpButton;
    private JButton StartButton;
	private JPanel GamePanel;
	public ArrayList<Card> cardButtons;;
	public HashMap<String, ImageIcon> imageMap;
	
	ListeningThread lt;
	SendingThread st;
	
    public GameWindow(ListeningThread l, SendingThread s) {
		lt = l;
		st = s;
		cardButtons = new ArrayList<Card>();
		imageMap = new HashMap<String, ImageIcon>();
		
		lookFeel();
		cursorSetting();
		musicSetting();
        initComponents();
		
    }
                     
    private void initComponents() {

        GameFrame = new JFrame();
        StartButton = new JButton();
        HelpButton = new JButton();
		
		setTitle("CS 142 MP2");
		setBackgroundDisplay("assets/bg.png");
		setResizable(false);

        layoutSetting();
		startPanel();

        pack();
    }   
	
	private void gamePanel(){
		GamePanel = new JPanel();
		getContentPane().add(GamePanel);
		setBackgroundDisplay("assets/green.jpg");
		
		setImages();
		setCards();
	}
	
	private void setImages(){
		ImageIcon batman = new ImageIcon("assets/batman.png");
		imageMap.put("batman", batman);
		ImageIcon carnage = new ImageIcon("assets/carnage.png");
		imageMap.put("carnage", carnage);
		ImageIcon catwoman = new ImageIcon("assets/catwoman.png");
		imageMap.put("catwoman", catwoman);
		ImageIcon drdoom = new ImageIcon("assets/drdoom.png");
		imageMap.put("drdoom", drdoom);
		ImageIcon goomba = new ImageIcon("assets/goomba.png");
		imageMap.put("goomba", goomba);
		ImageIcon hulk = new ImageIcon("assets/hulk.png");
		imageMap.put("hulk", hulk);
		ImageIcon ironman = new ImageIcon("assets/ironman.png");
		imageMap.put("ironman", ironman);
		ImageIcon joker = new ImageIcon("assets/joker.png");
		imageMap.put("joker", joker);
		ImageIcon magneto = new ImageIcon("assets/magneto.png");
		imageMap.put("magneto", magneto);
		ImageIcon maleficent = new ImageIcon("assets/maleficent.png");
		imageMap.put("maleficent", maleficent);
		ImageIcon mario = new ImageIcon("assets/mario.png");
		imageMap.put("mario", mario);
		ImageIcon penguin = new ImageIcon("assets/penguin.png");
		imageMap.put("penguin", penguin);
		ImageIcon redskull = new ImageIcon("assets/redskull.png");
		imageMap.put("redskull", redskull);
		ImageIcon spiderman = new ImageIcon("assets/spiderman.png");
		imageMap.put("spiderman", spiderman);
		ImageIcon thor = new ImageIcon("assets/thor.png");
		imageMap.put("thor", thor);
		ImageIcon venom = new ImageIcon("assets/venom.png");
		imageMap.put("venom", venom);
		ImageIcon wolverine = new ImageIcon("assets/wolverine.png");
		imageMap.put("wolverine", wolverine);
		ImageIcon wonderwoman = new ImageIcon("assets/wonderwoman.png");
		imageMap.put("wonderwoman", wonderwoman);
	}
	
	public void setCards(){
	}
	
	public void startPanel(){
		Icon sb=new ImageIcon("assets/startbutton.png");
		StartButton.setIcon(sb);
		StartButton.setBorder(BorderFactory.createEmptyBorder());
		StartButton.setContentAreaFilled(false);
		getContentPane().add(StartButton);
        StartButton.setBounds(300, 310, 140, 70);
        StartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });

		Icon hb=new ImageIcon("assets/helpbutton.png");
		HelpButton.setIcon(hb);
		HelpButton.setBorder(BorderFactory.createEmptyBorder());
		HelpButton.setContentAreaFilled(false);
		getContentPane().add(HelpButton);
        HelpButton.setBounds(500, 310, 140, 70);
		HelpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                HelpButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(null);
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                st.stopNow();
				lt.stopNow();
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
	}
	

	public void cursorSetting(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursorImage = toolkit.getImage("assets/cursor.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = toolkit.createCustomCursor(cursorImage, cursorHotSpot, "Cursor");
		setCursor(customCursor);
	}	
	
	public void musicSetting(){
		try
		{
			URL url = this.getClass().getClassLoader().getResource("assets/starwars.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
		 
			clip.open(audioIn);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e){
			System.out.println("Failed to load music");
			e.printStackTrace();
		}
	}

    private void StartButtonActionPerformed(ActionEvent evt) {                                            
        // TODO add your handling code here:
		//let game panel be visible
		StartButton.setVisible(false);
		HelpButton.setVisible(false);
		gamePanel();
    }  

	private void HelpButtonActionPerformed(ActionEvent evt) {                                            
        // TODO add your handling code here:
		//let help panel be visible
    }  
	
	public void stopNow(){
		st.stopNow();
		lt.stopNow();
		dispatchEvent(new WindowEvent(lt.gw, WindowEvent.WINDOW_CLOSING));
	}  
	
	public void setBackgroundDisplay(String filename){
		try{
			JLabel bg = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource(filename))));
			setContentPane(bg);
		}
		catch(Exception e){
			System.out.println("Failed to load background");
			e.printStackTrace();
		}
	}
	
	public void lookFeel(){
		try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	public void layoutSetting(){
		GroupLayout GameFrameLayout = new GroupLayout(GameFrame.getContentPane());
        GameFrame.getContentPane().setLayout(GameFrameLayout);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}
