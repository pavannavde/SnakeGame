import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {
    int GB_width=400;
    int GB_height=400;
    int Max_Dot=1600;
    int Dot_Size=10;
    int Dots;
    int []x=new int[Max_Dot];
    int []y=new int[Max_Dot];
    int Apple_x;
    int Apple_y;
   //Images
    Image head,body,apple;
    Timer timer;
    int DELAY=150;
    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;
    boolean inGame=true;
    GameBoard()
    {
        //object for the TAdapter class to use control key
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(GB_width,GB_height));
        setBackground(Color.BLACK);
        initGame();
        loadImages();
    }
    //Initialize Game
    public void initGame()
    {
        Dots=3;
      //Initialize Snake's position
        x[0]=250;
        y[0]=250;
        for(int i=1;i<Dots;i++)
        {
            x[i]= x[0]+Dot_Size*i;
            y[i]=y[0];
        }
        //Initialize Apple's position
        LocateApple();
        timer = new Timer(DELAY,this);
        timer.start();

    }
    //Load the images from resources to Image object
    public void loadImages()
    {
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body=bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head=headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple=appleIcon.getImage();
    }
    //draw the images at Snake's and apple position
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);
    }
    //draw images at their location
    public void doDrawing(Graphics g)
    {
        if(inGame)
        {
            g.drawImage(apple, Apple_x , Apple_y ,this);
            for(int i=0;i<Dots;i++)
            {
                if(i==0)
                {
                    g.drawImage(head, x[0] , y[0] ,this);
                }
                else
                {
                    g.drawImage(body , x[i] , y[i] ,this);
                }
            }
        }
        else
        {
            Gameover(g);
            timer.stop();
        }


    }
    //Randomize the apple position
    public void LocateApple()
    {
        Apple_x=((int)(Math.random()*39))*Dot_Size;
        Apple_y=((int)(Math.random()*39))*Dot_Size;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(inGame)
        {
            checkApple();
            collision();
            move();
        }
       repaint();
    }
    //Make snake move
    public void move()
    {
        //update the position of each dot body
        for(int i=Dots-1;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection)
        {
            x[0]=x[0]-Dot_Size;
        }
        if(rightDirection)
        {
            x[0]=x[0]+Dot_Size;
        }
        if(upDirection)
        {
            y[0]=y[0]-Dot_Size;
        }
        if(downDirection)
        {
            y[0]=y[0]+Dot_Size;
        }
    }
    //make snake eat apple
    public void checkApple()
    {
        if(Apple_x==x[0] &&Apple_y==y[0])
        {
            Dots++;
            LocateApple();
        }
    }
    //check collision with body and border
    public void collision()
    {
        //collision with body check
        for(int i=0;i<Dots;i++)
        {
            if(i>4 && x[0]==x[i] && y[0]==y[i])
            {
                inGame=false;
            }
        }
        //collision with border check
        //collision with left border
        if(x[0]<0)
        {
            inGame=false;
        }
        //collision with right border
        if(x[0]>=GB_width)
        {
            inGame=false;
        }
        //collision with top border
        if(y[0]<0)
        {
            inGame=false;
        }
        //collision with bottom border
        if(y[0]>=GB_height)
        {
            inGame=false;
        }
    }
    //implements controls
    private class TAdapter extends KeyAdapter
    {
       @Override
        public void keyPressed(KeyEvent keyEvent)
       {
           int key = keyEvent.getKeyCode();
           if(key==KeyEvent.VK_LEFT && !rightDirection)
           {
               leftDirection=true;
               upDirection= false;
               downDirection=false;
           }
           if(key==KeyEvent.VK_RIGHT && !leftDirection)
           {
               rightDirection=true;
               upDirection= false;
               downDirection=false;
           }
           if(key==KeyEvent.VK_UP && !downDirection)
           {
               upDirection=true;
               leftDirection= false;
               rightDirection=false;
           }
           if(key==KeyEvent.VK_DOWN && !upDirection)
          {
           downDirection=true;
           leftDirection= false;
           rightDirection=false;
         }
       }
    }
    //Display Game over Message
    public void Gameover(Graphics g)
    {
        String msg = "Game Over";
        int Score = (Dots-3)*100;
        String Scoremsg = "Score : "+Integer.toString(Score);
        Font small = new Font("Helvetica",Font.BOLD,18);
        FontMetrics fontmetrics = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(GB_width-fontmetrics.stringWidth(msg))/2,GB_height/2);
        g.drawString(Scoremsg,(GB_width-fontmetrics.stringWidth(Scoremsg))/2,3*(GB_height/5));


    }

}
