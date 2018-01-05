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
public class Animation {
    /*
     * Animation Speed, long
     * Amount of frames, int
     * Amount of Joints, int, read from Body.
     * Position of Joints in frames, int[frame][joint]
     */
    
    private List<NVector[][]> jointPositions;
    private int nrJoints;
    private List<Long> animationSpeed;
    private int currentAnimation;
    private int currentFrame;
    
    
    
    public Animation(int nrJoints) {
        this.nrJoints = nrJoints;
        currentAnimation = 0;
        currentFrame = 0;
    }
    
    public void addAnimation(NVector[][] v, long speed) {
        jointPositions.add(v);
        this.animationSpeed.add(speed);
    }
    
    public NVector[] getPositions() {
        return jointPositions.get(currentAnimation)[currentFrame];
    }
    
    public void next() {
        currentFrame++;
    }
    
    public void set(int a) {
        if(a <= jointPositions.size()) {
            currentAnimation = a;
            currentFrame = 0;
        }
    }
    
    public int getSpeed() {
        long ret = animationSpeed.get(currentAnimation);
        return (int)ret;
    }
    
    public int getCurrentAnimationNr() {
        return currentAnimation;
    }
}
