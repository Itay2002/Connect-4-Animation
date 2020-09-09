import java.util.Scanner;
/**
 * Write a description of class Runner here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Runner
{
    public static void main(String [] args){

        
        Scanner sc= new Scanner(System.in);
        System.out.println("Player 1,Enter your username");
        String name1=sc.next();

        System.out.println("Player 1,Enter your desired color(Yellow(y)/Red(r))");
        String color1=sc.next();
        while(!(color1.equals("y") ||color1.equals("r"))){
            System.out.println("Enter valid color input. Yellow(y) or red(r)");
            color1=sc.next();
        }
        Player1 p1=new Player1(name1,color1);

        System.out.println("Player 2,Enter your username");
        String name2=sc.next();
        while(name2.equals(name1)){
            System.out.println("Player 2, username taken. Choose your username");
            name2=sc.next();    
        }
        String color2="";
        if(color1.equals("r")){
            color2="y";   
        }
        else{
            color2="r";   
        }
        Player2 p2=new Player2(name2,color2);
        System.out.println(p1);
        System.out.println(p2);
        System.out.println("Lets Begin and Good Luck to both players!!!");
        Board gboard=new Board();
        AnimationTemplate app=new AnimationTemplate();
        app.debug(600,600);

        gboard.printBoard();
        int turn=0;
        //need to figure out fullboard...(maybe make a boolean full variable and while loop.
        while(turn>=0){
            if(turn%2==0){
                if(gboard.fullBoard()){
                    turn=-2;   
                }
                System.out.println("Player 1 turn");
                System.out.println("Select Board Position(column)");
                int x=sc.nextInt();

                while(x-gboard.getWidth()>0 || x<=0){
                    System.out.println("Enter a valid board position");
                    System.out.println("Select Board Position(column)");
                    x=sc.nextInt();

                }
                while(gboard.getBoard()[0][x-1]!=0){
                    System.out.println("Column is full. Select differnet column");
                    System.out.println("Select Board Position(column)");
                    x=sc.nextInt();
                }
                gboard.setBoard(gboard.findRow(x-1),x-1,1);
                gboard.printBoard();
                //had turn++ here previosuly...
                //do i need a findcol since this gives me current one
                if(gboard.findRow(x-1)+4<gboard.getHeight()||(x-1)<=gboard.getWidth()){
                    if(gboard.verticalFour()||gboard.horizontalFour()||gboard.diagFour()
                    ||gboard.reversediagFour()
                    ){
                        System.out.println(name1+" Wins!!!");
                        turn=-2;    
                    }
                    else{
                        System.out.println("inside");
                    }
                }
                turn++;
            }
            else{
                if(gboard.fullBoard()){
                    turn=-2;
                    System.out.println("Board is full. It's a tie");
                }
                System.out.println("Player 2 turn");
                System.out.println("Select Board Position(column)");
                int x=sc.nextInt();

                while(x-gboard.getWidth()>0 || x<=0){
                    System.out.println("Enter a valid board position");
                    System.out.println("Select Board Position(column)");
                    x=sc.nextInt();

                }
                while(gboard.getBoard()[0][x-1]!=0){
                    System.out.println("Column is full. Select differnet column");
                    System.out.println("Select Board Position(column)");
                    x=sc.nextInt();
                }
                gboard.setBoard(gboard.findRow(x-1),x-1,2);
                gboard.printBoard();

                if(gboard.findRow(x-1)+4<gboard.getHeight()||(x-1)<=gboard.getWidth()){
                    if(gboard.verticalFour()||gboard.horizontalFour()||gboard.diagFour()
                    ||gboard.reversediagFour()
                    ){
                        System.out.println(name2+" Wins!!!");
                        turn=-2;    
                    }
                    else{
                        System.out.println("inside");
                    }
                }
                turn++;
            }
        }
        //maybe add option to play again...
        System.out.println("Well Played! Game Over");

    }
}
