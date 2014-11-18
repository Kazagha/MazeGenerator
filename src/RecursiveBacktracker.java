import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class RecursiveBacktracker implements Algorithm {

	Model dataModel; 
	ArrayList<Point> pointArray = new ArrayList<Point>();
	Point pointCurrent = new Point(0, 0);
			
	Random rand = new Random();	
	
	@Override
	public void setModel(Model model) {
		this.dataModel = model;
		this.reset();
		
		this.setPos(randomRange(0, dataModel.get_X_Width()), randomRange(0, dataModel.get_Y_Height()));
	}

	@Override
	public void setPos(int x, int y) {
		pointCurrent.setLocation(x, y);
	}

	@Override
	public void next() {
		// Find all valid directions
		
		// Select a direction at random

		// {
		
		// Fetch the node in this position
		
		// Carve walls 
		
		// Move the current position to the selected node
		
		// Put the current position into the pointArray
		
		// } else {
		
		// No valid direction, set the last on the PointArray list as the current
		
		// Remove the elemeent
		
		//}
	}

	@Override
	public boolean validPos(int x, int y) {
		
		// Check the position is within the bounds of the Model
		if (x >= 0 && x < dataModel.get_X_Width()
				&& y >= 0 && y < dataModel.get_Y_Height())
		{
			// Check if the position has been visited already
			return ! (dataModel.getNode(x, y).getVisit());
		} 
		
		// Failing finding a valid position return false
		return false;
	}

	@Override
	public void reset() {
		dataModel.setAllColor(null);	
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		pointArray.clear();
	}

	@Override
	public boolean isComplete() {
		// Return true when there are no elements left in the pointArray
		return pointArray.size() == 0;
	}
	
	public ArrayList<Model.CardinalDirections> getValidPositions()
	{
		ArrayList<Model.CardinalDirections> tempList = new ArrayList<Model.CardinalDirections>();
		
		// Check the Cardinal directions for valid positions
		for(Model.CardinalDirections cd : Model.CardinalDirections.values())
		{
			Point tempPoint = new Point(pointCurrent.x + cd.getX(), (pointCurrent.y + cd.getY()));
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
