/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skills;

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

    private Block block;
    public static boolean startUpdate;
    public static boolean changeMenu;
    //Used for drawing the specific MenuObjects
    private MenuObject[] objectsRendered;
    private int from, to, showMax, nrOfObjects, min = 0, max;
    public static int typeToLoad = 1;
    private boolean scrollMenu = true;

    public ObjectPanel(int width, int height) {

        objectsRendered = new MenuObject[256];
        changeMenu = false;
        from = 0;
        to = (int) Math.floor(height / 100);
        changeMenu = true;
        startUpdate = true;
        addMouseWheelListener(l);
        setLayout(null);
        setLocation(width - 250, 0);
        setSize(250, height);
        setVisible(true);
        setBackground(Color.DARK_GRAY);
        setOpaque(true);
    }

    public void actionPerformed(ActionEvent e) {
    }
    MouseAdapter l = new MouseAdapter() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            //hBar.setValue(hBar.getValue()+e.getWheelRotation()*2);
            if (scrollMenu) {
                int a = e.getWheelRotation();
                if (from > min && a < 0) {
                    from += a;
                    System.out.println("Scroll down: " + from);
                    to = from + showMax;
                    if (from < 0) {
                        System.out.println("Test");
                        from = 1;
                        to = from + showMax;
                    }
                }
                if (to < max && a > 0) {
                    from += a;
                    System.out.println("Scroll up: " + from);
                    to = from + showMax;
                    if (to > max) {
                        System.out.println("Test 2");
                        to = max;
                        from = to - showMax;
                    }
                }
                //System.out.println(from + " : " + to);
                changeMenu = true;
            }

        }
    };

    public void update(int width, int height) {

        setLocation(width - 250, 0);
        setSize(250, height);
        showMax = (int) Math.floor(getHeight() / 100);
        if (changeMenu) {


            for (int i = 0; i < max; i++) {
                //remove(objectsRendered[i]);
                objectsRendered[i] = null;
                removeAll();
                revalidate();
            }
            switch (typeToLoad) {
                case 0:
                    for (int i = 0; i < MapArea.nrBack; i++) {
                        objectsRendered[i] = new MenuObject(MapArea.backBlocks[i+1]);
                    }
                    max = MapArea.nrBack;
                    if (max > getHeight() / 100) {
                        scrollMenu = true;
                    } else {
                        scrollMenu = false;
                    }
                    break;
                case 1:
                    for (int i = 0; i < MapArea.nrBlocks; i++) {
                        objectsRendered[i] = new MenuObject(MapArea.blockBlocks[i+1]);
                    }
                    max = MapArea.nrBlocks;
                    if (max > getHeight() / 100) {
                        scrollMenu = true;
                    } else {
                        scrollMenu = false;
                    }
                    break;
                case 2:
                    for (int i = 0; i < MapArea.nrFront; i++) {
                        objectsRendered[i] = new MenuObject(MapArea.frontBlocks[i+1]);
                    }
                    max = MapArea.nrFront;
                    if (max > getHeight() / 100) {
                        scrollMenu = true;
                    } else {
                        scrollMenu = false;
                    }
                    break;
                case 3:
                    for (int i = 0; i < MapArea.nrNpc; i++) {
                        objectsRendered[i] = new MenuObject(MapArea.npcs[i+1]);
                    }
                    max = MapArea.nrNpc;
                    if (max > getHeight() / 100) {
                        scrollMenu = true;
                    } else {
                        scrollMenu = false;
                    }
                    break;
                case 4:
                    for (int i = 0; i < MapArea.nrEmitters; i++) {
                        objectsRendered[i] = new MenuObject(MapArea.emitters[i+1]);
                    }
                    max = MapArea.nrEmitters;
                    if (max > getHeight() / 100) {
                        scrollMenu = true;
                    } else {
                        scrollMenu = false;
                    }
                    break;
                case 5:
                    for (int i = 0; i < MapArea.nrLights; i++) {
                        objectsRendered[i] = new MenuObject(MapArea.lights[i+1]);
                    }
                    max = MapArea.nrLights;
                    if (max > getHeight() / 100) {
                        scrollMenu = true;
                    } else {
                        scrollMenu = false;
                    }
                    break;
            }

            if (startUpdate) {
                from = 0;
                if (scrollMenu) {
                    to = (int) Math.floor(getHeight() / 100);
                } else {
                    to = max;
                }
                startUpdate = false;
            }
            int j = 0;
            for (int i = from; i < to; i++) {
                objectsRendered[i].setLoc(j * 100);
                add(objectsRendered[i]);
                validate();
                repaint();
                updateUI();
                j++;
            }

            changeMenu = false;
            startUpdate = false;

        }
    }

    public void updateMenu() {
    }
}
