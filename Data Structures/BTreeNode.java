/** Trees - BTreeNode.java* Defines a binary tree node class capable of * storing an object reference and a reference to the myLeft and myRight* subtrees, hehehe....trees ;).** This was uploaded to Katianie.com, Feel free to use this* code and share it with others, Just give me credit ^_^.** Eddie O'Hagan* CopymyRight © 2009 Katianie.com*/package Trees;
public class BTreeNode<T>
{
	private T myData;
	private BTreeNode<T> myLeft, myRight;

	public BTreeNode()	{
		myData = null;
		myLeft = myRight = null;
	}
	public BTreeNode(T newItem)
	{
		myData = newItem;
		myLeft = myRight = null;
	}
	public T getItem()
	{
		return myData;
	}
	public BTreeNode<T> getLeft()
	{
		return myLeft;
	}
	public BTreeNode<T> getRight()
	{
		return myRight;
	}	
	public void setItem(T newItem)
	{
		myData = newItem;
	}	
	public void setLeft(BTreeNode<T> child)
	{
		myLeft = child;
	}
	public void setmyRight(BTreeNode<T> child)
	{
		myRight = child;
	}
}
