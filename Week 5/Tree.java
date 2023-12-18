import java.util.*;

public class Tree extends LinkedBinaryTree<Integer>
{
	// To find a value, we look for that value in the subtree rooted at root. Iterates over all positions in the tree.
	public Position<Integer> find(int value) {

		Iterable<Position<Integer>> it = preOrder();
		for(Position<Integer> pos : it){
			if(pos.getElement() == value){
				return pos;
			}
		}

		return null;
	}

	public void test(){

		ArrayList<Integer> listie = new ArrayList<>();

		Iterable<Position<Integer>> it = preOrder();
		for(Position<Integer> pos : it){
			listie.add(pos.getElement());
		}

		for(Integer intie : listie){
			System.out.println(intie);
		}
	}

	// A function to print how to get to a node
	public void printPath(Position<Integer> pos)
	{

		// makes two stacks containing all parents of the node we want the path to and whether the node is the left or right child
		Stack<Position<Integer>> stack = new Stack<>();
		Stack<Boolean> leftChild = new Stack<>();
		stack.push(pos);

		// adds parent nodes to the stack using a temporary object.
		// Checks whether the pos is the left or right child and adds boolean value to stack.
		while(parent(pos) != null){

			Position<Integer> copyPos = parent(pos);
			leftChild.push(pos.equals(left(copyPos)));
			stack.push(copyPos);
			pos = copyPos;
		}

		// pops positions from stack and checks if the pos is left or right from the parent node.
		pos = stack.pop();
		System.out.println("start at " + pos.getElement());

		while(!stack.isEmpty()){
			pos = stack.pop();
			if(leftChild.pop()){
				System.out.println("left to " + pos.getElement());
			} else {
				System.out.println("right to " + pos.getElement());
			}
		}
	}

	// Method to find the lowest common ancestor of two positions
	public Position<Integer> lowestCommonAncestor(Position<Integer> first, Position<Integer> second)
	{
		// Checks if one of the positions is the root.
		if(parent(first) == null || parent(second) == null){
			return root();
		}

		// Makes two ArrayList and adds both positions to a list. Then adds all parents of the positions to the list.
		ArrayList<Position<Integer>> ancestorsOfFirstNode = new ArrayList<>();
		ancestorsOfFirstNode.add(first);
		ArrayList<Position<Integer>> ancestorsOfSecondNode = new ArrayList<>();
		ancestorsOfSecondNode.add(second);

		while(parent(first) != null){
			first = parent(first);
			ancestorsOfFirstNode.add(first);
		}

		while(parent(second) != null){
			second = parent(second);
			ancestorsOfSecondNode.add(second);
		}

		// for every position in the first ArrayList, checks if the position is also in the second ArrayList.
		// The first found position which is in both list is the LCA.
		for(Position<Integer> pos : ancestorsOfFirstNode){
			if(ancestorsOfSecondNode.contains(pos)){
				return pos;
			}
		}

		return null;
	}
	
	
	// Method to compute the diameter of a tree
	public int diameter()
	{
		Height height = new Height();
		Position<Integer> root = root();
		return diameterHelper(root, height);

	}

	// Private class to keep track of the heights of the nodes in a tree.
	private class Height{
		int height;
		private Height(){
			this.height = 0;
		}
	}

	// Helper method to compute the diameter. Uses the Height class to keep track of the height of the left and right child.
	public int diameterHelper(Position<Integer> root, Height height)
	{

		// initializes two height objects
		Height leftHeight = new Height();
		Height rightHeight = new Height();

		// returns zero if the position is a leaf.
		if(root == null){
			return 0;
		}

		// recurses through all positions
		int leftDiameter = diameterHelper(left(root), leftHeight);
		int rightDiameter = diameterHelper(right(root), rightHeight);

		// returns the height of a position
		height.height = Math.max(leftHeight.height, rightHeight.height) + 1;

		// returns the biggest of the diameter of the left subtree, the biggest diameter of the right subtree, or
		// the height of the left and right subtree if that gives a bigger diameter.
		return Math.max(leftHeight.height + rightHeight.height, Math.max(leftDiameter, rightDiameter));
	}

	// Determines whether a tree is symmetric
	public boolean isSymmetric()
	{
		Position<Integer> root = root();
		return isSymmetricHelper(left(root), right(root));
	}

	// Helper method to check if a tree is symmetric.
	public boolean isSymmetricHelper(Position<Integer> leftChild, Position<Integer> rightChild){

		// returns true if position is a leaf, if one child is null but other is not, then the tree is not symmetric.
		if(leftChild == null && rightChild == null){
			return true;
		} else if(leftChild == null || rightChild == null){
			return false;
		}

		// returns true if all childs are symmetric, otherwise returns false.
		return isSymmetricHelper(left(leftChild), right(rightChild))
				&& isSymmetricHelper(right(leftChild), left(rightChild));
	}
		
	public boolean isMinHeap()
	{
		Position<Integer> root = root();
		return isMinHeapHelper(root, 0, this.size());
	}

	// checks the completeness condition and the minimum condition for the heap.
	public boolean isMinHeapHelper(Position<Integer> root, int index, int numberPositions){


		// if root is null no deviations from being a minimum heap have been found.
		if (root == null)
			return true;

		// if the index of a node is bigger than the number of nodes, the completeness condition has been broken.
		if (index >= numberPositions)
			return false;

		// checks if children are bigger than their parent node
		if(left(root) != null){
			if(left(root).getElement() < root.getElement()){
				return false;
			}
		}

		if(right(root) != null){
			if(right(root).getElement() < root.getElement()){
				return false;
			}
		}

		// recursion for the childeren of the position.
		return isMinHeapHelper(left(root), 2 * index + 1, numberPositions)
				&& isMinHeapHelper(right(root), 2 * index + 2, numberPositions);
	}


	// returns realisation of the class preOrderIterator.
	public preOrderIterator preOrderIterator(){
			return new preOrderIterator();
	}

	// Iterator for the binary tree. Travels with pre order.
	private class preOrderIterator implements Iterator<Position<Integer>> {

		private final Queue<Position<Integer>> queue;


		// Adds the root position to a queue
		public preOrderIterator(){
			queue = new LinkedList<>();
			queue.add(root);
		}

		// If the queue is empty then the tree does not have a next element
		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		// Removes and returns the position from the queue. Adds the left and right child to the queue.
		@Override
		public Position<Integer> next() {
			if (!hasNext()) throw new NoSuchElementException("No more positions remain to iterate");

			final Position<Integer> pos = queue.remove();


			if (left(pos) != null)  queue.add(left(pos));
			if (right(pos) != null) queue.add(right(pos));

			return pos;
		}
	}

	// class which implements iterable.
	public class preOrderIterable implements Iterable<Position<Integer>>{
		@Override
		public Iterator<Position<Integer>> iterator() {
			return preOrderIterator();
		}
	}

	// returns an iterable.
	public Iterable<Position<Integer>> preOrder(){
		return new preOrderIterable();
	}
	
	public static void main(String[] args) 
	{
		Tree tree = generateExample();
		System.out.println("The path to 11 is as follows.");
		Position<Integer> pos = tree.find(11);
		tree.printPath(pos);

		Position<Integer> first  = tree.find(13);
		Position<Integer> second  = tree.find(7);
		pos = tree.lowestCommonAncestor(first, second);
		System.out.println("The LCA of " + first.getElement() + " and " + second.getElement() + " is " + pos.getElement());
		
		first  = tree.find(13);
		second  = tree.find(2);
		pos = tree.lowestCommonAncestor(first, second);
		System.out.println("The LCA of " + first.getElement() + " and " + second.getElement() + " is " + pos.getElement());
		
				
		System.out.println("The diameter of the tree is " + tree.diameter());
		
		System.out.println("The tree is symmetric: " + tree.isSymmetric());
		System.out.println("The tree is a min-heap: " + tree.isMinHeap());
		System.out.println();		
		
		System.out.println("Let's now consider a symmetric tree");
		tree = generateSymmetricTree(9);
		System.out.println("The tree is symmetric: " + tree.isSymmetric());
		System.out.println("The tree is a min-heap: " + tree.isMinHeap());
		System.out.println();
		
		System.out.println("Let's now consider a min-heap");
		tree = generateMinHeap(10);
		System.out.println("The tree is symmetric: " + tree.isSymmetric());
		System.out.println("The tree is a min-heap: " + tree.isMinHeap());
		System.out.println();
	}
	
	private static Tree generateExample()
	{
		Tree tree = new Tree();
		Position<Integer> n1 = tree.addRoot(1);
		Position<Integer> n2 = tree.addLeft(n1, 2);
		Position<Integer> n3 = tree.addRight(n1, 3);
		Position<Integer> n4 = tree.addLeft(n2, 4);
		Position<Integer> n5 = tree.addRight(n2, 5);
		tree.addLeft(n3, 6); // n6
		Position<Integer> n7 = tree.addLeft(n4, 7);
		tree.addRight(n4, 8); // n8
		Position<Integer> n9 = tree.addRight(n5, 9);
		Position<Integer> n10 = tree.addLeft(n7, 10);
		Position<Integer> n11 = tree.addLeft(n9, 11);
		tree.addLeft(n10, 12); // n12
		tree.addRight(n11, 13); // n13

		return tree;
	}
	
	// This method generates a random tree with nNodes nodes.
	private static Tree generateTree(int nNodes)
	{
		boolean print = false;
		int upperBound = 100; 
		
		Random rand = new Random(1234);
		Tree tree = new Tree();
		ArrayList<Position<Integer>> openNodes = new ArrayList<Position<Integer>>();
		if(nNodes > 0)
		{
			int newValue = rand.nextInt(upperBound);
			Position<Integer> root = tree.addRoot(newValue);
			openNodes.add(root);
			if(print) System.out.println(newValue + " is the root of the tree");
		}
		for(int i = 1; i < nNodes; i++)
		{
			int index = rand.nextInt(openNodes.size());
			if(print) System.out.print(index + "\t" + openNodes.size() + "\t");
			Position<Integer> pos = openNodes.get(index);
			if(tree.left(pos) == null)
			{
				int newValue = rand.nextInt(upperBound);
				tree.addLeft(pos, newValue);
				if(print) System.out.println(newValue + "\t" + tree.left(pos).getElement() + " is a left child of " + pos.getElement());
				openNodes.add(tree.left(pos));
			}
			else if(tree.right(pos) == null)
			{
				int newValue = rand.nextInt(upperBound);
				tree.addRight(pos, newValue);
				if(print) System.out.println(newValue + "\t" + tree.right(pos).getElement() + " is a right child of " + pos.getElement());
				openNodes.add(tree.right(pos));
				openNodes.remove(pos);
			}
			else 
			{
				System.out.println("This should not happen");
			}
		}
		return tree;
	}
	
	// This method generates a heap
	private static Tree generateMinHeap(int nNodes)
	{
		boolean print = false;
		int maxIncrease = 100;
		Random rand = new Random(12345);
		Tree tree = new Tree();
		Queue<Position<Integer>> openNodes = new LinkedList<Position<Integer>>();
		if(nNodes > 0)
		{	
			int newValue = rand.nextInt(maxIncrease);
			Position<Integer> root = tree.addRoot(newValue);
			openNodes.add(root);
			if(print) System.out.println(newValue + " is the root of the tree");
		}
		boolean left = true;
		for(int i = 1; i < nNodes; i++)
		{
			Position<Integer> newPos;
			int jump = rand.nextInt(maxIncrease);
			int newValue =  1 + openNodes.peek().getElement() + jump;
			Position<Integer> pos = openNodes.peek();
			if(left)
			{
				tree.addLeft(pos, newValue);
				if(print) System.out.println(jump + "\t" + newValue + " is a left child of " + pos.getElement());
				newPos = tree.left(pos);
				left = false;
			}
			else
			{
				tree.addRight(pos, newValue);
				if(print) System.out.println(jump + "\t" + newValue + " is a right child of " + pos.getElement());
				newPos = tree.right(pos);
				
				openNodes.poll();
				left = true;
			}
			openNodes.add(newPos);
		}
		return tree;
	}
	
	// This method generates a symmetric tree. Its code is a little cumbersome.
	private static Tree generateSymmetricTree(int nNodes)
	{
		int print = 0;
		int upperBound = 100; 
		
		Random rand = new Random(1234);
		Tree tree = new Tree();
		ArrayList<Position<Integer>> openNodes = new ArrayList<Position<Integer>>();
		if(nNodes > 0)
		{
			int newValue = rand.nextInt(upperBound);
			Position<Integer> root = tree.addRoot(newValue);
			openNodes.add(root);
			if(print > 0) System.out.println(newValue + " is the root of the tree: " + tree.root);
		}
		for(int i = 1; i < nNodes; i += 2)
		{
			int index = rand.nextInt(openNodes.size());
			Position<Integer> pos = openNodes.get(index);
			if(tree.numChildren(pos) == 2)
			{
				i -= 2; 
				openNodes.remove(pos);
				continue;
			}
			
			Stack<Character> path = new Stack<Character>();
			Position<Integer> current = pos;
			while(tree.parent(current) != null)
			{
				path.push(tree.left(tree.parent(current)) == current ? 'l' : 'r');
				current = tree.parent(current);
				
			}
			Position<Integer> symmetricPos = tree.root();
			while(!path.isEmpty())
			{
				Character dir = path.pop();
				if(dir == 'l')
				{
					symmetricPos = tree.right(symmetricPos);
				}
				else if(dir == 'r')
				{
					symmetricPos = tree.left(symmetricPos);
				}
			}
			
			if(tree.left(pos) == null)
			{
				int newValue = rand.nextInt(upperBound);
				tree.addLeft(pos, newValue);
				if(print > 0) System.out.println(newValue + "\t" + tree.left(pos).getElement() + " is a left  child of " + pos.getElement());
				if(print > 1) System.out.println(pos + " --> " + tree.left(pos));
				openNodes.add(tree.left(pos));
				tree.addRight(symmetricPos, newValue);
				if(print > 0) System.out.println(newValue + "\t" + tree.right(symmetricPos).getElement() + " is a right child of " + symmetricPos.getElement());
				if(print > 1) System.out.println(symmetricPos + " --> " + tree.right(symmetricPos));
				openNodes.add(tree.right(symmetricPos));
			}
			else if(tree.right(pos) == null)
			{
				int newValue = rand.nextInt(upperBound);
				tree.addRight(pos, newValue);
				if(print > 0) System.out.println(newValue + "\t" + tree.right(pos).getElement() + " is a right child of " + pos.getElement());
				if(print > 1) System.out.println(pos + " --> " + tree.right(pos));
				openNodes.add(tree.right(pos));
				tree.addLeft(symmetricPos, newValue);
				if(print > 0) System.out.println(newValue + "\t" + tree.left(symmetricPos).getElement() + " is a left child of " + symmetricPos.getElement());
				if(print > 1) System.out.println(symmetricPos + " --> " + tree.left(symmetricPos));
				openNodes.add(tree.right(symmetricPos));
			}
		}
		return tree;
	}
}
