/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine;

import characters.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import objects.*;
import emitters.*;
import java.awt.image.*;
import lights.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.*;

/**
 *
 * @author Kevin Söderberg
 */
//Only one will be created of this class, therefore I think that it's safe to
//use public static variables that are set in MenuObject and accessed here in
//the mouseListener

public class MapArea extends JPanel implements ActionListener {

    private JScrollBar hBar, vBar;
    public static Block selectedBlock;
    public static Emitter selectedEmitter;
    public static Creature selectedNpc;
    public static LightSource selectedLight;
    public static NObject selectedObject;
    public static Map currentMap;
    //True when you change something in the MapArea, e.g scroll or add block
    private boolean updateAll;
    private boolean dragMouseAdd, dragMouseRemove;
    public static boolean toggleGrid = true;
    public static boolean toggleLight = false;
    private Image lightLayer, emitterLayer;
    private Toolkit t;
    public static int blockLayer = 1;
    private CalcLight lightCalc;
    private CalcEmitters emitterCalc;
    //0=Block, 1=Emitter, 2=Npc, using this for a switch-case in mouseListener
    public enum TypeSelect {BLOCK, CREATURE, OBJECT};
    public static TypeSelect selectedType;
    public static int selectedLayer = 0;
    private int renderFromX, renderFromY, renderToX, renderToY;
    private int selectedIndex;

    public MapArea(int width, int height) {
        currentMap = new Map(400, 200);

        

        addMouseListener(l);
        addMouseMotionListener(l);
        addMouseWheelListener(l);



        setLayout(new BorderLayout());
        setLocation(200, 0);
        setSize(width - 400, height-200);

        hBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 2, 0, currentMap.getWidth() - getPosition(getWidth()));
        add(hBar, BorderLayout.SOUTH);

        vBar = new JScrollBar(JScrollBar.VERTICAL, 0, 2, 0, currentMap.getHeight() - getPosition(getHeight()));
        add(vBar, BorderLayout.EAST);



        lightCalc = new CalcLight(getWidth(), getHeight(), 230);
        emitterCalc = new CalcEmitters(getWidth(), getHeight());
        
        
        
        
        setVisible(true);
    }
    
    public void newMapCreation() {
        lightCalc = new CalcLight(getWidth(), getHeight(), 230);
        emitterCalc = new CalcEmitters(getWidth(), getHeight());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    private int getPosition(int x) {
        return (int) Math.floor(x / 32);
    }
    
    MouseAdapter l = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent me) {
            if (me.getButton() == me.BUTTON1) {
                dragMouseAdd = true;
                if(selectedType == TypeSelect.BLOCK) {
                    currentMap.addBlock(selectedLayer, selectedBlock, getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue());
                    
                } else if(selectedType == TypeSelect.OBJECT) {
                    currentMap.addObject(selectedObject, getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), selectedLayer);
                }
                
            } else if (me.getButton() == me.BUTTON3) {
                dragMouseRemove = true;
                if(selectedType == TypeSelect.BLOCK) {
                    currentMap.removeBlock(selectedLayer, getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue());
                } else if(selectedType == TypeSelect.OBJECT) {
                    currentMap.removeObject(selectedLayer, getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue());
                }
            }
            
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (dragMouseAdd) {
                
                if(selectedType == TypeSelect.BLOCK) {
                    currentMap.addBlock(selectedLayer, selectedBlock, getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue());
                } else if(selectedType == TypeSelect.OBJECT) {
                    currentMap.addObject(selectedObject, getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), selectedLayer);
                }
                
            } else if (dragMouseRemove) {
                
                if(selectedType == TypeSelect.BLOCK) {
                    currentMap.removeBlock(selectedLayer, getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue());
                } else if(selectedType == TypeSelect.OBJECT) {
                    currentMap.removeObject(selectedLayer, getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue());
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            dragMouseRemove = false;
            dragMouseAdd = false;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.isShiftDown()) {
                hBar.setValue(hBar.getValue() + e.getWheelRotation() * 2);
            } else {
                vBar.setValue(vBar.getValue() + e.getWheelRotation() * 2);
            }
        }
    };

    public void update(int width, int height) {
        setSize(width - 450, height-200);
        
        
        
        if (hBar.getValue() > currentMap.getWidth() - getPosition(getWidth())) {
            hBar.setValue(currentMap.getWidth() - getPosition(getWidth()));
        }
        if (vBar.getValue() > currentMap.getHeight() - getPosition(getHeight())) {
            vBar.setValue(currentMap.getWidth() - getPosition(getWidth()));
        }
        hBar.setMaximum(currentMap.getWidth() - getPosition(getWidth()));
        vBar.setMaximum(currentMap.getHeight() - getPosition(getHeight()));
        renderFromX = hBar.getValue();
        renderFromY = vBar.getValue();
        renderToX = getPosition(getWidth()) + renderFromX;
        renderToY = getPosition(getHeight()) + renderFromY;
        
        java.util.List<NObject> holder = currentMap.getObjects(renderFromX, renderFromY, renderToX, renderToY);
        
        lightCalc.setLight(holder);
        lightCalc.setSize(getWidth(), getHeight());
        lightCalc.getCurrentPosition(renderFromX, renderFromY);
        
        emitterCalc.setEmitters(holder);
        emitterCalc.setSize(getWidth(), getHeight());
        emitterCalc.setLoc(renderFromX, renderFromY);
        
        if(lightCalc.done())
            lightCalc.startCalculation();
        if(emitterCalc.done())
            emitterCalc.startCalculation();
        
        
        
        
        if(lightCalc.done()) 
            lightLayer = lightCalc.getLightLayer();
        if(emitterCalc.done())
            emitterLayer = emitterCalc.getEmitterLayer();
            
        
        lightLayer = lightCalc.getLightLayer();
        emitterLayer = emitterCalc.getEmitterLayer();
        
        //emitterUpdater.setLoc(renderFromX, renderFromY, renderToX, renderToY);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g3 = (Graphics2D)g;
        g3.clearRect(0, 0, getWidth(), getHeight());
        int squareWidth = (int) Math.floor(getWidth() / 32) + 1;
        int squareHeight = (int) Math.floor(getHeight() / 32) + 1;
        
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        
        currentMap.drawToImage(g2, renderFromX, renderFromY, renderToX, renderToY);
        g3.drawImage(img, null, 0, 0);
        
        //EmiterLayer
        g3.drawImage(emitterLayer,0,0,this);
        
        //LightLayer
        if (toggleLight) {
            g3.drawImage(lightLayer, 0, 0, this);
        }

        //Grid
        if (toggleGrid) {
            for (int i = 0; i < squareHeight; i++) {
                g.drawLine(0, i * 32, getWidth(), i * 32); // (x1, y1, x2, y2)
                for (int j = 0; j < squareWidth; j++) {
                    g.drawLine(j * 32, 0, j * 32, getHeight());
                }
            }
        }
    }

    
        
    }

