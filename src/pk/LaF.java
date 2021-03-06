package pk;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import laf.*;

/**
 *  Shows a panel with different laf options to change the look and feel of the app.
 * @author Rahul B.
 * @version 0.1.
 */
class LaF extends JPanel
{
    JFrame f;
    public LaF(JFrame f)
    {
        LafMenu lf = new LafMenu();
        this.f=f;

        JPanel panel;
        JButton windows;
        JButton nimbus;
        JButton motif;
        JButton metal;
        JButton tatoo;

        windows = new JButton("WINDOWS");
        nimbus = new JButton("NIBUS");
        motif = new JButton("MOTIF");
        metal = new JButton("METAL");
        tatoo = new JButton("TATTOO");
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        windows.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    set("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }
            });
        nimbus.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    set("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                }
            });
        motif.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    set("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                }
            });
        metal.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    set("javax.swing.plaf.metal.MetalLookAndFeel");
                }
            });
        tatoo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    f.setVisible(false); 
                    lf.showDialog(f);
                    SwingUtilities.updateComponentTreeUI(f);
                    f.setVisible(true); 
                }
            });

        panel.add(windows);
        panel.add(motif);
        panel.add(nimbus);
        panel.add(metal);
        panel.add(tatoo);
        add(panel);
    }

    public void set(String s)
    {
        try{
            UIManager.setLookAndFeel(s);
            SwingUtilities.updateComponentTreeUI(f);
        }catch(Exception ex)
        {
        }
    }
}