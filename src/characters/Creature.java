/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;
import java.awt.*;
import NScience.*;
/**
 *
 * @author Kevin
 */
public class Creature {
    
    private String name;
    private Body body;
    private NVector pos;
    private NVector acc;
    private NVector speed;
    private int runTopSpeed, walkTopSpeed;
    //Decides top speed and acceleration
    private enum Action {IDLE, WALK, RUN, JUMP}
    private Action currentAction = Action.IDLE;
    private boolean changeAction = false;
    private NVector posInGrid;
    
    public Creature() {
        
    }
    
    public void update() {
        
    }
    
    public void idleLeft() {
        body.playAnimation(0);
    }
    
    public void idleRight() {
        body.playAnimation(1);
    }
    
    public void walkLeft() {
        body.playAnimation(2);
        speed = new NVector(0,0);
    }
    
    public void walkRight() {
        body.playAnimation(3);
        speed = new NVector(0,0);
    }
    
    public void jump() {
        
    }
    
    public void playAnimation(int i) {
        
    }
    
    
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
    }
    
    
}
