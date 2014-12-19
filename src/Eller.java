import java.awt.Color;
import java.util.Random;


public class Eller implements Algorithm {
	
	Model dataModel;
	int[] setArray;

	Random rand = new Random();
	
	// Set Colors
	Color currentColor = new Color(220, 20, 20);
	Color neutralColor = new Color(255, 255, 255);
	
	public Eller(Model model)
	{
		this.setModel(model);
	}

	@Override
	public void setPos(int x, int y) {}

	@Override
	public boolean isComplete() {
		return false;
	}

	@Override
	public void next() {}

	@Override
	public void reset() {
		// Reset the maze
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		// Reset the 'sets' array	
		setArray = new int[dataModel.get_X_Width()];
	}

	@Override
	public boolean validPos(int x, int y) {
		return false;
	}

	@Override
	public void setModel(Model model) {
		this.dataModel = model;
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
}
