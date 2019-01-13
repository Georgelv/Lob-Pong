package LobPong2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameDisplay extends JPanel implements MouseMotionListener {
    //setup utilities
    Random random = new Random();
    Timer timer;
    //setup variables
    protected int TABLE_WIDTH=1000;
    protected int lives = 3;
    protected int point = 0;
    protected int ballX = 200;
    protected int ballY = 100;
    protected int ballSize = 16;
    protected int blockWidth =150;
    protected int blockHeight =20;
    protected int blockX =400;
    protected int blockY =700;
    protected int level = 1;
    protected int ySpeed=level*3-2;
    protected int xSpeed=random.nextInt(level * 2) + 3;
    protected int physical_Timer = 0;

    //keeping the block with the mouse drag
    public void mouseDragged(MouseEvent e){
        blockX = e.getX()-75;
    }
    public void mouseMoved(MouseEvent e){}

    public GameDisplay(){
        timer = new Timer(10, new TimerCallBack());
        addMouseMotionListener(this);
        timer.start();
    }

    class TimerCallBack implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if (lives == 0){
                timer.stop();
            }
            //when the ball the left wall
            if (ballX<=0||ballX>=TABLE_WIDTH- ballSize){
                xSpeed=-xSpeed;
            }

            //when you miss the ball
            if (ballY> blockY - ballSize &&(ballX< blockX ||ballX> blockX + blockWidth - ballSize)){
                blockX =400;
                blockY =700;
                ySpeed=level*3;
                xSpeed=random.nextInt(5+level*2);
                ballX=200;
                ballY=100;
                lives--;
            }

            //when you hit the roof
            if (ballY<=0){
                ySpeed=-ySpeed;
            }

            //when you catch the ball
            if (ballY>= blockY - ballSize && ballX > blockX && ballX<= blockX + blockWidth){
                ySpeed = -ySpeed;
                point++;
            }

            //when the time has reached 60s, greater difficulty
            if (physical_Timer ==6000) {
                blockX =400;
                blockY =700;
                ySpeed=level*3;
                xSpeed=random.nextInt(5+level*2);
                ballX=200;
                ballY=100;
                level++;
                point+=5;
                physical_Timer =0;
            }
            //general operations
            ballY+=ySpeed;
            ballX+=xSpeed;
            repaint();
            physical_Timer++;

            //improve randomness to the curves
            if(physical_Timer %30==0){
                ySpeed = ySpeed + 2;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        g.setFont(new Font("Times",Font.CENTER_BASELINE,30));
        if (lives == 0){
            g.drawString("Game Over",getWidth()/2-70,getHeight()/2);
        }else{
            g.setColor(Color.RED);
            g.fillOval(ballX,ballY, ballSize, ballSize);

            for(int x = lives-1; x > 0;x--) {
                g.fillOval(x*50-30, 100, ballSize, ballSize);
            }

            g.setColor(Color.BLACK);
            g.fillRect(blockX, blockY, blockWidth, blockHeight);
            g.setColor(Color.BLACK);
            g.drawString("Your Score: " + Integer.toString(point),30,50);
            g.drawString("Time left: " + Integer.toString(60- physical_Timer /100), 800,50);
            g.drawString("Level " + level , 850, 100);
        }
    }
}
