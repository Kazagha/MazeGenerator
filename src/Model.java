public class Model {	
	Node[][] nodeArray = new Node[1][1];
	
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
	
	public void setWalls(boolean wallsUp)
	{
		// Iterate through rows in the array
		for(Node[] nRow : nodeArray)
		{
			// Iterate through the elements in the row
			for(int i = 0; i < nRow.length; i++)
			{
				nRow[i].setWalls(wallsUp);
			}
		}
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

	class Node 
	{
		boolean northWall;
		boolean eastWall; 
		boolean southWall;
		boolean westWall;
		
		public Node() { }
		
		public void setWalls(boolean wallsUp)
		{
			northWall = wallsUp;
			eastWall = wallsUp;
			southWall = wallsUp;
			westWall = wallsUp;
		}
		
		public void setNorth(boolean b)
		{
			northWall = b;
		}
		
		public void setEast(boolean b)
		{
			eastWall = b;
		}
		
		public void setSouth(boolean b)
		{
			southWall = b;
		}
		
		public void setWest(boolean b)
		{
			westWall = b;
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
