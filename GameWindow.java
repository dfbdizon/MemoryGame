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
	
	ListeningThread lt;
	SendingThread st;
	
    public GameWindow(ListeningThread l, SendingThread s) {
		lt = l;
		st = s;
		
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
	
	public void gamePanel(){
		GamePanel = new JPanel();
		getContentPane().add(GamePanel);
		setBackgroundDisplay("assets/green.jpg");
	}
	
	public void startPanel(){
		Icon sb=new ImageIcon("assets/startbutton.png");
		StartButton.setIcon(sb);
		StartButton.setBorder(BorderFactory.createEmptyBorder());
		StartButton.setContentAreaFilled(false);
		getContentPane().add(StartButton);
        StartButton.setBounds(150, 190, 140, 70);
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
        HelpButton.setBounds(150, 310, 140, 70);
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
        GameFrameLayout.setHorizontalGroup(
            GameFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        GameFrameLayout.setVerticalGroup(
            GameFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}
