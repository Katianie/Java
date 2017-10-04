/** Node - Node.java
public class Node<T>
{
	private T myData; //storage for a reference to an object of type T
	private Node<T> myNext; //storage for the address of the myNext node in the list
	* default constructor --
	* initializes myData and myNext to null.
	*/
	public Node()
	{
		myNext = null;
	}
	/**
	* parameterized constructor --
	* initializes myData to the user specified value; myNext is set to null.
	* @param newItem - the value to be stored in the node
	*/
	public Node(T newItem)
	{
		myData = newItem;
		myNext = null;
	}
	/**
	* parameterized constructor --
	* initializes myData and myNext to user specified values.
	* @param newItem - the object reference to be stored in the node
	* @param myNextItem - the reference to the myNext node in the list
	*/
	public Node(T newItem, Node<T> myNextItem)
	{
		myData = newItem;
		myNext = myNextItem;
	}
	/**
	* setItem --
	* stores a new value in myData.
	* @param newItem - the object reference to be stored in this node
	*/
	public void setItem(T newItem)
	{
		myData = newItem;
	}
	/**
	* setmyNext --
	* stores a new value in myNext.
	* @param myNextItem - the reference to be stored in myNext
	*/
	public void setNext(Node<T> myNextItem)
	{
		myNext = myNextItem;
	}
	/**
	* getItem --
	* returns the reference stored in myData.
	* @return a reference to the myData stored in this node
	*/
	public T getItem()
	{
		return myData;
	}
	/**
	* getNext --
	* returns the reference stored in myNext.
	* @return the reference to the myNext node
	*/
	public Node<T> getNext()
	{
		return myNext;
	}
}