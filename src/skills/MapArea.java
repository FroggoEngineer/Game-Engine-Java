/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skills;

import npcs.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import objects.*;
import emitters.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import lights.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mapcreator.MapArea.currentMap;

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
    public static Npc selectedNpc;
    public static LightSource selectedLight;
    public static Map currentMap;
    //True when you change something in the MapArea, e.g scroll or add block
    private boolean updateAll;
    private boolean dragMouseAdd, dragMouseRemove;
    public static boolean toggleGrid = true;
    public static boolean toggleLight = false;
    private Image lightLayer;
    private Toolkit t;
    public static Block backBlocks[], frontBlocks[], blockBlocks[];
    public static LightSource lights[];
    public static Npc npcs[];
    public static Emitter emitters[];
    public static int nrBack, nrFront, nrBlocks, nrNpc, nrEmitters, nrLights;
    public static int blockLayer = 1;
    private calcLight lightCalc;
    private emitterUpdate emitterUpdater;
    //0=Block, 1=Emitter, 2=Npc, using this for a switch-case in mouseListener
    public static int selectedType;
    private int renderFromX, renderFromY, renderToX, renderToY;

    public MapArea(int width, int height) {
        currentMap = new Map(400, 200);
        backBlocks = new Block[256];
        frontBlocks = new Block[256];
        blockBlocks = new Block[256];
        npcs = new Npc[256];
        lights = new LightSource[256];
        emitters = new Emitter[256];
        //Loads blocks, npcs etc
        loadObjects();

        addMouseListener(l);
        addMouseMotionListener(l);
        addMouseWheelListener(l);



        setLayout(new BorderLayout());
        setLocation(200, 0);
        setSize(width - 400, height);

        hBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 2, 0, currentMap.getWidth() - getPosition(getWidth()));
        add(hBar, BorderLayout.SOUTH);

        vBar = new JScrollBar(JScrollBar.VERTICAL, 0, 2, 0, currentMap.getHeight() - getPosition(getHeight()));
        add(vBar, BorderLayout.EAST);



        lightCalc = new calcLight(getWidth(), getHeight(), 230);
        emitterUpdater = new emitterUpdate();
        emitterUpdater.setLoc(renderFromX, renderFromY, renderToX, renderToY);
        emitterUpdater.startCalculation();

        setVisible(true);
    }
    
    public void newMapCreation() {
        lightCalc = new calcLight(getWidth(), getHeight(), 230);
        emitterUpdater = new emitterUpdate();
        emitterUpdater.setLoc(renderFromX, renderFromY, renderToX, renderToY);
        emitterUpdater.startCalculation();
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
                switch (selectedType) { //0-2 are blocks, 3 npc, 4 emitter, 5 lightsource.
                    case 0:
                        if(selectedBlock.getId()==1 && blockLayer==1)
                            currentMap.setBlock(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(),
                                blockBlocks[(int)Math.floor(12*Math.random()+2)], blockLayer);
                        else
                            currentMap.setBlock(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(),
                                    selectedBlock, blockLayer);
                        break;
                    case 1:
                        currentMap.setEmitter(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), selectedEmitter);
                        break;
                    case 2:
                        //NPC
                        break;
                    case 3:
                        currentMap.setLightSource(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), selectedLight);
                        break;
                    case 4:
                        currentMap.setLightSource(getPosition(me.getX()), getPosition(me.getY()), selectedLight);
                        break;
                }
            } else if (me.getButton() == me.BUTTON3) {
                dragMouseRemove = true;
                switch (selectedType) {
                    case 0:
                        currentMap.setBlock(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(),
                                null, blockLayer);
                        break;
                    case 1:
                        currentMap.setEmitter(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), null);
                        System.out.println("WE ARE PLACING AN EMITTER YOU BASTARD!");
                        break;
                    case 2:
                        //NPC
                        break;
                    case 3:
                        currentMap.setLightSource(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), null);
                        break;

                }
            }

        }

        @Override
        public void mouseDragged(MouseEvent me) {
            if (dragMouseAdd) {
                switch (selectedType) {
                    case 0:
                        if(selectedBlock.getId()==1 && blockLayer==1)
                            currentMap.setBlock(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(),
                                blockBlocks[(int)Math.floor(13*Math.random()+1)], blockLayer);
                        else
                            currentMap.setBlock(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(),
                                    selectedBlock, blockLayer);
                        break;
                    case 1:
                        currentMap.setEmitter(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), selectedEmitter);
                        break;
                    case 2:
                        //NPC
                        break;
                    case 3:
                        currentMap.setLightSource(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), selectedLight);
                        break;
                }
            } else if (dragMouseRemove) {
                switch (selectedType) {
                    case 0:
                        currentMap.setBlock(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(),
                                null, blockLayer);
                        break;
                    case 1:
                        currentMap.setEmitter(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), null);
                        break;
                    case 2:
                        //NPC
                        break;
                    case 3:
                        currentMap.setLightSource(getPosition(me.getX()) + hBar.getValue(), getPosition(me.getY()) + vBar.getValue(), null);
                        break;

                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if (me.getButton() == me.BUTTON1) {
                dragMouseAdd = false;
            } else if (me.getButton() == me.BUTTON2) {
                dragMouseRemove = false;
            }
            switch (selectedType) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
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
        setSize(width - 450, height);
        lightCalc.setSize(getWidth(), getHeight());
        if (lightCalc.done()) {
            lightLayer = lightCalc.getLightLayer();
            lightCalc.getCurrentPosition(hBar.getValue(), vBar.getValue());
            lightCalc.setLight(currentMap.getLights());
            lightCalc.startCalculation();
        }
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
        emitterUpdater.setLoc(renderFromX, renderFromY, renderToX, renderToY);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        int squareWidth = (int) Math.floor(getWidth() / 32) + 1;
        int squareHeight = (int) Math.floor(getHeight() / 32) + 1;

        //Background
        //Backobjects
        for (int i = renderFromX; i < renderToX; i++) {
            for (int j = renderFromY; j < renderToY; j++) {
                currentMap.drawBlock(g, i, j, i - renderFromX, j - renderFromY, 0);
            }
        }
        //Middleobjects
        for (int i = renderFromX; i < renderToX; i++) {
            for (int j = renderFromY; j < renderToY; j++) {
                currentMap.drawBlock(g, i, j, i - renderFromX, j - renderFromY, 1);
            }
        }
        //Emitters
        for (int i = renderFromX; i < renderToX; i++) {
            for (int j = renderFromY; j < renderToY; j++) {
                if (currentMap.getEmitters(i, j) != null) {
                    currentMap.getEmitters(i, j).draw(g);
                }
            }
        }
        //Frontobjects
        for (int i = renderFromX; i < renderToX; i++) {
            for (int j = renderFromY; j < renderToY; j++) {
                currentMap.drawBlock(g, i, j, i - renderFromX, j - renderFromY, 2);
            }
        }
        //LightLayer
        if (toggleLight) {
            g.drawImage(lightLayer, 0, 0, this);
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

    private void loadObjects() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("data/objects/blockids.inf"));
            String line;
            int i = 1;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                blockBlocks[i] = new Block(splitline[0], Integer.valueOf(splitline[1]), Integer.valueOf(splitline[2]),
                        t.getImage(splitline[3]), i, Integer.valueOf(splitline[4]), Integer.valueOf(splitline[5]));
                i++;
            }
            nrBlocks = i-1;
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(ButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            br = new BufferedReader(new FileReader("data/objects/backobjectids.inf"));
            String line;
            int i = 1;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                backBlocks[i] = new Block(splitline[0], Integer.valueOf(splitline[1]), Integer.valueOf(splitline[2]),
                        t.getImage(splitline[3]), i, Integer.valueOf(splitline[4]), Integer.valueOf(splitline[5]));
                i++;
            }
            nrBack = i-1;
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(ButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            br = new BufferedReader(new FileReader("data/objects/frontobjectids.inf"));
            String line;
            int i = 1;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                frontBlocks[i] = new Block(splitline[0], Integer.valueOf(splitline[1]), Integer.valueOf(splitline[2]),
                        t.getImage(splitline[3]), i, Integer.valueOf(splitline[4]), Integer.valueOf(splitline[5]));
                i++;
            }
            nrFront = i-1;
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(ButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            br = new BufferedReader(new FileReader("data/npcs/npcids.inf"));
            String line;
            int i = 1;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                npcs[i] = new Npc(splitline[0], t.getImage(splitline[1]), t.getImage(splitline[2]), Integer.valueOf(splitline[3]), Integer.valueOf(splitline[4]), Integer.valueOf(splitline[5]), i);
                i++;
            }
            nrNpc = i-1;
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(ButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            br = new BufferedReader(new FileReader("data/lights/lightids.inf"));
            String line;
            int i = 1;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                lights[i] = new LightSource(splitline[0], 0, 0, Integer.valueOf(splitline[1]), Integer.valueOf(splitline[2]), Integer.valueOf(splitline[3]), Integer.valueOf(splitline[4]));
                lights[i].setId(i);
                i++;
            }
            nrLights = i-1;
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(ButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            br = new BufferedReader(new FileReader("data/emitters/emitterids.inf"));
            String line;
            int i = 1;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                int pIS = 1;
                int nrOfPars = Integer.valueOf(splitline[0]);
                Color[] col = new Color[nrOfPars];
                for(int j = 0; j < nrOfPars; j++) {
                    int r = Integer.valueOf(splitline[pIS++]);
                    int g = Integer.valueOf(splitline[pIS++]);
                    int b = Integer.valueOf(splitline[pIS++]);
                    int a = Integer.valueOf(splitline[pIS++]);
                    col[j] = new Color(r,g,b,a);
                }
                int[] shapes = new int[nrOfPars];
                for(int j = 0; j < nrOfPars; j++) {
                    shapes[j] = Integer.valueOf(splitline[pIS++]);
                }
                int[] radius = new int[nrOfPars];
                for(int j = 0; j < nrOfPars; j++) {
                    radius[j] = Integer.valueOf(splitline[pIS++]);
                }
                int[] spreadX = new int[nrOfPars];
                for(int j = 0; j < nrOfPars; j++) {
                    spreadX[j] = Integer.valueOf(splitline[pIS++]);
                }
                int[] spreadY = new int[nrOfPars];
                for(int j = 0; j < nrOfPars; j++) {
                    spreadY[j] = Integer.valueOf(splitline[pIS++]);
                }
                int[] distX = new int[nrOfPars];
                for(int j = 0; j < nrOfPars; j++) {
                    distX[j] = Integer.valueOf(splitline[pIS++]);
                }
                int[] distY = new int[nrOfPars];
                for(int j = 0; j < nrOfPars; j++) {
                    distY[j] = Integer.valueOf(splitline[pIS++]);
                }
              
                int[][] velX = new int[nrOfPars][3];
                for(int j = 0; j < nrOfPars; j++) {
                    velX[j][0] = Integer.valueOf(splitline[pIS++]);
                    velX[j][1] = Integer.valueOf(splitline[pIS++]);
                    velX[j][2] = Integer.valueOf(splitline[pIS++]);
                }
                int[][] velY = new int[nrOfPars][3];
                for(int j = 0; j < nrOfPars; j++) {
                    velY[j][0] = Integer.valueOf(splitline[pIS++]);
                    velY[j][1] = Integer.valueOf(splitline[pIS++]);
                    velY[j][2] = Integer.valueOf(splitline[pIS++]);
                }
                String nameOfEmit = splitline[pIS++];
                System.out.println(nameOfEmit);
                int amount = Integer.valueOf(splitline[pIS++]);
                System.out.println(amount);
                int[] pos = {0,0};
                emitters[i] = new Emitter(pos,nrOfPars, col, shapes,radius,spreadX,spreadY,
                                            distX,distY,velX,velY,nameOfEmit,amount);
                emitters[i].setId(i);
                i++;
            }
            nrEmitters = i-1;
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(ButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
class emitterUpdate implements Runnable {

    private Thread runner;
    private Emitter[][] emitters;
    private int renderFromX, renderToX, renderFromY, renderToY;
    
    public emitterUpdate() {
        this.emitters = currentMap.getEmitters();
    }
    
    
    @Override
    public void run() {
        while(true) {
        for (int i = renderFromX; i < renderToX; i++) {
            for (int j = renderFromY; j < renderToY; j++) {
                if (emitters[i][j] != null) {
                    emitters[i][j].setLoc(renderFromX, renderFromY);
                    
                    emitters[i][j].update();
                }
            }
        }
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
                
            }
        }
    }

    public void startCalculation() {
        runner = new Thread(this);
        runner.start();
    }

    public void setLoc(int renderFromX, int renderFromY, int renderToX, int renderToY) {
        this.renderFromX = renderFromX;
        this.renderFromY = renderFromY;
        this.renderToX = renderToX;
        this.renderToY = renderToY;
    }
}

class calcLight implements Runnable {

    private Image lightLayer;
    private BufferedImage img;
    private LightSource[][] sources;
    private static int width, height, alpha;
    private int currentX, currentY;
    private boolean isDone = true;
    private Thread runner;

    public calcLight(int width, int height, int alpha) {
        this.width = width;
        this.height = height;
        this.alpha = alpha;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, width, height);
        lightLayer = (Image) img;
    }

    public boolean done() {
        return isDone;
    }

    public void setLight(LightSource[][] sources) {
        this.sources = sources;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //Current upper-left block position being rendered
    public void getCurrentPosition(int x, int y) {
        currentX = x;
        currentY = y;
    }

    public Image getLightLayer() {
        return lightLayer;
    }

    public void startCalculation() {
        isDone = false;
        runner = new Thread(this);
        runner.start();
    }

    @Override
    public void run() {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        //System.out.println("New light calculation in progress");
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, width, height);
        java.util.List<LightSource> holder = new java.util.ArrayList<>();

        for (int i = 0; i < sources.length; i++) {
            for (int j = 0; j < sources[i].length; j++) {
                if (sources[i][j] != null) {
                    holder.add(sources[i][j]);
                }
            }
        }

        for (int i = 0; i < holder.size(); i++) {
            int x = holder.get(i).getX() - currentX;
            int y = holder.get(i).getY() - currentY;

            int length = holder.get(i).getLength();
            int from = holder.get(i).getFrom();
            int to = holder.get(i).getTo();
            int strength = holder.get(i).getStrength();
            g.setXORMode(new Color(255, 255, 255, strength));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.7f));
            g.fillArc((int) (x * 32 - length / 2 - 16), (int) (y * 32 - length / 2 - 16), length, length, from, to);
        }

        lightLayer = (Image) img;
        isDone = true;
        g.dispose();
    }
}