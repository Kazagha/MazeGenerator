import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Eller implements Algorithm {
	
	Model dataModel;
	ArrayList<Node> currentSet;
	ArrayList<Node> nextSet; 
	int setNumber;
	int rowCount;
	Mode mode;

	Random rand = new Random();
	
	enum Mode { INIT, CARVE };
	
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
		
		if(mode == Mode.INIT)
		{
		
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
			
			// Switch to carving mode
			mode = Mode.CARVE;
			
			// Prepare new setArray for the next row
			nextSet = new ArrayList<Node>();
			Node.resetIndex();		
			for(int i = 0; i < dataModel.get_X_Width(); i++)
			{
				nextSet.add(null);
			}
			
		} else if (mode == Mode.CARVE) {			
		
		// Carve one node from the current set
	
			// Collect all nodes that are in the same set as the first node
			ArrayList<Node> nodesInSet = new ArrayList<Node>();
			Node.resetIndex();
			
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
			currentNode.setSouth(false);
			adjacentNode.setNorth(false);
	
			// Add the 'adjacent' node's 'set number' into the next row
			nextSet.set(rand, new Node(currentSet.get(rand).getSetNum()));
		
		// Carve the remaining nodes at random
			
			for(Node n : nodesInSet)
			{
				// Randomly chose to carve 
				if(randomRange(0, 1) == 1)
				{
					// Find the index of the Node
					nodeIndex = n.getIndex();
					
					// Fetch 'current' and 'adjacent' nodes
					currentNode = dataModel.getNode(nodeIndex, rowCount);
					adjacentNode = dataModel.getNode(nodeIndex, rowCount + 1);
					
					// Carve nodes
					currentNode.setSouth(false);
					adjacentNode.setNorth(false);
					
					// Add the 'adjacent' node's 'set number' into the next row
					nextSet.set(rand, new Node(currentSet.get(rand).getSetNum()));
				}
			}
		
			// Remove the node from the current set				
			//currentSet.remove(nodesInSet);
			
			for(Node n : nodesInSet)
			{
				currentSet.remove(n);
			}
			
			if(currentSet.isEmpty())
			{
				// Prepare the next row
				
				// Set the mode to INIT the next row
				mode = Mode.INIT;				
				// Pass down the 'set number' in the next row to the current row
				currentSet = nextSet;
				// Move to the next row
				rowCount++;	
			}
		}
	}

	@Override
	public void reset() {
		// Reset the maze
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		// Set the default mode
		mode = Mode.INIT;
		Node.resetIndex();
		
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
			index = numberOfNodes++;
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
		
		public boolean equals(Object obj)
		{
			// Test if 'obj' and 'this' are the same object
			if(this == obj)
			{
				return true;
			}
			
			//Test of the object is null, or of a different class to 'this'
			if((obj == null) || (obj.getClass() != this.getClass()))
			{
				return false;
			}
			
			// Therefore it is safe to cast
			Node other = (Node) obj;
			
			// Test if the objects have the same index and set number. 
			return (this.getIndex() == other.getIndex() && this.getSetNum() == other.getSetNum());
		}
	}
}
