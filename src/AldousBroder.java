import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class AldousBroder implements Algorithm {

	Model dataModel;
	Point currentPos = new Point(0, 0);
	int nodeCount;
	int visitCount = 0;
	Random rand = new Random();
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250);  	
	
	public AldousBroder(Model model) {
		this.dataModel = model;
		// Every node in the Model
		this.nodeCount = dataModel.get_X_Width() * dataModel.get_Y_Height();
		// Selection a random starting position anywhere in the model. 
		// Minus one as the array starts with zero
		this.setPos(randomRange(dataModel.get_X_Width() - 1), randomRange(dataModel.get_Y_Height()) - 1);
	}
	
	@Override
	public void setPos(int x, int y) {
		currentPos.setLocation(x, y);
	}

	@Override
	public void next() {
		// Find the current Node
		Model.Node currentNode = dataModel.getNode(currentPos.x, currentPos.y);
		
		// Find all valid directions
		ArrayList<Model.CardinalDirections> validDirections = getValidPositions();		
		// Select direction at random, minus one as the array begins at zero
		int rand = randomRange(validDirections.size()) - 1;
		// Selection the direction of movement
		Model.CardinalDirections randDirection = validDirections.get(rand);
		
		// Find the next position using the random direction
		Point nextPos = new Point(currentPos.x + randDirection.getX(),
				currentPos.y + randDirection.getY());
		// Find the next node 
		Model.Node nextNode = dataModel.getNode(nextPos.x, nextPos.y);
		
		// If the node has not been visited yet do the following
		if(nextNode.getVisit() == false)
		{
			currentNode.setCardinal(randDirection, false);
			nextNode.setCardinal(randDirection.reverse(), false);
			
			nextNode.setVisit(true);
			visitCount++;
		}
		
		// Set background colors
		currentNode.setColor(visitColor);
		nextNode.setColor(currentColor);
		
		// Set the 'next node' as the new current position
		this.setPos(nextPos.x, nextPos.y);
	}
	
	@Override
	public void reset() {
		dataModel.setAllWalls(true);
		dataModel.setAllVisited(false);
		dataModel.setAllColor(null);
		visitCount = 0;
	}
	
	@Override
	public boolean isComplete()
	{
		System.out.format("%s of %s%n", nodeCount, visitCount);
		return nodeCount == visitCount;
	}
	
	public void setModel(Model model)
	{
		this.dataModel = model;
		this.nodeCount = dataModel.get_X_Width() * dataModel.get_Y_Height();
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
		return (x >= 0 && x < dataModel.get_X_Width()
				&& y >= 0 && y < dataModel.get_Y_Height());
	}
	
	/**
	 * Randomize a number between 1 and the specified <code>range</code>
	 * @param range - A count of range of valid number
	 * @return
	 */
	public int randomRange(int range)
	{
		return rand.nextInt(range) + 1;
	}
	
	public Point randomPoint(Point[] p)
	{		
		// Randomize a number inside the possible range
		int i = randomRange(p.length);
		// Return that point (minus one as the array starts at zero)
		return p[i - 1];
	}

}
