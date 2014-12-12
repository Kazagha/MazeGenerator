import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;


public class Kruskal implements Algorithm {
	
	Model dataModel;
	Tree.Node[][] treeNodeModel;
	ArrayList<Edge> edgeList;
	
	// Set Colors
	Color currentColor = new Color(205, 92, 92);
	Color visitColor = new Color(135, 206, 250);  	
	Color neutralColor = new Color(255, 255, 255);

	public Kruskal(Model model)
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
	public void next() {
		
	}

	@Override
	public void reset() {
		dataModel.setAllColor(neutralColor);
		dataModel.setAllVisited(false);
		dataModel.setAllWalls(true);
		
		// Create a new Tree Node model the same size as the dataModel
		treeNodeModel = new Tree.Node
				[dataModel.get_Y_Height()][dataModel.get_X_Width()];
		
		// Create an List of all edges
		edgeList = new ArrayList<Edge>();	
		
		// Populate the Tree Node Model with the nodes in the dataModel
		for(int x = 0; x < dataModel.get_X_Width(); x++)
		{
			for(int y = 0; y < dataModel.get_Y_Height(); y++)
			{
				Model.Node tempNode = dataModel.getNode(x, y);
				treeNodeModel[y][x] = new Tree.Node(tempNode);
				
				ArrayList<Model.CardinalDirections> directions = getValidDirections(new Point(x, y));
				for(Model.CardinalDirections cd : directions)
				{
					edgeList.add(new Edge(tempNode, cd));
				}
			}
		}
	}

	@Override
	public boolean validPos(int x, int y) {
		// Check the position is within the bounds of the Model
		// (only checking SOUTH and EAST)
		return (x < dataModel.get_X_Width()
				&& y < dataModel.get_Y_Height());
	}

	@Override
	public void setModel(Model model) {
		dataModel = model;
		reset();		
	}
	
	private class Edge
	{
		private Model.Node node;
		private Model.CardinalDirections direction;
		
		public Edge(Model.Node node, Model.CardinalDirections direction)
		{
			this.node = node;
			this.direction = direction;
		}
	}
	
	public ArrayList<Model.CardinalDirections> getValidDirections(Point currentPoint)
	{
		ArrayList<Model.CardinalDirections> tempList = new ArrayList<Model.CardinalDirections>();
		
		// Use only two possible directions
		Model.CardinalDirections[] directions =
			{  Model.CardinalDirections.SOUTH, Model.CardinalDirections.EAST }; 
		
		// Check the Cardinal directions for valid positions
		for(Model.CardinalDirections cd : directions)
		{
			Point tempPoint = new Point(currentPoint.x + cd.getX(), (currentPoint.y + cd.getY()));
			// Check if this is a valid position
			if(validPos(tempPoint.x, tempPoint.y))
			{
				tempList.add(cd);
			}
		}	
		
		return tempList;
	}
}
