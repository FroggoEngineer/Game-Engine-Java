/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import objects.*;
import emitters.*;

/**
 *
 * @author Kevin Söderberg
 */
public class ObjectPanel extends JPanel implements ActionListener {

    public enum loadType {BLOCKS, OBJECTS, CHARACTERS};
    private loadType selected;
    private MenuObject[] menuItems;
    private int maxItemsToRender, minItemsToRender;
    private int fromItem, toItem;
    private boolean changeMenu, scrollMenu=true;
    
    public ObjectPanel(int width, int height) {
        setSize(width,height);
        selected = loadType.BLOCKS;
        maxItemsToRender = (int)Math.floor(height/100);
        
        fromItem = 0;
        if(maxItemsToRender > ItemHolder.blocks.size()+1)
            toItem = ItemHolder.blocks.size();
        else
            toItem = maxItemsToRender;
        addMouseWheelListener(l);
        setLayout(null);
        setVisible(true);
        updateMenu();
    }

    MouseAdapter l = new MouseAdapter() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            //hBar.setValue(hBar.getValue()+e.getWheelRotation()*2);
            if (scrollMenu) {
                int a = e.getWheelRotation();
                if (fromItem > minItemsToRender && a < 0) {
                    fromItem += a;
                    
                    toItem = fromItem + maxItemsToRender;
                    if (fromItem < 0) {
                        
                        fromItem = 1;
                        toItem = fromItem + maxItemsToRender;
                    }
                }
                if (toItem < menuItems.length && a > 0) {
                    fromItem += a;
                    
                    toItem = fromItem + maxItemsToRender;
                    if (toItem > menuItems.length) {
                        
                        toItem = menuItems.length;
                        fromItem = toItem - maxItemsToRender;
                    }
                }
                //System.out.println(from + " : " + to);
                changeMenu = true;
            }

        }
    };
    
    
    public void actionPerformed(ActionEvent e) {
        
    }
   

    public void update(int width, int height) {
        setLocation(width - 250, 0);
        setSize(250, height);
        maxItemsToRender = (int)Math.floor(height/100);
        if(changeMenu)
            updateMenu();
    }

    public void updateMenu() {
        if(selected == loadType.BLOCKS) {
            menuItems = new MenuObject[ItemHolder.blocks.size()+1];
            for(int i = 0; i < menuItems.length; i++) {
                if(i < menuItems.length-1)
                    menuItems[i] = new MenuObject(ItemHolder.blocks.get(i));
                else
                    menuItems[i] = new MenuObject(ItemHolder.objects.get(0));
            }
            removeAll();
            for(int i = fromItem; i < toItem; i++) {
                menuItems[i].setLoc((i-fromItem)*100);
                add(menuItems[i]);
            }
            validate();
            repaint();
            updateUI();
        }
        changeMenu = false;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.DARK_GRAY);
        Color[] colors = new Color[10];
        colors[0] = colors[1] = colors[8] = colors[9] = Color.BLACK;
        colors[2] = colors[3] = colors[6] = colors[7] = Color.DARK_GRAY;
        colors[4] = colors[5] = Color.GRAY;
        for (int i = 0; i < 10; i++) {
            g.setColor(colors[i]);
            g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i - 1, 10, 10);
        }

    }
    
}
