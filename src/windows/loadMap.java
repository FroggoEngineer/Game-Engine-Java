/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapcreator.ButtonPanel;
import mapcreator.*;
import objects.*;
/**
 *
 * @author Kevin
 */
public class loadMap {
    
    public loadMap() {
        load();
    }
    
    private void load() {
        int w = 0, h = 0, spawnX = 0, spawnY = 0, backWidth = 0, backHeight = 0;
        String backPath = "";
        BufferedReader br;
        try {
            //Read the data from the file
            br = new BufferedReader(new FileReader("data/maps/combo"));
            String line, name = "";
            if ((line = br.readLine()) != null) {
                name = line;
            }
            if ((line = br.readLine()) != null) {
                w = Integer.valueOf(line);
            }
            if ((line = br.readLine()) != null) {
                h = Integer.valueOf(line);
            }
            if ((line = br.readLine()) != null) {
                spawnX = Integer.valueOf(line);
            }
            if ((line = br.readLine()) != null) {
                spawnY = Integer.valueOf(line);
            }
            char buffer[] = new char[w * h], backbuffer[] = new char[w * h], frontbuffer[] = new char[w * h], npcbuffer[] = new char[w * h];
            char emitters[] = new char[w*h], lights[] = new char[w*h];
            br.read(buffer, 0, w * h);
            br.read(backbuffer, 0, w * h);
            br.read(frontbuffer, 0, w * h);
            br.read(npcbuffer, 0, w * h);
            br.read(lights,0,w*h);
            br.read(emitters, 0, w*h);
            br.close();

            //Creating new map with given width and height
            MapArea.currentMap = new Map(w, h);
            MapArea.currentMap.setName(name);
            MapArea.currentMap.setSpawn(spawnX, spawnY);
            Toolkit t = Toolkit.getDefaultToolkit();
            System.out.println("build/objects/background/" + backPath + ".png");
            //Setting new values for scrollbars

            //Loading blocks to the new map from the file
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    MapArea.currentMap.setBlock(x, y, MapArea.blockBlocks[buffer[x + (y * w)]], 1);
                    MapArea.currentMap.setBlock(x, y, MapArea.backBlocks[backbuffer[x + (y * w)]], 0);
                    MapArea.currentMap.setBlock(x, y, MapArea.frontBlocks[frontbuffer[x + (y * w)]], 2);
                    MapArea.currentMap.setNpc(x, y, MapArea.npcs[npcbuffer[x + (y * w)]]);
                    MapArea.currentMap.setLightSource(x, y, MapArea.lights[lights[x + (y * w)]]);
                    MapArea.currentMap.setEmitter(x, y, MapArea.emitters[emitters[x + (y * w)]]);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
