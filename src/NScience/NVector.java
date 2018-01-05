/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NScience;
//import static NScience.NovusMath.*;
import static java.lang.Math.*;
/**
 *
 * @author Kevin
 */
public class NVector {
    //Static methods
    //----------------------------------------
    //Adds the two vectors v & u into w and returns w.
    public static double[] add(double[] v, double[] u) {
        double[] w = new double[v.length];
        for(int i = 0; i < v.length; i++) {
            w[i] = v[i]+u[i];
        }
        return w;
    }
    
    //Substracts u from v and adds it to w and returns w.
    public static double[] sub(double[] v, double[] u) {
        double[] w = new double[v.length];
        for(int i = 0; i < v.length; i++) {
            w[i] = v[i]-u[i];
        }
        return w;
    }
    //----------------------------------------
    //End of static methods
    
    private double[] vec;
    
    public NVector(double x, double y) {
        vec = new double[2];
        vec[0] = x;
        vec[1] = y;
    }
    
    public NVector(double x, double y, double z) {
        vec = new double[3];
        vec[0] = x;
        vec[1] = y;
        vec[2] = z;
    }
    
    public NVector copy() {
        if(this.length() == 2)
            return new NVector(getX(),getY());
        else
            return new NVector(getX(),getY(), getZ());
    }
    public double getX() {
        return vec[0];
    }
    public double getY() {
        return vec[1];
    }
    public double getZ() {
        if(vec.length==3)
            return vec[2];
        else
            return 0;
    }

    public double getIndex(int i) {
        return vec[i];
    }

    public int length() {
        return vec.length;
    }
    
    //Adds a vector to this vector, v + u
    public void add(NVector v) {
        if(v.length() == 2 && this.length() == 2) {
            vec[0] += v.getX();
            vec[1] += v.getY();
        } else if (v.length() == 3 && this.length() == 3) {
            vec[0] += v.getX();
            vec[1] += v.getY();
            vec[2] += v.getZ();
        }
    }
    
    //Substracts a vector from this vector, v - u
    public void sub(NVector v) {
        if(v.length() == 2 && this.length() == 2) {
            vec[0] -= v.getX();
            vec[1] -= v.getY();
        } else if (v.length() == 3 && this.length() == 3) {
            vec[0] -= v.getX();
            vec[1] -= v.getY();
            vec[2] -= v.getZ();
        }
    }
    
    //Multiplies this vector with a scalar, c * v
    public void mult(double scalar) {
        for(int i = 0; i < this.length(); i++)
            vec[i] *= scalar;
    }
    
    //Divides this vector with a scalar, v / c
    public void div(double scalar) {
        for(int i = 0; i < this.length(); i++)
            vec[i] /= scalar;
    }
    
    //Returns the magnitude of the vector, ||v||
    public double mag() {
        if(this.length() == 2)
            return sqrt(pow(vec[0], 2)+pow(vec[1], 2));
        else if(this.length() == 3)
            return sqrt(pow(vec[0], 2)+pow(vec[1], 2)+pow(vec[2], 2));
        else
            return 0;
    }
    
    //Changes the vector into a unit vector, v / ||v||
    public void normalize() {
        double mag = this.mag();
        //Error check if the vector is a zero vector
        if(mag > 0)
            this.div(mag);
    }

}
