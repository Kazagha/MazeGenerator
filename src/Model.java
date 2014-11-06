public class Model {	
	Node[][] nodeArray = new Node[1][1];
	
	/**
	 * 
	 * @param x - Width
	 * @param y - Height
	 */
	public Model(int x, int y)
	{
		nodeArray = new Node[y][x];	
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
		return nodeArray[y][x];
	}
	
	public int getHeight()
	{
		return nodeArray.length;
	}
	
	public int getWidth()
	{
		return nodeArray[1].length;
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
	}
}
