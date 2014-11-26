import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class BinaryTree implements Algorithm {

	Model dataModel;
	Point pointCurrent = new Point(0, 0);
	boolean complete;
	
	Random rand = new Random();	
	
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250); 
	Color neutralColor = new Color(255, 255, 255);
	
	public BinaryTree(Model model) {
		this.dataModel = model;
		reset();
	}
	
	@Override
	public void setPos(int x, int y) {
		pointCurrent.setLocation(x, y);
	}

	@Override
	public boolean isComplete() {
		return false;
	}

	@Override
	public void next() {
		dataModel.setAllColor(neutralColor);
		
		// Find valid directions
		ArrayList<Model.CardinalDirections> validDirections = getValidPositions(pointCurrent);
		
		// Check at least one direction is available
		if(validDirections.size() > 0)
		{
			// Select a random direction
			int rand = randomRange(0, validDirections.size() - 1);
			Model.CardinalDirections randDirection = validDirections.get(rand);
			
			// Find the current node and adjacent node
			Model.Node currentNode = dataModel.getNode(pointCurrent.x, pointCurrent.y);
			Model.Node adjacentNode = dataModel.getNode(pointCurrent.x + randDirection.getX(),
					pointCurrent.y + randDirection.getY());
			
			// Carve the walls
			currentNode.setCardinal(randDirection, false);
			adjacentNode.setCardinal(randDirection.reverse(), false);
			
			// Set background color
			currentNode.setColor(visitColor);
			adjacentNode.setColor(currentColor);
		}

		// Iterate through the nodes
		if(pointCurrent.x != dataModel.get_X_Width() - 1)
		{
			// Move across one position
			pointCurrent.setLocation(pointCurrent.x + 1, pointCurrent.y);
		} else if (pointCurrent.y != dataModel.get_Y_Height() - 1) {
			// Move to the beginning of the next row
			pointCurrent.setLocation(0, pointCurrent.y + 1);
		} else { 
			// The current position is the bottom right, the maze has been completed. 
			complete = true;
		}
	}

	@Override
	public void reset() {
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		// Set the position to the top left corner 
		this.setPos(0, 0);
		// Set the 'complete' flag to false
		complete = false;
	}

	@Override
	public boolean validPos(int x, int y) {
		// Check the position is within the bounds of the Model
		return (x >= 0 && x < dataModel.get_X_Width()
				&& y >= 0 && y < dataModel.get_Y_Height());
	}

	@Override
	public void setModel(Model model) {
		dataModel = model;
		reset();		
	}
	
	public ArrayList<Model.CardinalDirections> getValidPositions(Point currentPoint)
	{
		ArrayList<Model.CardinalDirections> tempList = new ArrayList<Model.CardinalDirections>();
		
		// Use only two possible directions
		Model.CardinalDirections[] directions =
			{  Model.CardinalDirections.SOUTH, Model.CardinalDirections.EAST }; 
		
		// Check the Cardinal directions for valid positions
		for(Model.CardinalDirections cd : directions)
		{
			Point tempPoint = new Point(currentPoint.x + cd.getX(), (currentPoint.y + cd.getY()));
			// Check if this is a valid position
			if(validPos(tempPoint.x, tempPoint.y))
			{
				tempList.add(cd);
			}
		}	
		
		return tempList;
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
