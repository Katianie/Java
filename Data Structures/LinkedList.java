package lists;

/** List - ArrayUnorderdList.java
* Represents a linked implementation of a list. The front of
* the list is referenced by contents. This class will be extended
* to create a specific kind of list.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import exceptionclasses.*;

public class LinkedList<T> implements ListADT<T>
{
	protected int numItems;
	protected Node<T> contents;
	protected int myNumComparisons;
	protected int myNumTempComparisons;

	/**
	 * default constructor --
	 * creates an empty list.
	 */
	public LinkedList()
	{
		numItems = 0;
		myNumTempComparisons = 0;//starts at 1 because the first comparison is not counted otherwise
		myNumComparisons = 0;//starts at 1 because the first comparison is not counted otherwise
		contents = null;
	}

	/*
	 * removeLast() method
	 * removes the last item from the list.
	 * @return the removed item
	 * @throws EmptyCollectionException
	 * @author Ivan, Sam
	 */
	/**
	 * removeLast method - removes the last item from the list.
	 * @return removeItem - the removed item
	 * @throws EmptyCollectionException - when the list is empty
	 * @author Ivan, Sam
	 */
	public T removeLast() throws EmptyCollectionException
	{
		if (isEmpty())
			throw new EmptyCollectionException("list");

		Node<T> current = contents;
		T removedItem;

		for (int i=1; i<numItems - 1; i++)
			current = current.getNext();

		removedItem = current.getNext().getItem();
		current.setNext(null);
		numItems--;
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
		if (isEmpty())
			throw new EmptyCollectionException ("linked list");

		T result = contents.getItem();
		contents = contents.getNext();
		numItems--;

		return result;
	}

	/**
	 * remove method - finds, removes, and returns the specified item
	 * @param - the item that is going to be removed
	 * @return - the removed item
	 * @author Ryan Handy and Eddie O'Hagan
	 */
	public T remove (T target)
	{
		Node<T>  current = contents, prev = current.getNext();

		if (isEmpty())
			throw new EmptyCollectionException("LinkedList");
		if (contents.getItem().equals(target))
		{
			contents = contents.getNext();
			numItems--;
			return current.getItem();
		}
		else
		{
			while (current != null)
			{
				if (target.equals(current.getItem()))
				{
					prev.setNext(current.getNext());
					numItems--;
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
		if (isEmpty())
			throw new EmptyCollectionException ("list");

		return contents.getItem();
	}

	/*
	 * last method 
	 * This method returns a reference to the last element in the list.
	 * @return a reference to the last element in the list.
	 * @author Jason Watts, Zouhair Arma
	 */
	/**
	 * last method - this method returns a reference to the last element in the list.
	 * @return temp - a reference to the last element in the list.
	 * @author Jason Watts, Zouhair Arma
	 */
	public T last()
	{
		if (isEmpty())
			throw new EmptyCollectionException("LinkedList");

		T temp;
		Node<T> current = contents;
		while (current.getNext() != null)
			current = current.getNext();
		temp = current.getItem();
		return temp;
	}

	/*
	 * Name: contains method
	 * Description: Checks the LinkedList for desired object
	 * @param: object
	 * @returns: true if found, false if not
	 * @throws: EmptyCollectionException
	 * authors: Brian Cribbin, Hasan Eksi
	 */
	/**
	 * contains method - Checks the LinkedList for desired object
	 * @param: target - specific item you were looking for
	 * @returns: true if found, false if not
	 * @throws: EmptyCollectionException - in the case the list is empty
	 * authors: Brian Cribbin, Hasan Eksi
	 */
	public boolean contains (T target)
	{
		Node<T> current = contents;
		myNumTempComparisons = 0;

		if (isEmpty())
			throw new EmptyCollectionException("LinkedList");

		while (!(current.getItem().equals(target)) && current.getNext() != null)
		{
			current = current.getNext();
			myNumTempComparisons++;
			myNumComparisons++;
		}

		if (current.getItem().equals(target))
		{	
			myNumTempComparisons++;
			myNumComparisons++;
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
	 * author: Eddie O'Hagan
	 */
	public T find(T target)
	{
		Node<T> current = contents;
		myNumTempComparisons = 0;

		if (isEmpty())
			throw new EmptyCollectionException("LinkedList");

		while (!(current.getItem().equals(target)) && current.getNext() != null)
		{
			current = current.getNext();
			myNumTempComparisons++;
			myNumComparisons++;
		}

		if (current.getItem().equals(target))
		{
			return current.getItem();
		}
		else
		{
			return null;
		}
	}

	/**
	 * isEmpty --
	 * determines whether or not the list is empty.
	 * @return true if this list is empty; false otherwise
	 */
	public boolean isEmpty()
	{
		return (numItems == 0);
	}

	/**
	 * size --
	 * returns a count of the number of items in this list.
	 * @return the number of items currently in the list
	 */
	public int size()
	{
		return numItems;
	}

	/**
	 * getNumComparisons --
	 * returns the number of comparisons a list has ever done
	 * @return myNumComparisons
	 */
	public int getNumComparisons()
	{
		return myNumComparisons;
	}
	
	public void setNumComparisons(int num)
	{
		myNumComparisons = num;
	}

	/**
	 * getNumComparisons --
	 * returns the number of comparisons on the last call to 
	 * a method involving comparisons(if you call contains method it will
	 * return the amount of comparisons jsut the conrtains method had
	 * to do}
	 * 
	 * @return myNumTempComparisons
	 */
	public int getNumTempComparisons()
	{
		return myNumTempComparisons;
	}
	
	public void setNumTempComparisons(int num)
	{
		myNumTempComparisons = num;
	}


	/**
	 * toString --
	 * returns a string representation of this list.
	 * @return a reference to a String containing the items in this list
	 */
	public String toString()
	{
		Node<T> current = contents;
		String result = "";

		while (current != null)
		{
			result = result + current.getItem().toString() + "\n";
			current = current.getNext();
		}

		return result;
	}

}
