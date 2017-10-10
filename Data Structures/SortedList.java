package lists;

/** List - ListADT.java
* Defines a Sorted List that will organize elements in 
* the list.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import exceptionclasses.ElementNotFoundException;
import exceptionclasses.EmptyCollectionException;

public class SortedList<T> extends LinkedList<T> implements OrderedListADT<T>
{

	public SortedList()
	{
		super();
	}

	/**
	 * contains - This method is overrided meaning when we call the contains method on a sorted list
	 * object it will use the following contains method instead of the one in the linklist parent class.
	 * 
	 * The contains method takes in a generic parameter and returns true if found false otherwise. The 
	 * main difference with this contains method and the one in the parent class is that this one will stop
	 * once the item being looked for is greater than 0. 
	 */
	public boolean contains(T target)
	{
		Node<T> current = contents;
		Comparable<T> temp = (Comparable<T>)target;
		myNumTempComparisons = 0;
		
		if (isEmpty())
			throw new EmptyCollectionException("LinkedList");

		while (((Comparable)current.getItem()).compareTo(temp) < 0 && current.getNext() != null)
		{
			myNumTempComparisons++;
			myNumComparisons++;
			current = current.getNext();
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
	 * add - adds a specified element to its proper position. This method will orginize string alphabeticly
	 * and numbers from lowest to highest.
	 */
	public void add(T target)
	{
		Node<T> current = contents;

		Comparable<T> temp = (Comparable<T>)target;


		if(isEmpty())
		{
			contents = new Node<T>(target);
			numItems++;
		}
		else
		{
			if(((Comparable)contents.getItem()).compareTo(temp) >= 0)
			{
				contents = new Node<T>(target, contents);
				numItems++;
			}
			else
			{
				if(numItems == 1)
				{
					current.setNext(new Node<T>(target));
					numItems++;
				}
				else
				{
					do
					{
						if(current.getNext() != null && ((Comparable)current.getNext().getItem()).compareTo(temp) < 0)
						{
							current = current.getNext();

						}
						else
						{

							current.setNext(new Node<T>(target,current.getNext()));
							numItems++;
							break;

						}
					}while(current != null);
				}

			}

		}

	}



}
