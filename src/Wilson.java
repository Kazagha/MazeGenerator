import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Wilson implements Algorithm {

	Model dataModel;
	Model.CardinalDirections[][] directionModel; 
	Point pointCurrent = new Point(0, 0);
	Point pointStart = new Point(0, 0);
	Mode mode;
	
	enum Mode { SEARCH, CARVE };
	
	Random rand = new Random();	
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250); 
	Color neutralColor = new Color(255, 255, 255);
	Color greyColor = new Color(205, 201, 201);
	
	public Wilson(Model model)
	{
		setModel(model);
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
		// Search the grid for a visited node (part of the established maze) 
		if(mode == Mode.SEARCH)
		{		
			// Fetch all valid directions
			ArrayList<Model.CardinalDirections> validDirections = getValidDirections(pointCurrent);
			
			// Set the background color of the 'current' node
			dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(visitColor);
		
			// Select a random direction
			int rand = randomRange(0, validDirections.size() - 1);
			Model.CardinalDirections randDirection = validDirections.get(rand);
		
			// Record the direction traveled
			directionModel[pointCurrent.x][pointCurrent.y] = randDirection;
		
			// Move the current position
			pointCurrent.setLocation(pointCurrent.x + randDirection.getX(), pointCurrent.y + randDirection.getY());
			
			dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(currentColor);
		
			// If the current position has been visited, begin carving
			if(dataModel.getNode(pointCurrent.x, pointCurrent.y).getVisit())
			{
				mode = Mode.CARVE;
				pointCurrent = pointStart;
				// Set the background color on the starting point
				dataModel.getNode(pointCurrent.x, pointCurrent.y).setColor(neutralColor);
				dataModel.getNode(pointCurrent.x, pointCurrent.y).setVisit(true);
			}
			
		} else if (mode == Mode.CARVE) {
		
		// Carve path according to directionModel
		
			// Using the currentPoint, find the direction from the 'directionModel'
			Model.Node currentNode = dataModel.getNode(pointCurrent.x, pointCurrent.y);
			Model.CardinalDirections direction = directionModel[pointCurrent.x][pointCurrent.y];
		
			// Fetch the 'next' nodes
			Point pointNext = new Point(pointCurrent.x + direction.getX(), pointCurrent.y + direction.getY());
			Model.Node nextNode = dataModel.getNode(pointNext.x, pointNext.y);
		
			// Carve the passage
			currentNode.setCardinal(direction, false);
			nextNode.setCardinal(direction.reverse(), false);
		
			// Replace the 'current point' with the 'next point'
			pointCurrent.setLocation(pointNext.x, pointNext.y);
		
			// If the next node has already been visited, carving has finished
			if(nextNode.getVisit())
			{
				mode = Mode.SEARCH;
				pointStart = getRandomStartingPoint();
				pointCurrent = new Point(pointStart);
			}			

			// Set the background color, and visited status of the node
			nextNode.setColor(neutralColor);
			nextNode.setVisit(true);
		}
	}

	@Override
	public void reset() {
		// Reset the maze
		dataModel.setAllColor(greyColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		// Create one visited node to begin the maze
		Model.Node visitedNode = dataModel.getNode(randomRange(0, dataModel.get_X_Width() - 1), randomRange(0, dataModel.get_Y_Height() - 1));
		visitedNode.setColor(neutralColor);
		visitedNode.setVisit(true);
		
		// Reset the starting position
		setPos(randomRange(0, dataModel.get_X_Width() - 1), randomRange(0, dataModel.get_Y_Height() - 1));
		pointStart = new Point(pointCurrent);
		// Reset the direction model 
		directionModel = new Model.CardinalDirections[dataModel.get_X_Width()][dataModel.get_Y_Height()];
		// Reset the mode back to searching 
		mode = Mode.SEARCH;
	}

	@Override
	public boolean validPos(int x, int y) {
		// Check the position is within the bounds of the Model
		return 	(x >= 0 && x < dataModel.get_X_Width()
				&& y >= 0 && y < dataModel.get_Y_Height());
	}

	@Override
	public void setModel(Model model) {
		dataModel = model;
		reset();		
	}
	
	public Point getRandomStartingPoint()
	{
		Point tempPoint = new Point();
		
		for(int y = dataModel.get_Y_Height() - 1; y > 0; y--)
		{		
			for(int x = dataModel.get_X_Width() - 1; x > 0; x--)
			{
				tempPoint.setLocation(x, y);
				
				if(! dataModel.getNode(tempPoint.x, tempPoint.y).getVisit())
				{
					return tempPoint;
				}
			}
		}
		
		/*
		while(true)
		{
			tempPoint.setLocation(
					randomRange(0, dataModel.get_X_Width() - 1),
					randomRange(0, dataModel.get_Y_Height() - 1));
			
			if(! dataModel.getNode(tempPoint.x, tempPoint.y).getVisit())
			{
				break;
			}
		}
		*/
		
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
	
	/**
	 * Find valid positions around the specified <code>position</code>
	 * @param currentPoint - The current position
	 * @return - ArrayList of valid positions
	 */
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
}
