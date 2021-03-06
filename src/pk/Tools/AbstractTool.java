package pk.Tools;


import java.awt.*;


/**
 * Abstract tools define the methods for the different tools
 * All tools must implement this AbstractTool.
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public abstract class AbstractTool
{
    public static Color color;
    public  String ID;
    
    /**
     * Handle method handles the drawing of the tool.
     */
    public abstract void handle(int x,int y,int x1, int y1);
    
    /**
     * Repeat method is used in case a repetitive drawing on the TGraph is required,
     * which must be repainted along with the graphics of Canvas
     */
    public void repeat(int x, int y)
    {
        
    }
    
    /**
     * Handles drag
     */
    public void drag(int x, int y, int x1,int y1)
    {
       handle(x,y,x1,y1); 
    }
    
    /**
     * Handles mouse pressed
     */
    public void pressed(int x, int y, int x1, int y1)
    {
    }
    
    /**
     * Handles mouse released
     */
    public void released(int x, int y)
    {
    }
    
    /**
     * Handles mouse moved
     */
    public void moved(int x, int y)
    {
    }
    
    /**
     * Sets the initial x,y cordinates when mouse is pressed.Useful for certaint tools like mystify,selec,etc...
     */
    public void setInit(int x,int y)
    {
    }
}
