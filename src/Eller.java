import java.awt.Color;
import java.util.Random;

public class Eller implements Algorithm {
	
	Model dataModel;
	Integer[] setArray;
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
			for(int i = 0; i < setArray.length; i++)
			{
				if(setArray[i] == null)
				{
					setArray[i] = setNumber++;
				}
			}
		
			// Iterate through the current row (minus the last node)
			for(int i = 0; i < setArray.length - 1; i++)
			{		
				// Check if the 'current' and 'adjacent' node are in different sets
				if(! (setArray[i].equals(setArray[i + 1])))
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
						setArray[i + 1] = setArray[i];
					}
				}
			}			

		
		// From the current row carve into the next row
		
		// Create new setArray for the next row
		
			// Iterate through the sets
		
				// For each set randomly select one or more nodes to carve into the next row
		
				// Fetch 'current' and 'adjacent' nodes
		
				// Carve nodes
		
				// Add the 'adjacent' node's 'set number' into the next row		

		setArray = new Integer[dataModel.get_X_Width()];
		rowCount++;
	}

	@Override
	public void reset() {
		// Reset the maze
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		// Reset the 'sets' array	
		setArray = new Integer[dataModel.get_X_Width()];
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
}
