import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class Prim implements Algorithm {
	
	Model dataModel;
	Point pointCurrent = new Point(0, 0);
	ArrayList<Point> frontierPointList = new ArrayList<Point>();
	
	Random rand = new Random();	
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250); 
	Color neutralColor = new Color(255, 255, 255);
	Color greyColor = new Color(205, 201, 201);
	
	public Prim(Model model)
	{
		this.setModel(model);
	}
	
	@Override
	public void setModel(Model model) {
		dataModel = model;
		reset();
	}

	@Override
	public void setPos(int x, int y) {
		pointCurrent.setLocation(x, y);
	}

	@Override
	public void next() {
		
		// Select a random 'frontier' node
		int rand = randomRange(0, frontierPointList.size() - 1);
		Point frontierPoint = frontierPointList.get(rand);
		
		// Remove the specified 'frontier' node from the ArrayList 
		frontierPointList.remove(rand);
		
		// Find 'adjacent' nodes that have been visited
		ArrayList<Model.CardinalDirections> visitedDirections = getVisitedDirections(frontierPoint);
		rand = randomRange(0, visitedDirections.size() - 1);
		Model.CardinalDirections adjacentDirection = visitedDirections.get(rand);
		
		// Select 'frontier' and 'adjacent' nodes
		Model.Node frontierNode = dataModel.getNode(frontierPoint.x, frontierPoint.y);
		Model.Node adjacentNode = dataModel.getNode(frontierPoint.x + adjacentDirection.getX(),  frontierPoint.y + adjacentDirection.getY());
		
		// Carve walls between the 'frontier' node and the 'adjacent' node
		frontierNode.setCardinal(adjacentDirection, false);
		adjacentNode.setCardinal(adjacentDirection.reverse(), false);
		
		// The specified 'frontier' has now been visited 
		frontierNode.setVisit(true);
		
		// Add the newly created 'frontier' nodes
		ArrayList<Point> newPoints = getValidPoints(frontierPoint);
		frontierPointList.addAll(newPoints);
		
		// Set background colors
		frontierNode.setColor(visitColor);
		//adjacentNode.setColor(visitColor);
		for(Point p : newPoints)
		{
			dataModel.getNode(p.x, p.y).setColor(currentColor);
		}
	}

	@Override
	public boolean validPos(int x, int y) {
		
		// Check the position is within the bounds of the Model
		if (x >= 0 && x < dataModel.get_X_Width()
				&& y >= 0 && y < dataModel.get_Y_Height())
		{
			// Check if the position has been visited already
			if (! (dataModel.getNode(x, y).getVisit()))
			{
				// Check if the point is already in the list
				return (! frontierContains(x, y));
			}
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
	public void reset() {		
		dataModel.setAllColor(greyColor);	
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		// Set a random starting point
		this.setPos(randomRange(0, dataModel.get_X_Width() - 1),
				randomRange(0, dataModel.get_Y_Height() - 1));
		
		// Remove any existing 'frontier' nodes
		frontierPointList.clear();
		// Set the starting node visited
		dataModel.getNode(pointCurrent.x, pointCurrent.y).setVisit(true);
		// Add newly created 'frontier' nodes
		ArrayList<Point> newPoints = getValidPoints(pointCurrent);
		frontierPointList.addAll(newPoints);
		
		// Set background colors
		dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(visitColor);		
		for(Point p : newPoints)
		{
			dataModel.getNode(p.x, p.y).setColor(currentColor);
		}
	}

	@Override
	public boolean isComplete() {
		return false;
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
	
	/**
	 * Find valid positions around the specified <code>position</code>
	 * @param currentPoint - The current position
	 * @return - ArrayList of valid positions
	 */
	public ArrayList<Point> getValidPoints(Point point)
	{
		ArrayList<Point> tempList = new ArrayList<Point>();
		
		// Check the Cardinal directions for valid positions
		for(Model.CardinalDirections cd : Model.CardinalDirections.values())
		{
			Point tempPoint = new Point(point.x + cd.getX(), (point.y + cd.getY()));
			// Check if this is a valid position
			if(validPos(tempPoint.x, tempPoint.y))
			{
				tempList.add(
					new Point(point.x + cd.getX(), point.y + cd.getY()));
			}
		}	
		
		return tempList;
	}
	
	/**
	 * Find visited positions around the specified <code>position</code>
	 * @param currentPoint - The current positions
	 * @return - ArrayList of visited positions
	 */
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
	
	/**
	 * Check if the specified position exists in the <code>frontierPointList</code>
	 * @param x - The 'X' value of the specified point
	 * @param y - The 'Y' value of the specified point
	 * @return - True if the position exists
	 */
	public boolean frontierContains(int x, int y)
	{
		for(Point p : frontierPointList)
		{
			// Check if the point exists
			if(p.x == x && p.y == y)
			{
				return true;
			}
		}
		// The point does not exist, return false 
		return false;
	}

}
