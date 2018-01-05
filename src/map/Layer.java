/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;
import emitters.Emitter;
import characters.Creature;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import objects.*;
import java.util.*;

/**
 *
 * @author Kevin
 */
public class Layer {
    
    public enum Type {MAIN, SECONDARY};
    private Type layerType;
    private int width, height;
    private NObject[][] objects;
    private Creature[][] characters;
    private Block[][] blocks;
    private BufferedImage staticBlockImage;
    
    public Layer(int width, int height, Type type) {
        this.width = width;
        this.height = height;
        objects = new NObject[width][height];
        characters = new Creature[width][height];
        blocks = new Block[width][height];
        for(int i=0; i < width; i++)
            for(int j = 0; j < height; j++) {
                objects[i][j] = null;
                characters[i][j] = null;
                blocks[i][j] = null;
            }
        layerType = type;
    }
    
    public boolean objectAtPos(int x, int y) {
        if(objects[x][y] == null)
            return false;
        else
            return true;
    }
    
    public List getObjects(int fromX, int fromY, int toX, int toY) {
        List<NObject> objectsForRender = new ArrayList<>();
        for(int i = fromX; i < toX; i++)
            for(int j = fromY; j < toY; j++) {
                if(objects[i][j] != null)
                    objectsForRender.add(objects[i][j]);
            }
        return objectsForRender;
    }
    
    public void addObject(NObject obj, int x, int y) {
        objects[x][y] = obj.copy();
        objects[x][y].setPosition(x, y);
        for(int i = 1; i < obj.getWidth(); i++)
            for(int j = 1; j < obj.getHeight(); j++)
                objects[x+i][y+j] = objects[x][y];
    }
    public void removeObject(int x, int y) {
        if(objects[x][y] != null) {
            int wi = objects[x][y].getWidth();
            int hi = objects[x][y].getHeight();
            for(int i=0; i< wi; i++)
                for(int j=0; j<hi;j++)
                    objects[x+i][y+j] = null;
        }
        
    }
    
    public boolean creatureAtPos(int x, int y) {
        if(characters[x][y] == null)
            return false;
        else
            return true;
    }
    
    public void addCreature(Creature cre, int x, int y) {
        characters[x][y] = cre;
    }
    
    public boolean blockAtPos(int x, int y) {
        if(blocks[x][y] == null)
            return false;
        else
            return true;
    }
    
    public void removeBlock(int x, int y) {
        if(blocks[x][y] != null) {
            int wi = blocks[x][y].getWidth();
            int hi = blocks[x][y].getHeight();
            for(int i=0; i< wi; i++)
                for(int j=0; j<hi;j++)
                    blocks[x+i][y+j] = null;
        }
        
    }
    
    public void addBlock(Block blo, int x, int y) {
        blocks[x][y] = blo.copy();
        blocks[x][y].setPosition(x, y);
        for(int i = 1; i < blo.getWidth(); i++)
            for(int j = 1; j < blo.getHeight(); j++)
                blocks[x+i][y+j] = blocks[x][y];
    }
    
    public Block[][] getBlocks() {
        return blocks;
    }
    
    public NObject[][] getObjects() {
        return objects;
    }
    
    public void setLayerType(Type t) {
        layerType = t;
    }
    
    public void drawStaticBackground() {
        Graphics2D g2 = (Graphics2D)staticBlockImage.getGraphics();
        for(int i = 0; i < blocks.length; i++) {
            for(int j = 0; j < blocks[i].length; j++) {
                if(blockAtPos(i, j))
                    blocks[i][j].draw(g2);
            }
        }
    }
    
    public void drawToImage(Graphics2D g2, int fromX, int fromY, int toX, int toY) {
        int startX = fromX;
        int startY = fromY;
        for(int i = fromX; i < toX; i++)
            for(int j = fromY; j < toY; j++) {
                if(blocks[i][j] != null)
                    g2.drawImage(blocks[i][j].getImage(), (blocks[i][j].getX()-startX)*32, 
                            (blocks[i][j].getY()-startY)*32, null);
                if(objects[i][j] != null) {
                    g2.drawImage(objects[i][j].getImage(), (objects[i][j].getX()-startX)*32,
                            (objects[i][j].getY()-startY)*32, null);
                }
            }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Type getType() {
        return layerType;
    }
}
