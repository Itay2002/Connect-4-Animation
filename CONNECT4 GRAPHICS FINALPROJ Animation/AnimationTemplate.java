import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;
import java.util.ArrayList;
/**
 * Write a description of class AnimationTemplate here.

 * @author (your name)
 * @version (a version number or a date)
 */
public class AnimationTemplate extends Applet implements ActionListener,MouseListener
{
    /****************************************************
     * The following code should NOT be changed for any
     * reason. Doing so will prevent the app from running
     ****************************************************/
    public boolean debugging;
    public void debug(int width, int height) {
        Applet applet = this;
        debugging = true;
        String windowTitle = applet.getClass().getName();
        JFrame frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height + 20);
        frame.setBackground(Color.BLACK);
        applet.setSize(width, height+frame.getY());
        frame.add(applet);
        applet.init();      // simulate browser call(1)
        applet.start();      // simulate browser call(2)
        frame.setVisible(true);
    }  

    public boolean debugging() {
        return debugging;

    }

    public void actionPerformed(ActionEvent ae) {
        repaint(); 
    }

    /** All code changes go below this point **/
    /** Variables declared here can be used in ALL following methods.
     * Generally, only those variables needed for animation purposes
     * should go here.
     */
    private Image dbImage;
    private Graphics dbg;//resolution flickering fix variables

    private int x,y;       //shape starting point
    private int pInc,yInc; //controls how much to move shape
    private Timer timer;   //used to force redraw 
    private int ctr;       //other needed variables
    private int numScreen;     //this is for switching screens\
    private int nemoX;
    private int nemoY;
    private int nemoWidth;
    private int nemoLength;
    private int discP;
    private int [] colNums;
    private boolean valid; //makes sure valid click
    private boolean fullCol;//boolean variable a full column
    private boolean start;// a variable that makes sure game has started
    private int colClicked;// an int variable to keep track of what column has been selected
    private ArrayList<Disc> discList;
    /**
     * This is always called before the first time that the start 
     * method is called. Here we set up the timer and initiailize 
     * the starting point for our moving shape
     */
    public void init()
    { 

        //added these variables for mousepressed boundaries used later when user clicks
        addMouseListener(this);

        //new var
        discP=210;
        colNums=new int[] {1,2,3,4,5,6,7};

        //numScreen is for switching the screens
        numScreen=0;
        //
        start=false;
        discList= new ArrayList<Disc>();

        //initialize other needed variables
        ctr=1;
        // define the timer and start it
        timer = new Timer(200,this); // 10 ms. Larger numbers = slower animation
        timer.start();
        //setup background color
        //I made this light blue as the ocean background 
        Color lightb=new Color(65, 232, 244);

        setBackground(lightb); //light blue background
    }

    public void paint(Graphics g){
        dbImage= createImage(getWidth(),getHeight());
        dbg=dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage,0,0,this);
    }

    public void paintComponent(Graphics g)
    {   
        //title

        if(numScreen==0){
            Color maincol=new Color(209, 136, 20);
            g.setColor(maincol);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.PLAIN,35));
            g.drawString("Welcome to Connect 4",140,30);
            g.drawString("By:Itay G",140,80);
            //p+=pInc;

            for(int row=200;row<=450;row+=50){
                for(int col=150;col<=450;col+=50){
                    g.drawRect(col,row,50,50);
                }

            }
            //this above creates the gameboard
            g.setColor(Color.BLACK);
            for(int colNum=150;colNum<=450;colNum+=50){
                g.drawRect(colNum,140,50,60);

            }
            //this above creates column slots
            g.setColor(Color.RED);
            g.setFont(new Font("Arial",Font.PLAIN,30));
            int spacing=0;
            for(int colNum=1;colNum<=7;colNum++){

                g.drawString(colNum+"",170+spacing,165);
                spacing+=50;
            }
            for(int pos=0;pos<=300;pos+=100){
                g.setColor(Color.YELLOW);
                g.fillOval(160+pos,discP,30,30);
            }
            for(int pos=50;pos<=300;pos+=100){
                g.setColor(Color.RED);
                g.fillOval(160+pos,discP,30,30);
            }
            discP+=50;
            if(discP>=500){
                discP=210;  
            }
            //this below prints user and team color
            g.setFont(new Font("Arial",Font.PLAIN,25));
            g.setColor(Color.BLACK);
            g.drawString("Player 1:"+Player1.getName()+"("+Player1.getColor()+")",240,525);
            g.drawString("VS.",240,550);
            g.drawString("Player 2:"+Player2.getName()+"("+Player2.getColor()+")",240,575);
            //this below creates the start button
            g.setColor(Color.RED);
            g.fillRect(295,98,60,40);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial",Font.PLAIN,15));
            g.drawString("START!",300,118);
        }

        else if(numScreen%1==0&&numScreen>0){
            Color maincol=new Color(209, 136, 20);
            g.setColor(maincol);
            g.setFont(new Font("Arial",Font.PLAIN,40));
            g.drawString("Player 1's Turn!",140,40);
            //This is the board
            for(int row=200;row<=450;row+=50){
                for(int col=150;col<=450;col+=50){
                    g.drawRect(col,row,50,50);
                }

            }
            //this above creates the gameboard
            g.setColor(Color.BLACK);
            for(int colNum=150;colNum<=450;colNum+=50){
                g.drawRect(colNum,140,50,60);

            }
            //this above creates column slots
            g.setColor(Color.RED);
            g.setFont(new Font("Arial",Font.PLAIN,30));
            int spacing=0;
            for(int colNum=1;colNum<=7;colNum++){

                g.drawString(colNum+"",170+spacing,165);
                spacing+=50;
            }
            g.drawString("Select Board Position(column)",140,70);
            if(colClicked>0){
                if(Player1.getColor().equals("red")){
                    g.setColor(Color.RED);
                    g.fillOval(160+((colClicked-1)*50),discP,30,30);
                    discList.add(new Disc(colClicked,Board.findRow(colClicked-1),1));
                }
                else{
                    g.setColor(Color.YELLOW);
                    g.fillOval(160+((colClicked-1)*50),discP,30,30);
                    discList.add(new Disc(colClicked,Board.findRow(colClicked-1),1));
                }

                int count=Board.findRow(colClicked-1);
                while(count>=0){
                    discP+=50;
                    count--;
                }
                

                //this while above moves the disc down to the proper row
                //numScreen++;
            }
        }
        else if(numScreen%2==0){
            //Player 2 Screen
            Color maincol=new Color(209, 136, 20);
            g.setColor(maincol);
            g.setFont(new Font("Arial",Font.PLAIN,40));
            g.drawString("Player 2's turn!",100,50);
            //
            g.drawString("Column is full. Select differnet column",100,50);
            g.drawString("Select Board Position(column)",100,70);
            g.drawString("click again",100,90);

        }
        else if(fullCol){
            g.drawString("Column is full. Select differnet column",100,50);
            g.drawString("Select Board Position(column)",100,70);
            g.drawString("click again",100,90);    
        }
    }

    public void mousePressed(MouseEvent me)
    {
        int mouseX=me.getX();  //this is to get nemo's position
        int mouseY=me.getY();
        // this if statement creates rectangular boundaries for the mouse press to start the
        //game
        //if(mouseX>=nemoX && mouseX<=nemoX+nemoLength&& mouseY>=nemoY && mouseY<=
        //nemoY+nemoWidth){
        //    numScreen=1;   //this will change to the next screen
        //}

        valid=false;
        if(mouseX>=295&& mouseX<=355&&mouseY>=98&mouseY<=138&& numScreen==0){
            numScreen++;    
        }
        //this above is the start button
        //this below are column user clicks
        if(mouseX>=150&& mouseX<=200&&mouseY>=140&mouseY<=200 && numScreen%1==0 && numScreen>0){
            valid=true;
            if(Board.getBoard()[0][0]!=0){
                fullCol=true;
            }
            Board.setBoard(Board.findRow(0),0,1); 
            colClicked=1;
            numScreen++;
        }
        else if(mouseX>=200&& mouseX<=250&&mouseY>=140&mouseY<=200 && numScreen%1==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][1]!=0){
                fullCol=true;
            }
            Board.setBoard(Board.findRow(1),1,1);
            colClicked=2;
            numScreen++;
        }
        else if(mouseX>=250&& mouseX<=300&&mouseY>=140&mouseY<=200 &&  numScreen%1==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][2]!=0){
                fullCol=true;
            }
            Board.setBoard(Board.findRow(2),2,1);

            colClicked=3;
            numScreen++;
        }
        else if(mouseX>=300&& mouseX<=350&&mouseY>=140&mouseY<=200 &&  numScreen%1==0&& numScreen>0){
            valid=true;

            if(Board.getBoard()[0][3]!=0){
                fullCol=true;    
            }
            Board.setBoard(Board.findRow(3),3,1);
            colClicked=4;
            numScreen++;
        }
        else if(mouseX>=350&& mouseX<=400&&mouseY>=140&mouseY<=200 &&  numScreen%1==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][4]!=0){
                fullCol=true;       
            }
            Board.setBoard(Board.findRow(4),4,1);   
            colClicked=5;
            numScreen++;
        }
        else if(mouseX>=400&& mouseX<=450&&mouseY>=140&mouseY<=200 &&  numScreen%1==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][5]!=0){
                fullCol=true;        
            }
            Board.setBoard(Board.findRow(5),5,1);
            colClicked=6;
            numScreen++;
        }
        else if(mouseX>=450&& mouseX<=500&&mouseY>=140&mouseY<=200&& numScreen%1==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][6]!=0){
                fullCol=true;        
            }
            Board.setBoard(Board.findRow(6),colNums[6]-1,1);
            colClicked=7;
            numScreen++;
        }

        //
        if(mouseX>=150&& mouseX<=200&&mouseY>=140&mouseY<=200 && numScreen%2==0 && numScreen>0){
            valid=true;
            if(Board.getBoard()[0][0]!=0){
                fullCol=true;
            }
            Board.setBoard(Board.findRow(0),0,2); 
            colClicked=1;
            numScreen++;
        }
        else if(mouseX>=200&& mouseX<=250&&mouseY>=140&mouseY<=200 && numScreen%2==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][1]!=0){
                fullCol=true;
            }
            Board.setBoard(Board.findRow(1),1,2);
            colClicked=2;
            numScreen++;
        }
        else if(mouseX>=250&& mouseX<=300&&mouseY>=140&mouseY<=200 && numScreen%2==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][2]!=0){
                fullCol=true;
            }
            Board.setBoard(Board.findRow(2),2,2);

            colClicked=3;
            numScreen++;
        }
        else if(mouseX>=300&& mouseX<=350&&mouseY>=140&mouseY<=200 && numScreen%2==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][3]!=0){
                fullCol=true;    
            }
            Board.setBoard(Board.findRow(3),3,2);
            colClicked=4;
            numScreen++;
        }
        else if(mouseX>=350&& mouseX<=400&&mouseY>=140&mouseY<=200 && numScreen%2==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][4]!=0){
                fullCol=true;       
            }
            Board.setBoard(Board.findRow(4),4,2);   
            colClicked=5;
            numScreen++;
        }
        else if(mouseX>=400&& mouseX<=450&&mouseY>=140&mouseY<=200 && numScreen%2==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][5]!=0){
                fullCol=true;        
            }
            Board.setBoard(Board.findRow(5),5,2);
            colClicked=6;
            numScreen++;
        }
        else if(mouseX>=450&& mouseX<=500&&mouseY>=140&mouseY<=200&& numScreen%1==0 && numScreen>0){
            valid=true;

            if(Board.getBoard()[0][6]!=0){
                fullCol=true;        
            }
            Board.setBoard(Board.findRow(6),colNums[6]-1,2);
            colClicked=7;
            numScreen++;
        }
    }

    public void mouseClicked(MouseEvent me)
    {
    }

    public void mouseEntered(MouseEvent me)
    {}

    public void mouseExited(MouseEvent me)
    {}

    public void mouseReleased(MouseEvent me)
    {}
}

