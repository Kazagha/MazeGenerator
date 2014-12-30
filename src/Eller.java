import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Eller implements Algorithm {
	
	Model dataModel;
	ArrayList<Node> currentSet;
	int setNumber;
	int rowCount;

	Random rand = new Random();
	
	// Set Colors
	Color currentColor = new Color(220, 20, 20);
	Color neutralColor = new Color(255, 255, 255);
	
	public Eller(Model model)
	{
		this.setModel(model);
	}

	@Override
	public void setPos(int x, int y) {}

	@Override
	public boolean isComplete() {
		return false;
	}

	@Override
	public void next() {
		
		//Initialize the current row	
		
			// Check that all nodes in the current row have a valid 'set number'		
			for(int i = 0; i < dataModel.get_X_Width(); i++)
			{
				if(currentSet.get(i) == null)
				{
					currentSet.set(i, new Node(setNumber++));
				}	
			}
		
			// Iterate through the current row (minus the last node)
			for(int i = 0; i < currentSet.size() - 1; i++)
			{		
				// Check if the 'current' and 'adjacent' node are in different sets
				if(! (currentSet.get(i).equals(currentSet.get(i + 1))))
				{						
					// Randomly decide if the nodes will be joined
					int rand = randomRange(0, 1);					
					if(rand == 1 || dataModel.get_Y_Height() - 1 == rowCount)
					{		
						// Fetch 'current' and 'adjacent' nodes
						Model.Node currentNode = dataModel.getNode(i, rowCount);
						Model.Node adjacentNode = dataModel.getNode(i + 1, rowCount);
			
						// Carve nodes
						currentNode.setEast(false);
						adjacentNode.setWest(false);
			
						// Add 'adjacent' node to the current set
						currentSet.get(i + 1).setSetNum(currentSet.get(i).getSetNum());
					}
				}
			}			
		
		// From the current row carve into the next row
		
		// Create new setArray for the next row
		ArrayList<Node> nextSet = new ArrayList<Node>();
		Node.resetIndex();		
		for(int i = 0; i < dataModel.get_X_Width(); i++)
		{
			nextSet.add(null);
		}

		// For each set randomly select one node to carve into the next row	
		
		
		ArrayList<Node> nodesInSet = new ArrayList<Node>();
		
		// Collect all nodes that are in the same set as the first node
		for(Node n : currentSet)
		{
			if(currentSet.get(0).getSetNum() == n.getSetNum())
			{
				nodesInSet.add(n);
			}
		}
		
		// Select random number from possible nodes in this set
		int rand = randomRange(0, nodesInSet.size() - 1);		
		
		// Find the 'index' of the randomly selected node
		int nodeIndex = nodesInSet.get(rand).getIndex();
		
		// Fetch 'current' and 'adjacent' nodes
		Model.Node currentNode = dataModel.getNode(nodeIndex, rowCount);
		Model.Node adjacentNode = dataModel.getNode(nodeIndex, rowCount + 1);					

		// Carve nodes
		currentNode.setNorth(false);
		adjacentNode.setSouth(false);

		// Add the 'adjacent' node's 'set number' into the next row
		nextSet.set(rand, new Node(currentSet.get(rand).getSetNum()));		
		
		// Remove the node from the current set
		currentSet.remove(rand);
		
		/*
		
		// Randomly carve the remaining nodes
		
		int rand = randomRange(0, nodesInSet.size());
		int nodeIndex = nodesInSet.get(rand).getIndex();
		
		// Fetch 'current' and 'adjacent' nodes
		Model.Node currentNode = dataModel.getNode(nodeIndex, rowCount);
		Model.Node adjacentNode = dataModel.getNode(nodeIndex, rowCount + 1);
		
		// Carve nodes
		currentNode.setSouth(false);
		adjacentNode.setNorth(false);
		
		// Add the 'adjacent' node's 'set number' into the next row
		nextSet[nodeIndex].setSetNum(nodesInSet.get(rand).getSetNum());		
		}
		*/
	

		currentSet = nextSet;
		rowCount++;
	}

	@Override
	public void reset() {
		// Reset the maze
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		// Reset the 'sets' array	
		currentSet = new ArrayList<Node>();
		for(int i = 0; i < dataModel.get_X_Width(); i ++)
		{
			currentSet.add(null);
		}
		rowCount = 0;
	}

	@Override
	public boolean validPos(int x, int y) {
		return false;
	}

	@Override
	public void setModel(Model model) {
		this.dataModel = model;
		reset();		
	}
	
	/**
	 * Randomize a number between the specified <code>min</code> and <code>max</code> 
	 * @param min
	 * @param max
	 * @return
	 */
	public int randomRange(int min, int max)
	{
		// Plus one required to include the max in the range
		return rand.nextInt((max - min) + 1) + min;
	}
	
	private static class Node
	{
		private static int numberOfNodes = 0;
		private int index;
		Integer setNum; 
		
		public Node(int setNum)
		{
			index = ++numberOfNodes;
			this.setNum = setNum;
		}
		
		public int getIndex()
		{
			return index;
		}
		
		public int getSetNum()
		{
			return setNum;
		}
		
		public void setSetNum(int setNum)
		{
			this.setNum = setNum;
		}
		
		public static void resetIndex()
		{
			numberOfNodes = 0;
		}
	}
}
