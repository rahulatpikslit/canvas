package pk;

import javax.swing.*;//if problem true erased
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import pk.Tools.*;
import javax.swing.undo.*;
import pk.Tools.KeyboardAction.*;
import java.awt.geom.*;
import Tools.Listener.*;
import pk.Resize.*;

/**
 * This class extends JPanel and thus it handles mouse events for drawing.
 * The image of class Easel is drawn on Canvas.<br>
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class Canvas extends JPanel 
{
    Easel easel;
    BufferedImage uim;
    Graphics2D g2d;

    int ox=0,oy=0,
    nx=0,ny=0;

    Graphics2D g;

    int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    int sw = width;
    int sh = height;
    static boolean turtle = false;
    TurtleTool th = new TurtleTool(); 

    Resizable jp;
    SelectionPanel ip;

    /**
     * This method sets the resizable panel for selectiontool.
     * Setting the resizable panel allows it to be removed once the selection tool is deseleted.
     */
    public void setResizable(Resizable jp)
    {
        this.jp = jp;
    }

    public Resizable getResizable()
    {
        return jp;
    }

    public SelectionPanel getPanel()
    {
        return ip;
    }

    /**
     * This method sets the SelectionPanel for copying the selected image into it, and later to draw it onto
     * the easel after dragging and resizing are finished.
     */
    public void setPanel(SelectionPanel ip)
    {
        this.ip = ip;
    }

    /**
     * Changes the dimensions of the easel which is to painted.
     * Though the dimensions of the canvas is unchanged easel's dimensions are changed and rest of the region is grayed.
     */
    public void changeDimension(int w,int h)
    {
        width = w;
        height= h;

        g = (Graphics2D)AbstractHandler.easel.image.getGraphics();
    }

    /**
     * Constructor for Canvas
     * Parameter bar (StatusBar) is passed.Status bar has components that displays mouse position,<br>
     * change dimension and zoom factor.
     */
    public Canvas(StatusBar bar)
    {
        setLayout(null);
        Resource.c = this;
        setFocusable(true);

        ToolManager.setTool(PencilTool.PENCIL_TOOL);
        addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    ox =e.getX();
                    oy =e.getY();

                    AbstractTool tool = ToolManager.getAsTool();
                    tool.setInit(ox,oy);

                    //General parameters are not applicable to polygon , selection tool and fillwithcolot tool so seperate
                    //cases is defined and accordingly handled
                    if(ToolManager.getTool()==PolygonTool.POLYGON_TOOL)
                    {
                        if(!PolygonHandler.inProcess){
                            ox =nx;oy=ny;(new PolygonTool()).start();//Start drawing polygon by setting initial point}
                            tool.pressed(nx,ny,ox,oy);
                        }
                    }
                    else if(ToolManager.getTool()==SelectionTool.SELECTION_TOOL && !e.isPopupTrigger())
                    {
                        if(jp!=null)
                        {

                            AbstractHandler.easel.image.getGraphics().drawImage(ip.bim.getSubimage(0,0,ip.w,ip.h),jp.getLocation().x,jp.getLocation().y,jp.getWidth(),jp.getHeight(),null);//Draw the selected image onto the easel

                            remove(jp);//Remove the resizable component
                            jp = null;

                            SelectionHandler.sp   = null;
                            SelectionHandler.drag = false;//Reset drag to false
                        }
                    }
                    else if(ToolManager.getTool() == FillWithColorTool.FWC_TOOL )
                    {
                        tool.pressed(ox,oy,(easel.getImage()).getRGB(ox,oy),oy);//
                    }
                    SelectionHandler.state = 1;
                    
                    easel.saveToUndoStack(easel.image);
                    AbstractHandler.easel.reset(ox,oy);
                    repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e)
                {
                    nx =e.getX();
                    ny =e.getY();

                    if(ToolManager.getTool()==SelectionTool.SELECTION_TOOL && jp!=null)
                    {
                        //remove(jp);
                    }
                    AbstractTool tool = ToolManager.getAsTool();
                    tool.setInit(ox,oy);
                    //SelectionHandler.state = 1;
                    if(tool instanceof FillWithColorTool )
                    {
                        tool.pressed(nx,ny,(easel.getImage()).getRGB(nx,ny),ny);
                        repaint();
                        easel.saveToUndoStack(easel.image);
                    }

                    ox =nx;
                    oy =ny;
                    
                    SelectionHandler.state = 0;
                }

                public void mouseReleased(MouseEvent e)
                {
                    AbstractTool tool = ToolManager.getAsTool();
                    if(!e.isPopupTrigger())
                    {
                        AbstractHandler.easel.reset();

                        tool.released(e.getX(),e.getY());

                        if(tool instanceof SelectionTool)
                        {
                            if(!SelectionHandler.drag)SelectionHandler.drag = true;
                        }
                    }
                    else
                    {
                        if(tool instanceof SelectionTool)
                        {
                            ContextMenu cm = new ContextMenu();
                            cm.show(Canvas.this,e.getX(),e.getY());
                        }
                    }

                }
            });
        addMouseMotionListener(new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {

                    nx = e.getX();
                    ny = e.getY();

                   
                    SelectionHandler.state = 2;
                    AbstractTool tool = ToolManager.getAsTool();
                    if(!(tool instanceof FillWithColorTool) &&!(tool instanceof PolygonTool))
                    {
                        tool.drag(ox,oy,nx,ny);
                    }
                    
                    repaint();

                    ox=nx;
                    oy=ny;
                    
                    int temp = e.getX();
                    int num = 0;

                    while(temp!=0)
                    {
                        int r =temp%10;
                        temp/=10;
                        
                        num++;
                    }
                    String org ="1000:1000";
                    String nstr=((num<99)?"  ":" ")+e.getX()+":"+e.getY();
                    String space ="";

                    for(int i=0;i<=(org.length()-nstr.length())+1;i++)
                    {
                        nstr +=" ";

                    }
                    bar.pos.setText("<html><body><pre>"+"|"+nstr+"|"+"</pre></body></html>");
                }

                public void mouseMoved(MouseEvent e)
                {
                    int temp = e.getX();
                    int num = 0;

                    while(temp!=0)
                    {
                        int r =temp%10;
                        temp/=10;
                        
                        num++;
                    }
                    String org ="1000:1000";
                    String nstr=((num<99)?"  ":" ")+e.getX()+":"+e.getY();
                    String space ="";

                    for(int i=0;i<=(org.length()-nstr.length())+1;i++)
                    {
                        nstr +=" ";

                    }
                    bar.pos.setText("<html><body><pre>"+"|"+nstr+"|"+"</pre></body></html>");
                    (ToolManager.getAsTool()).moved(e.getX(),e.getY());
                }
            });

    }


    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(easel == null)
        {

            easel = new Easel(width,height);
            pk.Tools.ColorModel.color =Color.white;
            easel.drawRect(true,0,0,width,height);
            pk.Tools.ColorModel.color =Color.black;
            AbstractHandler.setEasel(easel); 
            TGraph.g = g;
            easel.saveToUndoStack(easel.image);
        }

        TGraph.set(g);
        g.setColor(Color.gray);
        g.fillRect(0,0,(int)(sw*Zoom.zoomFactor),(int)(sh*Zoom.zoomFactor));

        g.setColor(Color.white);
        g.fillRect(0,0,(int)(width*Zoom.zoomFactor),(int)(height*Zoom.zoomFactor));

        ((Graphics2D)g).drawImage(easel.image,0,0,(int)(width*Zoom.zoomFactor),(int)(height*Zoom.zoomFactor),null);
        AbstractTool tool = ToolManager.getAsTool();
        
        g.setColor(pk.Tools.ColorModel.color);
        tool.repeat(nx,ny);

        if(turtle)
        {
            th.repeat(nx,ny);
        }

    }

}
