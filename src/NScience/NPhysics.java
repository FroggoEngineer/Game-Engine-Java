/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NScience;
import static java.lang.Math.*;
//import static NScience.NovusMath.*;
/**
 *
 * @author Kevin
 */
public class NPhysics {
    
    
    public static NVector friction(NVector velocity, double mew) {
        NVector w = velocity.copy();
        w.normalize();
        double normal = 1;
        double magnitude = -1*mew * normal;
        w.mult(magnitude);
        return w;
    }
    
    public static NVector fluidRes() {
        return new NVector(0,0);
    }
    
    public static NVector attraction() {
        return new NVector(0,0);
    }
}
