import java.util.Comparator;


/**
 * @author Megha
 * Comparator class used for the most thick method
 */
public class DiameterComparator implements Comparator<Tree> {
	
	@Override
	public int compare (Tree a, Tree b){
		
		return b.getDiameter() - a.getDiameter() ;
	}

}
