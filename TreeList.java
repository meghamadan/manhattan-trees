/**
 * @author Megha
 * TreeList class that performs the 5 different tasks discussed in treeInfo(most/least popular tree types, 
 * most/least green zip codes, and the thickest tree). Also represents a tree list or an array list of tree objects
 * We use this class to represent the data we are given in the input file and we use various methods to manipulate 
 * it to find information like the most popular types of trees
 */

import java.io.FileNotFoundException;
import java.util.*;

public class TreeList {

	//data field of an array list which will represent out treeList
	private ArrayList<Tree> treeList = new ArrayList<Tree>();
	
	
	//three add methods that are used in the treeList class to perform the tasks
	public void add(int treeID, String streetName, String crossOne, String crossTwo, int condition, int diameter, String species, String borough, int zipCode){
		Tree x = new Tree(treeID, streetName, crossOne, crossTwo, condition, diameter, species, borough, zipCode);
		treeList.add(x);
	}
	
	public void add(String species, Integer count){
		Tree x = new Tree(species, count);
		treeList.add(x);
	}
	
	public void add(String species, int diameter, String streetName, String crossOne, String crossTwo, int zipCode){
		Tree x = new Tree(species, diameter, streetName, crossOne, crossTwo, zipCode);
		treeList.add(x);
	}
	
	public Tree get(int i){
		
		return treeList.get(i);
	}
	
	/**
	 * Picks top three numbers of an ArrayList regardless of ties
	 * @param fullList this list represents an array list of tree objects - will be compared differently according to the method being used
	 * @return
	 */
	public ArrayList<Tree> findTopThree(ArrayList<Tree> fullList){
		
		ArrayList<Tree> threeList = new ArrayList<Tree>();
		int threeCounter = 0;
		int tieChecker = 0;
		
		//popular method specific compareTo method compares according to count
		//if the two numbers on the list are equal
		for(int i = 1; i < fullList.size() && threeCounter < 3 ; i++){
			if(fullList.get(i).getCount() == fullList.get(i-1).getCount()){
				tieChecker++;
				threeList.add(fullList.get(i-1));
				
				if(i == fullList.size()-1){
					threeList.add(fullList.get(i));
					threeCounter++;
				}
			}
			//if the two numbers being compared on the list are not equal
			else {
				//special case: at the end of the list and #s were previously tied and now #s are not equal
				if(i == fullList.size()-1 && tieChecker > 0 && threeCounter == 1){
					threeList.add(fullList.get(i-1));
					threeList.add(fullList.get(i));
					threeCounter++;
				}
				//special case: at end of the list and #s were not previously tied and now #s are not equal
				else if(i == fullList.size()-1 && threeCounter == 1){
					threeList.add(fullList.get(i));
					threeCounter++;
				}
				//not at end of list but #s were previously tied and now #s are not equal
				else if (tieChecker > 0){
					threeList.add(fullList.get(i-1));
					threeCounter++;
					tieChecker = 0;
				}
				else{
					threeList.add(fullList.get(i-1));
					threeCounter++;
				}
			}
		}
		return threeList;
	}
	
	/**
	 * This method is used to find the top three most common Tree types (species) from a treeList object
	 * @return a treeList object of 3 most common species of tree (filled with tree objects where each one represents the species and its total count of trees)
	 * @throws FileNotFoundException if the file species_list.txt cannot be found
	 */
	public TreeList mostPopular () throws FileNotFoundException{
		
		//same format/design as mostPopular method
		ArrayList <String> treeSpeciesList = new ArrayList <String> ();
			for(int i = 0; i < treeList.size(); i++){
				treeSpeciesList.add(treeList.get(i).getSpecies());
			}
		
		//Hashmap to count the number of trees according to each species
		//Was unaware we could not use Hashmaps - once I found out it was a little too late to change
		Map <String, Integer> map = new HashMap <String, Integer> ();
			for(String x: treeSpeciesList){
				Integer count = map.get(x);
				map.put(x, count == null?1:count +1);
			}
		//new Tree ArrayList to list the tree objects (different Tree obj format: species, count)
		ArrayList<Tree> maxList = new ArrayList<Tree>();
			for(Map.Entry<String, Integer> entry : map.entrySet()){
				Tree x = new Tree(entry.getKey(), entry.getValue());
				maxList.add(x);
			}
			
		treeList.get(0).setPopularTest();
		//sort list according to count - setPopularTest method helped with this
		Collections.sort(maxList);
		
		ArrayList<Tree> maxThreeList = findTopThree(maxList);
		
		//converting from species code to full species name
		TreeList maxThreeTreeList = new TreeList();
			for(int i = 0; i < maxThreeList.size(); i++){
				//if statement makes sure the species code of the treeList correspond to a species on the species list
				//if it doesn't, we ignore the tree altogether
				if(maxThreeList.get(i).getFullSpecies() != null){
					maxThreeTreeList.add(maxThreeList.get(i).getFullSpecies(), maxThreeList.get(i).getCount());
				}
			}
	
	return maxThreeTreeList;
		
	}
	
	/**
	 * This method uses the information from a treeList and creates a new treeList with the top three most common tree species in the list
	 * @return a treeList with the top three most common tree types in a given list
	 * @throws FileNotFoundException if the file species_list.txt cannot be found
	 */
	public TreeList leastPopular () throws FileNotFoundException{
		
		//same format/design as mostPopular method
		ArrayList <String> treeSpeciesList = new ArrayList <String> ();
			for(int i = 0; i < treeList.size(); i++){
				treeSpeciesList.add(treeList.get(i).getSpecies());
			}
			
		//explained in mostPopular method
		Map <String, Integer> map = new HashMap <String, Integer> ();
			for(String x: treeSpeciesList){
				Integer count = map.get(x);
				map.put(x, count == null?1:count +1);
			}
		ArrayList<Tree> leastList = new ArrayList<Tree>();
			for(Map.Entry<String, Integer> entry : map.entrySet()){
				Tree x = new Tree(entry.getKey(), entry.getValue());
				leastList.add(x);
			}
			
		//use method setPopularTest to use specific toString method and to compare trees via species count
		treeList.get(0).setPopularTest();
		//sort list according to count - setPopularTest method helped with this
		Collections.sort(leastList, Collections.reverseOrder());
		
		ArrayList<Tree> leastThreeList = findTopThree(leastList);
		
		//create one more arrayList that simply converts species code to full species code
		TreeList leastThreeTreeList = new TreeList();
		for(int i = 0; i < leastThreeList.size(); i++){
			//check if the species code can not be found - if so, we ignore this count
			if(leastThreeList.get(i).getFullSpecies() != null){
				leastThreeTreeList.add(leastThreeList.get(i).getFullSpecies(), leastThreeList.get(i).getCount());
			}
		}
		
	return leastThreeTreeList;
	}

	/**
	 * This method is identical to the mostPopular method. It finds the zipCodes that have the most trees
	 * @return an array list of tree objects that represent these most green zip codes
	 * @throws FileNotFoundException
	 */
	public ArrayList<Tree> mostGreen()  throws FileNotFoundException{
		
		ArrayList <String> treeZipList = new ArrayList <String> ();
			for(int i = 0; i < treeList.size(); i++){
				treeZipList.add(String.valueOf(treeList.get(i).getZip()));
				System.out.println();
			}
			
		Map <String, Integer> map = new HashMap <String, Integer> ();
			for(String x: treeZipList){
				Integer count = map.get(x);
				map.put(x, count == null?1:count +1);
			}
		ArrayList<Tree> maxList = new ArrayList<Tree>();
			for(Map.Entry<String, Integer> entry : map.entrySet()){
				Tree x = new Tree(Integer.parseInt(entry.getKey()), entry.getValue());
				maxList.add(x);
			}
			
		treeList.get(0).setGreenTest();
		Collections.sort(maxList);
		
		ArrayList<Tree> maxThreeList = findTopThree(maxList);
		
	return maxThreeList;
	}

	
	/**
	 * This method is identical to the leastPopular method. It finds the top 3 Zip codes with the least amount of trees.
	 * @return an ArrayList of tree objects that represent the 3 least green zipcodes
	 * @throws FileNotFoundException
	 */
	public ArrayList<Tree> leastGreen() throws FileNotFoundException{
		
		ArrayList <String> treeZipList = new ArrayList <String> ();
			for(int i = 0; i < treeList.size(); i++){
				treeZipList.add(String.valueOf(treeList.get(i).getZip()));
			}

		//keeps track of counts for each zip code
		Map <String, Integer> map = new HashMap <String, Integer> ();
			for(String x: treeZipList){
				Integer count = map.get(x);
				map.put(x, count == null?1:count +1);
			}
		ArrayList<Tree> leastList = new ArrayList<Tree>();
			for(Map.Entry<String, Integer> entry : map.entrySet()){
				Tree x = new Tree(Integer.parseInt(entry.getKey()), entry.getValue());
				leastList.add(x);
			}
		
		//sets the greenTest boolean to true to use a specific toString and compare method
		treeList.get(0).setGreenTest();
		Collections.sort(leastList, Collections.reverseOrder());
		
		//use method created to find top 3 on leastList
		ArrayList<Tree> leastThreeList = findTopThree(leastList);
	
	return leastThreeList;
	}
	
	
	/**
	 * Finds the tree with the largest diameter in the list.
	 * @return an array list (in case there is a tie) for the thickest tree
	 * @throws FileNotFoundException
	 */
	public ArrayList<Tree> mostThick() throws FileNotFoundException{
		
		//create array list of tree objects wthat contain all the information needed for output
		ArrayList <Tree> treeThickList = new ArrayList <Tree> ();
			for(int i = 0; i < treeList.size(); i++){
				Tree x = new Tree (treeList.get(i).getFullSpecies(), treeList.get(i).getDiameter(), treeList.get(i).getStreetName(),treeList.get(i).getCrossOne(), treeList.get(i).getCrossTwo(), treeList.get(i).getZip()) ;
				treeThickList.add(x);
			}
		//set compareTo and to string methods accordign to diameter and specific output
		treeList.get(0).setThickTest();
		Collections.sort(treeThickList, new DiameterComparator());
		
		//for loop with an if else statement to pick the largest tree including ties
		ArrayList <Tree> mostThickList = new ArrayList <Tree> ();
		for(int i = 1; i < treeThickList.size(); i++){
			mostThickList.add(treeThickList.get(i-1));
			if(treeThickList.get(i-1).getDiameter() == treeThickList.get(i).getDiameter()){
				
			}
			else{
				i = treeThickList.size();
			}
		}
		return mostThickList;
	}
	
	public String toString(){
		
		String x = "";
		for(int i = 0; i < treeList.size(); i++){
			x = x + treeList.get(i).toString() +"\r\n";
		}
		return x;
		
	}


	
	
}
