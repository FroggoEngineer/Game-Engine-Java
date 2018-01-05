/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emitters;
import java.awt.*;
/**
 *
 * @author Kevin Söderberg
 */
public class Particle {
    
    private int x, y;
    private final int firstY;
    private double posX, posY;
    private double[] vector, accel;
    private double timeToLive;
    private Color col;
    private int shape, radius;
    
    //PosX, PosY, vector, accel, color, shape
    public Particle(int x, int y, double[] vector, double[] accel, Color col, int shape, int radius) {
        posX = this.x = x;
        posY = this.y = firstY = y;
        this.vector = vector;
        timeToLive = Math.random();
        this.accel = accel;
        this.col = col;
        this.shape = shape;
        this.radius = radius;
    }
    
    public void update() {
        timeToLive -= 0.016;
        vector[0] += accel[0]*0.016;
        vector[1] += accel[1]*0.016;
        posX += vector[0];
        posY += vector[1];
        x =(int) Math.floor(posX);
        y =(int) Math.floor(posY);
    }
    
    public void draw(Graphics g) {
        g.setColor(col);
        if(shape == 0)
            g.fillOval(x-(int)Math.floor(radius/2), y-(int)Math.floor(radius/2), radius, radius);
        else
            g.fillRect(x-(int)Math.floor(radius/2), y-(int)Math.floor(radius/2), radius, radius);
        g.setColor(Color.black);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public double[] getVector() {
        return vector;
    }
    
    public void setVecX(double vecX) {
        vector[0] = vecX;
    }
    
    public void setVecY(double vecY) {
        vector[1] = vecY;
    }
    
    public double getLifetime() {
        return timeToLive;
    }
}
