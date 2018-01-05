/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Kevin
 */
public class addObject extends JFrame implements ActionListener {
    
    private JTextField objectName = new JTextField(15);
    private JTextField objectWidth = new JTextField(6);
    private JTextField objectHeight = new JTextField(6);
    private JPanel up = new JPanel(); //Övre
    private JPanel midup = new JPanel(); //Mitten övre
    private JPanel middown = new JPanel(); //Mitten nedre
    private JPanel down = new JPanel(); //Nedre
    private JPanel side = new JPanel(); //Sida, preview av objekt
    private JLabel textshit = new JLabel("Name: ");
    private JLabel textWidth = new JLabel("Width: ");
    private JLabel textHeight = new JLabel("Height: ");
    private JRadioButton blocks = new JRadioButton("Blocks", true),
                         backobject = new JRadioButton("Back object", false),
                         background = new JRadioButton("Background", false),
                         frontobject = new JRadioButton("Front object", false);
    private JButton done = new JButton("Done");
    private JButton cancel = new JButton("Cancel");
    private JButton testLoad = new JButton("Test load");
    
    public addObject() {
        setLayout(new GridLayout(5,1));
        add(up); add(midup); add(middown); add(down); add(side);
        
        //Adding radiobuttons to the upper JLabel, setting actionlistener
        up.add(blocks); up.add(backobject);  up.add(frontobject);
        ButtonGroup types = new ButtonGroup();
        types.add(blocks); types.add(backobject);  types.add(frontobject);
        blocks.addActionListener(this);
        blocks.setToolTipText("Objects that go into the middle block layer");
        backobject.addActionListener(this);
        backobject.setToolTipText("Objects that go into the back layer");
        frontobject.addActionListener(this);
        frontobject.setToolTipText("Objects that go into the front layer");
        
        done.addActionListener(this);
        cancel.addActionListener(this);
        testLoad.addActionListener(this);
        testLoad.setToolTipText("Tries to load the object with the given name and display it below");
        objectName.addActionListener(this);
        objectName.setToolTipText("Name of the object file");
        objectWidth.addActionListener(this);
        objectWidth.setToolTipText("Width of the object in blocks");
        objectHeight.addActionListener(this);
        objectHeight.setToolTipText("Height of the object in blocks");
        
        midup.add(textshit); midup.add(objectName);
        
        middown.add(textWidth); middown.add(objectWidth);
        middown.add(textHeight); middown.add(objectHeight);
        
        done.setSize(100,50); cancel.setSize(100,50); testLoad.setSize(100,50);
        down.add(done); down.add(cancel); down.add(testLoad);
        setSize(400,250);
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object b = e.getSource();
        FileWriter fileWriter = null;
        String pathName = "";
        String idInfo = "";
        String nameOfObject, widthOfObject, heightOfObject;
        if(b == done) {
            nameOfObject = objectName.getText();
            widthOfObject = objectWidth.getText();
            heightOfObject = objectHeight.getText();
            if(blocks.isSelected()) {
                pathName = "data\\objects\\blockids.inf";
                idInfo = nameOfObject + " 32 32 data/objects/blocks/" + nameOfObject + ".png " + widthOfObject + " " + heightOfObject;
            }
            else if(backobject.isSelected()) {
                pathName = "data\\objects\\backobjectids.inf";
                idInfo = nameOfObject + " 32 32 data/objects/backobjects/" + nameOfObject + ".png " + widthOfObject + " " + heightOfObject;
            }
            else if(frontobject.isSelected()) {
                pathName = "data\\objects\\frontobjectids.inf";
                idInfo = nameOfObject + " 32 32 data/objects/frontobjects/" + nameOfObject + ".png " + widthOfObject + " " + heightOfObject;
            }
            
            try {
                fileWriter = new FileWriter(pathName, true);
            } catch (IOException ex) {
                
            }
            PrintWriter outputFile = new PrintWriter(fileWriter);
            outputFile.println(idInfo);
            outputFile.close();
            dispose();
        }
        else if(b == cancel) {
            dispose();
        }
        else if(b == testLoad) {
            
        }
    }
}
