/** List - ArrayList.java
* Represents an array implementation of a list. The front of
* the list is kept at array index 0. This class will be extended
* to create a specific kind of list.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/
package lists;

import exceptionclasses.*;

public class ArrayList<T> implements ListADT<T>
{
	protected final int DEFAULT_CAPACITY = 100;
	protected  int myCount;
	protected T[] myContents;

	/**
	* Creates an empty list using the default capacity.
	*/
	@SuppressWarnings("unchecked") 
	public ArrayList()
	{
		myCount = 0;
		myContents = (T[])(new Object[DEFAULT_CAPACITY]);
	}

	/**
	* Creates an empty list using the specified capacity.
	* @param initialCapacity the initial size of the list as specified by the user
	*/
	@SuppressWarnings("unchecked")
	public ArrayList (int initialCapacity)
	{
		myCount = 0;
		myContents = (T[])(new Object[initialCapacity]);
	}

	/**
	* Removes and returns the last item in this list.
	* @return a reference to what was the last item in the list
	* @throws EmptyCollectionException if the list is empty
	*/
	public T removeLast ()
	{
		if(myCount == 0)
		{
			throw new EmptyCollectionException("ArrayList");
		}
		
		myCount --;
		return myContents[myCount];
	}

	/**
	* Removes and returns the first item in this list.
	* @return a reference to what was the first item in the list
	* @throws EmptyCollectionException if the list is empty
	*/
	public T removeFirst()
	{
		T temp;
		
		if(myCount == 0)
		{
			throw new EmptyCollectionException("ArrayList");
		}
		
		temp = myContents[0];
		
		for(int i = 0; i < myCount - 1; i++)
		{
			myContents[i] = myContents[i + 1];
		}
		
		myCount--;
		
		return temp;	
	}

	/**
	* Removes and returns the specified element. The order of the
	* items in the list will be maintained by shifting the remaining
	* items.
	* @param element the item to be removed from the list
	* @return a reference to the item removed from the list
	*/
	public T remove (T element)
	{
		T result;
		int index = find (element);

		if (index == -1)
		{
			throw new ElementNotFoundException ("ArrayList");
		}

		result = myContents[index];
		myCount--;

		for (int i = index; i < myCount; i++)
		{
			myContents[i] = myContents[i + 1];
		}

		myContents[myCount] = null;

		return result;
	}

	/**
	* Returns a reference to the element at the front of this list. The element
	* is not removed from the list.
	* @return a reference to the first item in the list
	* @throws EmptyCollectionException if the list is empty
	*/
	public T first()
	{
		if (isEmpty() == true)
		{
			throw new EmptyCollectionException ("ArrayList");
		}

		return myContents[0];
	}

	/**
	* Returns a reference to the element at the rear of this list. The element
	* is not removed from the list.
	* @return a reference to the last item in the list
	* @throws EmptyCollectionException if the list is empty
	*/
	public T last()
	{
		if (isEmpty() == true)
		{
			throw new EmptyCollectionException ("ArrayList");
		}

		return myContents[myCount - 1];
	}

	/**
	* Returns true if this list contains the specified element.
	* @param target a reference to the item to be located
	* @return true if the target is found; false otherwise
	*/
	public boolean contains (T target)
	{
		return (find(target) != -1);
	}

	/**
	* getItem: returns a refrence to an item in the list.
	* The method uses the comparable interface to compare 
	* two objects to eachother.
	* @param target
	* @return
	*/
	public T getItem(T target)
	{
		for(int i = 0; i < myCount; i++)
		{
			Comparable<T> temp = (Comparable<T>)myContents[i];

			if(temp.compareTo(target) == 0)
			{
				return myContents[i];
			}		  
		}
		
		return null;
	}

	/**
	* Returns the array index of the specified element.
	* @param target a reference to the item to be located
	* @return the array index of the specified element if it is found; -1 if it
	* is not found
	*/
	protected int find (T target)
	{
		for (int i = 0; i < myCount; i++)
		{		
			if (target.equals(myContents[i]) == true)
			{
				   return i;
			}
		}
		
		return -1;
	}

	/**
	* Determines whether or not the list is empty.
	* @return true if this list is empty; false otherwise
	*/
	public boolean isEmpty()
	{
		return (myCount == 0);
	}

	/**
	* Returns a myCount of the number of items in this list.
	* @return the number of items currently in the list
	*/
	public int size()
	{
		return myCount;
	}

	/**
	* Returns a string representation of this list.
	* @return a reference to a String containing the items in the list
	*/
	public String toString()
	{
		String temp = "";
		
		for(int i = 0; i < myCount; i++)
		{
			temp += myContents[i].toString() + "\n";
		}
		
		return temp;
	}

	/**
	* Creates a new array to store the myContents of this list with
	* twice the capacity of the old one.
	*/
	@SuppressWarnings("unchecked")
	protected void expandCapacity()
	{
		T[] larger = (T[])(new Object[myContents.length*2]);

		for (int i=0; i < myCount; i++)
		{
			larger[i] = myContents[i];
		}

		myContents = larger;
	}
}
