import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javax.jws.WebParam.Mode;

public class Sidewinder implements Algorithm {
	
	public Sidewinder(Model model)
	{
		this.dataModel = model;
		reset();
	}

	Model dataModel;
	Point pointCurrent = new Point(0, 0);
	boolean complete = false;
	ArrayList<Integer> runSet = new ArrayList<Integer>();
	
	Random rand = new Random();	
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250); 
	Color neutralColor = new Color(255, 255, 255);
	
	@Override
	public void setPos(int x, int y) {
		pointCurrent.setLocation(x, y);
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

	@Override
	public void next() {
		
		Model.CardinalDirections randDirection = null;
		
		// Find valid directions
		if(pointCurrent.y == 0)
		{
			// The top row of the maze must carve east
			randDirection = Model.CardinalDirections.EAST;
			
		} else if(pointCurrent.x == dataModel.get_X_Width() - 1) {
			// The last row in the maze must carve north
			randDirection = Model.CardinalDirections.NORTH;
			
		} else {			
			// Carve either NORTH or EAST
			int rand = randomRange(1, 2);
			
			if(rand == 1)
			{
				randDirection = Model.CardinalDirections.NORTH;
			} else {
				randDirection = Model.CardinalDirections.EAST;
			}
		}
		
		// Carve EAST from the current cell
		if(randDirection == Model.CardinalDirections.EAST)
		{
			// Fetch the 'current' and 'adjacent' nodes
			Model.Node currentNode = dataModel.getNode(pointCurrent.x, pointCurrent.y);
			Model.Node adjacentNode = dataModel.getNode(pointCurrent.x + 1, pointCurrent.y);
			
			// Carve the nodes
			currentNode.setCardinal(randDirection, false);
			adjacentNode.setCardinal(randDirection.reverse(), false);
			
		} else {
	
			// Else Carve NORTH from a random cell in the 'runSet'
		
			// Remove existing cells from the run set
			
		}
		
		// Iterate through the nodes
		if(pointCurrent.x < dataModel.get_X_Width())
		{
			// Move EAST one position
			pointCurrent.setLocation(pointCurrent.x + 1, pointCurrent.y);
		} else if(pointCurrent.y < dataModel.get_Y_Height()) {
			// Else move SOUTH and start at the beginning of the row	
			pointCurrent.setLocation(0, pointCurrent.y + 1);
		} else {		
			// All nodes have been visited, flag the maze as complete
			complete = true;
		}
	}

	@Override
	public void reset() {
		// Reset the maze
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);		
		
		// Set the starting position to the top left corder
		pointCurrent.setLocation(0, 0);
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
	
	public ArrayList<Model.CardinalDirections> getValidDirections(Point currentPoint)
	{
		ArrayList<Model.CardinalDirections> tempList = new ArrayList<Model.CardinalDirections>();
		
		// Use only two possible directions
		Model.CardinalDirections[] directions =
			{  Model.CardinalDirections.NORTH, Model.CardinalDirections.EAST }; 
		
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
