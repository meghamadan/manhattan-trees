
/**
 * @author Megha Madan
 * 
 * Program that takes an input file of tree data and sorts the information into tree lists using a hashmap to find 
 * the most/least popular tree types, most/least green zip codes, and the thickest tree. 
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class TreeInfo {
	public static void main(String[]args) throws FileNotFoundException{
		
		//First verify if the name of an input file was provided
		if(args.length != 1) {
			System.err.printf("ERROR: The command line argument is not correct \n\n");
			System.exit(1);
		}
		
		//Assign the first command line argument as a file object
		File fileTrees = new File (args[0]);
	
		//Then verify the file exists and can be read
		if (!fileTrees.exists() || !fileTrees.canRead()) {
			System.err.printf("ERROR: cannot find/read %s\n\n", args[0]);
			System.exit(1);
		}
		
		//get name of the input file to use for output file name
		String fileName = fileTrees.getName();
		
		if (fileName.contains(".")){
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		File outFileTrees = new File (fileName + ".out");
		
		//create a scanner object to read the input file
		Scanner inTrees = new Scanner ( fileTrees );
		
		int lineCounter = 1;
		TreeList myTreeList = new TreeList();
		
		while (inTrees.hasNext()) {
			String line = inTrees.nextLine();
			String [] treeInfo = line.split(",");
			
			//ignores title line
			if(lineCounter == 1){
				lineCounter++;
				continue;
			}
			
			//if there are not 9 comma separated entries, then we skip the line
			if(treeInfo.length != 9){
				continue;
			}
			
			//if neither of the above are the case, then we create a Tree object corresponding to the entry
			//we then store the tree in out TreeList array so we can perform various tasks on them
			else {
				
				try{
				//index 0, 4, 5, 8 are integers
				treeInfo[0].trim();treeInfo[1].trim();treeInfo[3].trim();treeInfo[4].trim();treeInfo[5].trim();treeInfo[6].trim();treeInfo[7].trim();treeInfo[8].trim();
			
					if(treeInfo[1].matches(".*[A-Z]|.*[a-z]|.*[0-9]") && treeInfo[2].matches(".*[A-Z]|.*[a-z]|.*[0-9]") && treeInfo[3].matches(".*[A-Z]|.*[a-z]|.*[0-9]") && treeInfo[6].matches(".*[A-Z]|.*[a-z]|.*[0-9]") && treeInfo[7].matches(".*[A-Z]|.*[a-z]")){
						myTreeList.add(Integer.parseInt(treeInfo[0]), treeInfo[1], treeInfo[2], treeInfo[3], Integer.parseInt(treeInfo[4]), Integer.parseInt(treeInfo[5]), treeInfo[6], treeInfo[7], Integer.parseInt(treeInfo[8]));
					}
				}
				catch (Exception e){ }
			}
			lineCounter++;
		}
		
		//open Print writer object to start printing out the information received form the methods
		PrintWriter printOut = new PrintWriter(outFileTrees);
		
    	printOut.println("Most Popular Trees: \n \n");
		printOut.println("\r\n" + myTreeList.mostPopular());
		
		printOut.println("Least Popular Trees: \n \n");
		printOut.println("\r\n" + myTreeList.leastPopular());
	
		//used for loops in the following to print out each object of the array list on a new line
		printOut.println("Most green ZIP codes: \n ");
		printOut.print("\r\n");
		for(int i = 0; i < myTreeList.mostGreen().size(); i++){
			printOut.println("\r" + myTreeList.mostGreen().get(i));
		}
		
		printOut.print("\r\n");
		printOut.println("Least green ZIP codes: \n \n");
		printOut.print("\r\n");
		for(int i = 0; i < myTreeList.leastGreen().size(); i++){
			printOut.println(myTreeList.leastGreen().get(i));
		}
	
		printOut.print("\r\n");
		printOut.println("The largest tree: \n \n");
		printOut.print("\r\n");
		for(int i = 0; i < myTreeList.mostThick().size(); i++){
			printOut.print(myTreeList.mostThick().get(i));
		}
		
		inTrees.close();
		printOut.close();
		
	}

}
