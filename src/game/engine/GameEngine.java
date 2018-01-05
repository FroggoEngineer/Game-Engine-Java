/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine;


import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 *
 * @author Kevin
 */
public class GameEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Activates hardware acceleration
        //System.setProperty("sun.java2d.translaccel", "True");
        //Sets OpenGL to be used for rendering
        //System.setProperty("sun.java2d.opengl", "True");
        ItemHolder.loadObjects("Dungeon");
        while(ItemHolder.loaded == false) { }
        Window frame = new Window();
        
        while (true) {

            if(frame.getHeight() < 400) {
                frame.setSize(frame.getWidth(), 400);
            }
            if(frame.getWidth() < 600) {
                frame.setSize(600, frame.getHeight());
            }
            
            frame.update();
            frame.repaint();
            
            
            try {
                TimeUnit.MILLISECONDS.sleep(16);
            } catch (InterruptedException ex) {
            }
        }
    }
}

class Window extends JFrame {

    private final ButtonPanel bp;
    private final ObjectPanel op;
    private final MapArea ma;
    private final EditorInfo ei;
    private int width=800, height=600;
    private final int widthDec=11, heightDec=34;
    
    public Window() {

        bp = new ButtonPanel(width, height);
        add(bp);
        
        ma = new MapArea(width, height);
        add(ma);
        
        ei = new EditorInfo(width,height);
        add(ei);
        
        op = new ObjectPanel(width, height);
        add(op);

        setTitle("Novus: Game Engine");
        setLayout(null);
        setVisible(true);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void update() {
        bp.update(200, getHeight()-heightDec);
        op.update(getWidth()-widthDec, getHeight()-heightDec);
        ma.update(getWidth()-widthDec, getHeight()-heightDec);
        ei.update(getWidth()-widthDec, getHeight()-heightDec);
    }
}