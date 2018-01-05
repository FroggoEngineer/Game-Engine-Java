/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine;

import objects.*;
import emitters.*;
import characters.*;
import lights.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author Kevin Söderberg
 */
public class MenuObject extends JPanel implements ActionListener {

    private JButton select;
    private Block block;
    private Emitter emitter;
    private LightSource light;
    private NObject objects;
    private Creature npc;
    private JLabel nameShow, iconShow, paraShow;
    private int type;

    public MenuObject(Block block) {
        select = new JButton("Select");
        select.setBounds(100, 10, 100, 25);
        select.addActionListener(this);
        add(select);
        this.block = block;

        type = 0;

        nameShow = new JLabel(block.getName());
        iconShow = new JLabel(block.getIcon());
        paraShow = new JLabel("Parameters");

        nameShow.setLocation(10, 10);
        nameShow.setSize(50, 10);
        iconShow.setLocation(20, 40);
        iconShow.setSize(32, 32);
        paraShow.setLocation(120, 40);
        paraShow.setSize(70, 10);

        add(nameShow);
        add(iconShow);
        add(paraShow);

        setLayout(null);
        setVisible(true);
        setSize(230, 100);
    }
    
    public MenuObject(NObject objects) {
        select = new JButton("Select");
        select.setBounds(100, 10, 100, 25);
        select.addActionListener(this);
        add(select);
        this.objects = objects;

        type = 1;

        nameShow = new JLabel(objects.getName());
        iconShow = new JLabel(objects.getIcon());
        paraShow = new JLabel("Parameters");

        nameShow.setLocation(10, 10);
        nameShow.setSize(50, 10);
        iconShow.setLocation(20, 40);
        iconShow.setSize(32, 32);
        paraShow.setLocation(120, 40);
        paraShow.setSize(70, 10);

        add(nameShow);
        add(iconShow);
        add(paraShow);

        setLayout(null);
        setVisible(true);
        setSize(230, 100);
    }

    public MenuObject(Emitter emitter) {
        select = new JButton("Select");
        select.setBounds(100, 10, 100, 25);
        select.addActionListener(this);
        select.setToolTipText("Selects this emitter");
        add(select);
        
        nameShow = new JLabel(emitter.getName());
        paraShow = new JLabel("Parameters");

        nameShow.setLocation(10, 10);
        nameShow.setSize(70, 15);
        paraShow.setLocation(120, 40);
        paraShow.setSize(70, 10);

        add(nameShow);
        add(paraShow);
        
        this.emitter = emitter;
        type = 1;

        setLayout(null);
        setVisible(true);
        setSize(250, 100);
    }
    /*
    public MenuObject(Creature npc) {
        select = new JButton("Select");
        select.setBounds(100, 10, 100, 25);
        select.addActionListener(this);
        select.setToolTipText("Selects this npc");
        add(select);
        
        nameShow = new JLabel(npc.getName());
        iconShow = new JLabel(npc.getIcon());
        paraShow = new JLabel("Parameters");

        nameShow.setLocation(10, 10);
        nameShow.setSize(70, 15);
        iconShow.setLocation(20, 40);
        iconShow.setSize(32, 32);
        paraShow.setLocation(120, 40);
        paraShow.setSize(70, 10);

        add(nameShow);
        add(iconShow);
        add(paraShow);
        
        this.npc = npc;
        type = 2;

        setLayout(null);
        setVisible(true);
        setSize(250, 100);
    }
    */
    
    public MenuObject(LightSource light) {
        select = new JButton("Select");
        select.setBounds(100, 10, 100, 25);
        select.addActionListener(this);
        select.setToolTipText("Selects this lightsource");
        add(select);
        
        System.out.println(light.getName());
        nameShow = new JLabel(light.getName());
        paraShow = new JLabel("Parameters");

        nameShow.setLocation(10, 10);
        nameShow.setSize(70, 10);
        paraShow.setLocation(120, 40);
        paraShow.setSize(70, 10);
        
        add(nameShow);
        add(paraShow);
        
        this.light = light;
        type = 3;

        setLayout(null);
        setVisible(true);
        setSize(250, 100);
    }

    public void setLoc(int y) {
        setLocation(10, y+10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == select) {
            switch (type) {
                case 0:
                    MapArea.selectedBlock = block;
                    MapArea.selectedType = MapArea.TypeSelect.BLOCK;
                    break;
                case 1:
                    MapArea.selectedObject = objects;
                    MapArea.selectedType = MapArea.TypeSelect.OBJECT;
                    break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        //Ritar panelen
        super.paintComponent(g);

        g.drawLine(type, type, type, type);
        
        Color[] colors = new Color[5];
        colors[0] = colors[4] = Color.BLACK;
        colors[1] = colors[3] = Color.DARK_GRAY;
        colors[2] = Color.GRAY;
        for(int i=0; i < 5; i++) {
            g.setColor(colors[i]);
            g.drawRoundRect(0 + i, 0 + i, getWidth()-1 - 2 * i, 99 - 2 * i, 10, 10);
        }
        
        

        //Ramen till block/npc/etc...
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < 3; i++) {
            g.drawRoundRect(17 + i, 37 + i, 37 - 2 * i, 37 - 2 * i, 5, 5);
        }
    }
}
