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
		ArrayList<Model.CardinalDirections> validDirections = getValidPositions(pointCurrent);
		
		if(validDirections != null)
		{
			int rand = randomRange(0, validDirections.size() - 1);
			Model.CardinalDirections randDirection = validDirections.get(rand);
			
			Point pointNext = new Point(pointCurrent.x + randDirection.getX(), 
					pointCurrent.y + randDirection.getY());			
			
			// Carve walls 
			
			// Move the current position to the selected node
			
			// Put the current position into the pointArray
		}
		
		// } else {
		
		// No valid direction, set the last on the PointArray list as the current
		
		// Remove the elemeent
		
		//}
		
		dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(currentColor);
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
	
	public ArrayList<Model.CardinalDirections> getValidPositions(Point currentPoint)
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
