package stacks;

/** Stacks - ArrayStack.java
* Defines the properties and behaviors of a basic stack.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import exceptionclasses.*;

public class ArrayStack<T> implements StackADT<T>
{
	/**
	 * items - a reference to an Object array; the array contains the stack items
	 */
	protected T[] contents;
	/**
	 * top - an index to the top-most item in the stack
	 */
	protected int top;

	/**
	 * default constructor - creates an empty stack capable of storing at most 10 items
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack()
	{
		contents = (T[]) (new Object[10]);
		top = -1;
	}

	/**
	 * push method - stores a new item on the top of the stack; the stack size
	 * is increased if necessary
	 * @param item a reference to the item to be stored on the top of the stack
	 */
	public void push(T item)
	{
		if (top == contents.length-1)
			expandCapacity();
		
		top++;
		contents[top] = item;
	}

	/**
	 * pop method - removes the top-most item from the stack
	 * @return a reference to the item which was stored on top of the stack
	 * @throws EmptyCollectionException if the stack is empty
	 */
	public T pop()
	{
		T temp;
		
		if(top == -1)
		{
			throw new EmptyCollectionException("Array Stack");
		}
		else
		{
			temp = contents[top];
			top--;
			return temp;
		}

	}

	/**
	 * peek method - returns the top-most item on the stack without removing it
	 * @return a reference to the item which is stored on top of the stack
	 * @throws EmptyCollectionException if the stack is empty
	 */    
	public T peek()
	{
		if(top == -1)
		{
			throw new EmptyCollectionException("Array Stack");
		}
		else
		{
			
			return contents[top];
		}
	}

	/**
	 * search method - returns the 1-based position where an item is on the stack.
	 * If the item is in the stack, the method returns the distance from the top 
	 * of the stack; the topmost item on the stack is considered to be at distance
	 * 1. The equals method is used to compare target to the items in the stack. 
	 * @param target a reference to the item to search for
	 * @return the 1-based position from the top of the stack where the item 
	 * is located; returns -1 if the item is not on the stack
	 */
	public int search(T target)
	{
		if(top == -1)
		{
			throw new EmptyCollectionException("Array Stack");
		}
		else
		{
			int temp = 0;
			
			for(int i = top; i >= 0; i--)
			{
				if(contents[i].equals(target))
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
		return top == -1;
	}

	/**
	 * size method - returns a count of the number of items in the stack
	 * @return the number of items in the stack
	 */
	public int size()
	{
		return top + 1;
	}

	/**
	 * toString method - returns a String representing the state of the stack
	 * @return a string containing all items in the stack
	 */
	public String toString()
	{
		String temp = new String("");
		
		for(int i = top; i >= 0; i--)
		{
			temp += contents[i].toString() + "\n";
		}
		
		return temp;
	}

	/**
	 * Creates a new array to store the contents of the stack with
	 * twice the capacity of the old one.
	 */
	@SuppressWarnings("unchecked")
	private void expandCapacity()
	{
		T[] larger = (T[])(new Object[contents.length*2]);

		for (int index=0; index < contents.length; index++)
			larger[index] = contents[index];

		contents = larger;
	}
}
