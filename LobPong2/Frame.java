package LobPong2;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Frame {

	  public static void main (String[]args){
	        JFrame frame = new JFrame("LOBPONG");
	        GameDisplay gameDisplay = new GameDisplay();
	        frame.add(gameDisplay);
	        gameDisplay.setPreferredSize(new Dimension(1000,800));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	        frame.pack();
	    }
	}
