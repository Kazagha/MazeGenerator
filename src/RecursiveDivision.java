
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
	
	public class Rect
	{
		int x;
		int y;
		int width;
		int height; 
		
		public Rect(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
		}
		
		public int getX()
		{
			return x;
		}
		
		public int getY()
		{
			return y;
		}
		
		public int getWidth()
		{
			return width;
		}
		
		public int getHeight()
		{
			return height;
		}
		
		public Rect[] split()
		{
			Rect[] tempRect = new Rect[1];
			
			// Is the shape wider than it is high?
			if(this.getWidth() > this.getHeight())
			{
				// Split Vertically
			} else {
				// Split Horizontally 
			}
			
			return tempRect;
		}
	}

}
