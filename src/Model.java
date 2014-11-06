public class Model {	
	Node[][] nodeArray = new Node[1][1];
	
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
