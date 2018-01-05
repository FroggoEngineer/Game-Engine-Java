/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NScience;

/**
 *
 * @author Kevin
 */
public class NovusMath {
    
    public static final double PI = Math.PI;
    
    public static int sgd(int m, int n) {
        int r;
        if(n <= 0)
            return n;
        r = m%n;
        if(r == 0)
            return n;
        while(r != 0) {
            m = n;
            n = r;
            r = m%n;
        }
        return n;
    }
    
    public static double sin(double n) {
        
        double result = 0;
        int switcher = 1;
        int y = 1;
        double rad = n * 1. / 180. * Math.PI;
        while (true) {
            double term = Math.pow(rad, y) / fak(y);
            if (term < Math.pow(10, -5)) {
                break;
            }
            if (switcher == 1) {
                result += term;
            } else {
                result -= term;
            }
            switcher *= -1;
            y += 2;
        }
        return result;
        
        /*
        // angle to radians
        double rad = n * 1. / 180. * Math.PI;
        // the first element of the taylor series
        double sum = rad;
        // add them up until a certain precision (eg. 10)
        for (int i = 1; i <= precision; i++) {
            if (i % 2 == 0) {
                sum += Math.pow(rad, 2 * i + 1) / fak(2 * i + 1);
            } else {
                sum -= Math.pow(rad, 2 * i + 1) / fak(2 * i + 1);
            }
        }
        
        return sum;
        */
    }
    
    private static double fak(double x) {
        int fakultet = 1;
        for(int i = 1; i <= x; i++) {
            fakultet *= i;
        }
            
        return fakultet;
    }
    
    public static int[] primes(int antal) {
        long time = System.currentTimeMillis();
        int prime = 1;
        int[] primeList = new int[antal];
        primeList[0] = 2;
        boolean addPrime;
        int nrOfPrimes=1;
        while (nrOfPrimes < antal) {
            prime += 2;
            addPrime = true;
            for (int i = 0; i < nrOfPrimes; i++) {
                if (prime % primeList[i] == 0) {
                    addPrime = false;
                    break;
                }
            }
            if (addPrime) {
                primeList[nrOfPrimes] = prime;
                nrOfPrimes++;
            }
        }
        time = System.currentTimeMillis()-time;
        System.out.println("Runtime: " + time + "ms");
        return primeList;
    }
    
    public static double sqrt(double n) {
        double gNew, gOld=0;
        gNew = n/2;
        while(abs(gNew-gOld) > pow(10,-4)) {
            gOld = gNew;
            gNew = (gOld + (n/gOld))/2;
        }
        return gNew;
    }
    
    public static double abs(double n) {
        if(n<0)
            return -1*n;
        else
            return n;
    }
    
    public static double pow(double n, int y) {
        double result = n;
        if (y == 0) {
            result = 1;
        } else if (y > 0) {
            for (int i = 0; i < y; i++) {
                result *= n;
            }
        } else {
            for (int i = 0; i >= y; i--) {
                result /= n;
            }
        }
        return result;
    }
    
    public static boolean equals(int[] a, int[] b) {
        if(a.length != b.length)
            return false;
        for(int i=0; i < a.length; i++)
            if(a[i] != b[i])
                return false;
        return true;
    }
    
    public static int[] rightRotation(int[] array, int steps) {
        for(int i = 0; i < steps; i++) {
            int temp=array[array.length-1];
            for(int j = array.length-1; j > 0; j--) {
                array[j]=array[j-1];
            }
            array[0] = temp;
        }
        
        return array;
    }
    
    public static NVector laff_copy(NVector v) {
        return v.copy();
    }
    
    public static NVector laff_axpy(double alpha, NVector v, NVector u) {
        NVector vec_out = v.copy();
        vec_out.mult(alpha);
        vec_out.add(u);
        return vec_out;
    }
    
    public static NVector laff_scal(double alpha, NVector v) {
        NVector vec_out = v.copy();
        vec_out.mult(alpha);
        return vec_out;
    }
    
    public static double laff_dot(NVector v, NVector u) {
        double dot = 0;
        for(int i = 0; i < v.length(); i++)
            dot += v.getIndex(i) + u.getIndex(i);
        return dot;
    }
}
