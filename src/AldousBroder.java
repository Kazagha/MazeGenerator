import java.awt.Point;
import java.util.ArrayList;

public class AldousBroder implements Algorithm {

	Model dataModel;
	Point currentPos = new Point(0, 0);
	
	public AldousBroder(Model model) {
		this.dataModel = model;
	}
	
	@Override
	public void setPos(int x, int y) {
		currentPos.setLocation(x, y);
	}

	@Override
	public void next() {
		
		// Find all valid directions
		ArrayList<Model.CardinalDirections> validDirections = getValidPositions();		
		// Select direction at random, minus one as the array begins at zero
		int rand = randomRange(validDirections.size()) - 1;
		// Selection the direction of movement
		Model.CardinalDirections randDirection = validDirections.get(rand);
		
		Model.Node currentNode = dataModel.getNode(currentPos.x, currentPos.y);
		Model.Node nextNode = dataModel.getNode(
				currentPos.x + randDirection.getX(),
				currentPos.y + randDirection.getY());
		
		if(nextNode.getVisit() == false) {}
		
		System.out.println("Random " + rand + " " + randDirection.getX() + " " + randDirection.getY());
	}

	@Override
	public void reset() {
		dataModel.setAllWalls(false);
		dataModel.setAllVisited(false);
	}
	
	public ArrayList<Model.CardinalDirections> getValidPositions()
	{
		ArrayList<Model.CardinalDirections> tempList = new ArrayList<Model.CardinalDirections>();
		
		// Check the Cardinal directions for valid positions
		for(Model.CardinalDirections cd : Model.CardinalDirections.values())
		{
			Point tempPoint = new Point(currentPos.x + cd.getX(), (currentPos.y + cd.getY()));
			// Check if this is a valid position
			if(validPos(tempPoint.x, tempPoint.y))
			{
				tempList.add(cd);
			}
		}	
		
		return tempList;
	}

	@Override
	public boolean validPos(int x, int y) {
		
		// Check the position is within the bounds of the Model
		return (x > 0 && x < dataModel.get_X_Width()
				&& y > 0 && y < dataModel.get_Y_Height());
	}
	
	/**
	 * Randomize a number between 1 and the specified <code>range</code>
	 * @param range - A count of range of valid number
	 * @return
	 */
	public int randomRange(int range)
	{
		return (int)((Math.random() * range) + 1);
	}
	
	public Point randomPoint(Point[] p)
	{		
		// Randomize a number inside the possible range
		int i = randomRange(p.length);
		// Return that point (minus one as the array starts at zero)
		return p[i - 1];
	}

}
