/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import characters.*;

/**
 *
 * @author Kevin
 */
public class AnimationPreview extends JFrame {
    private DrawHelp drawpane;
    
    public AnimationPreview(Body bodyPreview) {
        
        drawpane = new DrawHelp(bodyPreview);
        drawpane.setBounds(0, 0, 300, 500);
        drawpane.setLayout(null);
        drawpane.setVisible(true);
        add(drawpane);
        setLayout(null);
        setSize(300,500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}

class DrawHelp extends JPanel implements Runnable {
    
    private Body skelHelp;
    private Thread runner;
    
    public DrawHelp(body skels) {
        this.skelHelp = skels;
        runner = new Thread(this);
        runner.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        setBackground(Color.white);
        super.paintComponent(g);
        skelHelp.draw(g);
        Color[] colors = new Color[10];
        colors[0] = colors[1] = colors[8] = colors[9] = Color.BLACK;
        colors[2] = colors[3] = colors[6] = colors[7] = Color.DARK_GRAY;
        colors[4] = colors[5] = Color.GRAY;
        for (int i = 0; i < 10; i++) {
            g.setColor(colors[i]);
            g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i - 1, 10, 10);
        }
    }
    
    @Override
    public void run() {
        while(true) {
            
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
                
            }
            skelHelp.update();
            repaint();
        }
    }
    
}
