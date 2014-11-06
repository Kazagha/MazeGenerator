import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class Controller {
	
	private View view;
	private Model model;
	//private String[] mazeType = new String[] {"Grid"};

	public Controller(View v)
	{
		this.view = v;
		v.setActionListener(new MyActionListener());
		v.setMazeOptions(new String[] {" ", "Load Grid", "Do Something"});
	}
	
	public void doChanges()
	{
		for(int i = 0; i < model.get_Y_Height(); i++)
		{
		model.getNode(2, i).setNorth(false);
		model.getNode(2, i).setSouth(false);
		}
	}
	
	public class MyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() instanceof JComboBox)
			{
				JComboBox combo = (JComboBox) e.getSource();
							
				switch((String) combo.getSelectedItem())
				{
				case "Load Grid":
					model = new Model(5, 10);
					model.setWalls(true);
	
					view.setNodeModel(model);
					view.showMazeViewer();
					
					view.revalidate();
					System.out.println("action running");				
					break;
				case "Do Something":
					System.out.println("Do Something");
					break;
				}
			} else if (e.getSource() instanceof JButton) {
				
			}
		}		
	}
}
