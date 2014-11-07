import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class Controller {
	
	private View view;
	private Model model;
	//private String[] mazeType = new String[] {"Grid"};

	public Controller(Model m, View v)
	{
		this.view = v;
		this.model = m;
		
		v.setModel(m);
		
		m.attachListener(new modelListener());				
		v.setActionListener(new MyActionListener());
		
		v.setMazeOptions(new String[] 
			{	" ",
				"Load Model",
				"Reset Model",
				"Show Model",
				"Hide Model",
				"Do Something"
			});
	}
	
	
	public void doChanges()
	{
		for(int i = 0; i < model.get_Y_Height(); i++)
		{
			model.getNode(2, i).setNorth(false);
			model.getNode(2, i).setSouth(false);
		}
	}
	
	public class modelListener extends Listener {
		
		@Override
		public void update() {
			System.out.println("there has been an update");	
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
				case "Load Model":
					//model = new Model(5, 10);
					model.setWalls(true);
					view.setModel(model);
					view.modelUpdated();
					break;
				case "Reset Model":
					model.setWalls(true);
					view.modelUpdated();
					break;
				case "Show Model":	
					//view.showMazeViewer(false);
					view.setMazeViewerVisible(true);
					//view.revalidate();
					//view.modelUpdated();
					break;
				case "Hide Model":
					view.setMazeViewerVisible(false);
					break;
				case "Do Something":
					doChanges();
					view.modelUpdated();
					break;
				}
			} else if (e.getSource() instanceof JButton) {
				
			}
		}		
	}
}
