/** Trees - BTreeNode.java
public class BTreeNode<T>
{
	private T myData;
	private BTreeNode<T> myLeft, myRight;

	public BTreeNode()
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