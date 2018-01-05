/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lights;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import objects.*;
/**
 *
 * @author Kevin
 */
public class CalcLight implements Runnable {

    private Image lightLayer;
    private BufferedImage img;
    private LightSource[][] sources;
    private List<NObject> holder;
    private int width, height, alpha;
    private int currentX, currentY;
    private boolean isDone = true;
    private Thread runner;

    public CalcLight(int width, int height, int alpha) {
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

    public void setLight(List<NObject> holder) {
        this.holder = holder;
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
        
        long tStart = System.currentTimeMillis();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        img.setAccelerationPriority(1.0f);
        
        g.setColor(new Color(0, 0, 0, alpha));
        g.fillRect(0, 0, width, height);
        //java.util.List<LightSource> holder = new java.util.ArrayList<>();
        /*
        if(sources != null)
        for (int i = 0; i < sources.length; i++) {
            for (int j = 0; j < sources[i].length; j++) {
                if (sources[i][j] != null) {
                    //holder.add(sources[i][j]);
                    int x = sources[i][j].getX() - currentX;
                    int y = sources[i][j].getY() - currentY;

                    int length = sources[i][j].getLength();
                    int from = sources[i][j].getFrom();
                    int to = sources[i][j].getTo();
                    //int strength = sources[i][j].getStrength();
                    g.setColor(Color.yellow);
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT, 0.75f));
                    g.fillArc((int) (x * 32 - length / 2 - 16), (int) (y * 32 - length / 2 - 16), length, length, from, to);
                    sources[i][j].update();
                }
            }
        }
        */
        try {
            for (int i = 0; i < holder.size(); i++) {
            int x = holder.get(i).getLight().getX() - currentX;
            int y = holder.get(i).getLight().getY() - currentY;
            int offsetX = holder.get(i).getLight().getOffsetX();
            int offsetY = holder.get(i).getLight().getOffsetY();
            
            int length = holder.get(i).getLight().getLength();
            int from = holder.get(i).getLight().getFrom();
            int to = holder.get(i).getLight().getTo();
            int strength = holder.get(i).getLight().getStrength();
            
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT, 0.075f));
            int partial = 20;
            for(int k = 0; k < partial/2; k++) {
                
                g.fillArc((int) (x * 32 - length/2 +(k*partial/2) - 16)+offsetX, (int) (y * 32 - length/2 +(k*partial/2) - 16)+offsetY, length-(k*partial), length-(k*partial), from, to);
            }
            
            //g.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT, 0.75f));
            //g.fillArc((int) (x * 32 - length / 2 - 16), (int) (y * 32 - length / 2 - 16), length, length, from, to);
            holder.get(i).update();
        }
        } catch(Exception e) {
            
        }
        
        
        lightLayer = (Image) img;
        
        isDone = true;
        g.dispose();
        long tPause = System.currentTimeMillis() - tStart;
        
        
    
    }
}
