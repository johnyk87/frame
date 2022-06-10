package jk.frame.utils.tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

class TreeNode<T> {
	
	private TreeNode<T> parent;
	private T value;
	private List<TreeNode<T>> children;
	
	TreeNode(TreeNode<T> parent, T value)
	{
		this.parent = parent;
		this.value = value;
		this.children = new LinkedList<TreeNode<T>>();
	}
	
	public TreeNode<T> getParent()
	{
		return parent;
	}
	
	public void setParent(TreeNode<T> parent)
	{
		this.parent = parent;
	}

	public T getValue()
	{
		return value;
	}
	
	public void insert(TreeNode<T> node, Comparator<T> comparator)
	{
		if(node == null || this.equals(node) || hasChild(node))
			return;
		
		for(int i=0; i<children.size(); i++)
		{
			TreeNode<T> child = children.get(i);
			int comp = comparator.compare(child.getValue(), node.getValue());
			if(comp == -1)
			{
				child.insert(node, comparator);
				return;
			}
			if(comp == 1)
			{
				removeChild(child);
				node.addChild(child);
				i--;
			}
		}

		addChild(node);
	}
	
	public boolean contains(TreeNode<T> child)
	{
		if(hasChild(child))
			return true;
		
		for(int i=0; i<children.size(); i++)
		{
			if(children.get(i).contains(child))
				return true;
		}
		
		return false;
	}
	
	public TreeNode<T> lookup(T value)
	{
		if(this.value.equals(value))
			return this;
		
		for(int i=0; i<children.size(); i++)
		{
			TreeNode<T> node = children.get(i).lookup(value);
			if(node != null)
				return node;
		}
		
		return null;
	}
	
	public boolean hasChild(TreeNode<T> child)
	{
		return children.contains(child);
	}
	
	public void addChild(TreeNode<T> child)
	{
		if(!children.contains(child))
		{
			children.add(child);
			child.setParent(this);
		}
	}
	
	public void removeChild(TreeNode<T> child)
	{
		if(children.contains(child))
		{
			children.remove(child);
			child.setParent(null);
		}
	}
	
	public boolean equals(TreeNode<T> o)
	{
		return o != null && value.equals(o.getValue());
	}
	
	/**
	 * DEBUG
	 * @param depth
	 */
	public void print(int depth)
	{
		String tab = "";
		for(int i=0; i<depth; i++)
		{
			tab += "\t";
		}
		System.out.println(tab + value.toString());
		for(int i=0; i<children.size(); i++)
		{
			children.get(i).print(depth + 1);
		}
	}
}
