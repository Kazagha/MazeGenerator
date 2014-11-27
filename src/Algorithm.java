
public abstract interface Algorithm {

	public abstract void setPos(int x, int y);
	
	public abstract boolean isComplete();
	
	public abstract void next();
	
	public abstract void reset();

	public boolean validPos(int x, int y);
	
	public abstract void setModel(Model model);
}
