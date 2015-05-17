/*
CS 140 HTXY
Joni Marie Jimenez
Roanna Ellise David
Jannieca Camba
Mary Agnes Jardeleza
*/

import java.awt.geom.*;
import java.awt.*;
import javax.swing.*;  
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;

public class GameCursor extends JFrame{			
	int x;
	int y;
	Image image;
	int wOffset = (69/2);	
	int hOffset = (47/2);
	
	public GameCursor(String filename){
		this.image = Toolkit.getDefaultToolkit().getImage("." + File.separatorChar + "images" + File.separatorChar + filename );
	}
}