/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;
import java.awt.Graphics2D;
import java.util.*;
import objects.*;
/**
 *
 * @author Kevin
 */
public class Map {
    
    private List<Layer> layers;
    private String name;
    private boolean addDarkness;
    
    
    public Map(int x, int y) {
        layers = new LinkedList<>();
        Layer main = new Layer(x,y,Layer.Type.MAIN);
        layers.add(main);
    }
    
    public List getObjects(int fromX, int fromY, int toX, int toY) {
        return layers.get(layers.size()-1).getObjects(fromX, fromY, toX, toY);
    }
    
    public void addObject(NObject obj, int x, int y, int layer) {
        layers.get(layer).addObject(obj, x, y);
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getWidthOfLayer(int i) {
        return layers.get(i).getWidth();
    }
    
    
    
    public Layer getLayer(int i) {
        return layers.get(i);
    }
    
    public int getHeightOfLayer(int i) {
        return layers.get(i).getHeight();
    }
    
    public void setDarkness(boolean dark) {
        addDarkness = dark;
    }
    
    public void addLayer(int width, int height, Layer.Type type) {
        layers.add(0,new Layer(width, height, type));
    }
    
    public int getWidth() {
        int width=0;
        for(Layer s : layers) {
            if(s.getType() == Layer.Type.MAIN)
                width = s.getWidth();
            
        }
        return width;
    }
    
    public int getHeight() {
        int height=0;
        for(Layer s : layers) {
            if(s.getType() == Layer.Type.MAIN)
                height = s.getHeight();
        }
        return height;
    }
    
    public int getNrOfLayers() {
        return layers.size();
    }
    
    public void drawStaticLayers() {
        for(int i = 0; i < layers.size(); i++) {
            layers.get(i).drawStaticBackground();
        }
    }
    
    public void addBlock(int layerNr, Block blo, int x, int y) {
        int addX = x;
        int addY = y;
        if(layers.get(layerNr).getType() == Layer.Type.SECONDARY) {
            double relativeX = (double)layers.get(layerNr).getWidth()/(double)getWidth();
            double relativeY = (double)layers.get(layerNr).getHeight()/(double)getHeight();
            addX = (int)Math.floor(x*relativeX);
            addY = (int)Math.floor(y*relativeY);
        }
        
        layers.get(layerNr).addBlock(blo, addX, addY);
    }
    
    public void removeBlock(int layerNr, int x, int y) {
        layers.get(layerNr).removeBlock(x, y);
    }
    
    public void removeObject(int layerNr, int x, int y) {
        layers.get(layerNr).removeObject(x,y);
    }
    
    public void drawToImage(Graphics2D g2, int renderFromX, int renderFromY, 
                            int renderToX, int renderToY) {
        int renderAmountX = renderToX - renderFromX;
        int renderAmountY = renderToY - renderFromY;
        for(Layer s : layers) {
            if(s.getType() == Layer.Type.SECONDARY) {
                double relativeX = (double)s.getWidth()/(double)getWidth();
                double relativeY = (double)s.getHeight()/(double)getHeight();
                int renderX = (int)Math.floor(renderFromX*relativeX);
                int renderY = (int)Math.floor(renderFromY*relativeY);
                
                
                s.drawToImage(g2, renderX, renderY, renderAmountX+renderX, renderAmountY+renderY);
            } else if(s.getType() == Layer.Type.MAIN) {
                s.drawToImage(g2, renderFromX, renderFromY, renderToX, renderToY);
            }
            
        }
    }
}
