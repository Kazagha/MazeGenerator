import java.awt.Color;
import java.util.ArrayList;

public class Model {	
	Node[][] nodeArray = new Node[1][1];
	ArrayList<Listener> listenerArray = new ArrayList<Listener>();
	
	/**
	 * 
	 * @param x - Width
	 * @param y - Height
	 */
	public Model(int x, int y)
	{
		nodeArray = new Node[x][y];
		initializeArray();
	}
	
	/**
	 * Initialize a basic <code>Node</code> in all positions in the Model.
	 */
	private void initializeArray()
	{
		// Iterate through rows in the array
		for(Node[] nRow : nodeArray)
		{
			// Iterate through the elements in the row
			for(int i = 0; i < nRow.length; i++)
			{
				nRow[i] = new Node();
			}
		}
	}
	
	/**
	 * Set all walls in <code>this</code> Model on or off
	 * @param wallsUp - True to turn walls on. 
	 */
	public void setWalls(boolean wallsOn)
	{
		// Iterate through rows in the array
		for(Node[] nRow : nodeArray)
		{
			// Iterate through the elements in the row
			for(int i = 0; i < nRow.length; i++)
			{
				nRow[i].setWalls(wallsOn);
			}
		}

		notifyListener();
	}
	
	/**
	 * Return the node in the specified position
	 * @param x - Width
	 * @param y - Height
	 * @return - Specified node
	 */
	public Node getNode(int x, int y)
	{
		return nodeArray[x][y];
	}
	
	public int get_Y_Height()
	{
		return nodeArray[1].length;
	}
	
	public int get_X_Width()
	{
		return nodeArray.length;
	}
	
	public Node[][] getArray()
	{
		return nodeArray;
	}
	
	/**
	 * Register the specified <code>Listener</code> on <code>this</code>. 
	 * @param listener - The specified listener
	 */
	public void attachListener(Listener listener)
	{
		listenerArray.add(listener);
	}
	
	/**
	 * Notify any attached listeners <code>this</code> has been updated
	 */
	public void notifyListener()
	{
		for(Listener listener : listenerArray)
		{
			listener.update();
		}
	}

	class Node 
	{
		boolean northWall;
		boolean eastWall; 
		boolean southWall;
		boolean westWall;
		Color backgroundColor;
		
		public Node()
		{
			backgroundColor = null;
		}
		
		public void setWalls(boolean wallsUp)
		{
			northWall = wallsUp;
			eastWall = wallsUp;
			southWall = wallsUp;
			westWall = wallsUp;
		}
		
		public void setColor(Color c)
		{
			backgroundColor = c;
			notifyListener();
		}		
		
		public void setNorth(boolean b)
		{
			northWall = b;
			notifyListener();
		}
		
		public void setEast(boolean b)
		{
			eastWall = b;
			notifyListener();
		}
		
		public void setSouth(boolean b)
		{
			southWall = b;
			notifyListener();
		}
		
		public void setWest(boolean b)
		{
			westWall = b;
			notifyListener();
		}
		
		public Color getColor()
		{
			return backgroundColor;
		}
		
		public boolean getNorth()
		{
			return northWall;
		}
		
		public boolean getEast()
		{
			return eastWall;
		}
		
		public boolean getSouth()
		{
			return southWall;
		}
		
		public boolean getWest()
		{
			return westWall;
		}
	}
}
