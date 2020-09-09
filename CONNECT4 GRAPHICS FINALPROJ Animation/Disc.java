
/**
 * Write a description of class Disc here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Disc
{
    private static int x;
    private static int y;
    private static String color;
    public Disc(int x,int y,int teamNum){
        x=this.x;
        y=this.y;
        if(teamNum==1){
        color=Player1.getColor();    
        }
        else{
        color=Player2.getColor();        
        }
    }
    
    public static int getXpos(){
    return x;    
    }
    
    public static int getYpos(){
    return y;    
    }

}
