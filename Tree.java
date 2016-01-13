import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;


/**
 * @author Megha
 * Tree class that represents a single tree with an ID, street names, condition, diameter, species, borough, and a zipCode
 * This class is also used to switch between different compare methods according to different attributes (species, zipCode)
 */

public class Tree implements Comparable <Tree> {
	private int treeID;
	private String streetName;
	private String crossOne;
	private String crossTwo;
	private int condition;
	private int diameter;
	private String species;
	private String borough;
	private int zipCode;
	private Integer count = 0;
	
	private String fullSpecies;
	
	
	private static boolean popularTest = false;
	private static boolean greenTest = false;
	private static boolean thickTest = false;
	
	//four different constructors to form different types of tree lists
	public Tree(int treeID, String streetName, String crossOne, String crossTwo, int condition, int diameter, String species, String borough, int zipCode){
		
		this.treeID = treeID;
		this.streetName = streetName;
		this.crossOne = crossOne;
		this.crossTwo = crossTwo;
		this.condition = condition;
		this.diameter = diameter;
		this.species = species;
		this.borough = borough;
		this.zipCode = zipCode;
	}
	
	public Tree(String species, Integer count){
		this.species = species;
		this.count = count;
	}
	
	public Tree(int zipCode, Integer count){
		this.zipCode = zipCode;
		this.count = count;
	}
	
	
	public Tree(String species, int diameter, String streetName, String crossOne, String crossTwo, int zipCode){
		this.species = species;
		this.diameter = diameter;
		this.streetName = streetName;
		this.crossOne = crossOne;
		this.crossTwo = crossTwo;
		this.zipCode = zipCode;
	}
	
	public String getSpecies(){
		return species;
	}
	
	public int getZip(){
		return zipCode;
	}
	
	public int getDiameter(){
		return diameter;
	}
	
	public String getStreetName(){
		return streetName;
	}
	
	public String getCrossOne(){
		return crossOne;
	}
	
	public String getCrossTwo(){

		return crossTwo;
	}
	
	public Integer getCount(){
		return count;
	}
	
	/**
	 * Use hard-coded species_list.txt file to match species code with its full name
	 * @return a string representation of the full name of the species
	 * @throws FileNotFoundException for species_file.txt - if it is not in the program's workign directory
	 */
	public String getFullSpecies() throws FileNotFoundException{
		//create file object for the species list file
		File speciesList = new File ("species_list.txt");
		//create scanner object to read the file
		Scanner inSpecies = new Scanner ( speciesList );
		//keeps track of how many lines/kinds of species
		int lineCounter2 = 0;
		//2D array to represent the code and full name
		String [] [] speciesArray = new String [119] [2];
		
		//use a while there is a next line condition
		while(inSpecies.hasNext()){
			String line = inSpecies.nextLine();
			//there are some spaces in my species_list.txt file so we skip those
			if (!line.contains(" ")){
				continue;
			}
			//otherwise we enter the information into the 2d array
			else{
				//used a delimiter to keep the split from making more than 2 blocks
				String [] speciesInfo = line.split(" ", 2);

				speciesArray[lineCounter2][0] = speciesInfo[0];
				speciesArray [lineCounter2][1] = speciesInfo[1];
				
				lineCounter2++;
			}
		}
		inSpecies.close();
		//run through each species code to find a match with the one you are looking for when using the method
		for(int i = 0; i < speciesArray.length; i++){
			if(species.compareTo(speciesArray[i][0]) == 0){
				fullSpecies = speciesArray[i][1];
			}
		
		}
		return fullSpecies;
	}
	
	
	public void setCount(Integer count){
		this.count = count;
	}
	//the following three setters are to control the type of compare to and to string method we use for each of our 5 tasks
	public void setPopularTest(){
		popularTest = true;
		greenTest = false;
		thickTest = false;
	}
	
	public void setGreenTest(){
		greenTest = true;
		popularTest = false;
		thickTest = false;
	}
	
	public void setThickTest(){
		thickTest = true;
		greenTest = false;
		popularTest = false;
	}
	
	@Override
	public String toString() {

		//these are the different kinds of to string methods
		if(popularTest == true){
			return String.format("%-3s %-3s", species, count);
		}
		
		if (greenTest == true){
			return String.format("%-3d   %-3d", zipCode, count);
		}
		
		if (thickTest == true){
			return String.format("%-3s, %4d inches in diameter \r\n%-10s (%-10s, %-10s) \r\n%-5d", species, diameter, streetName, crossOne, crossTwo, zipCode);
		}
		
		return String.format("%3s", species);
	}
	
	@Override
	public int compareTo(Tree compareTree) {
		if(popularTest == true || greenTest == true){
			int compareCount = compareTree.count;
			return compareCount - this.count;
		}
		return 0;
	}
}
