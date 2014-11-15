import java.awt.Color;
import java.util.Random;


public class RecursiveDivision implements Algorithm {

	Model dataModel;
	Rect rectModel;	
	Random rand = new Random();
	enum Split { HORIZONTAL, VERTICAL };
	
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
		
		//Rect[] test = (new Rect(5, 5, 6, 4)).split();
		//System.out.println(test[0].x + (test[0].getWidth() - 1) + " - " + test[1].x);
		//System.out.println(test[0].y + (test[0].getHeight() - 1) + " - " + test[1].y);
		
		colorRect(new Rect(0, 0, 0, 5), Color.GREEN);
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
		int height; 
		int width;
		
		public Rect(int x, int y, int width, int height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
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
			Split division;
			
			// A 'Rect' must be smaller than 2 cannot be split
			if(this.getWidth() < 2 && this.getHeight() < 2)
			{
				return null;
			}
			
			// Is the shape wider than it is high?
			if (this.getWidth() > this.getHeight())
			{
				// Split Vertically
				division = Split.VERTICAL;
			} else if (this.getHeight() > this.getWidth()) {
				// Split Horizontally
				division = Split.HORIZONTAL;
			} else {
				// Random Split				
				if(randomRange(1, 2) == 1)
				{
					division = Split.HORIZONTAL;
				} else {
					division = Split.VERTICAL;
				}
			}				
				
			if(division == Split.VERTICAL)
			{
				// Split Vertically
				int split = randomRange(1, getWidth() - 1);
				
				tempRect[0] = new Rect(
						this.getX(),
						this.getY(),
						split,
						this.getHeight());						
				
				tempRect[1] = new Rect(
						this.getX() + split,
						this.getY(), 
						this.getWidth() - split,
						this.getHeight());				
			} else {
				// Split Horizontally 
				int split = randomRange(1, getHeight() - 1);
				
				tempRect[0] = new Rect(
						this.getX(),
						this.getY(),
						this.getWidth(), 
						split);
				
				tempRect[1] = new Rect(
						this.getX(),
						this.getY() + split,
						this.getWidth(),
						this.getHeight() - split);
			}			
			
			return tempRect;
		}
	}

}
