package LobPong2;
/*
 * Name: Zhaozhou Lyu
 * NetID: zlyu2
 * Assignment number: Project04
 * Lab: TR6:15PM
 * 
 * I did not collaborate with anyone on this assignment.
 */
/**
 * This class contains all the code for operating the game
 * @author georgelyu
 *
 */
import java.awt.*;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.*; 
public class LobPong {
	private JFrame f=new JFrame("Lob Pong");
	Random rand=new Random();
 	private final int TABLE_WIDTH=1000;
 	private final int TABLE_HEIGHT=1000;
 	private final int RACKET_WIDTH=150;
 	private final int RACKET_HEIGHT=20;
 	private int racketX=400;
 	private int racketY=700;
 	private final int BALL_SIZE=16;
 	private int level = 1;
 	private int ySpeed=level*3;
 	private int xSpeed=rand.nextInt(level*2)+3;
 	private int ballX=rand.nextInt(200);
 	private int ballY=100;
 	private MyCanvas tableArea=new MyCanvas();
 	Timer timer;
 	private int lives = 3;
 	private int point = 0;
 	private int time_times=0;
 	
	public void init(){
		tableArea.setPreferredSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
		f.add(tableArea);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		KeyAdapter keyProcessor=new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode()==KeyEvent.VK_LEFT){
					if(racketX>0)racketX-=40;
				}
				if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
					if(racketX<TABLE_WIDTH-RACKET_WIDTH)racketX+=40;
				}
   			}
		};
		f.addKeyListener(keyProcessor);
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ballX<=0||ballX>=TABLE_WIDTH-BALL_SIZE){
					xSpeed=-xSpeed;
					}
				if(ballY>racketY-BALL_SIZE&&(ballX<racketX||ballX>racketX+RACKET_WIDTH-BALL_SIZE)){
					racketX=400;
					racketY=700;
					ySpeed=level*3;
					xSpeed=rand.nextInt(5+level*2);
  					ballX=rand.nextInt(200);
				    ballY=100;
  					lives--;
   					tableArea.repaint();
   				
   					}else if(ballY<=0||(ballY>=racketY-BALL_SIZE&&ballX>racketX&&ballX<=racketX+RACKET_WIDTH)){
   						ySpeed=-ySpeed;
   						if((ballY>=racketY-BALL_SIZE&&ballX>racketX&&ballX<=racketX+RACKET_WIDTH)) point++;
   					}else if(lives == 0) {
   							timer.stop();
   					}else if(time_times==6000) {
   						racketX=400;
		   				racketY=700;
		   				ySpeed=level*3;
		   				xSpeed=rand.nextInt(5+level*2);
	       				ballX=rand.nextInt(200);
		   				ballY=100;
		   				level++;
		   				time_times=0;
	       				tableArea.repaint();
   					}
   				ballY+=ySpeed;
   				ballX+=xSpeed;
   				tableArea.repaint();
   				time_times++;
   				if(time_times%30==0){
      					ySpeed = ySpeed + 1;
          		}		
			}
		};
		timer=new Timer(10,taskPerformer);
		timer.start();
		f.pack();
		f.setVisible(true);
	}
 class MyCanvas extends Canvas{
  public void paint(Graphics g){
   if(lives == 0){
    g.setColor(Color.BLACK);
    g.setFont(new Font("Times",Font.BOLD,30));
    g.drawString("You Lose",400,500);
   }else{
    g.setColor(Color.RED);
    g.fillOval(ballX,ballY,BALL_SIZE,BALL_SIZE);
    for(int x = lives-1; x > 0;x--) {
    	g.fillOval(x*50-30, 100, BALL_SIZE,BALL_SIZE);
    }
    g.setColor(Color.BLUE);
    g.fillRect(racketX,racketY,RACKET_WIDTH,RACKET_HEIGHT);
    g.setColor(Color.BLACK);
    g.setFont(new Font("Times",Font.BOLD,30));
    g.drawString(Integer.toString(point),50,50);
    g.drawString(Integer.toString(60-time_times/100), 900,50);
    g.drawString("Level " + level , 900, 100);
   }
  }
 }
 public static void main(String[] args) 
 {
  new LobPong().init();
 }
} 