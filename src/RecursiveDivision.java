import java.awt.Color;
import java.util.Random;


public class RecursiveDivision implements Algorithm {

	Model dataModel;
	Rect rectModel;	
	Random rand = new Random();
	
	// Set Color
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250); 
	
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
		colorRect(rectModel, visitColor);		
		
		Rect[] test = rectModel.split();
	
		colorRect(test[0], currentColor);
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
		rectModel = new Rect(0, 0, dataModel.get_X_Width(), dataModel.get_Y_Height());
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
	
	public void colorRect(Rect r, Color c)
	{
		for(int x = r.getX(); x < (r.getX() + r.getWidth()); x++)
		{
			for(int y = r.getY(); y < (r.getY() + r.getHeight()); y++)
			{
				dataModel.getNode(x, y).setColor(c);
			}
		}
	}
	
	public class Rect
	{
		int x;
		int y;
		int x2;
		int y2;
		
		public Rect(int x, int y, int x2, int y2)
		{
			this.x = x;
			this.y = y;
			this.x2 = x2;
			this.y2 = y2;
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
			return x2 - x;
		}
		
		public int getHeight()
		{
			return y2 - y;
		}
		
		/**
		 * Split <code>this</code> into two at a random point. <br>
		 *  - If the shape is wider than it is high; split vertically <br>
		 *  - If the shape is higher than it is wide; split horizontally <br> 
		 *  - <code>split</code> represents the wall between cells; 
		 *    1|2|3|4|5 - Five cells and four walls <br>
		 * @return
		 */
		public Rect[] split()
		{
			Rect[] tempRect = new Rect[2];
			
			// Check if the Rect can be split
			if(this.getWidth() < 2 && this.getHeight() < 2)
			{
				return null;
			}
			
			// Is the shape wider than it is high?
			if(this.getWidth() > this.getHeight())
			{
				// Split Vertically. 
				int split = randomRange(this.getX(), this.getX() + getWidth() - 2);
				
				tempRect[0] = new Rect(this.getX(), this.getY(), 
						split, this.getY() + this.getHeight());
				
				// Start one further than the split on the X axis				
				tempRect[1] = new Rect(split + 1, this.getY(),
						this.getX() + this.getWidth(), this.getY() + this.getHeight());				
			} else {
				// Split Horizontally 
				int split = randomRange(this.getY(), this.getY() + getHeight() - 2);
				
				tempRect[0] = new Rect(this.getX(), this.getY(),
						this.getX() + this.getWidth(), split);		
				
				// Start one further than the split on the Y axis
				tempRect[1] = new Rect(this.getX(), split + 1,
						this.getX() + this.getWidth(), this.getY() + this.getHeight());
				
			}			
			
			return tempRect;
		}
	}

}
