import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Alignment;

public class View extends JPanel {
	
	// Frame Width and Height
	final int FRAME_HEIGHT = 750;
	final int FRAME_WIDTH = 750;
	
	Model nodeArray;
	MazeView mv;
	
	public View()
	{			
		JLabel titleLabel = new JLabel("Maze Generation");		
		mv = new MazeView();
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
							.addComponent(titleLabel, Alignment.CENTER)
							.addComponent(mv, Alignment.CENTER)
							)
					.addContainerGap()					
				);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(titleLabel)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(mv)
				);
	}	
	
	public void setNodeArray(Model nodeModel)
	{
		this.nodeArray = nodeModel;
	}
	
	class MazeView extends Component
	{
		public void paint(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			
			for(int i = 0; i < nodeArray.getWidth(); i++)
			{
				System.out.println(i);
				for(int j = 0; j < nodeArray.getHeight(); j++)
				{
					Model.Node n = nodeArray.getNode(i, j);
					
					g2.drawLine(i * 50, j * 50, (i * 50) + 45, (j * 50));
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
