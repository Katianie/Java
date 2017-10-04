/** Stacks - ArrayStack.java* Defines the properties and behaviors of a basic stack.** This was uploaded to Katianie.com, Feel free to use this* code and share it with others, Just give me credit ^_^.** Eddie O'Hagan* Copyright © 2009 Katianie.com*/package stacks;
import exceptionclasses.*;
public class ArrayStack<T> implements StackADT<T>
{
	protected T[] myContents; //a reference to an Object array; the array contains the stack items
	protected int myTop; //an index to the myTop-most item in the stack
	/**
	* default constructor - creates an empty stack capable of storing at most 10 items
	*/
	@SuppressWarnings("unchecked")
	public ArrayStack()
	{
		myContents = (T[]) (new Object[10]);
		myTop = -1;
	}
	/**
	* push method - stores a new item on the myTop of the stack; the stack size
	* is increased if necessary
	* @param item a reference to the item to be stored on the myTop of the stack
	*/
	public void push(T item)
	{
		if (myTop == myContents.length - 1)		{
			expandCapacity();		}		
		myTop++;
		myContents[myTop] = item;
	}
	/**
	* pop method - removes the myTop-most item from the stack
	* @return a reference to the item which was stored on myTop of the stack
	* @throws EmptyCollectionException if the stack is empty
	*/
	public T pop()
	{
		T temp;
		if(myTop == -1)
		{
			throw new EmptyCollectionException("Array Stack");
		}
		else
		{
			temp = myContents[myTop];
			myTop--;
			return temp;
		}
	}
	/**
	* peek method - returns the myTop-most item on the stack without removing it
	* @return a reference to the item which is stored on myTop of the stack
	* @throws EmptyCollectionException if the stack is empty
	*/    
	public T peek()
	{
		if(myTop == -1)
		{
			throw new EmptyCollectionException("Array Stack");
		}
		else
		{
			return myContents[myTop];
		}
	}
	/**
	* search method - returns the 1-based position where an item is on the stack.
	* If the item is in the stack, the method returns the distance from the myTop 
	* of the stack; the myTopmost item on the stack is considered to be at distance	*
	* 1. The equals method is used to compare target to the items in the stack. 
	* @param target a reference to the item to search for	*
	* @return the 1-based position from the myTop of the stack where the item 	* is located; returns -1 if the item is not on the stack.
	*/
	public int search(T target)
	{
		if(myTop == -1)
		{
			throw new EmptyCollectionException("Array Stack");
		}
		else
		{
			int temp = 0;
			for(int i = myTop; i >= 0; i--)
			{
				if(myContents[i].equals(target) == true)
				{
					temp = i;
					break;
				}
				else
				{
					temp++;
				}
			}

			if(temp == size())
			{
				throw new ElementNotFoundException("Array Stack");
			}
			return temp;
		}
	}
	/**
	 * isEmpty method - determines whether or not the stack is empty
	 * @return true if the stack is empty; false if the stack is not empty
	 */
	public boolean isEmpty()
	{
		return myTop == -1;
	}
	/**
	* size method - returns a count of the number of items in the stack
	* @return the number of items in the stack
	*/
	public int size()
	{
		return myTop + 1;
	}
	/**
	* toString method - returns a String representing the state of the stack
	* @return a string containing all items in the stack
	*/
	public String toString()
	{
		String temp = new String("");
		for(int i = myTop; i >= 0; i--)
		{
			temp += myContents[i].toString() + "\n";
		}		
		return temp;
	}
	/**
	* Creates a new array to store the myContents of the stack with
	* twice the capacity of the old one.
	*/
	@SuppressWarnings("unchecked")
	private void expandCapacity()
	{
		T[] larger = (T[])(new Object[myContents.length * 2]);
		for (int index = 0; index < myContents.length; index++)		{
			larger[index] = myContents[index];		}		
		myContents = larger;
	}
}
