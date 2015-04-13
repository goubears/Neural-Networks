
	/****************************************
	 *                                      *
	 *           inputNode class            *
	 *         Andrew Miller-Smith          *
	 *                                      *
	 ****************************************/

	/*

	Description: THE CAKE IS A LIE.

	*/

import java.io.*;
import java.util.*;

public class inputNode{

	private int value;
	private int identifier;
 
    //constructor takes value of node
    public inputNode(int val, int index){
        
        value = val;
        identifier = index;
    }
 
    public int getValue(){
 
        return value;
    }

    public void setValue(int newValue){

    	value = newValue;

    }

    public int getIdentifier(){

    	return identifier;
    }

    public boolean equals(inputNode x){

    	if (x.getIdentifier() == identifier){

    		return true;
    	}

    	return false;
    }
}