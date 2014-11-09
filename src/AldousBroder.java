import java.awt.Point;

public class AldousBroder implements Algorithm {

	Model dataModel;
	Point currentPos = new Point(0, 0);
	
	public AldousBroder(Model model) {
		model = dataModel;
	}
	
	@Override
	public void setPos(int x, int y) {
		currentPos.setLocation(x, y);
	}

	@Override
	public void next() {
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
	
	public Point randomPoint(Point... p)
	{		
		// Randomize a number inside the possible range
		int i = randomRange(p.length);
		// Return that point (minus one as the array starts at zero)
		return p[i - 1];
	}

}
