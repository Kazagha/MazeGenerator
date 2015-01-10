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
				// Check if the set number already exists
				if(! currentSet.get(i).isSet())
				{
					currentSet.get(i).setSetNum(setNumber++);
				}
			}
			
			System.out.println(currentSet.size());
		
			// Iterate through the current row (minus the last node)
			for(int i = 0; i < currentSet.size() - 1; i++)
			{		
				// Check if the 'current' and 'adjacent' node are in different sets
				//if(! (currentSet.get(i).equals(currentSet.get(i + 1))))
				//System.out.println(i + "#: " + currentSet.get(i).getSetNum() + " - " + currentSet.get(i + 1).getSetNum());
				if(! (currentSet.get(i).getSetNum() == currentSet.get(i + 1).getSetNum()))
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
			//nextSet = new ArrayList<Node>();
			for(int i = 0; i < dataModel.get_X_Width(); i++)
			{
				nextSet.get(i).nullSet();
			}
			
		} else if (mode == Mode.CARVE) {			
		
		// Carve one node from the current set
	
			// Collect all nodes that are in the same set as the first node
			ArrayList<Node> nodesInSet = new ArrayList<Node>();
			
			// Find the set number of the first unvisited node
			int setNo = -1;
			for(Node n : currentSet)
			{
				if(n.getVisited() == false)
				{
					setNo = n.getSetNum();
					break;
				}
			}			
			
			for(Node n : currentSet)
			{
				if(setNo == n.getSetNum())
				{
					nodesInSet.add(n);
					System.out.format("Set: %d %n", n.getSetNum());
				}				
			}
			System.out.format("-%n");
			
			// Select random number from possible nodes in this set
			int rand = randomRange(0, nodesInSet.size() - 1);		
			
			// Find the 'index' of the randomly selected node
			int nodeIndex = currentSet.indexOf(nodesInSet.get(rand));
			
			// Fetch 'current' and 'adjacent' nodes
			Model.Node currentNode = dataModel.getNode(nodeIndex, rowCount);
			Model.Node adjacentNode = dataModel.getNode(nodeIndex, rowCount + 1);					
	
			// Carve nodes
			currentNode.setSouth(false);
			adjacentNode.setNorth(false);
			
			// Add the 'adjacent' node's 'set number' into the next row
			//nextSet.set(currentSet.get(rand).getIndex(), new Node(currentSet.get(rand).getSetNum()));
			nextSet.get(nodeIndex).setSetNum(currentSet.get(nodeIndex).getSetNum());
		
		// Carve the remaining nodes at random
			
			for(Node n : nodesInSet)
			{				
				dataModel.getNode(currentSet.indexOf(n), rowCount).setColor(Color.GRAY);
				
				// Randomly chose to carve 
				if(randomRange(0, 1) == 1)
				{
					// Find the index of the Node
					nodeIndex = currentSet.indexOf(n);
					
					// Fetch 'current' and 'adjacent' nodes
					currentNode = dataModel.getNode(nodeIndex, rowCount);
					adjacentNode = dataModel.getNode(nodeIndex, rowCount + 1);
					
					// Carve nodes
					currentNode.setSouth(false);
					adjacentNode.setNorth(false);
					
					//System.out.format("Index: %d:", n.getIndex());
					
					// Add the 'adjacent' node's 'set number' into the next row
					//TODO: Error when fetching the current set by Index, when elements have been removed from this ArrayList
					nextSet.get(nodeIndex).setSetNum(n.getSetNum());
					
					//System.out.format("Random Node: Rand-%d Index-%d Set-%d %n", nodeIndex, nextSet.get(nodeIndex).getIndex(), nextSet.get(nodeIndex).getSetNum());
				}
			}
			
			for(Node n : nodesInSet)
			{
				currentSet.get(currentSet.indexOf(n)).setVisit(true);
			}
			System.out.format("-%n");
			
			if(allVisited(currentSet))
			{				
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
		
		// Reset the 'sets' array	
		currentSet = new ArrayList<Node>();
		nextSet = new ArrayList<Node>();
		
		for(int i = 0; i < dataModel.get_X_Width(); i ++)
		{
			currentSet.add(new Node());
			nextSet.add(new Node());
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
	
	public boolean allVisited(ArrayList<Node> list)
	{
		for(Node n : list)
		{
			if(! n.getVisited())
			{
				return false;
			}
		}
		
		return true;
	}
	
	private static class Node
	{
		Integer setNum;
		boolean visited;
		int index;
		static int NumberOfNodes = 0;
		
		public Node(int setNum)
		{
			this.setNum = setNum;
			index = NumberOfNodes++;
			visited = false;			
		}
		
		public Node()
		{
			this.setNum = null;
			visited = false;
			index = NumberOfNodes++;
		}
		
		public int getSetNum()
		{
				return setNum;
		}
		
		public boolean getVisited()
		{
			return visited;
		}
		
		public boolean isSet()
		{
			return ! (setNum == null);
		}
		
		public void setSetNum(int setNum)
		{
			this.setNum = setNum;
		}
		
		public void nullSet()
		{
			this.setNum = null;
		}
		
		public void setVisit(boolean isVisited)
		{
			visited = isVisited;
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
			return (this.index == other.index && this.getSetNum() == other.getSetNum());
		}
	}
}
