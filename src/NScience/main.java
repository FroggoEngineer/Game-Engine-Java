/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NScience;

/**
 *
 * @author Kevin
 */
public class main {
    public static void main(String[] args) {
        System.out.println(Math.sqrt(2));
        System.out.println(NovusMath.sqrt(2));
        NVector v = new NVector(250, 250, 250);
        long result = 0;
        final long nr = 100000;
        for(int i = 0; i < nr; i++) {
            long time = System.currentTimeMillis();
            NVector u = v.copy();
            u.normalize();
            result += System.currentTimeMillis() - time;
        }
        NVector u = v.copy();
        u.normalize();
        System.out.println("x: " + u.getX());
        System.out.println("y: " + u.getY());
        System.out.println("z: " + u.getZ());
        System.out.println("Mag: " + u.mag());
        System.out.println("Average runtime: " + result);

    }
}
