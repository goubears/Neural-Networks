
        /****************************************
         *                                      *
         *              Path class              *
         *         Andrew Miller-Smith          *
         *                                      *
         ****************************************/

        /*

        Description: THE CAKE IS A LIE.

        */

import java.io.*;
import java.util.*;
import java.util.Random;
import java.lang.Math;

public class Path{
 
	private inputNode start;
	private outputNode end;
	private double weight;
	private int identifier;

	public final double e = 2.71828;
	private final double INITIAL_WEIGHT_LIMIT = 0.15;

	private final Random weightGenerator = new Random();
	 
	//constructor
	public Path(inputNode begin, outputNode destination, int index){
	   
	    start = begin;
	    end = destination;
	    identifier = index;

	    //INITIALIZE WEIGHT
	    double potentialWeight = weightGenerator.nextDouble();

	    while (potentialWeight > INITIAL_WEIGHT_LIMIT*2){
	    	potentialWeight = weightGenerator.nextDouble();
	    }

	    weight = INITIAL_WEIGHT_LIMIT - potentialWeight;
	    //System.out.println("Weight: " + weight);
	}

	public void setStart(inputNode newStart){

		start = newStart;
	}

	public void setEnd(outputNode newEnd){

		end = newEnd;
	}
	 
	public void updateWeight(double alpha, double inputSum){
	   
	    weight += alpha * start.getValue() * (end.getTarget() - end.getValue()) * getDerivative(inputSum);
	}
	 
	public double getDerivative(double x){
	   
	    //System.out.println("Derivative: " + Math.pow(e, x) / Math.pow((1 + Math.pow(e, x)), 2));
	    return Math.pow(e, x) / Math.pow(1 + Math.pow(e, x), 2);
	}
	 
	public double getValue(){
	   
	    return (double)weight*(double)start.getValue();
	}

	public inputNode getStart(){

		return start;
	}

	public outputNode getEnd(){

		return end;
	}

	public int getIdentifier(){

    	return identifier;
    }

    public boolean equals(Path x){

    	if (x.getIdentifier() == identifier){

    		return true;
    	}

    	return false;
    }
 
}