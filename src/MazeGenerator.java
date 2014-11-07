
public class MazeGenerator {
	public static void main(String args[])
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				View v = new View();
				Model m = new Model(10, 10);
				
				new Controller(m, v);
				
				v.createAndShowGUI();
			}
		});	
	}	
}
