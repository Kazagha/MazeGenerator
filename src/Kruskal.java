import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;


public class Kruskal implements Algorithm {
	
	Model dataModel;
	Tree.Node[][] treeNodeModel;
	ArrayList<Tree> treeList;
	
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

}
