import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.sun.org.apache.xpath.internal.operations.Mod;

public class Kruskal implements Algorithm {
	
	Model dataModel;
	Tree.Node[][] treeNodeModel;
	ArrayList<Edge> edgeList;
	
	Random rand = new Random();
	
	// Set Colors
	ArrayList<Color> colorList = new ArrayList<Color>();
	Color currentColor = new Color(220, 20, 20);
	Color neutralColor = new Color(255, 255, 255);
	int colorShift = 40;

	public Kruskal(Model model)
	{
		this.setModel(model); 
	}
	
	@Override
	public void setPos(int x, int y) {}

	@Override
	public boolean isComplete() {
		return edgeList.size() == 0;
	}

	@Override
	public void next() {
		
		// Randomly select an edge
		int rand = randomRange(0, edgeList.size() - 1);
		Edge tempEdge = edgeList.get(rand);	
		
		// Find the 'current' and 'adjacent' nodes		
		Tree.Node currentNode = treeNodeModel[tempEdge.point.y][tempEdge.point.x];
		Tree.Node adjacentNode = treeNodeModel
				[tempEdge.point.y + tempEdge.direction.getY()]
				[tempEdge.point.x + tempEdge.direction.getX()];
		
		// Check if removing the edge joins two disjoint trees
		if(! currentNode.isRelated(adjacentNode))
		{				
			// Carve walls between nodes
			((Model.Node) currentNode.getData()).setCardinal(tempEdge.direction, false);
			((Model.Node) adjacentNode.getData()).setCardinal(tempEdge.direction.reverse(), false);
				
			// Check which tree is larger
			if(currentNode.getRootNode().getNodeCount() > adjacentNode.getRootNode().getNodeCount())
			{
				// On the current node, add the adjacent node as a child
				currentNode.addChild(adjacentNode.getRootNode());
				// Use the existing background color of the current node
				colorTree(currentNode.getRootNode(), ((Model.Node) currentNode.getData()).getColor());	
			} else if(currentNode.getRootNode().getNodeCount() < adjacentNode.getRootNode().getNodeCount()){
				// On the adjacent node, add the current node as a child
				adjacentNode.addChild(currentNode.getRootNode());
				// Use the existing background color of the adjacent node
				colorTree(adjacentNode.getRootNode(), ((Model.Node) adjacentNode.getData()).getColor());
			} else {
				// On the current node, add the adjacent node as a child
				currentNode.addChild(adjacentNode.getRootNode());
				// Select a new color as the background color 
				currentColor = nextColor();
				colorTree(currentNode.getRootNode(), currentColor);
			}
		}
		
		// Remove the edge from the ArrayList
		edgeList.remove(rand);
	}

	@Override
	public void reset() {
		// Reset the maze
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
				// Fetch the node at the current position
				Model.Node tempNode = dataModel.getNode(x, y);
				
				// Create a 'Tree Node' from the current position
				treeNodeModel[y][x] = new Tree.Node(tempNode);
				
				// Add valid edges in the ArrayList
				Point tempPoint = new Point(x, y);
				ArrayList<Model.CardinalDirections> directions = getValidDirections(tempPoint);
				for(Model.CardinalDirections cd : directions)
				{
					edgeList.add(new Edge(tempPoint, cd));
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
		private Point point;
		private Model.CardinalDirections direction;
		
		public Edge(Point point, Model.CardinalDirections direction)
		{
			this.point = point;
			this.direction = direction;
		}
	}
	
	/**
	 * Find the next color in sequence 
	 * @return - The next color 
	 */
	public Color nextColor()
	{
		Color color = null; 
				
		// Red is dominate; decrease blue and increase green 
		if(currentColor.getRed() == 220 && currentColor.getBlue() > 20)
		{
			color = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue() - colorShift);
		} else if (currentColor.getRed() == 220 && currentColor.getGreen() < 220) {
			color = new Color(currentColor.getRed(), currentColor.getGreen() + colorShift, currentColor.getBlue());
		}  
		
		// Green is dominate; decrease red and increase blue
		if(currentColor.getGreen() == 220 && currentColor.getRed() > 20)
		{
			color = new Color(currentColor.getRed() - colorShift, currentColor.getGreen(), currentColor.getBlue());
		} else if (currentColor.getGreen() == 220 && currentColor.getBlue() < 220) {
			color = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue() + colorShift);
		} 
		
		// Blue is dominate; decrease green increase red
		if(currentColor.getBlue() == 220 && currentColor.getGreen() > 20)
		{
			color = new Color(currentColor.getRed(), currentColor.getGreen() - colorShift, currentColor.getBlue());
		} else if (currentColor.getBlue() == 220 && currentColor.getRed() < 220) {
			color = new Color(currentColor.getRed() + colorShift, currentColor.getGreen(), currentColor.getBlue());
		} 
		
		// Set the 'current color' 		
		currentColor = color; 
		
		return color; 
	}
	
	/**
	 * Color the specified <code>treeNode</code> and all children in the specified color <code>c</code>
	 * @param treeNode - The specified node
	 * @param c - The specified color
	 */
	public void colorTree(Tree.Node treeNode, Color c)
	{
		// Color the root node
		Model.Node currentNode = ((Model.Node) treeNode.getData());
		currentNode.setColor(c);
				
		// Count the children of the current node 
		int childCount = treeNode.getChildCount();
		
		// If there are children, iterate through them
		for(int i = 0; i < childCount; i++)
		{
			// Find the child node in this position
			Tree.Node childNode = treeNode.getChildAt(i);
			
			// Color the specified node and child nodes
			colorTree(childNode, c);
		}
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
