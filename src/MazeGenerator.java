
public class MazeGenerator {
	public static void main(String args[])
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				View v = new View();
				
				new Controller(v);
				
				v.createAndShowGUI();
			}
		});	
	}	
}
