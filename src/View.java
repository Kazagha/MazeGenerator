import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Alignment;

public class View extends JPanel {
	
	// Frame Width and Height
	final int FRAME_HEIGHT = 750;
	final int FRAME_WIDTH = 750;
	
	// Set the grid and indent size
	int nodeSize = 50;
	int indentSize = 2;
	
	Model nodeModel;
	MazeView mazeViewer = new MazeView();
	JButton generateButton = new JButton("Generate Maze");
	
	public View()
	{			
		JLabel titleLabel = new JLabel("Maze Generation");
		mazeViewer.setVisible(false);
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
							.addComponent(titleLabel, Alignment.CENTER)
							.addComponent(generateButton, Alignment.CENTER)
							.addComponent(mazeViewer, Alignment.CENTER)
							)
					.addContainerGap()					
				);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(titleLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(generateButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(mazeViewer)
				);
	}	
	
	public void setNodeModel(Model nodeModel)
	{
		this.nodeModel = nodeModel;
	}
	
	public void showMazeViewer()
	{		
		mazeViewer.setVisible(true);		
	}
	
	class MazeView extends Component
	{
		Graphics2D g2;
		
		public void paint(Graphics g)
		{
			g2 = (Graphics2D) g;
			Point NW = new Point(0, 0);
			Point NE = new Point(0, 0);
			Point SE = new Point(0, 0);
			Point SW = new Point(0, 0);
			
			// Iterate on the 'X' axis
			for(int i = 0; i < nodeModel.get_X_Width(); i++)
			{
				// Iterate on the 'Y' axis
				for(int j = 0; j < nodeModel.get_Y_Height(); j++)
				{
					// Get the node at this position
					Model.Node node = nodeModel.getNode(i, j);
					
					// Calculate the corners of the node
					NW.setLocation((i * nodeSize), (j * nodeSize));
					NE.setLocation((i * nodeSize) + nodeSize, (j * nodeSize));
					SE.setLocation((i * nodeSize) + nodeSize, (j * nodeSize) + nodeSize);
					SW.setLocation((i * nodeSize), (j * nodeSize) + nodeSize);
					
					if(node.getNorth()) 
					{
						g2.drawLine(NW.x, NW.y, NE.x, NE.y);
					}
					
					if(node.getEast())
					{
						g2.drawLine(NE.x, NE.y, SE.x, SE.y);
					}
					
					if(node.getSouth())
					{
						g2.drawLine(SE.x, SE.y, SW.x, SW.y);
					}
					
					if(node.getWest())
					{
						g2.drawLine(SW.x, SW.y, NW.x, NW.y);
					}
				}
			}
		}		
	}
	
	public void createAndShowGUI()
	{
		//Create frame setup Window
		JFrame frame = new JFrame("Maze Generation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		
		//Add the content  
		frame.getContentPane().add(this);
		
		//Display the window
		frame.pack();
		frame.setVisible(true);		
	}
}
