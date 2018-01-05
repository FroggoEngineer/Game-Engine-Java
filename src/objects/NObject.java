/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;
import lights.LightSource;
import emitters.Emitter;
import java.awt.image.*;
import NScience.*;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
/**
 *
 * @author Kevin
 */
public class NObject {
    /*
     * 
     * blockSize
     */
    
    private NVector positionInGrid;
    private NVector coordinatePosition;
    private Emitter emitters;
    private boolean activeEmitter;
    private LightSource light;
    private boolean activeLight;
    private Image tex;
    private NVector emitterPosition;
    private NVector lightPosition;
    private NVector texturePosition;
    private int[] blockSize;
    private String name;
    
    public NObject(Emitter emit, LightSource light, Image tex,
            int width, int height, String name) {
        emitters = emit;
        this.light = light;
        this.tex = tex;
        blockSize = new int[2];
        blockSize[0] = width;
        blockSize[1] = height;
        
        emitterPosition = new NVector(emit.getX(), emit.getY());
        this.name = name;
        this.light.setLoc(width, height);
    }
    
    public ImageIcon getIcon() {
        ImageIcon ic = new ImageIcon(tex);
        return ic;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX() {
        return (int)positionInGrid.getX();
    }
    
    public int getY() {
        return (int)positionInGrid.getY();
    }
    
    public Image getImage() {
        return tex;
    }
    
    public int getWidth() {
        return blockSize[0];
    }
    
    public int getHeight() {
        return blockSize[1];
    }
    
    public void setLightOffset(int x, int y) {
        lightPosition = new NVector(x,y);
        light.setOffset(lightPosition);
    }
    
    //Sets the position of the object
    public void setPosition(int x, int y) {
        positionInGrid = new NVector(x, y);
        coordinatePosition = positionInGrid.copy();
        coordinatePosition.mult(32);
        light.setLoc((int)positionInGrid.getX()+1, (int)positionInGrid.getY()+1);
        
    }
    
    //Updates emitter, light and texture positions
    public void update() {
        NVector v = coordinatePosition.copy();
        v.add(emitterPosition);
        emitters.setPosition(v);
    }
    
    public void switchEmitter() {
        if(activeEmitter)
            activeEmitter = false;
        else
            activeEmitter = true;
    }
    
    public NObject copy() {
        NObject newObject = new NObject(emitters.copy((int)positionInGrid.getX(), (int)positionInGrid.getY()),
                            light.copy(), tex, blockSize[0], blockSize[1], name);
        newObject.setPosition((int)positionInGrid.getX(), (int)positionInGrid.getY());
        newObject.update();
        return newObject;
    }
    
    public void switchLight() {
        if(activeLight)
            activeLight = false;
        else
            activeLight = true;
    }
    
    //Pre-check before CalcLight requests the LightSource
    public boolean activeLight() {
        return activeLight;
    }
    
    public LightSource getLight() {
        return light;
    }
    
    //Pre-check before CalcEmitter requests the Emitter
    public boolean activeEmitter() {
        return activeEmitter;
    }
    
    public Emitter getEmitter() {
        return emitters;
    }
    
    //If the object isn't movable, you only need to render the picture once
    //to the static image
    public void drawToStatic(Graphics2D g2) {
        g2.drawImage(tex, (int)(coordinatePosition.getX()+texturePosition.getX()), 
                        (int)(coordinatePosition.getY()+texturePosition.getY()), null);
    }
    
}
