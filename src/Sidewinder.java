import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Sidewinder implements Algorithm {

	Model dataModel;
	Point pointCurrent = new Point(0, 0);
	boolean complete = false;
	
	Random rand = new Random();	
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250); 
	Color neutralColor = new Color(255, 255, 255);
	
	@Override
	public void setPos(int x, int y) {
		pointCurrent.setLocation(x, y);
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

	@Override
	public void next() {
		
	}

	@Override
	public void reset() {
		// Reset the maze
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);		
		
		// Set the starting position to the top left corder
		pointCurrent.setLocation(0, 0);
		// Set the 'complete' flag to false
		complete = false;
	}

	@Override
	public boolean validPos(int x, int y) {
		// Check the position is within the bounds of the Model
		return (x >= 0 && x < dataModel.get_X_Width()
				&& y >= 0 && y < dataModel.get_Y_Height());
	}

	@Override
	public void setModel(Model model) {
		dataModel = model;
		reset();
	}

}
