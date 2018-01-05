/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import java.util.*;
import NScience.*;
/**
 *
 * @author Kevin
 */
public class Body {
    
    private NVector positionOnMap;
    boolean newFrame;
    private NVector[] nextPositions; //Joint positions in next frame
    private NVector[] movePerUpdate; //Movement of joints per update
    private int step;
    private Animation animations;
    private int animationSpeed;
    private List<Joint> joints;
    /*
     * Set of Joints
     * Set of animations (loaded from file)
     * 
     */
    
    public Body() {
        
    }
    
    public void addJoint(int xPos, int yPos) {
        
    }
    
    public void move() {
        if(step < animationSpeed) {
            for(int i = 0; i < joints.size(); i++) {
                joints.get(i).add(movePerUpdate[i]);
            }
            step++;
        } else if(step == animationSpeed) {
            for(int i = 0; i < animationSpeed; i++) {
                joints.get(i).set(nextPositions[i]);
            }
            nextFrame();
            step=0;
        }
    }
    
    public void nextFrame() {
        animations.next();
        nextPositions = animations.getPositions();
        for(int i = 0; i < joints.size(); i++) {
            NVector next = nextPositions[i].copy();
            next.sub(joints.get(i).getPosition());
            next.div(animationSpeed);
            movePerUpdate[i] = next.copy();
            
        }
    }
    
    public void playAnimation(int i) {
        if(animations.getCurrentAnimationNr() != i) {
            animations.set(i);
            nextFrame();
            step = 0;
            animationSpeed = animations.getSpeed();
        }
        
    }
}
