import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.*;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.sound.sampled.*;
import java.io.*;
import java.awt.image.BufferedImage;
@SuppressWarnings("unchecked")
	
public class GameWindow extends JFrame {
	                  
    private JFrame GameFrame;
    public JButton ReadyButton;
    private JButton StartButton;
	public JTextField textfield;
	public JPanel GamePanel;
	public JLabel waitPanel;
	public ArrayList<Card> cardList;
	public HashMap<String, ImageIcon> imageMap;
	public HashMap<Integer, Coordinates> coor = new HashMap<Integer, Coordinates>();
	public ArrayList<Card> openCards = new ArrayList<Card>(2);
	public boolean showReadyButton;
	public String clientName;
	public JLabel yourScore;

	ListeningThread lt;
	SendingThread st;
	
	int score = 0;

	//cards
	Card batman1;
	Card carnage1;
	Card catwoman1;
	Card drdoom1;
	Card goomba1;
	Card hulk1;
	Card ironman1;
	Card joker1;
	Card magneto1;
	Card maleficent1;
	Card mario1;
	Card penguin1;
	Card redskull1;
	Card spiderman1;
	Card thor1;
	Card venom1;
	Card wolverine1;
	Card wonderwoman1;
	Card batman2;
	Card carnage2;
	Card catwoman2;
	Card drdoom2;
	Card goomba2;
	Card hulk2;
	Card ironman2;
	Card joker2;
	Card magneto2;
	Card maleficent2;
	Card mario2;
	Card penguin2;
	Card redskull2;
	Card spiderman2;
	Card thor2;
	Card venom2;
	Card wolverine2;
	Card wonderwoman2;
	
    public GameWindow(ListeningThread l, SendingThread s) {
		lt = l;
		st = s;
		cardList = new ArrayList<Card>();
		imageMap = new HashMap<String, ImageIcon>();
		
		lookFeel();
		cursorSetting();
		//musicSetting();
        initComponents();
		
    }
                     
    private void initComponents() {

        GameFrame = new JFrame();
		GamePanel = new JPanel();
		GamePanel.setVisible(false);
        ReadyButton = new JButton();
		showReadyButton = false;
		
		setTitle("CS 142 MP2");
		setResizable(false);

		initCoor();
		setImages();
		setCards();

        layoutSetting();
		startPanel();

        pack();
    }   
	
	private void gamePanel(){
		setBackgroundDisplay("assets/green.jpg");
		GamePanel.setVisible(true);
		//getContentPane().add(GamePanel);
		
		int x, y;
		Card card;

		Collections.shuffle(cardList);

		ImageIcon waitBox = new ImageIcon("assets/test.png");
		waitPanel = new JLabel();
		waitPanel.setIcon(waitBox);
		waitPanel.setBounds(300, 215, 330, 240);
		getContentPane().add(waitPanel);

		//GamePanel.setLayout(null);
		//GamePanel.setBounds(0, 0, 1117, 670);
		for(int i = 0; i < cardList.size(); i++){
			card = cardList.get(i);
			x = coor.get(i+1).getX();
			y = coor.get(i+1).getY();
			card.setBounds(x, y, 100, 100);
			getContentPane().add(card);
		}
		
		JPanel sidePanel = new JPanel();	
		sidePanel.setOpaque(false);
		sidePanel.setLayout(null);
		sidePanel.setBounds(830, 10, 267, 650);
		getContentPane().add(sidePanel);
		
		
		ImageIcon scoreImg = new ImageIcon("assets/score_panel2.png");
		JLabel scorePanel = new JLabel();
		scorePanel.setIcon(scoreImg);
		scorePanel.setBounds(0, 0, 267, 650);
		
		yourScore = new JLabel("00");
		yourScore.setForeground(new Color(26, 76, 30));
		yourScore.setFont(new Font("Calibri", Font.BOLD,90));
		yourScore.setBounds(88, 90, 100, 100);
		
		sidePanel.add(yourScore);
		sidePanel.add(scorePanel);

		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try{
					Thread.sleep(5000);
				} catch (Exception e){
					e.printStackTrace();
				}
				startGame();
        	}
    	});
	}
	
	private void setImages(){
		ImageIcon batman = new ImageIcon("assets/cards/batman.png");
		imageMap.put("batman", batman);
		ImageIcon carnage = new ImageIcon("assets/cards/carnage.png");
		imageMap.put("carnage", carnage);
		ImageIcon catwoman = new ImageIcon("assets/cards/catwoman.png");
		imageMap.put("catwoman", catwoman);
		ImageIcon drdoom = new ImageIcon("assets/cards/drdoom.png");
		imageMap.put("drdoom", drdoom);
		ImageIcon goomba = new ImageIcon("assets/cards/goomba.png");
		imageMap.put("goomba", goomba);
		ImageIcon hulk = new ImageIcon("assets/cards/hulk.png");
		imageMap.put("hulk", hulk);
		ImageIcon ironman = new ImageIcon("assets/cards/ironman.png");
		imageMap.put("ironman", ironman);
		ImageIcon joker = new ImageIcon("assets/cards/joker.png");
		imageMap.put("joker", joker);
		ImageIcon magneto = new ImageIcon("assets/cards/magneto.png");
		imageMap.put("magneto", magneto);
		ImageIcon maleficent = new ImageIcon("assets/cards/maleficent.png");
		imageMap.put("maleficent", maleficent);
		ImageIcon mario = new ImageIcon("assets/cards/mario.png");
		imageMap.put("mario", mario);
		ImageIcon penguin = new ImageIcon("assets/cards/penguin.png");
		imageMap.put("penguin", penguin);
		ImageIcon redskull = new ImageIcon("assets/cards/redskull.png");
		imageMap.put("redskull", redskull);
		ImageIcon spiderman = new ImageIcon("assets/cards/spiderman.png");
		imageMap.put("spiderman", spiderman);
		ImageIcon thor = new ImageIcon("assets/cards/thor.png");
		imageMap.put("thor", thor);
		ImageIcon venom = new ImageIcon("assets/cards/venom.png");
		imageMap.put("venom", venom);
		ImageIcon wolverine = new ImageIcon("assets/cards/wolverine.png");
		imageMap.put("wolverine", wolverine);
		ImageIcon wonderwoman = new ImageIcon("assets/cards/wonderwoman.png");
		imageMap.put("wonderwoman", wonderwoman);
		ImageIcon back = new ImageIcon("assets/cards/back.png");
		imageMap.put("back", back);
	}
	
	public void setCards(){
		ImageIcon cardBack = imageMap.get("back");
		batman1 = new Card(imageMap.get("batman"), cardBack, "batman");
		batman1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	batman1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(batman1);
           		batman1.setEnabled(false);
                cardOpen();
            }
        });

		carnage1 = new Card(imageMap.get("carnage"), cardBack, "carnage");
		carnage1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	carnage1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(carnage1);
            	carnage1.setEnabled(false);
                cardOpen();
            }
        });

		catwoman1 = new Card(imageMap.get("catwoman"), cardBack, "catwoman");
		catwoman1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                catwoman1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(catwoman1);
            	catwoman1.setEnabled(false);
                cardOpen();
            }
        });

		drdoom1 = new Card(imageMap.get("drdoom"), cardBack, "drdoom");
		drdoom1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                drdoom1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(drdoom1);
                drdoom1.setEnabled(false);
                cardOpen();
            }
        });

		goomba1 = new Card(imageMap.get("goomba"), cardBack, "goomba");
		goomba1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goomba1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(goomba1);
                goomba1.setEnabled(false);
                cardOpen();
            }
        });

		hulk1 = new Card(imageMap.get("hulk"), cardBack, "hulk");
		hulk1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                hulk1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(hulk1);
                hulk1.setEnabled(false);
                cardOpen();
            }
        });

		ironman1 = new Card(imageMap.get("ironman"), cardBack, "ironman");
		ironman1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ironman1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(ironman1);
                ironman1.setEnabled(false);
                cardOpen();
            }
        });

		joker1 = new Card(imageMap.get("joker"), cardBack, "joker");
		joker1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                joker1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(joker1);
                joker1.setEnabled(false);
                cardOpen();
            }
        });

		magneto1 = new Card(imageMap.get("magneto"), cardBack, "magneto");
		magneto1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                magneto1.showFront();
            	if(openCards.size() == 2) closeCards();
            	magneto1.setEnabled(false);
            	openCards.add(magneto1);
                cardOpen();
            }
        });

		maleficent1 = new Card(imageMap.get("maleficent"), cardBack, "maleficent");
		maleficent1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                maleficent1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(maleficent1);
                maleficent1.setEnabled(false);
                cardOpen();
            }
        });

		mario1 = new Card(imageMap.get("mario"), cardBack, "mario");
		mario1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mario1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(mario1);
                mario1.setEnabled(false);
                cardOpen();
            }
        });

		penguin1 = new Card(imageMap.get("penguin"), cardBack, "penguin");
		penguin1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                penguin1.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(penguin1);
                penguin1.setEnabled(false);
                cardOpen();
            }
        });

		redskull1 = new Card(imageMap.get("redskull"), cardBack, "redskull");
		redskull1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                redskull1.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(redskull1);
                redskull1.setEnabled(false);
                cardOpen();
            }
        });

		spiderman1 = new Card(imageMap.get("spiderman"), cardBack, "spiderman");
		spiderman1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                spiderman1.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(spiderman1);
                spiderman1.setEnabled(false);
                cardOpen();
            }
        });

		thor1 = new Card(imageMap.get("thor"), cardBack, "thor");
		thor1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                thor1.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(thor1);
                thor1.setEnabled(false);
                cardOpen();
            }
        });

		venom1 = new Card(imageMap.get("venom"), cardBack, "venom");
		venom1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                venom1.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(venom1);
                venom1.setEnabled(false);
                cardOpen();
            }
        });

		wolverine1 = new Card(imageMap.get("wolverine"), cardBack, "wolverine");
		wolverine1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                wolverine1.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(wolverine1);
                wolverine1.setEnabled(false);
                cardOpen();
            }
        });

		wonderwoman1 = new Card(imageMap.get("wonderwoman"), cardBack, "wonderwoman");
		wonderwoman1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                wonderwoman1.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(wonderwoman1);
                wonderwoman1.setEnabled(false);
                cardOpen();
            }
        });

		batman2 = new Card(imageMap.get("batman"), cardBack, "batman");
		batman2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                batman2.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(batman2);
                batman2.setEnabled(false);
                cardOpen();
            }
        });

		carnage2 = new Card(imageMap.get("carnage"), cardBack, "carnage");
		carnage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                carnage2.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(carnage2);
                carnage2.setEnabled(false);
                cardOpen();
            }
        });

		catwoman2 = new Card(imageMap.get("catwoman"), cardBack, "catwoman");
		catwoman2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                catwoman2.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(catwoman2);
                catwoman2.setEnabled(false);
                cardOpen();
            }
        });
		
		drdoom2 = new Card(imageMap.get("drdoom"), cardBack, "drdoom");
		drdoom2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                drdoom2.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(drdoom2);
                drdoom2.setEnabled(false);
                cardOpen();
            }
        });

		goomba2 = new Card(imageMap.get("goomba"), cardBack, "goomba");
		goomba2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goomba2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(goomba2);
                goomba2.setEnabled(false);
                cardOpen();
            }
        });

		hulk2 = new Card(imageMap.get("hulk"), cardBack, "hulk");
		hulk2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                hulk2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(hulk2);
                hulk2.setEnabled(false);
                cardOpen();
            }
        });

		ironman2 = new Card(imageMap.get("ironman"), cardBack, "ironman");
		ironman2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ironman2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(ironman2);
                ironman2.setEnabled(false);
                cardOpen();
            }
        });

		joker2 = new Card(imageMap.get("joker"), cardBack, "joker");
		joker2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                joker2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(joker2);
                joker2.setEnabled(false);
                cardOpen();
            }
        });

		magneto2 = new Card(imageMap.get("magneto"), cardBack, "magneto");
		magneto2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                magneto2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(magneto2);
                magneto2.setEnabled(false);
                cardOpen();
            }
        });

		maleficent2 = new Card(imageMap.get("maleficent"), cardBack, "maleficent");
		maleficent2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                maleficent2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(maleficent2);
                maleficent2.setEnabled(false);
                cardOpen();
            }
        });

		mario2 = new Card(imageMap.get("mario"), cardBack, "mario");
		mario2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mario2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(mario2);
                mario2.setEnabled(false);
                cardOpen();
            }
        });

		penguin2 = new Card(imageMap.get("penguin"), cardBack, "penguin");
		penguin2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                penguin2.showFront();
            	if(openCards.size() == 2) closeCards();
                openCards.add(penguin2);
                penguin2.setEnabled(false);
                cardOpen();
            }
        });

		redskull2 = new Card(imageMap.get("redskull"), cardBack, "redskull");
		redskull2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                redskull2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(redskull2);
                redskull2.setEnabled(false);
                cardOpen();
            }
        });

		spiderman2 = new Card(imageMap.get("spiderman"), cardBack, "spiderman");
		spiderman2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                spiderman2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(spiderman2);
                spiderman2.setEnabled(false);
                cardOpen();
            }
        });

		thor2 = new Card(imageMap.get("thor"), cardBack, "thor");
		thor2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                thor2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(thor2);
                thor2.setEnabled(false);
                cardOpen();
            }
        });

		venom2 = new Card(imageMap.get("venom"), cardBack, "venom");
		venom2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                venom2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(venom2);
                venom2.setEnabled(false);
                cardOpen();
            }
        });

		wolverine2 = new Card(imageMap.get("wolverine"), cardBack, "wolverine");
		wolverine2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                wolverine2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(wolverine2);
                wolverine2.setEnabled(false);
                cardOpen();
            }
        });

		wonderwoman2 = new Card(imageMap.get("wonderwoman"), cardBack, "wonderwoman");
		wonderwoman2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                wonderwoman2.showFront();
            	if(openCards.size() == 2) closeCards();
            	openCards.add(wonderwoman2);
                wonderwoman2.setEnabled(false);
                cardOpen();
            }
        });

		cardList.add(batman1);
		cardList.add(carnage1);
		cardList.add(catwoman1);
		cardList.add(drdoom1);
		cardList.add(goomba1);
		cardList.add(hulk1);
		cardList.add(ironman1);
		cardList.add(joker1);
		cardList.add(magneto1);
		cardList.add(maleficent1);
		cardList.add(mario1);
		cardList.add(penguin1);
		cardList.add(redskull1);
		cardList.add(spiderman1);
		cardList.add(thor1);
		cardList.add(venom1);
		cardList.add(wolverine1);
		cardList.add(wonderwoman1);
		cardList.add(batman2);
		cardList.add(carnage2);
		cardList.add(catwoman2);
		cardList.add(drdoom2);
		cardList.add(goomba2);
		cardList.add(hulk2);
		cardList.add(ironman2);
		cardList.add(joker2);
		cardList.add(magneto2);
		cardList.add(maleficent2);
		cardList.add(mario2);
		cardList.add(penguin2);
		cardList.add(redskull2);
		cardList.add(spiderman2);
		cardList.add(thor2);
		cardList.add(venom2);
		cardList.add(wolverine2);
		cardList.add(wonderwoman2);		
	}
	
	public void startPanel(){
	    StartButton = new JButton();
		setBackgroundDisplay("assets/bg.png");
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
	}
	
	public void readyPanel(){
		ReadyButton = new JButton();
		setBackgroundDisplay("assets/green.jpg");
		Icon rb=new ImageIcon("assets/helpbutton.png");
		ReadyButton.setIcon(rb);
		ReadyButton.setBorder(BorderFactory.createEmptyBorder());
		ReadyButton.setContentAreaFilled(false);
		getContentPane().add(ReadyButton);
        ReadyButton.setBounds(600, 310, 140, 70);
		ReadyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ReadyButtonActionPerformed(evt);
            }
        });
		if(showReadyButton){
			ReadyButton.setVisible(true);
		}
		else{
			ReadyButton.setVisible(true);
		}
		textfield = new JTextField();
		textfield.setFont(new Font("Arial", Font.BOLD,30));
		textfield.setText("Enter your name!");
		textfield.setBounds(200, 310, 400, 70);
		getContentPane().add(textfield);
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
			URL url = this.getClass().getClassLoader().getResource("assets/music/starwars.wav");
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
		System.out.println("Start button pressed");
		StartButton.setVisible(false);
		st.sendMessage("Client: Start");
		readyPanel();
		
    }  

	private void ReadyButtonActionPerformed(ActionEvent evt) {                                            
        // TODO add your handling code here:
		//let game panel be visible
		clientName = textfield.getText().trim();
		setTitle("CS 145 MP 2 - " + clientName);
		st.sendMessage("Name: " + clientName);
		ReadyButton.setVisible(false);
		textfield.setVisible(false);
		gamePanel();
		
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

        setSize(new Dimension(1000, 400));
	}

	public void cardOpen(){
		repaint();
		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            if(openCards.size() == 2){
					if(openCards.get(0).type.equals(openCards.get(1).type)){
						try{
			        		Thread.sleep(500);
			        	} catch(Exception e){
			        		e.printStackTrace();
			        	}
						score += 2;
						System.out.println("score: " + score);
						openCards.get(0).setVisible(false);
						openCards.get(1).setVisible(false);
						st.sendMessage("score: " + score);
						
						String scoreString = String.valueOf(score);
						if(scoreString.length() == 1){
							scoreString = "0" + scoreString;
						}
						yourScore.setText(scoreString);
						repaint();
					}
				}
        	}
    	});
	}

	public void closeCards(){ 
		openCards.get(0).showBack();
		openCards.get(1).showBack();
		openCards.get(0).setEnabled(true);
		openCards.get(1).setEnabled(true);
		openCards.remove(0);
		openCards.remove(0);
		openCards = new ArrayList<Card>(2);
		repaint();

	}

	public void startGame(){
		for(Card card: cardList){
			card.enableButton();
		}
		waitPanel.setVisible(false);
		repaint();
	}
	
	public void initCoor(){
		/* 1st Row */
		coor.put(1, new Coordinates(90, 10));
		coor.put(2, new Coordinates(200, 10));
		coor.put(3, new Coordinates(310, 10));
		coor.put(4, new Coordinates(420, 10));
		coor.put(5, new Coordinates(530, 10));
		coor.put(6, new Coordinates(640, 10));

		/* 2nd Row */
		coor.put(7, new Coordinates(90, 120));
		coor.put(8, new Coordinates(200, 120));
		coor.put(9, new Coordinates(310, 120));
		coor.put(10, new Coordinates(420, 120));
		coor.put(11, new Coordinates(530, 120));
		coor.put(12, new Coordinates(640, 120));

		/* 3rd Row */
		coor.put(13, new Coordinates(90, 230));
		coor.put(14, new Coordinates(200, 230));
		coor.put(15, new Coordinates(310, 230));
		coor.put(16, new Coordinates(420, 230));
		coor.put(17, new Coordinates(530, 230));
		coor.put(18, new Coordinates(640, 230));

		/* 4th Row */
		coor.put(19, new Coordinates(90, 340));
		coor.put(20, new Coordinates(200, 340));
		coor.put(21, new Coordinates(310, 340));
		coor.put(22, new Coordinates(420, 340));
		coor.put(23, new Coordinates(530, 340));
		coor.put(24, new Coordinates(640, 340));

		/* 5th Row */
		coor.put(25, new Coordinates(90, 450));
		coor.put(26, new Coordinates(200, 450));
		coor.put(27, new Coordinates(310, 450));
		coor.put(28, new Coordinates(420, 450));
		coor.put(29, new Coordinates(530, 450));
		coor.put(30, new Coordinates(640, 450));

		/* 6th Row */
		coor.put(31, new Coordinates(90, 560));
		coor.put(32, new Coordinates(200, 560));
		coor.put(33, new Coordinates(310, 560));
		coor.put(34, new Coordinates(420, 560));
		coor.put(35, new Coordinates(530, 560));
		coor.put(36, new Coordinates(640, 560));
	}
}
