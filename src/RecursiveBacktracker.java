import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class RecursiveBacktracker implements Algorithm {

	Model dataModel; 
	ArrayList<Point> pointArray = new ArrayList<Point>();
	Point pointCurrent = new Point(0, 0);
			
	Random rand = new Random();	
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250); 
	Color neutralColor = new Color(255, 255, 255);
	Color greyColor = new Color(205, 201, 201);
	
	public RecursiveBacktracker(Model model) {
		this.setModel(model);
	}
	
	@Override
	public void setModel(Model model) {
		this.dataModel = model;
		this.reset();
	}

	@Override
	public void setPos(int x, int y) {
		pointCurrent.setLocation(x, y);
	}

	@Override
	public void next() {
		
		ArrayList<Model.CardinalDirections> validDirections = getValidDirections(pointCurrent);
		
		if(validDirections.size() > 0)
		{			
			pointArray.add(pointCurrent);
			
			int rand = randomRange(0, validDirections.size() - 1);
			Model.CardinalDirections randDirection = validDirections.get(rand);
			
			Point pointNext = new Point(pointCurrent.x + randDirection.getX(), 
					pointCurrent.y + randDirection.getY());			
			
			Model.Node currentNode = dataModel.getNode(pointCurrent.x, pointCurrent.y);
			Model.Node nextNode = dataModel.getNode(pointNext.x, pointNext.y);
			
			currentNode.setCardinal(randDirection, false);
			nextNode.setCardinal(randDirection.reverse(), false);
			
			// Set background colors
			dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(visitColor);
			dataModel.getNode(pointNext.x, pointNext.y).setColor(currentColor);
			
			// Set the next node as visited
			nextNode.setVisit(true);
			
			// Replace the 'current node' with the 'next node' 
			pointCurrent = new Point(pointNext.x, pointNext.y);	
			
		} else {	
			// Find the last point in the array
			Point pointNext = pointArray.get(pointArray.size() - 1);
			// Remove the point from the array
			pointArray.remove(pointArray.size() - 1);
			
			// Set background colors
			dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(neutralColor);
			dataModel.getNode(pointNext.x, pointNext.y).setColor(currentColor);
						
			// Set the current position to the specified point
			pointCurrent = pointNext;	
		}		
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

	@Override
	public void reset() {
		dataModel.setAllColor(greyColor);	
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		pointArray.clear();
		
		// Set a new starting point
		this.setPos(randomRange(0, dataModel.get_X_Width() - 1), randomRange(0, dataModel.get_Y_Height() - 1));
		pointArray.add(pointCurrent);
		dataModel.getNode(pointCurrent.x, pointCurrent.y).setVisit(true);
	}

	@Override
	public boolean isComplete() {
		// Return true when there are no elements left in the pointArray
		return pointArray.size() == 0;
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
