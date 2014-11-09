import java.awt.Point;
import java.util.ArrayList;

public class AldousBroder implements Algorithm {

	Model dataModel;
	Point currentPos = new Point(0, 0);
	
	public enum CardinalDirections
	{		
		// Assume grid origin is top-left. 
		NORTH(0, -1),
		EAST(1, 0),
		SOUTH(0, 1),
		WEST(-1, 0);

		private int x;
		private int y;
		private CardinalDirections(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX()
		{
			return x;
		}
		
		public int getY()
		{
			return y;
		}
	}
	
	public AldousBroder(Model model) {
		model = dataModel;
	}
	
	@Override
	public void setPos(int x, int y) {
		currentPos.setLocation(x, y);
	}

	@Override
	public void next() {
		
		// Get Array List
		
		// Random number randomRange(list.length)
		
		// Select that list.get(x)
	}

	@Override
	public void reset() {
		dataModel.setAllWalls(false);
		dataModel.setAllVisited(false);
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
