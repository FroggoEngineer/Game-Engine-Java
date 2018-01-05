/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import map.*;
/**
 *
 * @author Kevin
 */
public class EditorInfo extends JPanel implements ActionListener {
    
    private JComboBox layers;
    private JButton newLayer;
    private JLabel boxText = new JLabel("Current Layer: ");
    private JTextField width, height;
    
    public EditorInfo(int width, int height) {
        
        int num = MapArea.currentMap.getNrOfLayers();
        String[] boxNums = new String[num];
        for(int i = 1; i <= num; i++) {
            boxNums[i-1] = ""+i;
        }
        layers = new JComboBox(boxNums);
        layers.setBounds(110, 20, 50, 20);
        layers.setVisible(true);
        add(layers);
        boxText.setBounds(15, 20, 100, 20);
        boxText.setVisible(true);
        boxText.setForeground(Color.white);
        add(boxText);
        newLayer = new JButton("New layer");
        newLayer.setBounds(15, 60, 145,30);
        newLayer.setVisible(true);
        add(newLayer);
        this.width = new JTextField();
        this.width.setBounds(30, 100, 40,20);
        this.width.setVisible(true);
        add(this.width);
        this.height = new JTextField();
        this.height.setBounds(90, 100, 40,20);
        this.height.setVisible(true);
        add(this.height);
        
        newLayer.addActionListener(this);
        layers.addActionListener(this);
        setSize(width - 450, 200);
        setLocation(200, height-200);
        setLayout(null);
        setVisible(true);
    }
    
    public void update(int width, int height) {
        setSize(width - 450, 200);
        setLocation(200, height-200);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == layers) {
            MapArea.selectedLayer = layers.getSelectedIndex();
        } else if(e.getSource() == newLayer) {
            MapArea.currentMap.addLayer(Integer.valueOf(width.getText()), Integer.valueOf(height.getText()), Layer.Type.SECONDARY);
            int num = MapArea.currentMap.getNrOfLayers();
            layers.addItem(""+num);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.DARK_GRAY);
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
