       
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
		readfileTesting(fileTesting)

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
					for(int i = 0; i < splitStr.length; i++)
					{
						temp[i] = Integer.parseInt(splitStr[i]);
						inputNode tempI = new inputNode(temp[i], i); //unknown identifier
						tempA.add(tempI);
					}
					//System.out.printf("%d ", tempA.size());
					trainingInputVector.add(tempA);
					tempA = new Vector<inputNode>();
					//a++;
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
					for(int i = 0; i < splitStr.length; i++)
					{
						temp[i] = Integer.parseInt(splitStr[i]);
						inputNode tempI = new inputNode(temp[i], i); //unknown identifier
						tempA.add(tempI);
					}
					//System.out.printf("%d ", tempA.size());
					testingInputVector.add(tempA);
					tempA = new Vector<inputNode>();
					//a++;
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