import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class HuntAndKill implements Algorithm {

	Model dataModel;
	Point pointCurrent = new Point(0, 0);
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
		
	}

	@Override
	public void reset() {
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		this.setPos(randomRange(0, dataModel.get_X_Width() - 1), randomRange(0, dataModel.get_Y_Height() - 1));
		dataModel.getNode(pointCurrent.x, pointCurrent.y).setVisit(true);
	}

	@Override
	public boolean validPos(int x, int y) {
		return false;
	}

	@Override
	public void setModel(Model model) {
		dataModel = model;
		this.reset();
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
