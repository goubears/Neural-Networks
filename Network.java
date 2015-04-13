        /****************************************
         *                                      *
         *             Network class            *
         *         Andrew Miller-Smith          *
         *                                      *
         ****************************************/

        /*

        Description:    The Enrichment Center promises to always provide a safe testing environment. In dangerous testing environments, 
                        the Enrichment Center promises to always provide useful advice. For instance, the floor here will kill you. Try to avoid it.
        */

import java.util.*;
import java.io.*;
import java.util.Random;

public class Network {
 
//buffer for determining if an output value is correct
private static final double ERROR_BUFFER = 0.05;
private static final double OUTPUT_MULTIPLIER = 10;

//number of epochs for each training run
private static final int NUM_EPOCHS = 50;

//variable for tracking network performance
private static int NUM_CORRECT = 0;
private static int NUM_POSSIBLE = 0;
private static double PERCENT_CORRECT;

//VARIABLES TO BE SET AT COMMAND LINE. REPLACE WHEN INTEGRATING WITH READIN CODE
private static int NUM_INPUT_NODES = 10;
private static int NUM_OUTPUT_NODES = 10;
private static double ALPHA = 0.1;
private static Vector<inputNode> inputs = new Vector<inputNode>();;
private static Vector<outputNode> outputs = new Vector<outputNode>();

 
//vectors of inputNodes and outputNodes, constants storing their sizes
// private static Vector<inputNode> inputs;
// private static Vector<outputNode> outputs;
// private static int NUM_INPUT_NODES;
// private static int NUM_OUTPUT_NODES;

//learning rate will be specified by command line input
// private static double ALPHA;
 
 
//2D array of paths
private static Path[][] paths;

//random number generator for use in methods
private static Random rand = new Random();

public static void main(String[] args){

//ACTUAL METHOD TO CALL ONCE WE HAVE INTEGRATED WITH READ IN CODE
//public static Path[][] train(Vector<inputNode> trainingInput, Vector<outputNode> trainingOutput, Path[][]trainingPaths, double alpha){

    //set variables to given parameters
    // inputs = trainingInput;
    // outputs = trainingOutput;
    // paths = trainingPaths;
    // ALPHA = alpha;
    // NUM_INPUT_NODES = inputs.size();
    // NUM_OUTPUT_NODES = outputs.size();



    

    // System.out.println("Entered main.");

    // inputNode testInput = new inputNode(5, 0);
    // outputNode testOutput = new outputNode(10, 1);
    // testOutput.calculateValue(5);

    // Path testPath = new Path(testInput, testOutput, 2);
    // testPath.updateWeight(0.5, 10);

    // System.out.println("Input value: " + testInput.getValue());
    // System.out.println("Output value: " + testOutput.getValue());
    // System.out.println("Path value: " + testPath.getValue());

    // System.out.println("Input identifier: " + testInput.getIdentifier());
    // System.out.println("Output identifier: " + testOutput.getIdentifier());
    // System.out.println("Path identifier: " + testPath.getIdentifier());

    //create paths array from input and output nodes
    generateInputNodes();
    generateOutputNodes();

    //test randomly generated input nodes and output nodes
    for (int i = 0; i < NUM_INPUT_NODES; i++){

        System.out.println("Input node " + inputs.get(i).getIdentifier() + " value: " + inputs.get(i).getValue());

        if (i == NUM_INPUT_NODES - 1){
            System.out.println();
        }
    }

    for (int i = 0; i < NUM_OUTPUT_NODES; i++){

        System.out.println("Output node " + outputs.get(i).getIdentifier() + " target: " + outputs.get(i).getTarget());

        if (i == NUM_OUTPUT_NODES - 1){
            System.out.println();
        }
    }

    //create paths array. THIS SHOULD BE DONE IN READ IN BEFORE PASSED
    generatePaths(inputs, outputs);        

    //test print of paths
    for (int i = 0; i < NUM_INPUT_NODES; i++){
        for (int j = 0; j < NUM_OUTPUT_NODES; j++){

            //System.out.println("Path " + paths[i][j].getIdentifier() + " start: " + paths[i][j].getStart().getIdentifier() + " End: " + paths[i][j].getEnd().getIdentifier());
        }
    }


    int numIterations = 0;
    //train nodes on practice problems
    while (numIterations < NUM_EPOCHS){
       
        //array for storing input sums for each node
        double inputSums[] = new double[NUM_OUTPUT_NODES];
        //calculate input sums for each output node
        for (int i = 0; i < NUM_INPUT_NODES; i++){
     
            for (int j = 0; j < NUM_OUTPUT_NODES; j++){
     
                inputSums[j] += paths[i][j].getValue();
                //System.out.println("Path " + paths[i][j].getIdentifier() + " weighted value: " + paths[i][j].getValue());
     
            }

            if (i == NUM_INPUT_NODES - 1){
            //System.out.println();
            }
        }

        //test print of weighted sums
        for (int i = 0; i < NUM_OUTPUT_NODES; i++){

            //System.out.println("Output node " + outputs.get(i).getIdentifier() + " input sum : " + inputSums[i]);

            if (i == NUM_OUTPUT_NODES - 1){
                System.out.println();
            }
        }

        //calculate values of output nodes based on weighted inputs
        for (int i = 0; i < NUM_OUTPUT_NODES; i++){
     
            outputs.get(i).calculateValue(inputSums[i]);

            System.out.println("Output node " + outputs.get(i).getIdentifier() + " value : " + outputs.get(i).getValue());

            if (i == NUM_OUTPUT_NODES - 1){
                System.out.println();
            }
        }
     
        //update weights based on output values
        for (int i = 0; i < NUM_INPUT_NODES; i++){
            for(int j = 0; j < NUM_OUTPUT_NODES; j++){
     
            //System.out.println("Path " + paths[i][j].getIdentifier() + " previous value: " + paths[i][j].getValue());
            paths[i][j].updateWeight(ALPHA, inputSums[j]);

            //System.out.println("Path " + paths[i][j].getIdentifier() + " updated value: " + paths[i][j].getValue());
            //System.out.println();

            }
        }

        numIterations++;



    }

    test(inputs, outputs, paths, ALPHA);
}





//testing method to be called once 
public static boolean test(Vector<inputNode> testInput, Vector<outputNode> testOutput, Path[][]testPaths, double alpha){

    //set value and data structures equal to new testing parameters
    inputs = testInput;
    outputs = testOutput;
    paths = testPaths;
    ALPHA = alpha;
    NUM_INPUT_NODES = inputs.size();
    NUM_OUTPUT_NODES = outputs.size();

    //update start and end nodes for each path
    for (int i = 0; i < NUM_INPUT_NODES; i++){
        for (int j = 0; j < NUM_OUTPUT_NODES; j++){

            paths[i][j].setStart(inputs.get(i));
            paths[i][j].setEnd(outputs.get(j));

            System.out.println("Path " + paths[i][j].getIdentifier() + " updated.");
        }
    }

    //array for storing input sums for each node
    double inputSums[] = new double[NUM_OUTPUT_NODES];
 
    //calculate input sums for each output node
    for (int i = 0; i < NUM_INPUT_NODES; i++){
 
        for (int j = 0; j < NUM_OUTPUT_NODES; j++){
 
            inputSums[j] += paths[i][j].getValue();
            System.out.println("Input for output node " + outputs.get(j).getIdentifier() + ": " + inputSums[j]);
 
        }
    }

    //calculate values of output nodes based on weighted inputs
    for (int i = 0; i < NUM_OUTPUT_NODES; i++){
 
        outputs.get(i).calculateValue(inputSums[i]);

        System.out.println("Output node " + outputs.get(i).getIdentifier() + " value : " + outputs.get(i).getValue());

        if (i == NUM_OUTPUT_NODES - 1){
            System.out.println();
        }
    }

    //check correctness of tests
    int numCorrect = 0;
    int numPossible = NUM_OUTPUT_NODES;
    double percentCorrect;
    double result;
    double integerTarget;
    double percentError;
 
    //case with only one output node
    if (outputs.size() == 1){
 
        result = (int)(outputs.get(0).getValue() * OUTPUT_MULTIPLIER + 0.5);
        // if (result > ((double) NUM_OUTPUT_NODES - 0.5)){
        //     result = NUM_OUTPUT_NODES;
        // }
        // else if ((result - (int)result) > 0.5){
        //     result = (int)(result + 1);
        // }
        // else{
        //     result = (int) result;
        // }
        
        integerTarget = (int) (outputs.get(0).getTarget() * OUTPUT_MULTIPLIER);

        //check if output is within percent error bounds (i.e. if it is considered correct)
        percentError = Math.abs((integerTarget - result) / integerTarget);
        if (percentError < ERROR_BUFFER){

            numCorrect++;
            System.out.println("Solution correct. Target: " + integerTarget + " Network solution: " + result + " Percent error: " + percentError);
            return true;
        }
        else{
            System.out.println("Solution incorrect. Target: " + integerTarget + " Network solution: " + result + " Percent error: " + percentError);
            return false;
        }
 
    }

    //output is represented in bit string
    else{

        //loop through outputs. The node with the highest value is the network's output
        int bestNode = 0;
        double bestValue = Double.NEGATIVE_INFINITY;
        int actualTarget = 0;
        for (int i = 0; i < NUM_OUTPUT_NODES; i++){

            if (outputs.get(i).getValue() > bestValue){

                bestNode = i + 1;
                bestValue = outputs.get(i).getValue();
            }

            //find which node is the actual target value
            if (outputs.get(i).getTarget() == 1){

                actualTarget = i + 1;
            }
        }

        //check if output is within percent error bounds (i.e. if it is considered correct)
        result = bestNode;
        integerTarget = actualTarget;
        percentError = Math.abs((integerTarget - result) / integerTarget);
        if (percentError < ERROR_BUFFER){

            numCorrect++;
            System.out.println("Solution correct. Target: " + integerTarget + " Network solution: " + result + " Percent error: " + percentError);
            return true;
        }
        else{
            System.out.println("Solution incorrect. Target: " + integerTarget + " Network solution: " + result + " Percent error: " + percentError);
            return false;
        }

    }

}

 
//     //check correctness of tests
//     int numCorrect = 0;
//     int numPossible = NUM_OUTPUT_NODES;
//     double percentCorrect;
 
//     //case with only one output node
//     if (outputs.size() == 1){
 
//         result = (int) outputs.get(0) * 10;
 
//         //CHECK IF OUTPUT IS CONSIDERED CORRECT, ADJUST numCorrect ACCORDINGLY
 
//     }
 
//     //case with mulitple output nodes
//     double maxOutput = Integer.NEGATIVE_INFINITY;
//     else{
 
//         for (int i = 0; i < NUM_OUTPUT_NODES; i++){
 
//             //CHECK IF OUTPUT MATCHES TARGET VALUE, ADJUST numCorrect ACCORDINGLY
 
//         }
//     }
 
//         //compute percentCorrect
//         percentCorrect = (double) numCorrect / (double) numPossible;
 
//     tests++;
// }
 
 
 
   
 
 
 
 
 
 
 
// */

    public static void generateInputNodes(){

        for (int i = 0; i < NUM_INPUT_NODES; i++){

            double randomDouble = rand.nextDouble();
            int value;

            if (randomDouble <= 0.5){
                value = 0;
            }
            else{
                value = 1;
            }

            //create random inputNode, add to inputs vector
            inputNode newInput = new inputNode(value, i);
            inputs.add(newInput);
            
        }
        
    }

    public static void generateOutputNodes(){

        for (int i = 0; i < NUM_OUTPUT_NODES; i++){

            //int randomInt = rand.nextInt(9);
            double randomDouble = rand.nextDouble();
            double value;

            if (NUM_OUTPUT_NODES == 1){
                value = randomDouble;
            }
            else if (randomDouble <= 0.5){
                value = 0;
            }
            else{
                value = 1;
            }

            //create random inputNode, add to inputs vector
            outputNode newOutput = new outputNode(value, i);
            outputs.add(newOutput);
        }
        
    }

    //function creates paths between every inputNode and every outputNode
    public static void generatePaths(Vector<inputNode> inputNodes, Vector<outputNode> outputNodes){

            int numInputs = inputNodes.size();
            int numOutputs = outputNodes.size();
            //initialize paths array
            paths = new Path[numInputs][numOutputs];

            int uniqueIdentifer = 0;
            //create paths
            for (int i = 0; i < numInputs; i++){
                for (int j = 0; j < numOutputs; j++){

                    Path newPath = new Path(inputNodes.get(i), outputNodes.get(j), uniqueIdentifer);
                    paths[i][j] = newPath;
                    uniqueIdentifer++;
                }

            }
    }



}


