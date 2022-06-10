package jk.frame.utils.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Tree<T> {
	
	private TreeNode<T> root;
	private Comparator<T> comparator;
	
	public Tree(T root, Comparator<T> comparator)
	{
		this.root = new TreeNode<T>(null, root);
		this.comparator = comparator;
	}
	
	public Tree(Comparator<T> comparator)
	{
		this.root = null;
		this.comparator = comparator;
	}
	
	public void insert(T value)
	{
		if(root == null)
		{
			this.root = new TreeNode<T>(null, value);
			return;
		}
		else
		{
			if(contains(value))
				return;
			
			int comp = comparator.compare(root.getValue(), value);
			if(comp == -1)
			{
				root.insert(new TreeNode<T>(null, value), comparator);
				return;
			}
			if(comp == 1)
			{
				TreeNode<T> newNode = new TreeNode<T>(null, value);
				newNode.addChild(root);
				root = newNode;
				return;
			}
		}
	}
	
	public boolean contains(T value)
	{
		return root != null && root.contains(new TreeNode<T>(null, value));
	}
	
	public List<T> getOrder(T leaf)
	{
		List<T> order = new LinkedList<T>();
		
		if(root == null || leaf == null)
			return order;
		
		TreeNode<T> node = root.lookup(leaf);
		if(node == null)
			return order;
		
		while(node != null)
		{
			order.add(0, node.getValue());
			node = node.getParent();
		}
			
		return order;
	}
	
	/**
	 * DEBUG
	 */
	public void printTree()
	{
		if(root != null)
			root.print(0);
	}
}
