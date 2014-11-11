import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class Controller {
	
	private View view;
	private Model model;
	private Algorithm algorithm; 
	private Thread thread = new Thread();

	public Controller(Model m, View v)
	{
		this.view = v;
		this.model = m;
		
		v.setModel(m);		
		m.attachListener(new modelListener());				
		v.setActionListener(new MyActionListener());
		
		v.setMazeOptions(new String[] 
			{	" ",
				"Aldous and Broder",
				"Set Size"
			});
	}
	
	private void setModel(Model m)
	{
		model = m;
		view.setModel(model);
		model.attachListener(new modelListener());	
	}
	
	public class modelListener extends Listener {
		
		@Override
		public void update() {
			view.modelUpdated();
		}		
	}
	
	public class MyActionListener implements ActionListener
	{		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() instanceof JComboBox)
			{
				JComboBox<String> combo = (JComboBox) e.getSource();
							
				switch((String) combo.getSelectedItem())
				{
				case "Show Model":	
					view.setMazeViewerVisible(true);
					break;
				case "Hide Model":
					view.setMazeViewerVisible(false);
					break;
				case "Set Size":
					int x = Integer.valueOf(view.getXString());
					int y = Integer.valueOf(view.getYString());
					setModel(new Model(x, y));
				case "Aldous and Broder":
					algorithm = new AldousBroder(model);
					algorithm.reset();
					break;
				}
			} else if (e.getSource() instanceof JButton) {				
				switch(e.getActionCommand())
				{
				case "Step":
					// Stop thread from running and return to single steps
					if(thread.isAlive())
					{
						thread.interrupt();
					}
					
					// Progress the algorithm one step
					algorithm.next();
					break;
				case "Run":						
					// If the thread is already running, interrupt it
					if(thread.isAlive())
					{
						thread.interrupt();	
					
					} else {
					// No alive thread, create a new thread then start it
						thread = new Thread(new Runnable() {
							
							@Override
							public void run() {
								try{
									// Continue to run the algorithm until complete
									while(! algorithm.isComplete())
									{
										algorithm.next();
										Thread.sleep(100);
									}
								} catch (InterruptedException e) {
									// Thread has been interrupted
								}
							}
						});
						
						thread.start();					
					}					
					break;
				case "Reset":
					// Reset the model as determined by the algorithm
					algorithm.reset();
					break;
				}
			}
		}		
	}
}
