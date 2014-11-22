import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;


public class HuntAndKill implements Algorithm {

	Model dataModel;
	Point pointCurrent = new Point(0, 0);
	int row; 
	boolean complete = false; 
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
		return complete;
	}

	@Override
	public void next() {
		// Fetch all valid directions
		ArrayList<Model.CardinalDirections> validDirections = getValidDirections(pointCurrent);
		
		if(validDirections.size() > 0)
		{			
			// Reset color on visited / unvisited nodes
			colorVisited();
				
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

			// Reset Colors
			colorVisited();
			// Highlight the specified row
			colorRow(row);
			// Search for a new starting point in the specified row
			Point tempPoint = scanRow(row);			
			
			if(tempPoint != null)
			{				
				// Match found
				pointCurrent = tempPoint;
				dataModel.getNode(pointCurrent.x, pointCurrent.y).setVisit(true);
				dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(currentColor);
				
				// Select a random visited node
				ArrayList<Model.CardinalDirections> visitedDirections = getVisitedDirections(pointCurrent);
				int rand = randomRange(0, visitedDirections.size() - 1);				
				Model.CardinalDirections randDirection = visitedDirections.get(rand);
						
				// Find the 'current' and 'adjacent' nodes
				Model.Node currentNode = dataModel.getNode(pointCurrent.x, pointCurrent.y);
				Model.Node adjacentNode = dataModel.getNode(pointCurrent.x + randDirection.getX(), pointCurrent.y + randDirection.getY());
				
				// Carve walls between new starting position and adjacent node
				currentNode.setCardinal(randDirection, false);
				adjacentNode.setCardinal(randDirection.reverse(), false);
				
				// Reset the 'hunt' back to the first row
				row = 0;
			} else {
				// No match, begin the 'hunt' on the next row
				row++;
				
				if(row == dataModel.get_Y_Height())
				{
					complete = true;
				}
			}			
		}
	}

	@Override
	public void reset() {
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		this.setPos(randomRange(0, dataModel.get_X_Width() - 1), randomRange(0, dataModel.get_Y_Height() - 1));
		dataModel.getNode(pointCurrent.x, pointCurrent.y).setVisit(true);
		
		// Reset the 'hunt' back to the first row
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
		for(int i = 0; i < dataModel.get_X_Width(); i++)
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
	
	public void colorRow(int searchRow)
	{
		for(int i = 0; i < dataModel.get_X_Width(); i++)
		{
			Point tempPoint = new Point(i, searchRow);	
		
			dataModel.getNode(tempPoint.x, tempPoint.y).setColor(Color.GREEN);
		}
	}
	
	public void colorVisited()
	{
		for(int x = 0; x < dataModel.get_X_Width(); x++)
		{
			for(int y = 0; y < dataModel.get_Y_Height(); y++)
			{
				Model.Node tempNode = dataModel.getNode(x, y);
				
				if(tempNode.getVisit())
				{
					tempNode.setColor(visitColor);
				} else {
					tempNode.setColor(neutralColor);
				}
			}
		}
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
