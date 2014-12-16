import java.util.ArrayList;

public class Tree<T> {
	private Node<T> root;
	
	public Tree(T rootData)
	{
		root = new Node<T>(rootData);
	}	
	
	public Node<T> getModel()
	{
		return root;
	}
	
	public static class Node<T> {
		private T data;
		private Node<T> parent;
		private ArrayList<Node<T>> children;
		private int nodeCount;
		
		public Node(T nodeData)
		{
			data = nodeData;
			children = new ArrayList<Node<T>>();
			nodeCount = 1;
		}
		
		public String toString()
		{
			return data.toString();
		}
		
		public T getData()
		{
			return data;
		}
		
		public int getNodeCount()
		{
			return nodeCount;
		}
		
		public boolean isRelated(Node node)
		{
			if(node == null)
			{
				return false;
			}
			
			return this.getRootNode() == node.getRootNode();
		}
		
		public int getChildCount()
		{
			return children.size();
		}
		
		public Node<T> getChildAt(int index)
		{
			return children.get(index);
		}
		
		public Node<T> getParentNode()
		{
			return parent;
		}
		
		public Node<T> getRootNode()
		{
			Node current = this;
			Node check = this.getParentNode();
			
			while(check != null)
			{
				current = check;
				check = current.getParentNode();
			}
			
			return current;
		}
		
		public void addChild(Node<T> child)
		{
			children.add(child);
			child.parent = this;
			this.getRootNode().nodeCount += child.nodeCount;
		}
	}
}