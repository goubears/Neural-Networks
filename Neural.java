       
		/****************************************
         *                                      *
         *              Neural class            *
         *              Adela Yang              *
         *                                      *
         ****************************************/

        /*
        
        Description:    This class is the main class that calls the ACS and Elitist Ant Algorithm. 
        				The script for running the tests is also available here.         
        */

import java.io.*;
import java.util.*;

public class Neural{

	//file Variables
	private static BufferedReader reader = null;

	private static File fileTraining;
	private static File fileTesting;
	private static String fileNameTraining;
	private static String fileNameTesting;

	//(training) vectors of input and output nodes 
	private static Vector<Vector<inputNode>> trainingInputVector = new Vector<Vector<inputNode>>();
	private static Vector<Vector<outputNode>> trainingOutputVector = new Vector<Vector<outputNode>>();

	private static File file;
	private static String fileName;
	private static Network network = new Network();

	private static Path[][] paths;

	//(testing) vectors of input and output nodes 
	private static Vector<Vector<inputNode>> testingInputVector = new Vector<Vector<inputNode>>();
	private static Vector<Vector<outputNode>> testingOutputVector = new Vector<Vector<outputNode>>();

	//command line arguments
	private static int fileSize; // 8 or 32
	private static String method; // s: single or t: ten


	//it reads in from the command line in this order:
		//file name training
		//file name testing
		//size (8 or 32)
		//method (single or ten)
	public static void main(String[] args) 
	{
		fileNameTraining = args[0];
		fileNameTesting = args[1];
		
		fileTraining = new File(fileNameTraining);
		fileTesting = new File(fileNameTesting);

		fileSize = Integer.parseInt(args[2]);
		method = args[3];
		readFileTraining(fileTraining);
		readFileTesting(fileTesting);

	}

	public static void readFileTraining(File f) 
	{	
		Vector<inputNode> tempA = new Vector<inputNode>();
		Vector<outputNode> tempB = new Vector<outputNode>();
		boolean tempR = true; 
		int counter = 0;


		//testing
		int a = 0; 

		try 
		{
			reader = new BufferedReader(new FileReader(f));
			String line;

			if(fileSize == 8)
			{
				while ((line = reader.readLine()) != null) 
				{
				
					String tempL = line.trim();
					String[] splitStr = tempL.split(",");

					//System.out.printf("%d ", splitStr.length);
					int[] temp = new int[splitStr.length];
					for(int i = 0; i < splitStr.length-1; i++)
					{
						temp[i] = Integer.parseInt(splitStr[i]);
						inputNode tempI = new inputNode(temp[i], i); //unknown identifier
						tempA.add(tempI);
					}
					//System.out.printf("%d ", tempA.size());
					trainingInputVector.add(tempA);
					tempA = new Vector<inputNode>();

					if(method.equals("s")){
						outputNode tempC = new outputNode(Double.parseDouble(splitStr[64])/10.0, 0);
						tempB.add(tempC);
						trainingOutputVector.add(tempB);
						tempB = new Vector<outputNode>();
					}
					else if(method.equals("t")){
						double[] tempD = new double[10];
						//initialize to tempD to 0
						for(int j=0; j < tempD.length; j++){
							tempD[j] = 0;
						}
						int tempOut = Integer.parseInt(splitStr[64]);
						tempD[tempOut] = 1;
						for(int k=0; k<tempD.length; k++){
							outputNode tempC = new outputNode(tempD[k], k);
       						tempB.add(tempC);		
   						}
   						trainingOutputVector.add(tempB);
   						tempB = new Vector<outputNode>();
					}

				}
				//System.out.printf("%d", a);
			}


			else if (fileSize == 32)
			{
				while ((line = reader.readLine()) != null) 
				{
					//skips the first 3 lines of file
					if(tempR == true){
						for(int j = 0; j<3; j++){
							line = reader.readLine();
						}
						tempR = false;
					}

					String tempL = line.trim();

					// if(a==0){
					// 	System.out.printf("%s %n", tempL);
					// 	a=1;
					// }


					int[] temp = new int[tempL.length()];
					for(int i = 0; i < tempL.length(); i++)
					{
						temp[i] = Character.getNumericValue(tempL.charAt(i));
						inputNode tempI = new inputNode(temp[i], i); //unknown identifier
						tempA.add(tempI);
					}
					
					counter++;
				
					if(counter == 32){
						trainingInputVector.add(tempA);	
						tempA = new Vector<inputNode>();

						line = reader.readLine();
						String tempM = line.trim();
						if(tempM.length() != 1){
							System.out.println("The file is not reading right.");
							System.exit(0);
						}

						if(method.equals("s")){
							outputNode tempO = new outputNode((double)Character.getNumericValue(tempM.charAt(0))/10.0, 0);
							tempB.add(tempO);
							trainingOutputVector.add(tempB);
							tempB = new Vector<outputNode>();
						}

						else if(method.equals("t")){
							double[] tempD = new double[10];
							//initialize to tempD to 0
							for(int j=0; j < tempD.length; j++){
								tempD[j] = 0;
							}
							int tempOut = Character.getNumericValue(tempM.charAt(0));
							tempD[tempOut] = 1;
							for(int k=0; k<tempD.length; k++){
								outputNode tempO = new outputNode(tempD[k], k);
       							tempB.add(tempO);		
   							}
   							trainingOutputVector.add(tempB);
   							tempB = new Vector<outputNode>();
						}
						counter = 0;
					}
				}
			}

			else{
				System.out.println("There was an error reading the file.");
				System.exit(0);
			}

			reader.close();
		} 
		catch (Exception e) 
		{
			System.err.format("Exception occurred trying to read '%s'.", f);
			e.printStackTrace();	
		}

		// fileSize = Integer.parseInt(args[1]);
		// method = args[2];
		// readFile(file);
		// generatePaths(inputVector.get(0), outputVector.get(0));

		// System.out.printf("%d ", inputVector.get(0).size());

		// for(int i=0; i<inputVector.get(0).size(); i++){
		//  	System.out.printf("%d ", inputVector.get(0).get(i).getValue());
		// }
		// System.out.println();
		
		// for(int i=0; i<outputVector.get(0).size(); i++){
		// 	System.out.printf("%f ", outputVector.get(1).get(i).getTarget());
		// }
		// System.out.println();

		// Path[][] tempPaths;
		// for (int i = 0; i < inputVector.size() - 1; i++){

		// 	System.out.println(outputVector.get(i).get(0).getTarget());
		// 	tempPaths = network.train(inputVector.get(i), outputVector.get(i), paths, 0.1);
		// 	paths = tempPaths;

		// }
		// //paths = network.train(inputVector.get(0), outputVector.get(0), paths, 0.1);
		// System.out.println("Trained on first example.");

	}



	//(training) reads file and stores the cities coordinates and identifier
	public static void readFileTesting(File f) 
	{	
		Vector<inputNode> tempA = new Vector<inputNode>();
		Vector<outputNode> tempB = new Vector<outputNode>();
		boolean tempR = true; 
		int counter = 0;


		//testing
		int a = 0; 

		try 
		{
			reader = new BufferedReader(new FileReader(f));
			String line;

			if(fileSize == 8)
			{
				while ((line = reader.readLine()) != null) 
				{
				
					String tempL = line.trim();
					String[] splitStr = tempL.split(",");

					//System.out.printf("%d ", splitStr.length);
					int[] temp = new int[splitStr.length];
					for(int i = 0; i < splitStr.length-1; i++)
					{
						temp[i] = Integer.parseInt(splitStr[i]);
						inputNode tempI = new inputNode(temp[i], i); //unknown identifier
						tempA.add(tempI);
					}
					//System.out.printf("%d ", tempA.size());
					testingInputVector.add(tempA);
					tempA = new Vector<inputNode>();
					//a++;

					if(method.equals("s")){
						outputNode tempC = new outputNode(Double.parseDouble(splitStr[64])/10.0, 0);
						tempB.add(tempC);
						testingOutputVector.add(tempB);
						tempB = new Vector<outputNode>();
					}
					else if(method.equals("t")){
						double[] tempD = new double[10];
						//initialize to tempD to 0
						for(int j=0; j < tempD.length; j++){
							tempD[j] = 0;
						}
						int tempOut = Integer.parseInt(splitStr[64]);
						tempD[tempOut] = 1;
						for(int k=0; k<tempD.length; k++){
							outputNode tempC = new outputNode(tempD[k], k);
       						tempB.add(tempC);		
   						}
   						testingOutputVector.add(tempB);
   						tempB = new Vector<outputNode>();
					}
				}
				//System.out.printf("%d", a);
			}


			else if (fileSize == 32)
			{
				while ((line = reader.readLine()) != null) 
				{
					//skips the first 3 lines of file
					if(tempR == true){
						for(int j = 0; j<3; j++){
							line = reader.readLine();
						}
						tempR = false;
					}

					String tempL = line.trim();

					// if(a==0){
					// 	System.out.printf("%s %n", tempL);
					// 	a=1;
					// }


					int[] temp = new int[tempL.length()];
					for(int i = 0; i < tempL.length(); i++)
					{
						temp[i] = Character.getNumericValue(tempL.charAt(i));
						inputNode tempI = new inputNode(temp[i], i); //unknown identifier
						tempA.add(tempI);
					}
					
					counter++;
				
					if(counter == 32){
						testingInputVector.add(tempA);	
						tempA = new Vector<inputNode>();

						line = reader.readLine();
						String tempM = line.trim();
						if(tempM.length() != 1){
							System.out.println("The file is not reading right.");
							System.exit(0);
						}

						if(method.equals("s")){
							outputNode tempO = new outputNode((double)Character.getNumericValue(tempM.charAt(0))/10.0, 0);
							tempB.add(tempO);
							testingOutputVector.add(tempB);
							tempB = new Vector<outputNode>();
						}

						else if(method.equals("t")){
							double[] tempD = new double[10];
							//initialize to tempD to 0
							for(int j=0; j < tempD.length; j++){
								tempD[j] = 0;
							}
							int tempOut = Character.getNumericValue(tempM.charAt(0));
							tempD[tempOut] = 1;
							for(int k=0; k<tempD.length; k++){
								outputNode tempO = new outputNode(tempD[k], k);
       							tempB.add(tempO);		
   							}
   							testingOutputVector.add(tempB);
   							tempB = new Vector<outputNode>();
						}
						counter = 0;
					}
				}
			}

			else{
				System.out.println("There was an error reading the file.");
				System.exit(0);
			}

			reader.close();
		} 
		catch (Exception e) 
		{
			System.err.format("Exception occurred trying to read '%s'.", f);
			e.printStackTrace();	
		}
	}
}

		/****************************************
         *                                      *
         *        generatePaths function        *
         *         Andrew Miller-Smith          *
         *                                      *
         ****************************************/

	//function creates paths between every inputNode and every outputNode. Called in Neural class after files have been read in.
    // //Takes vectors of inputNodes and outputNodes as parameters, returns void
    // public static void generatePaths(Vector<inputNode> inputNodes, Vector<outputNode> outputNodes){

    //         int numInputs = inputNodes.size();
    //         int numOutputs = outputNodes.size();
    //         //initialize paths array
    //         paths = new Path[numInputs][numOutputs];

    //         int uniqueIdentifer = 0;
    //         //create paths
    //         for (int i = 0; i < numInputs; i++){
    //             for (int j = 0; j < numOutputs; j++){

    //                 Path newPath = new Path(inputNodes.get(i), outputNodes.get(j), uniqueIdentifer);
    //                 paths[i][j] = newPath;
    //                 uniqueIdentifer++;
    //             }

    //         }

    //   //       for (int i = 0; i < numInputs; i++){
    //   //   		for (int j = 0; j < numOutputs; j++){

    //   //       		System.out.println("Path " + paths[i][j].getIdentifier() + " start: " + paths[i][j].getStart().getIdentifier() + " End: " + paths[i][j].getEnd().getIdentifier() + " Weight: " + paths[i][j].getValue());
    //   //   		}
    // 		// }
    // }



/* Adela:
Storage of print statements

	//System.out.printf("%d ", inputVector.get(0).size());

	// for(int i=0; i<inputVector.get(0).size(); i++){
	//  	System.out.printf("%d ", inputVector.get(0).get(i).getValue());
	// }
	// System.out.println();
		
	// for(int i=0; i<outputVector.get(0).size(); i++){
	// 	System.out.printf("%f ", outputVector.get(1).get(i).getTarget());
	// }
	// System.out.println();
*/
