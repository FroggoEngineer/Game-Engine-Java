/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;
import game.engine.ItemHolder;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
/**
 *
 * @author Kevin
 */
public class Block {
    /*
     * Enum for deciding the inclination
     * Width and height in blocks, int[2]
     * getYatX(int x)
     *      Returns the Y of the block at X calculated from inclination
     * Name for the block, String
     * Icon that represents it, ImageIcon made from the Texture
     * Texture for the block, BufferedImage;
     */
    
    public enum DirectionTop {LEFT, FLAT, RIGHT, SPECIAL};
    public enum DirectionBot {LEFT, FLAT, RIGHT, SPECIAL};
    private int posX, posY;
    private int[] blockSize = new int[2];
    private int[] yAtXTop, yAtXBot;
    private DirectionTop dirT;
    private DirectionBot dirB;
    private Image tex;
    private boolean solid;
    private String name;
    private int id;
    
    public Block(int width, int height, DirectionTop dirT, DirectionBot dirB, 
                    Image tex, boolean solid, String name) {
        blockSize[0] = width; 
        blockSize[1] = height;
        this.dirT = dirT;
        this.dirB = dirB;
        yAtXTop = new int[blockSize[0]*32];
        yAtXBot = new int[blockSize[0]*32];
        calcInclination();
        this.tex = tex;
        this.solid = solid;
        this.name = name;
    }
    
    public int getX() {
        return posX;
    }
    
    public int getY() {
        return posY;
    }
    
    public void setId(int i) {
        id = i;
    }
    
    public int getId() {
        return id;
    }
    
    public int getWidth() {
        return blockSize[0];
    }
    
    public int getHeight() {
        return blockSize[1];
    }
    
    public Block copy() {
        Block newBlock = new Block(blockSize[0], blockSize[1], dirT, dirB, tex, solid, name);
        newBlock.setId(id);
        return newBlock;
    }
    
    public void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }
    
    public Block(int width, int height, int[] yAtXT, int[] yAtXB, 
                    BufferedImage tex, boolean solid, String name) {
        blockSize[0] = width; 
        blockSize[1] = height;
        this.dirT = DirectionTop.SPECIAL;
        this.dirB = DirectionBot.SPECIAL;
        yAtXTop = yAtXT.clone();
        yAtXBot = yAtXB.clone();
        this.tex = tex;
        this.solid = solid;
    }
    
    public Block(int width, int height, BufferedImage tex, boolean solid, String name) {
        blockSize[0] = width; 
        blockSize[1] = height;
        this.dirT = null;
        this.dirB = null;
        yAtXTop = null;
        yAtXBot = null;
        this.tex = tex;
        this.solid = solid;
    }
    
    public String getName() {
        return name;
    }
    
    public int getYatXTop(int x) {
        return yAtXTop[x];
    }
    public int getYatXBot(int x) {
        return yAtXBot[x];
    }
    
    public boolean isSolid() {
        return solid;
    }
    
    private void calcInclination() {
        //Derivative
        double deriv = (double)blockSize[1]/(double)blockSize[0];
        //Split it into blockSize[0]*32 parts, array length for yAtX
        deriv /= yAtXTop.length;
        //Top inclination
        if(dirT == DirectionTop.LEFT) {
            yAtXTop[0] = 0;
            for(int i = 1; i < yAtXTop.length; i++) {
                yAtXTop[i] = yAtXTop[i-1] + (int)Math.floor(deriv);
            }
        } else if (dirT == DirectionTop.RIGHT) {
            yAtXTop[yAtXTop.length-1] = 0;
            for(int i = yAtXTop.length-2; i >= 0; i--) {
                yAtXTop[i] = yAtXTop[i+1] + (int)Math.floor(deriv);
            }
        } else {
            for(int i = 0; i < yAtXTop.length; i++) {
                yAtXTop[i] = 0;
            }
        }
        //Bottom inclination
        if(dirB == DirectionBot.LEFT) {
            yAtXBot[0] = 0;
            for(int i = 1; i < yAtXBot.length; i++) {
                yAtXBot[i] = yAtXBot[i-1] + (int)Math.floor(deriv);
            }
        } else if (dirB == DirectionBot.RIGHT) {
            yAtXBot[yAtXBot.length-1] = 0;
            for(int i = yAtXBot.length-2; i >= 0; i--) {
                yAtXBot[i] = yAtXBot[i+1] + (int)Math.floor(deriv);
            }
        } else {
            for(int i = 0; i < yAtXBot.length; i++) {
                yAtXBot[i] = blockSize[1]*32;
            }
        }
    }
    
    public ImageIcon getIcon() {
        ImageIcon ic = new ImageIcon(tex);
        return ic;
    }
    
    public void draw(Graphics2D g2) {
        g2.drawImage(ItemHolder.blocks.get(id).getImage(), posX, posY, null);
    }
    
    public Image getImage() {
        return tex;
    }
    
}
