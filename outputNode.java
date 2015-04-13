
        /****************************************
         *                                      *
         *           outputNode class           *
         *         Andrew Miller-Smith          *
         *                                      *
         ****************************************/

        /*

        Description: THE CAKE IS A LIE.

        */

import java.util.*;
import java.io.*;
import java.util.Random;
import java.lang.Math;

public class outputNode {

    private double value = 0;
    private double target;
    public final double e = 2.71828;
    private int identifier;
    private final double OUTPUT_MULTIPLIER = 10;
 
    //constructor takes target value of output node & identifier. Value depends on output representation specified on command line
    public outputNode(double tar, int index){
 
        target = tar;       
        identifier = index;
    }
 
    public void calculateValue(double input){
 
        value = 1 / (1 + Math.pow(e, -input));
 
   }
 
    public double getValue(){
 
        return value;
    }
 
    public double getTarget(){
 
        return target;
    }

    public int getIdentifier(){

        return identifier;
    }

    public boolean equals(outputNode x){

        if (x.getIdentifier() == identifier){

            return true;
        }

        return false;
    }

}
