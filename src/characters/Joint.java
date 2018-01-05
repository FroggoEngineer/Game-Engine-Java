/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;
import NScience.*;
import java.util.*;

/**
 *
 * @author Kevin
 */
public class Joint {
    /*
     * X, Y position, NVector v
     * Back/Middle/Front drawing layer
     * Joint number, Integer
     * ConnectedToJoint (Connection to a lower position joint)
     *     *Joint 7 -> Joint 8, texture will be drawn from 7 -> 8
     * move(NVector u), moves position of Joint by adding v+u
     * NVector between ConnectedToJoint decides direction for gear textures
     */
    
    private NVector position;
    public enum Layer {BACK, MIDDLE, FRONT};
    private Layer drawLayer;
    private int id;
    private List<Integer> connectedTo;
    
    public Joint(NVector v, Layer lay, int id) {
        position = v;
        drawLayer = lay;
        this.id = id;
        connectedTo = new LinkedList<>();
    }
    
    public void add(NVector u) {
        position.add(u);
    }
    
    public void set(NVector u) {
        position = u.copy();
    }
    
    public int getId() {
        return id;
    }
    
    public Layer getLayer() {
        return drawLayer;
    }
    
    public void connectJoint(int id) {
        connectedTo.add(id);
    }
    
    public List getConnections() {
        return connectedTo;
    }
    
    public NVector getPosition() {
        return position.copy();
    }
}
