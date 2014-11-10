import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class View extends JPanel {
	
	// Frame Width and Height
	final int FRAME_HEIGHT = 750;
	final int FRAME_WIDTH = 750;
	
	// Set the grid and indent size
	private int nodeSize = 50;
	private int indentSize = 2;
	
	private Model nodeModel;
	private MazeView mazeViewer = new MazeView();
	
	JComboBox<String> mazeSelection = new JComboBox<String>();
	private JTextField xTextField = new JTextField(4);
	private JTextField yTextField = new JTextField(4);

	private JButton stepButton = new JButton("Step");
	private JButton runButton = new JButton("Run");
	private JButton resetButton = new JButton("Reset");
	
	public View()
	{			
		JLabel titleLabel = new JLabel("Maze Generation");
		JLabel selectLabel = new JLabel("Select algorithm:");
		JLabel dimensionsLable = new JLabel("Maze size:");
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
							.addComponent(titleLabel, Alignment.CENTER)
							.addComponent(mazeViewer, Alignment.CENTER)		
							.addGroup(Alignment.CENTER, layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup()
											.addComponent(selectLabel)
											.addComponent(dimensionsLable)
											)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(layout.createParallelGroup()
											.addComponent(mazeSelection, 200, 200, 200)
											.addGroup(layout.createSequentialGroup()
													.addComponent(xTextField, 50, 50, 50)
													.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(yTextField, 50, 50, 50)
													)
											)
									)
							.addGroup(Alignment.CENTER, layout.createSequentialGroup()
									.addComponent(stepButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(runButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(resetButton)
									)
							)
					
					.addContainerGap()					
				);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(titleLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup()
							.addComponent(selectLabel)
							.addComponent(mazeSelection, 20, 20, 20)
							)							
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup()
							.addComponent(dimensionsLable)
							.addComponent(xTextField, 20, 20, 20)
							.addComponent(yTextField, 20, 20, 20)
							)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup()
							.addComponent(stepButton)
							.addComponent(runButton)
							.addComponent(resetButton)
							)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGap(20, 20, 20)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(mazeViewer)
					.addContainerGap()
				);
	}	
	
	public void setModel(Model nodeModel)
	{
		this.nodeModel = nodeModel;
	}
	
	public void modelUpdated()
	{
		mazeViewer.repaint();
	}
	
	public void setMazeOptions(String[] options)
	{
		for(String s : options)
		{
			mazeSelection.addItem(s);
		}
	}
	
	public void setNodeSize(int i)
	{
		nodeSize = i;
	}
	
	public void setMazeViewerVisible(boolean b)
	{		
		mazeViewer.setVisible(b);
	}
	
	public void setActionListener(ActionListener aListener)
	{
		// Set actions on buttons
		stepButton.addActionListener(aListener);
		stepButton.setActionCommand("Step");
		runButton.addActionListener(aListener);
		runButton.setActionCommand("Run");
		resetButton.addActionListener(aListener);
		resetButton.setActionCommand("Reset");
		
		// Set action on JComboBox drop down
		mazeSelection.addActionListener(aListener);		
	}
	
	public String getXString()
	{
		return xTextField.getText();
	}
	
	public String getYString()
	{
		return yTextField.getText();
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
					
					if(node.getColor() != null)
					{
						g2.setColor(node.getColor());
						// Make rect one pixel less in all dimensions to preserve the grid
						//g2.fillRect(NW.x + 1, NW.y + 1, nodeSize - 1, nodeSize - 1);
						g2.fillRect(NW.x, NW.y, nodeSize, nodeSize);
						g2.setColor(Color.BLACK);
					}
					
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
