import java.util.ArrayList;

/**
 * The Combinatorics class gives methods to find all combinations and permutations of a list of objects E.
 * Result of the permutation or combination method are stored in an list 'result'.
 *
 * @author Carlos de Cloet
 *
 * @param <E> type of object
 */
public class Combinatorics<E>
{
	// result are stored in the list.
	public ArrayList<ArrayList<E>> result = new ArrayList<ArrayList<E>>();

	/**
	 * Finds all permutations of a list.
	 *
	 * @param L list with objects
	 */
	public void findAllPermutations(ArrayList<E> L){
		permutation(L, 0,L.size());
	}

	/**
	 * Finds all k-permutations of a list.
	 *
	 * @param L list with objects
	 * @param k size of the permutation
	 */
	public void findAllPermutations(ArrayList<E> L, int k)
	{
		permutation(L, 0, k);
	}

	/**
	 * Method to find all permutations or k-permutations. Finds all permutations by swapping objects in the provided list.
	 * Then it recursivly applies the permutation method again with the k index one step larger. Finally swaps the objects back.
	 * If k equals l, adds the l elements of the list to a copy list, which is added to results.
	 *
	 * @param L list with objects
	 * @param k index to keep track of which objects and how much objects needs to be swapped
	 * @param l the size of the permutations we need to find.
	 */
	private void permutation(ArrayList<E> L, int k, int l){

		// Swaps elements in the list. When two objects are swapped it recursively applies the permutation method again.
		// When no elements can be swapped anymore, it starts to swap objects back to restore the original list.

		for(int i = k; i < L.size(); i++){
			swap(L, i, k);
			permutation(L, k+1, l);
			swap(L, k, i);
		}

		// If the method has finished swapping all objects, adds a copy of the list to results. List has k elements
		// if k-permutation is used. Has all elements if all permutations needs to be found.

		if (k == l){
			ArrayList<E> copy = new ArrayList<>();
			for(int number = 0; number < l; number++){
				copy.add(L.get(number));
			}
			result.add(copy);
		}
	}

	/**
	 * Swaps two elements in a list.
	 *
	 * @param L List with objects
	 * @param i index i
	 * @param k index k
	 */
	private void swap(ArrayList<E> L, int i, int k){
		E ele = L.get(i);
		L.set(i, L.get(k));
		L.set(k,ele);
	}

	/**
	 * Finds all combinations of a certain size of a list.
	 *
	 * @param L List with objects
	 * @param k size of the combinations
	 */
	public void findAllCombinations(ArrayList<E> L, int k)
	{
		ArrayList<E> s = new ArrayList<>();
		boolean findKCombinations = true;
		findCombinations(s, L, k, findKCombinations);
	}

	/**
	 * Finds all combinations of a list.
	 *
	 * @param L List with objects
	 */
	public void findAllCombinations(ArrayList<E> L)
	{
		ArrayList<E> s = new ArrayList<>();
		boolean findKCombinations = false;
		findCombinations(s, L, L.size(), findKCombinations);
	}

	/**
	 * Finds all combinations of a list. Adds objects of L to the empty list s, and removes the object from list L.
	 * Does so by using copies of the lists. Then calls the findCombination method again to perform the method with
	 * a smaller L. This way it recursively adds elements of L to a list s. If L is empty or the list s has a certain size,
	 * the list is added to the results. Then the method removes elements of list s and adds them back to list L to restore the
	 * original L. Then the first element of L is removed and the method is called again for a smaller list L.
	 *
	 * @param s empty list
	 * @param L list with objects of which combinations need to be found
	 * @param k size of combinations to be found
	 * @param findKCombinations true if combinations of certain size are to be found
	 */
	private void findCombinations(ArrayList<E> s, ArrayList<E> L, int k, boolean findKCombinations){

		// Initializes copies
		ArrayList<E> copy = (ArrayList<E>) s.clone();
		ArrayList<E> copyL = (ArrayList<E>) L.clone();

		// Adds list to result if list is certain size or L is empty.
		if(findKCombinations){
			if(copy.size() == k){
				if(!result.contains(copy)) {
					result.add(copy);
				}
			}
		} else {
			if (L.isEmpty()) {
				result.add(copy);
			}
		}

		// Adds all element of a copy of L to a list, and performs the method again. Adds all elements
		// back to L after the recursion is finished.
		if(copy.size()<k) {
			for (E ele : copyL) {
				copy.add(ele);
				copyL.remove(ele);
				findCombinations(copy, copyL, k, findKCombinations);
				copy.remove(ele);
				copyL.add(ele);
			}
		}

		// When to recursion is finished and L is restored, performs the method again with a smaller list L.
		if(!L.isEmpty()) {
			L.remove(0);
			findCombinations(copy, L, k, findKCombinations);
		}
	}

	public static void main(String [] args)
	{
		char [] chars = {'A','B','C', 'D'};
		
		// Determine all permutations.
		ArrayList<Character> L = new ArrayList<Character>();
		for(int i = 0; i < chars.length; i++) {
			L.add(chars[i]);
		}

		// Find all permutations
		Combinatorics<Character> comb = new Combinatorics<Character>();
		comb.findAllPermutations(L);

		// Print all permutations
		System.out.println("All permutations: " + comb.result.size());
		for(ArrayList<Character> list : comb.result) {
			System.out.println(list);
		}
		System.out.println();
		
		L = new ArrayList<Character>();
		for(int i = 0; i < chars.length; i++) {
			L.add(chars[i]);
		}
		// Find all 2-permutations
		comb = new Combinatorics<Character>();
		comb.findAllPermutations(L, 2);
		// Print all 2-permutations
		System.out.println("All 2-permutations: " + comb.result.size());
		for(ArrayList<Character> list : comb.result) {
			System.out.println(list);
		}
		System.out.println();

		L = new ArrayList<Character>();
		for(int i = 0; i < chars.length; i++) {
			L.add(chars[i]);
		}

		// Find all combinations
		comb = new Combinatorics<Character>();
		comb.findAllCombinations(L);
		// Print all combinations
		System.out.println("All combinations: " + comb.result.size());
		for(ArrayList<Character> list : comb.result) {
			System.out.println(list);
		}
		System.out.println();
		
		L = new ArrayList<Character>();
		for(int i = 0; i < chars.length; i++) {
			L.add(chars[i]);
		}
		// Find all 2-combinations
		comb = new Combinatorics<Character>();
		comb.findAllCombinations(L, 2);
		// Print all 2- combinations
		System.out.println("All 2-combinations: " + comb.result.size());
		for(ArrayList<Character> list : comb.result) {
			System.out.println(list);
		}
		System.out.println();
	}
}