/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lights;
import NScience.*;
/**
 *
 * @author Kevin Söderberg
 */
public class LightSource {
    
    private int posX, posY, lightStrength, length, from, to, id;
    private String name;
    private int i=0, a=1;
    private NVector offset;
    
    public LightSource(String name, int x, int y, int strength, int length, int from, int to) {
        this.name = name;
        posX = x;
        posY = y;
        lightStrength = strength;
        this.length = length;
        this.from = from;
        this.to = to;
        offset = new NVector(0,0);
    }
    
    public void setOffset(NVector u) {
        offset = u.copy();
    }
    
    public int getOffsetX() {
        return (int)offset.getX();
    }
    
    public int getOffsetY() {
        return (int)offset.getY();
    }
    
    public int getId() {
        return id;
    }
    
    public LightSource copy() {
        
        LightSource newLight = new LightSource(name,posX,posY,lightStrength, length, from, to);
        newLight.setOffset(offset);
        return newLight;
    }
    
    //Updates the animation for the light
    public void update() {
        
    }
    
    public void setId(int x) {
        id = x;
    }
    
    public void setLoc(int x, int y) {
        posX = x;
        posY = y;
    }
    
    public int getLength() {
        return length;
    }
    
    public String getName() {
        return name;
    }
    
    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
    
    public int getX() {
        return posX;
    }
    
    public int getY() {
        return posY;
    }
    
    public int getStrength() {
        return lightStrength;
    }
    
}
