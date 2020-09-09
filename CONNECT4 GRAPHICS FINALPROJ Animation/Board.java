
/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board
{
    private static int width;
    private static int height;
    private static int [][] gameboard;
    public Board(int w,int h){

        width=w;
        height=h;
        gameboard=new int[height][width];    
    }

    public Board(){
        width=7;
        height=6;
        gameboard=new int[height][width];    
    }
    public static int getWidth(){
    return width;    
    }
    public static int getHeight(){
    return height;    
    }
    

    public static boolean horizontalFour(){
        boolean result=false;
        for(int row=0;row<height;row++){

            for(int col=0;col<width;col++){
                if(gameboard[row][col]!=0 && col+4<=width && gameboard[row][col]==gameboard[row][col+1]&& 
                gameboard[row][col+1]==gameboard[row][col+2] && 
                gameboard[row][col+2]==gameboard[row][col+3] 
                ){

                    result=true;    
                }
            }
        }
        return result;
    }

    public static boolean verticalFour(){
        boolean result=false;

        for(int col=0;col<width;col++){
            for(int row=0;row<height;row++){
                if(gameboard[row][col]!=0 && row+4<=height &&gameboard[row][col]==gameboard[row+1][col]&& 
                gameboard[row+1][col]==gameboard[row+2][col] && 
                gameboard[row+2][col]==gameboard[row+3][col] 
                ){
                    result=true;    
                }  
            }
        }   
        return result;
    }

    /**
     * 
     */
    public static boolean reversediagFour(){
        boolean result=false;
        //may have to multiple ors
        for (int row=0;row<height;row++){
            for(int col=0;col<width;col++){
                if(gameboard[row][col]!=0 && row+4<=height && col>=3&&
                gameboard[row][col]==gameboard[row+1][col-1] &&
                gameboard[row+1][col-1]==gameboard[row+2][col-2] &&
                gameboard[row+2][col-2]==gameboard[row+3][col-3] 
                ){
                    result=true;
                }
            }
        }
        return result;
    }

    public static boolean diagFour(){
        boolean result=false;
        //cant use [rc][rc] index since not always perfect square
        for (int row=0;row<height;row++){
            for(int col=0;col<width;col++){
                if(gameboard[row][col]!=0 && col+4<=width&& row+4<=height&&
                gameboard[row][col]==gameboard[row+1][col+1] &&
                gameboard[row+1][col+1]==gameboard[row+2][col+2] &&
                gameboard[row+2][col+2]==gameboard[row+3][col+3]
                
                
                ){
                    result=true;
                }
            }
        }
        return result;
    }

    public static void setBoard(int x,int y,int num){
        gameboard[x][y]=num;    
    }

    public static int findRow(int col){
        int rowNum=0;

        while(rowNum<height-1&& gameboard[rowNum+1][col]==0){
            //System.out.print(rowNum+1+":\t");
            //System.out.println(gameboard[rowNum+1][col]);
            rowNum++;

        }
        return rowNum;    
    }

    public static int [][]getBoard(){
        return gameboard;    
    }

    public void printBoard(){
        System.out.println("Current Board is:");
        for(int row=0;row<height;row++){

            for(int col=0;col<width;col++){
                System.out.print(gameboard[row][col]+" ");    
            }
            System.out.println("");
        }
    }

    public boolean fullBoard(){
        boolean result=true;
        for(int row=0;row<height;row++){
            for(int col=0;col<width;col++){
                if(gameboard[row][col]==0){
                    result=false;    
                }
            }
        }
        return result;
    }

}

