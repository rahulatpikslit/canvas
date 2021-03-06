package pk.Tools;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import pk.Dialogs.*;

/**
 * This is the properties toolbox for fractal tool.
 * Variable description 
 *  frn    :- Select pre-saved fractal from a combobox<br>
 *  
 *  var    :- The combobox for choosing variations of a type of fractal-Variations are the different patterns obtained
 *            by different combinations of rotation angle and random factor.<br>
 *          
 *  level  :- It is a spinner used to specify the level of the fractal<br>
 *  
 *  rot    :- It is a spinner for choosing the rotation angle <br>
 *  
 *  varName:- Type the name of the variation you have created and click save to save the variation.<br>
 *  
 *  cont   :- It is a jckeckbox for specifying whether to reset the initial direction every time or to increment it.(Continue from previous)<br>
 *  
 *  auto   :- It is a jcheckbox for choosing to draw with automatic length or the length specified in the FractDial dialog box.<br>
 *  
 * @author Rahul B.
 * @version 0.1.
 */
public class FractalProp extends JPanel
{
    JComboBox frn = new JComboBox();
    JComboBox var  = new JComboBox();
    JSpinner level = new JSpinner();
    JSpinner rot   = new JSpinner(new SpinnerNumberModel(0.1,0.1,360.0,0.1));

    JTextField nme = new JTextField(10);
    JTextField varName = new JTextField(10);
    JTextField autoK = new JTextField(10);

    JCheckBox cont = new JCheckBox();
    JCheckBox auto = new JCheckBox();

    Canvas c;

    FractalDial  f = new FractalDial();
    public FractalProp()
    {
        refresh();//Load the saved fractal
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        frn.addItemListener(new ItemListener()
            {
                @Override
                public void itemStateChanged(ItemEvent event) {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        String item = event.getItem().toString();
                        if(item.endsWith(".frc"))
                        {
                            pk.Dialogs.FractalIO.read(item);//Use FractalIO.read to read the saved Fractal
                            sync();//Sync the level and rotation angle
                        }
                    }
                }       

            });
        

        JButton savVar = new JButton(new ImageIcon("icons/save16.png"));
        savVar.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ev)
                {
                    //Save variation
                    //File Format: 
                    //     Dir:fractals/FRACTAL_NAME/VARIATION_NAME.var
                    try{
                        File f1 = new File(System.getProperty("user.dir")+"/"+"fractals/"+frn.getSelectedItem().toString().substring(0,frn.getSelectedItem().toString().indexOf('.'))+"/");
                        File f2 = new File(System.getProperty("user.dir")+"/"+"fractals/"+frn.getSelectedItem().toString().substring(0,frn.getSelectedItem().toString().indexOf('.'))+"/"+varName.getText()+".var");
                        
                        f1.mkdirs();
                        f2.createNewFile();
                        PrintWriter pw = new PrintWriter(f2);
                        pw.println(AbstractHandler.easel.frc.rotation);
                        pw.println(AbstractHandler.easel.frc.randF);
                        pw.close();
                    }catch(Exception ec){ec.printStackTrace();}
                }
            });
            
        savVar.setText("Save Variation");
        JPanel rf = new JPanel();
        rf.add(new JLabel("Read Fractal:"));
        add(frn);

        JPanel lev = new JPanel();
        lev.add(new JLabel("Level"));
        lev.add(level);

        JPanel rotat = new JPanel();
        rotat.add(new JLabel("Rotation"));
        rotat.add(rot);
        add(lev);
        add(rotat);

        JPanel variation = new JPanel();
        variation.add(new JLabel("Variation Name"));
        variation.add(varName);

        level.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent e)
                {
                    AbstractHandler.easel.frc.level = ((Integer)(level.getValue())).intValue();
                }
            });

        rot.addChangeListener(new ChangeListener() 
            {
                public void stateChanged(ChangeEvent e)
                {
                    AbstractHandler.easel.frc.rotation = ((Double)(rot.getValue())).doubleValue();
                }
            });

        JButton save = new JButton(new ImageIcon("icons/save16.png"));
        save.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ex)
                {
                    Fractal e = AbstractHandler.easel.frc;
                    pk.Dialogs.FractalIO.write(new String[]{e.axiom,e.strF,e.strf,e.strX,e.strY,""+e.rotation,""+e.dirStart,""+e.lengthFract,""+e.reductFact,e.level+"",e.randF+"",nme.getText()});
                    File f = new File(System.getProperty("user.dir")+"/"+"fractals/"+nme.getText()+"/");
                    f.mkdirs();
                    refresh();
                }
            });
        save.setText("Save");

        cont.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    FractalHandler.cont = !FractalHandler.cont;
                }
            });
        JPanel name = new JPanel();
        name.add(new JLabel("Name"));
        name.add(nme);
        name.add(save);

        JPanel contAngle = new JPanel();
        contAngle.add(new JLabel("Continuos Angle:"));
        contAngle.add(cont);

        JButton fractal = new JButton(new ImageIcon("icons/fractal.png"));
        fractal.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    f.show();
                    sync();
                }
            });
        fractal.setText("Create Fractal");

        JPanel autoLen = new JPanel();
        autoLen.add(new JLabel("Auto Length:"));
        autoLen.add(auto);
        auto.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    FractalHandler.autoLen = !FractalHandler.autoLen;
                }
            });

        //add(rf);
        add(name);
        add(save);
        //add(variation);
        //add(savVar);
        add(contAngle);
        add(autoLen);
        add(fractal);
    }

    /**
     *  Refreshes the frn combobox by reloading the saved-fractals.
     */
    public void refresh()
    {
        File f = new File(System.getProperty("user.dir")+"/"+"fractals/");
        File[] f2 = f.listFiles();

        for(int i=0;i<f2.length;i++)
        {
            String s1 = f2[i].toString();
            int p = s1.lastIndexOf('\\');
            System.out.println(s1.substring(p));
            if(s1.endsWith(".frc"))
                frn.addItem(s1.substring(p+1));

        }
    }
    
    /**
     *  Syncs the level and rotation values.
     */
    public void sync()
    {
        level.setValue(AbstractHandler.easel.frc.level);
        rot.setValue(AbstractHandler.easel.frc.rotation);
    }
}
