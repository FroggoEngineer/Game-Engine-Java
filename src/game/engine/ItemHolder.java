/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine;

import emitters.Emitter;
import java.awt.Toolkit;
import java.io.*;
import lights.LightSource;
import objects.Block;
import java.util.*;
import objects.*;
import characters.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kevin
 */
public class ItemHolder {
    
    //Lists for all types of items
    public static List<Block> blocks = new LinkedList<>();
    public static List<NObject> objects  = new LinkedList<>();
    private static List<Background> backgrounds = new LinkedList<>();
    private static List<Creature> creatures = new LinkedList<>();
    private static List<LightSource> lights = new LinkedList<>();
    private static List<Emitter> emitters = new LinkedList<>();
    public static boolean loaded = false;
    /*
     * Static class, contains all items for the current project.
     * Other classes call on this to get copies of items.
     */
    
    public static BufferedImage toBufferedImage(Image img)
{
    if (img instanceof BufferedImage)
    {
        return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
}
    
    public static void loadObjects(String projectName) {
        BufferedReader br;
        Toolkit t;
        String s;
        
        
        //Blocks
        try {
            
            s = "data/" + projectName + "/blocks/blockids.inf";
            br = new BufferedReader(new FileReader(s));
            
            String line;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                
                String splitline[] = line.split(" ");
                
                Block test = new Block(Integer.parseInt(splitline[0]), Integer.parseInt(splitline[1]),
                        Block.DirectionTop.valueOf(splitline[2]),Block.DirectionBot.valueOf(splitline[3]),
                        t.getImage(splitline[4]), Boolean.valueOf(splitline[5]), splitline[6]);
                test.setId(blocks.size());
                blocks.add(test);
                
            }
            br.close();
        } catch (Exception ex) {System.out.println(ex);}
        
        //Backgrounds
        try {
            s = "data/" + projectName + "/backgrounds/backgroundids.inf";
            br = new BufferedReader(new FileReader(s));
            String line;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                backgrounds.add(new Background());
            }
            br.close();
        } catch (Exception ex) {}
        
        
        
        //Light sources
        try {
            s = "data/" + projectName + "/lights/lightids.inf";
            br = new BufferedReader(new FileReader(s));
            String line;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                lights.add(new LightSource(splitline[0], 0, 0, Integer.valueOf(splitline[1]), 
                        Integer.valueOf(splitline[2]), Integer.valueOf(splitline[3]), 
                        Integer.valueOf(splitline[4])));
            }
            br.close();
        } catch (Exception ex) {}
        
        try {
            br = new BufferedReader(new FileReader("data/" + projectName + "/emitters/emitterids.inf"));
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
                int amount = Integer.valueOf(splitline[pIS++]);
                int[] pos = {0,0};
                emitters.add(new Emitter(pos,nrOfPars, col, shapes,radius,spreadX,spreadY,
                                            distX,distY,velX,velY,nameOfEmit,amount));
            }
            br.close();
        } catch (Exception ex) {}
        
        //Objects
        try {
            
            s = "data/" + projectName + "/objects/objectids.inf";
            br = new BufferedReader(new FileReader(s));
            String line;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                
                Emitter newEmit = ItemHolder.emitters.get(
                        Integer.valueOf(splitline[2])).copy(Integer.valueOf(splitline[3]), 
                                                             Integer.valueOf(splitline[4]));
                
                LightSource newLight = ItemHolder.lights.get(Integer.valueOf(splitline[5])).copy();
                
                newLight.setLoc(Integer.valueOf(splitline[6]), Integer.valueOf(splitline[7]));
                
                objects.add(new NObject(newEmit, newLight, t.getImage(splitline[1]), Integer.valueOf(splitline[8]),
                        Integer.valueOf(splitline[9]), splitline[0]));
                
                objects.get(objects.size()-1).setLightOffset(Integer.valueOf(splitline[6]), Integer.valueOf(splitline[7]));
                objects.get(objects.size()-1).setPosition(0,0);
                
                
            }
            br.close();
        } catch (Exception ex) {}
        
        //Creatures
        try {
            s = "data/" + projectName + "/creatures/creatureids.inf";
            br = new BufferedReader(new FileReader(s));
            String line;
            t = Toolkit.getDefaultToolkit();
            while ((line = br.readLine()) != null) {
                String splitline[] = line.split(" ");
                creatures.add(new Creature());
            }
            br.close();
        } catch (Exception ex) {}
        loaded = true;
    }
    
}
