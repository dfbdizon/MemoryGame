import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.*;
import java.io.*;

class Card extends JButton{
	String type;
	ImageIcon backImage;
	ImageIcon frontImage;
	
	public Card(ImageIcon front, ImageIcon back, String type){
		backImage = back;
		frontImage = front;
		this.type = type;
		
		setIcon(backImage);
		setMargin(new Insets(0, 0, 0, 0));
		setIconTextGap(0);
		setBorderPainted(false);
		setBorder(null);
		setText(null);
		setSize(backImage.getImage().getWidth(null), backImage.getImage().getHeight(null));
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);		
		setVisible(true);
	}
	
	public void showFront(){
		setIcon(frontImage);
	}
	
	public void showBack(){
		setIcon(backImage);
	}

}