/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import game.engine.MapArea;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.*;

import javax.swing.*;
/**
 *
 * @author Kevin
 */
public class SaveMap extends JFrame implements ActionListener{
    
    private JPanel up, down;
    private JButton saveButton, cancel;
    private JTextField name;
    private JLabel nameText;
    public SaveMap() {
        up = new JPanel();
        down = new JPanel();
        setLayout(new GridLayout(2,1));
        add(up); add(down);
        saveButton = new JButton("save");
        cancel = new JButton("cancel");
        saveButton.addActionListener(this);
        cancel.addActionListener(this);
        down.add(saveButton); down.add(cancel);
        
        name = new JTextField();
        name.setPreferredSize(new Dimension(100,25));
        nameText = new JLabel("Name on map: ");
        up.add(nameText); up.add(name);
        
        setTitle("Save map");
        setSize(200,150);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancel) {
            dispose();
        } else if(e.getSource() == saveButton) {
            save();
            dispose();
        }
    }
    
    private void save() {
        FileWriter fileWriter = null; 
            try {
                String mapPath = "data/Dungeon/maps/" + name.getText();
                fileWriter = new FileWriter(mapPath, false);
                
            } catch (IOException ex) {}
            
            PrintWriter outputFile = new PrintWriter(fileWriter);
            outputFile.println(MapArea.currentMap.getNrOfLayers());
            
            for(int i = 0; i < MapArea.currentMap.getNrOfLayers(); i++) {
                outputFile.println(MapArea.currentMap.getWidthOfLayer(i) + " " + MapArea.currentMap.getHeightOfLayer(i));
                for(int j = 0; j < MapArea.currentMap.getWidthOfLayer(i); j++) {
                    outputFile.println();
                    for(int k = 0; k < MapArea.currentMap.getHeightOfLayer(i); k++) {
                        if(MapArea.currentMap.getLayer(i).getBlocks()[j][k] != null)
                            outputFile.print(MapArea.currentMap.getLayer(i).getBlocks()[j][k].getId()+1 +" ");
                        else
                            outputFile.print("0 ");
                    }
                }
                for(int j = 0; j < MapArea.currentMap.getWidthOfLayer(i); j++) {
                    outputFile.println();
                for(int k = 0; k < MapArea.currentMap.getHeightOfLayer(i); k++) {
                        if(MapArea.currentMap.getLayer(i).getObjects()[j][k] != null)
                            outputFile.print(0+" ");
                        else
                            outputFile.print("null ");
                    }
            } 
            }
            outputFile.close();
            /*
            int w = MapArea.currentMap.getWidth(), h = MapArea.currentMap.getHeight();
            char blockz[] = new char[w*h], backblockz[] = new char[w*h], frontblockz[] = new char[w*h], npcz[] = new char[w*h], lightz[] = new char[w*h], emitterz[] = new char[w*h];
            for(int y=0;y<h;y++)
                for(int x=0;x<w;x++)
                {
                    blockz[x+(y*w)] = (char)(MapArea.currentMap.getBlock(x, y, 1) == null ? 0 : MapArea.currentMap.getBlock(x, y, 1).getId());
                    backblockz[x+(y*w)] = (char)(MapArea.currentMap.getBlock(x, y, 0) == null ? 0 : MapArea.currentMap.getBlock(x, y, 0).getId());
                    frontblockz[x+(y*w)] = (char)(MapArea.currentMap.getBlock(x, y, 2) == null ? 0 : MapArea.currentMap.getBlock(x, y, 2).getId());
                    npcz[x+(y*w)] = (char)(MapArea.currentMap.getNpc(x, y) == null ? 0 : MapArea.currentMap.getNpc(x, y).getId());
                    lightz[x+(y*w)] = (char)(MapArea.currentMap.getLight(x, y) == null ? 0 : MapArea.currentMap.getLight(x, y).getId());
                    emitterz[x+(y*w)] = (char)(MapArea.currentMap.getEmitter(x, y) == null ? 0 : MapArea.currentMap.getEmitter(x, y).getId());
                }
            
            PrintWriter outputFile = new PrintWriter(fileWriter);
            outputFile.println(MapArea.currentMap.getName());
            outputFile.println(w); //width in blocks
            outputFile.println(h); //height in blocks
            outputFile.println(MapArea.currentMap.getSpawnX());
            outputFile.println(MapArea.currentMap.getSpawnY());
            outputFile.print(blockz);
            outputFile.print(backblockz);
            outputFile.print(frontblockz);
            outputFile.print(npcz);
            outputFile.print(lightz);
            outputFile.print(emitterz);
            outputFile.close();
            */ 
    }
}
