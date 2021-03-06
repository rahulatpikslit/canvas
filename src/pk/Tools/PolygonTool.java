package pk.Tools;

import java.util.*;
import java.awt.event.*;
import java.awt.*;

/**
 *  Polygon Tool
 *  
 *  @author Rahul B.
 * @version 0.1.
 */
public class PolygonTool extends AbstractTool
{






    public static final int POLYGON_TOOL =5;
    public static final String POLYGON ="Square";
    public boolean isSelected =false;
    
    Vector v  = new Vector();
    int index =0;
     public PolygonTool(){
         
    }
    public boolean getSelected()
    {
        return isSelected;
    }
    
    public void pressed(int x,int y,int x1,int y1)
    {
        handle(x,y,x1,y1);
    }
    public void setSelected(boolean sel)
    {
        isSelected = sel;
    }
    public void handle(int x,int y,int x1, int y1)
    {
        (new PolygonHandler()).handle(x,y,x1,y1);
    }
    public void newPoint(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        
        Point p = new Point(x,y);
        
        check();
        index ++;
    }
    public void start()
    {
        PolygonHandler.inProcess = true;
    }
    public void check()
    {
        Point last = (Point)v.elementAt(index);
        int x=  (int)last.getX();
        int y = (int)last.getY();
        
        for(int  i =0;i<v.size();i++)
        {
            Point temp =(Point) v.elementAt(i);
            if(temp.getX() ==x && temp.getY() ==y)
            {
                PolygonHandler.inProcess = false;
            }
        }
        
    }
    public String toString()
    {
        return POLYGON;
    }
}


