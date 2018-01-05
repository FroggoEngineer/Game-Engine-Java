/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emitters;

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
public class CalcEmitters implements Runnable {

    private Thread runner;
    private List<NObject> emitters = new LinkedList<>();
    private int renderFromX, renderToX, renderFromY, renderToY;
    private int width, height;
    private Image emitterLayer;
    private BufferedImage img;
    private boolean isDone = true;
    
    public CalcEmitters(int width, int height) {
        this.width = width;
        this.height = height;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        img.setAccelerationPriority(1.0f);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);
        emitterLayer = (Image) img;
        g.dispose();
    }
    
    public Image getEmitterLayer() {
        return img;
    }
    
    public void setEmitters(List emitters) {
        this.emitters = emitters;
    }
    
    @Override
    public void run() {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        img.setAccelerationPriority(1.0f);
        
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width, height);
        for(NObject s : emitters) {
            s.getEmitter().update();
            s.getEmitter().renderLocation(renderFromX, renderFromY);
            s.getEmitter().draw(g);
        }
        emitterLayer = (Image) img;
        g.dispose();
        isDone = true;
        
    }
    
    public boolean done() {
        return isDone;
    }
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void startCalculation() {
        runner = new Thread(this);
        isDone = false;
        runner.start();
    }

    
    
    public void setLoc(int renderFromX, int renderFromY) {
        this.renderFromX = renderFromX;
        this.renderFromY = renderFromY;
       
    }
}

