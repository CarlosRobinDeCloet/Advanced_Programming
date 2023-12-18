import java.util.ArrayList;

/**
 * Class which can find all subsets of a list which sum to a certain number. Uses the Combinatorics class to find all subsets.
 *
 * @author Carlos de Cloet
 */

public class SubsetSum 
{
	public ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

	/**
	 * Finds all subsets of a list which sum to a certain number. Uses the Combinatorics class to find all
	 * subsets of the provided list, and adds the list to result if the list sums to a number.
	 *
	 * @param L list with integers.
	 * @param k number
	 * @return amount of subsets which sum to k
	 */
	public int findSubsetSum(ArrayList<Integer> L, int k) 
	{

		// Initializes the combinatorics class and finds all subsets of L.
		int subsetSum = 0;
		Combinatorics<Integer> comb = new Combinatorics<Integer>();
		comb.findAllCombinations(L);

		//Loops through all subsets
		for(ArrayList<Integer> subset: comb.result){
			int sum = 0;

			// Loops through all integers of a subset to find the sum. If the sum equals k, adds the list to the results.
			for(Integer number: subset){
				sum += number;
			}
			if(sum == k){
				subsetSum++;
				this.result.add(subset);
			}
		}
		 return subsetSum;
	}

	/**
	 * Finds all subsets of size m of a list which sum to a certain number. Uses the Combinatorics class to
	 * find all subsets of the provided list, and adds the list to result if the list sums to a number.
	 *
	 * @param L list with integers.
	 * @param k number
	 * @param m size of subsets
	 * @return amount of subsets which sum to k
	 */
	public int findSubsetSum(ArrayList<Integer> L, int k, int m) 
	{
		// Initializes the combinatorics class and finds all subsets of L.
		int subsetSum = 0;
		Combinatorics<Integer> comb = new Combinatorics<Integer>();
		comb.findAllCombinations(L,m);

		//Loops through all subsets
		for(ArrayList<Integer> subset: comb.result){
			int sum = 0;
			// Loops through all integers of a subset to find the sum. If the sum equals k, adds the list to the results.
			for(Integer number: subset){
				sum += number;
			}
			if(sum == k){
				subsetSum++;
				this.result.add(subset);
			}
		}
		return subsetSum;
	}
	
	public static void main(String [] args)
	{
		// Define an instance of subset sum
		int [] numbers = {2, 28, 41, 51, 55, 75, 80, 84};
		int sum = 210;
		// There are (at least) three solutions:  
		// 2 + 28 + 41 + 55 + 84 = 51 + 75 + 84 = 55 + 75 + 80 = 210;
		
		// Determine and print all subsets of L that add up to sum.
		// Initialize the ArrayList L
		ArrayList<Integer> L = new ArrayList<Integer>();
		for(int i = 0; i < numbers.length; i++) {
			L.add(numbers[i]);
		}
		// Determine all subsets with 3 elements that add up to sum
		SubsetSum sss = new SubsetSum();
		sss.findSubsetSum(L,  sum);
		// Print all these subsets
		System.out.println("All solutions: " + sss.result.size());
		for(ArrayList<Integer> list : sss.result) {
			System.out.println(list);
		}
		System.out.println();
		
		// Determine and print all subsets of L with 3 elements that add up to sum.
		// Initialize the ArrayList L
		L = new ArrayList<Integer>();
		for(int i = 0; i < numbers.length; i++) {
			L.add(numbers[i]);
		}
		// Determine all subsets with 3 elements that add up to sum
		sss = new SubsetSum();
		sss.findSubsetSum(L,  sum, 3);
		// Print all these subsets
		System.out.println("All 3-solutions: " + sss.result.size());
		for(ArrayList<Integer> list : sss.result) {
			System.out.println(list);
		}
	}
}
