/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package emitters;

import java.awt.*;
import java.util.ArrayList;
import NScience.*;
/**
 *
 * @author Kevin Söderberg
 */
public class Emitter {
    
    private String name;
    private Color[] col;
    private int[] shapes, radius, position, spreadX, spreadY, distX, distY;
    private int[][] velX, velY;
    private int nrOfPar, amount, currentAmount, id;
    private int renderAtX, renderAtY;
    private java.util.ArrayList<Particle> particles;
    private NVector realPosition;
    private int renderX = 0, renderY = 0;
    
    public Emitter(int[] position, int nrOfPar, Color[] col, int[] shapes, int[] radius, int[] spreadX, int[] spreadY, 
                    int[] distX, int[] distY, int[][] velX, int[][] velY, String name, int amount) {
        this.position = position;
        this.nrOfPar = nrOfPar;
        this.col = col;
        this.shapes = shapes;
        this.radius = radius;
        this.velX = velX;
        this.velY = velY;
        this.name = name;
        this.amount = amount;
        this.spreadX = spreadX;
        this.spreadY = spreadY;
        this.distX = distX;
        this.distY = distY;
        renderAtX = position[0];
        renderAtY = position[1];
        currentAmount=0;
        particles = new java.util.ArrayList<>(amount);
    }
    
    public int getId() {
        return id;
    }
    
    public void setPosition(NVector v) {
        realPosition = v;
        renderAtX = (int)v.getX();
        renderAtY = (int)v.getY();
    }
    
    public void setId(int x) {
        id = x;
    }
    
    public void setColorRed(int whatPar, int value) {
        col[whatPar] = new Color(value,col[whatPar].getGreen(),col[whatPar].getBlue(),col[whatPar].getAlpha());
    }
    
    public void setColorGreen(int whatPar, int value) {
        col[whatPar] = new Color(col[whatPar].getRed(),value,col[whatPar].getBlue(),col[whatPar].getAlpha());
    }
    
    public void setColorBlue(int whatPar, int value) {
        col[whatPar] = new Color(col[whatPar].getRed(),col[whatPar].getGreen(),value,col[whatPar].getAlpha());
    }
    
    public void setColorAlpha(int whatPar, int value) {
        col[whatPar] = new Color(col[whatPar].getRed(),col[whatPar].getGreen(),col[whatPar].getBlue(),value);
    }
    
    public int getColorRed(int whatPar) {
        return col[whatPar].getRed();
    }
    
    public int getColorGreen(int whatPar) {
        return col[whatPar].getGreen();
    }
    
    public int getColorBlue(int whatPar) {
        return col[whatPar].getBlue();
    }
    
    public int getColorAlpha(int whatPar) {
        return col[whatPar].getAlpha();
    }
    
    public Color[] getColors() {
        return col;
    }
    
    public void setNrOfPar(int value) {
        nrOfPar = value;
    }
    
    public int getNrOfPar() {
        return nrOfPar;
    }
    
    public void setShape(int whatPar, int shape) {
        shapes[whatPar] = shape;
    }
    
    public int getShape(int whatPar) {
        return shapes[whatPar];
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setVelX(int whatPar, int type, int value) {
        velX[whatPar][type] = value; //0=minX,1=maxX,2=accX
    }
    
    public int getVelX(int whatPar, int type) {
        return velX[whatPar][type];
    }
    
    public void setVelY(int whatPar, int type, int value) {
        velY[whatPar][type] = value; //0=minY,1=maxY,2=accY
    }
    
    public int getVelY(int whatPar, int type) {
        return velY[whatPar][type];
    }
    
    public void setAmount(int value) {
        amount = value;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setRadius(int whatPar, int value) {
        radius[whatPar] = value;
    }
    
    public int getRadius(int whatPar) {
        return radius[whatPar];
    }
    
    public ArrayList getList() {
        return particles;
    }
    
    public void setSpreadX(int whatPar, int value) {
        spreadX[whatPar] = value;
    }
    
    public int getSpreadX(int whatPar) {
        return spreadX[whatPar];
    }
    
    public void setSpreadY(int whatPar, int value) {
        spreadY[whatPar] = value;
    }
    
    public int getSpreadY(int whatPar) {
        return spreadY[whatPar];
    }
    
    public void setDistX(int whatPar, int value) {
        distX[whatPar] = value;
    }
    
    public int getDistX(int whatPar) {
        return distX[whatPar];
    }
    
    public void setDistY(int whatPar, int value) {
        distY[whatPar] = value;
    }
    
    public int getDistY(int whatPar) {
        return distY[whatPar];
    }
    
    public Emitter copy(int x, int y) {
        
        return new Emitter(new int[]{x,y}, nrOfPar, col.clone(), shapes.clone(), radius.clone(), spreadX.clone(), spreadY.clone(), 
                            distX.clone(), distY.clone(), velX.clone(), velY.clone(), name, amount);
    }
    
    public void renderLocation(int x, int y) {
        renderX = x;
        renderY = y;
    }
    
    public void createParticle(int nr) {
        
            int type = nr%nrOfPar;
            double[] speedVector = new double[2]; //X and Y speed
            //Creates X&Y speed from min and max values and adds it to the vector
            speedVector[0] = (Math.random()*((double)velX[type][1]-(double)velX[type][0])+(double)velX[type][0])/10;
            speedVector[1] = (Math.random()*((double)velY[type][1]-(double)velY[type][0])+(double)velY[type][0])/10;
            //Adds X&Y acceleration values to a vector
            double[] accVector = new double[2];
            
            
                
            
            accVector[0] = ((double)velX[type][2]*-1)/5;
            accVector[1] = ((double)velY[type][2])/5;
            
            
            int[] pos = new int[2];
            pos[0] = renderAtX + distX[type] + (int)Math.floor(Math.random()*-2*spreadX[type]+spreadX[type])-(renderX*32);
            pos[1] = renderAtY + distY[type] + (int)Math.floor(Math.random()*-2*spreadY[type]+spreadY[type])-(renderY*32);

            
            //PosX, PosY, vector, accel, color, shape, radius
            //System.out.println(nrOfPar);
            //System.out.println(particles.size());
            particles.add(new Particle(pos[0], pos[1], speedVector, accVector, 
                                        col[type], shapes[type], radius[type]));
        
    }
    
   
    public void update() {
        for(int i = 0; i < currentAmount; i++) {
            particles.get(i).update();
            if(particles.get(i).getLifetime() <= 0) {
                particles.remove(i);
                currentAmount--;
                i--;
            }
        }
        if(currentAmount < amount) {
            for(int i = 0; i < 45; i++) {
                createParticle(i);
                currentAmount++;
            }   
        }
        
    }
    
    public int getX() {
        return renderAtX;
    }
    
    public int getY() {
        return renderAtY;
    }
    
    public void draw(Graphics g) {
        for(int i = 0; i < currentAmount; i++) {
            particles.get(i).draw(g);
        }
    }
    
    public void setRenderAt() {
        renderAtX = position[0];
        renderAtY = position[1];
    }
    
    public void setLoc(int x, int y) {
        renderAtX = (position[0] - x)*32+16;
        renderAtY = (position[1] - y)*32+32;

    }
    
}
