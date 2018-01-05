/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skills;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import windows.*;

/**
 *
 * @author Kevin Söderberg
 */
public class ButtonPanel extends JPanel implements ActionListener {

    private JButton addObject, showBlocks, showBackObject, showFrontObject, showLight;
    private JButton toggleGrid, showNpc, showBackground, saveMap, loadMap, newMap;
    private JButton toggleLight, showEmitter;
    private JButton startEmitterEditor, startLightEditor, startSkeletonEditor;
    private java.util.ArrayList<JButton> buttons;
    
    
    public ButtonPanel(int width, int height) {
        
        buttons = new java.util.ArrayList<>();
        addButtons();

        setLayout(null);
        setLocation(0, 0);
        setSize(200, height);
        setVisible(true);
        setBackground(Color.DARK_GRAY);
        setOpaque(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addObject) {
            windows.addObject addObj = new windows.addObject();
        } else if (e.getSource() == startEmitterEditor) {
            EmitterEditor eE = new EmitterEditor();
        } else if (e.getSource() == startLightEditor) {
            LightEditor lE = new LightEditor();
        } else if (e.getSource() == showBlocks) {
            MapArea.blockLayer = 1;
            ObjectPanel.typeToLoad = 1;
            ObjectPanel.startUpdate = true;
            ObjectPanel.changeMenu = true;
        } else if (e.getSource() == showBackObject) {
            MapArea.blockLayer = 0;
            ObjectPanel.typeToLoad = 0;
            ObjectPanel.startUpdate = true;
            ObjectPanel.changeMenu = true;
        } else if (e.getSource() == showFrontObject) {
            MapArea.blockLayer = 2;
            ObjectPanel.typeToLoad = 2;
            ObjectPanel.startUpdate = true;
            ObjectPanel.changeMenu = true;
        } else if (e.getSource() == showBackground) {
        } else if (e.getSource() == showNpc) {
            ObjectPanel.typeToLoad = 3;
            ObjectPanel.startUpdate = true;
            ObjectPanel.changeMenu = true;
        } else if (e.getSource() == showLight) {
            ObjectPanel.typeToLoad = 5;
            ObjectPanel.startUpdate = true;
            ObjectPanel.changeMenu = true;
        } else if (e.getSource() == showEmitter) {
            ObjectPanel.typeToLoad = 4;
            ObjectPanel.startUpdate = true;
            ObjectPanel.changeMenu = true;
        } else if (e.getSource() == saveMap) {
            windows.SaveMap sM = new windows.SaveMap();
        } else if (e.getSource() == loadMap) {
            windows.loadMap lM = new windows.loadMap();
        } else if (e.getSource() == newMap) {
            windows.newMap nM = new windows.newMap();
        } else if (e.getSource() == toggleGrid) {
            if (MapArea.toggleGrid) {
                MapArea.toggleGrid = false;
            } else {
                MapArea.toggleGrid = true;
            }
        } else if (e.getSource() == toggleLight) {
            if (MapArea.toggleLight) {
                MapArea.toggleLight = false;
            } else {
                MapArea.toggleLight = true;
            }
        } else if (e.getSource() == startSkeletonEditor) {
            SkeletonEditor se = new SkeletonEditor();
        }
    }

    private void addButtons() {
        addObject = new JButton("Add new object");
        addObject.setBounds(25, 30, 150, 30);
        buttons.add(addObject);
        startEmitterEditor = new JButton("Emitter Editor");
        startEmitterEditor.setBounds(25, 80, 150, 30);
        startEmitterEditor.setToolTipText("Tool for creating new emitters");
        buttons.add(startEmitterEditor);
        startLightEditor = new JButton("Light Editor");
        startLightEditor.setBounds(25, 130, 150, 30);
        startLightEditor.setToolTipText("Tool for creating new lights");
        buttons.add(startLightEditor);
        showBlocks = new JButton("Show blocks");
        showBlocks.setBounds(25, 180, 150, 30);
        buttons.add(showBlocks);
        showBackObject = new JButton("Show back objects");
        showBackObject.setBounds(25, 230, 150, 30);
        buttons.add(showBackObject);
        showFrontObject = new JButton("Show front objects");
        showFrontObject.setBounds(25, 280, 150, 30);
        buttons.add(showFrontObject);
        showBackground = new JButton("Show backgrounds");
        showBackground.setBounds(25, 330, 150, 30);
        buttons.add(showBackground);
        showNpc = new JButton("Show NPCs");
        showNpc.setBounds(25, 380, 150, 30);
        buttons.add(showNpc);
        showLight = new JButton("Show lights");
        showLight.setBounds(25, 430, 150, 30);
        buttons.add(showLight);
        showEmitter = new JButton("Show emitters");
        showEmitter.setBounds(25, 480, 150, 30);
        buttons.add(showEmitter);
        saveMap = new JButton("Save map");
        saveMap.setBounds(25, 530, 150, 30);
        buttons.add(saveMap);
        loadMap = new JButton("Load map");
        loadMap.setBounds(25, 580, 150, 30);
        buttons.add(loadMap);
        newMap = new JButton("New map");
        newMap.setBounds(25, 630, 150, 30);
        buttons.add(newMap);
        toggleGrid = new JButton("Toggle Grid");
        toggleGrid.setBounds(25, 680, 150, 30);
        buttons.add(toggleGrid);
        toggleLight = new JButton("Toggle Light");
        toggleLight.setBounds(25, 730, 150, 30);
        buttons.add(toggleLight);
        startSkeletonEditor = new JButton("Skeleton Editor");
        startSkeletonEditor.setBounds(25, 780, 150, 30);
        buttons.add(startSkeletonEditor);
        
        //Add actionlisteners
        addObject.addActionListener(this);
        startEmitterEditor.addActionListener(this);
        startLightEditor.addActionListener(this);
        showBlocks.addActionListener(this);
        showBackObject.addActionListener(this);
        showBackground.addActionListener(this);
        showFrontObject.addActionListener(this);
        showNpc.addActionListener(this);
        showLight.addActionListener(this);
        showEmitter.addActionListener(this);
        saveMap.addActionListener(this);
        loadMap.addActionListener(this);
        newMap.addActionListener(this);
        toggleGrid.addActionListener(this);
        toggleLight.addActionListener(this);
        startSkeletonEditor.addActionListener(this);
        
        //adding the buttons to the panel
        add(addObject);
        add(startEmitterEditor);
        add(startLightEditor);
        add(showBlocks);
        add(showBackObject);
        add(showBackground);
        add(showFrontObject);
        add(showNpc);
        add(showLight);
        add(showEmitter);
        add(saveMap);
        add(loadMap);
        add(newMap);
        add(toggleGrid);
        add(toggleLight);
        add(startSkeletonEditor);
    }

    public void update(int width, int height) {
        setSize(200, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color[] colors = new Color[10];
        colors[0] = colors[1] = colors[8] = colors[9] = Color.BLACK;
        colors[2] = colors[3] = colors[6] = colors[7] = Color.DARK_GRAY;
        colors[4] = colors[5] = Color.GRAY;
        for (int i = 0; i < 10; i++) {
            g.setColor(colors[i]);
            g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i - 1, 10, 10);
        }

    }
}
