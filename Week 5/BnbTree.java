// A class that represents a branch-and-bound tree
public class BnbTree extends LinkedBinaryTree<BnbTree.BnbNode>
{
	// Static inner class that represents a node in a branch and bound tree.
	// It stores a lower bound, an upper bound, and a computation time.
	public static class BnbNode
	{
		private double lowerBound;
		private double upperBound;
		private double computationTime;
		
		public BnbNode(double lowerBound, double upperBound, double computationTime)
		{
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
			this.computationTime = computationTime;
		}
		
		public double getLowerBound()
		{
			return lowerBound;
		}
		public double getUpperBound()
		{
			return upperBound;
		}
		public double getComputationTime()
		{
			return computationTime;
		}
	}
	
	// Compute the best lower bound for this branch-and-bound tree
	public double bestLowerBound()
	{
		// Your code here
		return 0.0;
	}
	
	// Compute the best upper bound for this branch-and-bound tree
	public double bestUpperBound()
	{
		// Your code here
		return 0.0;
	}
	
	// Compute the total computation time for this branch-and-bound tree
	public double totalComputationTime()
	{
		// Your code here
		return 0.0;
	}
	
	public static void main(String [] args)
	{
		BnbTree tree = generateBnBTree();
		
		System.out.println("Computation time: " + tree.totalComputationTime());
		System.out.println("Best lower bound: " + tree.bestLowerBound());
		System.out.println("Best upper bound: " + tree.bestUpperBound());
	}
	
	// Generate a branch-and-bound tree to test your code
	private static BnbTree generateBnBTree()
	{
		BnbTree tree = new BnbTree();
		Position<BnbNode> rt = tree.addRoot(new BnbNode(30.5, 98, 14.3));
		
		Position<BnbNode> l = tree.addLeft(rt, new BnbNode(35.5, 90, 4.2));
		Position<BnbNode> r = tree.addRight(rt, new BnbNode(38, 95, 6.6));
		
		Position<BnbNode> ll =  tree.addLeft(l, new BnbNode(42.1, 90, 9.3));
		Position<BnbNode> lr =  tree.addRight(l, new BnbNode(45, 110, 5.6));

		Position<BnbNode> rl =  tree.addLeft(r, new BnbNode(39.2, 88, 8.3));
		Position<BnbNode> rr =  tree.addRight(r, new BnbNode(43.0, 78, 27));
		
		Position<BnbNode> rll =  tree.addLeft(rl, new BnbNode(41.1, 86, 1.2));
		Position<BnbNode> rlr =  tree.addRight(rl, new BnbNode(41.5, 104, 2.7));

		Position<BnbNode> rlll =  tree.addLeft(rll, new BnbNode(41.8, 84, 3.6));
		Position<BnbNode> rllr =  tree.addRight(rll, new BnbNode(45.2, 79, 7.5));
		
		return tree;
	}
}