/** Trees - BinarySearchTree.java
* Defines a binary search tree, 
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/package Trees;
public class BinarySearchTree<T>
{
	//instance variables
	private int myCount;
	private BTreeNode<T> myRoot;
	private String myStringTemp;
	/**
	* Creates an empty binary search tree. 
	*/
	public BinarySearchTree()
	{
		myCount = 0;
		myRoot = null;
		myStringTemp = "";
	}
	/**
	* Creates a binary search tree containing one node.
	* @param item a reference to the item that will be stored in
	* the myRoot of this tree
	*/
	public BinarySearchTree (T item)
	{
		myCount = 1;
		myRoot = new BTreeNode<T>(item);
		myStringTemp = "";
	}
	/**
	* add - adds the new item to the binary search tree
	* @param item a reference to the item to be added to this tree
	*/
	public void add(T newItem)
	{
		myRoot = addNode(myRoot, newItem);
	}
	public void printInOrder()
	{
		inOrder(myRoot);
	}
	public String toString()
	{
		return toStringPriv(myRoot);
	}
	public boolean contains(T target)
	{
		return priContains(myRoot,target);
	}	
	public T findMin()
	{
		return priFindMin(myRoot);
	}
	/**
	* priFindMin - Is called by the findMin method to determine
	* what the lowest number in the tree is
	* 
	* @param the current node
	* @return the lowest number in the tree.
	*/
	private T priFindMin(BTreeNode<T> current)
	{
		if(current == null)
		{
		   return null;
		}
		else if(current.getLeft() == null)
		{
		   return current.getItem();
		}
		else
		{
		   return priFindMin(current.getLeft());
		}
	}
	/**
	* priContains - Is called by the Contains method to see if a particular
	* item is in a Binary Search tree. This is private because outside of this
	* class we do not have access to "myRoot"
	* 
	* @param the current node 
	* @param the item(target) we are looking for
	* @return true if the target is found, false otherwise.
	*/
	private boolean priContains(BTreeNode<T> current, T target)
	{
		if(current == null)
		{
		   return false;
		}
		if(current.getItem().equals(target) == true)
		{
			return true;
		}
		else if ( ((Comparable<T>)target).compareTo(current.getItem()) < 0)
		{
			return priContains(current.getLeft(), target);
		}
		else
		{
			return priContains(current.getRight(), target);
		}   
	}
	/**
	* inOrder - is Called by the printInOrder method to print out
	* all of the elements in the BinarySearchTree from lowest to highest
	*
	* @param the current node
	*/
	private void inOrder(BTreeNode<T> current)
	{
		if(current != null)
		{
		   inOrder(current.getLeft());
		   System.out.println(current.getItem());
		   inOrder(current.getRight());
		}
	}
	/**
	* addNode - inserts the new item into its appropriate location
	* within the tree.
	* @param current a reference to the current node
	* @param item a reference to the item to be added to this tree
	* @return the reference to the new node created to store the new item
	*/
	@SuppressWarnings("unchecked")
	private BTreeNode<T> addNode(BTreeNode<T> current, T item)
	{
		if (current == null)		{
			current = new BTreeNode<T>(item);		}
		else if ( ((Comparable<T>)item).compareTo(current.getItem()) < 0 )		{
			current.setLeft(addNode(current.getLeft(), item));		}
		else		{
			current.setRight(addNode(current.getRight(), item));		}		
		return current;
	}
	private String toStringPriv(BTreeNode<T> current)
	{   
		if(current != null)
		{
		   toStringPriv(current.getLeft());		   
		   myStringTemp += current.getItem() + "\n";		   
		   toStringPriv(current.getRight());
		} 		
		return myStringTemp;
	}
}
