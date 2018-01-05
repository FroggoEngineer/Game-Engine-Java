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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kevin
 */
public class newMap extends JFrame implements ActionListener {
    
    private JTextField mapName = new JTextField(15);
    private JTextField mapWidth = new JTextField(6);
    private JTextField mapHeight = new JTextField(6);
    
    private JRadioButton day = new JRadioButton("Day ", false),
                         night = new JRadioButton("Night ", true);
    
    private JLabel textMap = new JLabel("Name: ");
    private JLabel textWidth = new JLabel("Width: ");
    private JLabel textHeight = new JLabel("Height: ");
    
    private JPanel up = new JPanel(); //Övre
    private JPanel midup = new JPanel(); //Mitten övre
    private JPanel middown = new JPanel(); //Mitten nedre
    private JPanel down = new JPanel(); //Nedre
    
    private JButton done = new JButton("Done");
    private JButton cancel = new JButton("Cancel");
    
    public newMap() {
        setLayout(new GridLayout(4,1));
        add(up); add(midup); add(middown); add(down);
        
        //Add to upper grid
        up.add(textMap); up.add(mapName);
        
        //Add to midup grid
        midup.add(textWidth); midup.add(mapWidth); midup.add(textHeight); midup.add(mapHeight);
        
        //Add to middown grid
        middown.add(day); middown.add(night);
        ButtonGroup types = new ButtonGroup();
        types.add(day); types.add(night);
        
        done.setSize(100,50); cancel.setSize(100,50);
        down.add(done); down.add(cancel);
        
        mapName.addActionListener(this);
        mapWidth.addActionListener(this);
        mapHeight.addActionListener(this);
        day.addActionListener(this);
        night.addActionListener(this);
        done.addActionListener(this);
        cancel.addActionListener(this);
        
        setSize(400,200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        String nameOfMap; 
        int widthOfMap, heightOfMap;
        
        if(e.getSource() == done) {
            nameOfMap = mapName.getText();
            widthOfMap = Integer.valueOf(mapWidth.getText());
            heightOfMap = Integer.valueOf(mapHeight.getText());
            game.engine.MapArea.currentMap = new map.Map(widthOfMap, heightOfMap);
            game.engine.MapArea.currentMap.setName(nameOfMap);
            if(night.isSelected())
                game.engine.MapArea.currentMap.setDarkness(true);
            else
                game.engine.MapArea.currentMap.setDarkness(false);
            dispose();
        }
        else if(e.getSource() == cancel) {
            dispose();
        }
        
    }
}
