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
	}
	
	public void setWalls(boolean wallUp)
	{
		for(Node[] nRow : nodeArray)
		{
			for(Node nElement : nRow)
			{
				nElement = new Node(wallUp);
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
		
		public Node(boolean wallUp)
		{
			northWall = wallUp;
			eastWall = wallUp;
			southWall = wallUp;
			westWall = wallUp;
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
