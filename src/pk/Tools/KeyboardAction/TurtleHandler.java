package pk.Tools.KeyboardAction;


import pk.Tools.*;
import java.awt.*;

/**
 *  Handler for turtle tool ie draws the turtle triangle
 *  @author Rahul B.
 * @version 0.1.
 */
public class TurtleHandler extends AbstractHandler
{

    public void handle(int x, int y, int x1,int y1)
    {
    }
    public void repeat(int x, int y)
    {
       float fx = easel.fx(Resource.turtle.x);//tip of turtle
       float fy = easel.fy(Resource.turtle.y);
       
       //Other two points
       float p1y= fy-5;
       float p1x= fx-5;
       float p2x= fx+5;
       float p2y= fy-5;
       
       //Rotate the points based on the angle of turtle
       float[] r1x = easel.rotate(p1x,p1y,fx,fy,Resource.turtle.angle);
       float[] r2x = easel.rotate(p2x,p2y,fx,fy,Resource.turtle.angle);
       
       float[] xx  ={fx,r1x[0],r2x[0]};
       float[] yy  ={fy,r1x[1],r2x[1]};
       
       //Draw the traingle for the turtle.
       easel.drawTriangle(TGraph.g,xx,yy);
    }
}
