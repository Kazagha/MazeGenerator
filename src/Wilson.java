import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Wilson implements Algorithm {

	Model dataModel;
	Model.CardinalDirections[][] directionModel; 
	Point pointCurrent = new Point(0, 0);
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
		
			// Fetch all valid directions
		
			// Select a random direction
		
			// Record the direction traveled
		
			// Move the current position
		
			// If the current position has been visited, begin carving
		
		// Carve path according to directionModel
		
			// Using the currentPoint, find the direction from the 'directionModel'
		
			// Fetch the 'current' and 'next' nodes
		
			// Carve the passage
		
			// Replace the 'current node' with the 'next node'
		
			// If the next node has been visited, carving has finished
	}

	@Override
	public void reset() {
		// Reset the maze
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		setPos(randomRange(0, dataModel.get_X_Width() - 1), randomRange(0, dataModel.get_Y_Height() - 1));
		directionModel = new Model.CardinalDirections[dataModel.get_X_Width() - 1][dataModel.get_Y_Height() - 1];
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
