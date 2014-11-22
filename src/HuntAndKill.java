import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;


public class HuntAndKill implements Algorithm {

	Model dataModel;
	Point pointCurrent = new Point(0, 0);
	int row; 
	// Create the 'random' class
	Random rand = new Random();
	// Set Colors
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250);  	
	Color neutralColor = new Color(255, 255, 255);
	
	public HuntAndKill(Model model) {
		dataModel = model;
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
		// Fetch all valid directions
		ArrayList<Model.CardinalDirections> validDirections = getValidDirections(pointCurrent);
		
		if(validDirections.size() > 0)
		{
				
			// Select a random direction
			int rand = randomRange(0, validDirections.size() - 1);
			Model.CardinalDirections randDirection = validDirections.get(rand);
			
			// Find the next point using the random direction
			Point pointNext = new Point(pointCurrent.x + randDirection.getX(), 
					pointCurrent.y + randDirection.getY());			
			
			// Fetch the 'current' and 'next' nodes
			Model.Node currentNode = dataModel.getNode(pointCurrent.x, pointCurrent.y);
			Model.Node nextNode = dataModel.getNode(pointNext.x, pointNext.y);
			
			// Set background colors
			dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(visitColor);
			dataModel.getNode(pointNext.x, pointNext.y).setColor(currentColor);
			
			// Carve walls
			currentNode.setCardinal(randDirection, false);
			nextNode.setCardinal(randDirection.reverse(), false);
			
			// Set the next node as visited
			nextNode.setVisit(true);
			
			// Replace the 'current node' with the 'next node' 
			pointCurrent = new Point(pointNext.x, pointNext.y);
			
		} else {

			//ArrayList<Model.CardinalDirections> randDirection;
			
			Point tempPoint = scanRow(row);
			
			if(tempPoint != null)
			{				
				pointCurrent = tempPoint;
				dataModel.getNode(pointCurrent.x, pointCurrent.y).setVisit(true);
				System.out.format("Match on %s at %s %s%n", row, pointCurrent.x, pointCurrent.y);
				row = 0;
			} else {
				System.out.format("No match on row %s%n", row);
				row++;
			}
			

			
			// Check for valid starting position - adjacent to visited node 
			
			// Carve walls between new starting position and adjacent node
		}
	}

	@Override
	public void reset() {
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		this.setPos(randomRange(0, dataModel.get_X_Width() - 1), randomRange(0, dataModel.get_Y_Height() - 1));
		dataModel.getNode(pointCurrent.x, pointCurrent.y).setVisit(true);
		row = 0;
	}

	@Override
	public boolean validPos(int x, int y) {
		
		// Check the position is within the bounds of the Model
		if (x >= 0 && x < dataModel.get_X_Width()
				&& y >= 0 && y < dataModel.get_Y_Height())
		{
			// Check if the position has been visited already
			return (! (dataModel.getNode(x, y).getVisit()));
		} 
		
		// Failing finding a valid position return false
		return false;
	}
	
	public boolean visitedPos(int x, int y) {
		
		// Check the position is within the bounds of the Model
		if (x >= 0 && x < dataModel.get_X_Width()
				&& y >= 0 && y < dataModel.get_Y_Height())
		{
			// Check if the position has been visited already
			return ((dataModel.getNode(x, y).getVisit()));
		} 
		
		// Failing finding a valid position return false
		return false;
	}

	@Override
	public void setModel(Model model) {
		dataModel = model;
		this.reset();
	}
	
	public ArrayList<Model.CardinalDirections> getValidDirections(Point currentPoint)
	{
		ArrayList<Model.CardinalDirections> tempList = new ArrayList<Model.CardinalDirections>();
		
		// Check the Cardinal directions for valid positions
		for(Model.CardinalDirections cd : Model.CardinalDirections.values())
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
	
	public ArrayList<Model.CardinalDirections> getVisitedDirections(Point currentPoint)
	{
		ArrayList<Model.CardinalDirections> tempList = new ArrayList<Model.CardinalDirections>();
		
		// Check the Cardinal directions for valid positions
		for(Model.CardinalDirections cd : Model.CardinalDirections.values())
		{
			Point tempPoint = new Point(currentPoint.x + cd.getX(), (currentPoint.y + cd.getY()));
			// Check if this is a valid position
			if(visitedPos(tempPoint.x, tempPoint.y))
			{
				tempList.add(cd);
			}
		}	
		
		return tempList;
	}
	
	public Point scanRow(int searchRow)
	{
		for(int i = 0; i < dataModel.get_X_Width() - 1; i++)
		{
			Point tempPoint = new Point(i, searchRow);
			
			if(! dataModel.getNode(tempPoint.x, tempPoint.y).getVisit())
			{				
				if(getVisitedDirections(tempPoint).size() > 0)
				{
					return tempPoint;
				}
			}
		}
		
		return null;
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
