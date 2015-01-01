import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
		v.setTextFieldListener(new MyDocumentListener());
		
		v.setMazeOptions(new String[] 
			{	" ",
				"Aldous and Broder",
				"Binary Tree",
				"Eller's Algorithm",
				"Hunt and Kill",
				"Kruskal's Algorithm",
				"Prim's Algorithm",
				"Recursive Backtracker",
				"Recursive Division",
				"Sidewinder",
				"Wilson's Algorithm"
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
				case "Aldous and Broder":
					algorithm = new AldousBroder(model);
					algorithm.reset();
					break;
				case "Binary Tree":
					algorithm = new BinaryTree(model);
					algorithm.reset();
					break;
				case "Eller's Algorithm":
					algorithm = new Eller(model);
					algorithm.reset();
					break;
				case "Hunt and Kill":
					algorithm = new HuntAndKill(model);
					algorithm.reset();
					break;
				case "Kruskal's Algorithm":
					algorithm = new Kruskal(model);
					algorithm.reset();
					break;
				case "Prim's Algorithm":
					algorithm = new Prim(model);
					algorithm.reset();
					break;
				case "Recursive Division":
					algorithm = new RecursiveDivision(model);
					algorithm.reset();
					break;
				case "Recursive Backtracker":
					algorithm = new RecursiveBacktracker(model);
					algorithm.reset();
					break;
				case "Sidewinder":
					algorithm = new Sidewinder(model);
					algorithm.reset();
					break;
				case "Wilson's Algorithm":
					algorithm = new Wilson(model);
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
										Thread.sleep(75);
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
	
	public class MyDocumentListener implements DocumentListener
	{

		@Override
		public void changedUpdate(DocumentEvent e) {
			// Do nothing
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			JTextField textField = null;
			
			// Find the JTextField that fired the event using the 'owner' property
			Object owner = e.getDocument().getProperty("owner");
			if(owner instanceof JTextField)
			{
				textField = (JTextField) owner;
			} else {
				// At this stage JTextField should be the only source
				return;
			}
			
			try {				
				
				int var = Integer.valueOf(textField.getText());
				
				// The grid size cannot be <= 0
				if(var < 1) return;
				
				if(e.getDocument().getProperty("dimension").equals("x"))
				{
					setModel(new Model(var, model.get_Y_Height()));
				} else {
					setModel(new Model(model.get_X_Width(), var));
				}
			
			} catch (NumberFormatException ex) {
				// The user has entered a alpha character
			}			
			
			if(algorithm != null)
			{
				algorithm.setModel(model);
				algorithm.reset();
			} else {
				System.out.println("null");
			}			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// Do nothing
			
		}
	}	
}
