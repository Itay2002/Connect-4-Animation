
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player1
{
    private static String name;
    private static String color;
    public Player1(String n,String c){
        name=n;
        if(c.equals("y")){
            color="yellow";
        }
        else{
            color="red";    
        }

    }

    public static String getName(){
        return name;    
    }
    public static String getColor(){
        return color;    
    }

    public String toString(){
        return name+" has the "+color+" team";    
    }
}
