package pk;


import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import Undo.*;
import java.awt.event.*;
import pk.Tools.*;
import Tools.KeyboardAction.*;
import pk.Panel.*;
import laf.*;
import java.io.*;
import pk.Tools.Listener.*;

/**
 *  Main class ... displays the JFrame
 *  @author Rahul B.
 *  @version 0.1.
 */
public class Main extends JFrame implements ZoomListener , DimensionListener
{
    StatusBar bar1  = new StatusBar(this,this,this);
    Canvas c  =new Canvas(bar1);

    JScrollPane pane =new JScrollPane(c);
    static int WIDTH;

    int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public Main()
    {
        
        super("PiksLit canvas");        
        
        SplashScreen.main();
        //setDefaultLookAndFeelDecorated(true);
        //setUndecorated(true);
        Resource.frame = this;

        new KAction(this);

        Zoom.zoomFactor = 1;
        WIDTH=width;
        c.setPreferredSize(new Dimension(width,height));
        PropertiesBar bar = new PropertiesBar();
        bar.update(4);

        
        setLayout(new BorderLayout());
        
        setSize(width,height);

        add(pane,BorderLayout.CENTER);
        add(new ToolBar(c,bar,this),BorderLayout.NORTH);
        add(bar,BorderLayout.EAST); 
        add(bar1,BorderLayout.SOUTH);
        add(new LaF(this),BorderLayout.WEST);
        Actions.addPropertiesListener(bar);
        addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    System.exit(-1);                    
                }
            });

            //SwingUtilities.updateComponentTreeUI(this);
        setVisible(true);
        
    }

    /**Method implemented from ZoomListener 
     *Manages Zooming function
     */
    public void zoomed(double a)
    {
        c.setPreferredSize(new Dimension((int)(width*a),(int)(height*a)));
        c.repaint();
        c.invalidate();
        c.revalidate();
        pane.repaint();
        pane.invalidate();
        repaint();
    }

    /**Main method
     * Creating an instance of Main class
     */
    public static void main(String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Main m =  new Main();
        try{
             //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
             //UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
            SwingUtilities.updateComponentTreeUI(m);
        }catch(Exception ex){}
    }

    public void dimensionChanged(int w,int h)
    {
        BufferedImage im = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        AbstractHandler.easel.setImage(resize(AbstractHandler.easel.image,w,h));
        c.changeDimension(w,h);
        repaint();
        c.repaint();
    }

    /**Method to resize a BufferedImage 
     *   img BufferedImage to resize
     *   newW -> Width of the resized image
     *   newH -> Height of the resized image
     */
    public  BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return dimg;
    }  
}
