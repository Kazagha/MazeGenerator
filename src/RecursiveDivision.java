
public class RecursiveDivision implements Algorithm {

	Model dataModel;
	
	
	public RecursiveDivision(Model model) {
		this.setModel(model);
	}
	
	@Override
	public void setPos(int x, int y) {}

	@Override
	public boolean isComplete() {
		return false;
	}

	@Override
	public void next() {
		
	}

	@Override
	public void reset() {
		dataModel.setAllColor(null);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(false);		
	}

	@Override
	public boolean validPos(int x, int y) {
		return false;
	}

	@Override
	public void setModel(Model model) {
		this.dataModel = model;		
	}
	
	/**
	 * Randomize a number between the specified <code>min</code> and <code>max</code> 
	 * @param min
	 * @param max
	 * @return
	 */
	public int randomRange(int min, int max)
	{
		int range = max - min;
		return (int)((Math.random() * (range)) + min);
	}
	

}
