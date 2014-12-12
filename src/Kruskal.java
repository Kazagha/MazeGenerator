import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;


public class Kruskal implements Algorithm {
	
	Model dataModel;
	Tree.Node[][] treeNodeModel;
	ArrayList<Wall> wallList;
	
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
		
		// Populate the Tree Node Model with the nodes in the dataModel
		for(int x = 0; x < dataModel.get_X_Width(); x++)
		{
			for(int y = 0; y < dataModel.get_Y_Height(); y++)
			{
				treeNodeModel[y][x] = new Tree.Node(dataModel.getNode(x, y));
			}
		}	
		
		wallList = new ArrayList<Wall>();
		
		
	}

	@Override
	public boolean validPos(int x, int y) {
		return false;
	}

	@Override
	public void setModel(Model model) {
		dataModel = model;
		reset();		
	}
	
	private class Wall
	{
		private Model.Node node;
		private Model.CardinalDirections direction;
		
		public Wall(Model.Node node, Model.CardinalDirections direction)
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
			{  Model.CardinalDirections.NORTH, Model.CardinalDirections.EAST }; 
		
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
