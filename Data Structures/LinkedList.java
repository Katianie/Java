/** List - ArrayUnorderdList.java* Represents a linked implementation of a list. The front of* the list is referenced by myContents. This class will be extended* to create a specific kind of list.** This was uploaded to Katianie.com, Feel free to use this* code and share it with others, Just give me credit ^_^.** Eddie O'Hagan* Copyright © 2009 Katianie.com*/package lists;
import exceptionclasses.*;
public class LinkedList<T> implements ListADT<T>
{	protected int myNumItems;
	protected Node<T> myContents;
	/**
	* default constructor --
	* creates an empty list.
	*/
	public LinkedList()
	{
		myNumItems = 0;
		myContents = null;
	}
	/*
	 * removeLast(): method
	 * removes the last item from the list.
	 * @return the removed item
	 * @throws EmptyCollectionException
	*/
	public T removeLast() throws EmptyCollectionException
	{		Node<T> current = myContents;		T removedItem;		
		if (isEmpty() == true)		{
			throw new EmptyCollectionException("list");		}
		for (int i = 1; i < myNumItems - 1; i++)		{
			current = current.getNext();		}

		removedItem = current.getNext().getItem();
		current.setNext(null);
		myNumItems--;
		return removedItem;
	}
	/**
	* removeFirst --
	* Removes and returns the first item in this list.
	* @return a reference to what was the first item in the list
	* @throws EmptyCollectionException if the list is empty
	*/
	public T removeFirst() throws EmptyCollectionException
	{
		if (isEmpty() == true)		{
			throw new EmptyCollectionException ("linked list");		}

		T result = myContents.getItem();		
		myContents = myContents.getNext();		
		myNumItems--;
		return result;
	}
	/**
	* remove method - finds, removes, and returns the specified item
	* @param - the item that is going to be removed
	* @return - the removed item
	*/
	public T remove (T target)
	{
		Node<T>  current = myContents, prev = current.getNext();
		if (isEmpty() == true)		{
			throw new EmptyCollectionException("LinkedList");		}		
		if (myContents.getItem().equals(target) == true)
		{
			myContents = myContents.getNext();
			myNumItems--;
			return current.getItem();
		}
		else
		{
			while (current != null)
			{
				if (target.equals(current.getItem()) == true)
				{
					prev.setNext(current.getNext());
					myNumItems--;
					return current.getItem();
				}
				else
				{
					prev = current;
					current = current.getNext();
				}
			}
			throw new ElementNotFoundException("LinkedList");
		}
	}
	/**
	 * first --
	 * returns a reference to the item at the front of this list. The item
	 * is not removed from the list.
	 * @return a reference to the first item in the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	public T first() throws EmptyCollectionException
	{
		if (isEmpty() == true)		{
			throw new EmptyCollectionException("list");		}		
		return myContents.getItem();
	}
	/*
	* last method 
	* This method returns a reference to the last element in the list.
	* @return a reference to the last element in the list.	*/
	public T last()
	{		T temp;		Node<T> current = myContents;		
		if (isEmpty() == true)		{
			throw new EmptyCollectionException("LinkedList");		}		
		while (current.getNext() != null)		{
			current = current.getNext();		}
		temp = current.getItem();
		return temp;
	}
	/*
	 * Name: contains method
	 * Description: Checks the LinkedList for desired object
	 * @param: object
	 * @returns: true if found, false if not
	 * @throws: EmptyCollectionException
	 */
	public boolean contains (T target)
	{
		Node<T> current = myContents;
		if (isEmpty() == true)		{
			throw new EmptyCollectionException("LinkedList");		}
		while (current.getItem().equals(target) == false && current.getNext() != null)
		{
			current = current.getNext();
		}
		if (current.getItem().equals(target) == true)
		{	
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	* find method - Returns a particular item from the linked list.
	* This method was not in the original blueprint of the class
	* but I created it to make life easyer.
	* Note: this works similar to a remove method but does
	* NOT actualy remove it from the list.
	* 
	* @param target looking for
	* @return the target searched for 
	*/
	public T find(T target)
	{
		Node<T> current = myContents;

		if (isEmpty() == true)		{
			throw new EmptyCollectionException("LinkedList");		}

		while (current.getItem().equals(target) == false && current.getNext() != null)
		{
			current = current.getNext();
		}
		if (current.getItem().equals(target) == true)
		{
			return current.getItem();
		}

		return null;
	}

	/**
	* isEmpty --
	* determines whether or not the list is empty.
	* @return true if this list is empty; false otherwise
	*/
	public boolean isEmpty()
	{
		return (myNumItems == 0);
	}
	/**
	* size --
	* returns a count of the number of items in this list.
	* @return the number of items currently in the list
	*/
	public int size()
	{
		return myNumItems;
	}
	/**
	* toString --
	* returns a string representation of this list.
	* @return a reference to a String containing the items in this list
	*/
	public String toString()
	{
		Node<T> current = myContents;
		String result = "";
		while (current != null)
		{
			result = result + current.getItem().toString() + "\n";
			current = current.getNext();
		}
		return result;
	}
}
