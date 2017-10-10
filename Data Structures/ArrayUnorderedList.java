package lists;

/** List - ArrayUnorderdList.java
* Represents an array implementation of an unordered list.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import exceptionclasses.*;

/**
 * <p>Title: ArrayUnorderedList.java</p>
 *
 * <p>Description: </p>
 *
 * @author Ryan Handy and Eddie O'Hagan
 */
public class ArrayUnorderedList<T> extends ArrayList<T>
         implements UnorderedListADT<T>
{
	/**
	 * Creates an empty list using the default capacity.
	 */
	public ArrayUnorderedList()
	{
		super();
	}

	/**
	 * Creates an empty list using the specified capacity.
	 * @param initialCapacity the initial size of the list as specified by the user
	 */
	public ArrayUnorderedList (int initialCapacity)
	{
		super(initialCapacity);
	}

	/**
	 * Adds the specified item to the front of this list.
	 * @param item a reference to the item to be added
	 */
	public void addToFront (T item)
	{
		if(size() == contents.length)
			expandCapacity();
		for(int i=count; i>0; i--)
		{
			contents[i] = contents[i-1];
		}
		contents[0] = item;
		count ++;
	}

	/**
	 * Adds the specified item to the rear of this list.
	 * @param item a reference to the item to be added
	 */
	public void addToRear (T item)
	{
		if (size() == contents.length)
			expandCapacity();

		contents[count] = item;
		count++;
	}

	/**
	 * Adds the specified item after the specified target. The original
	 * ordering of the items in the list will be maintained.
	 * @param item a reference to the new item to be added
	 * @param target a reference to the target
	 * @throws an ElementNotFoundException if the target is not found
	 */
	public void addAfter (T item, T target)
	{
		if (size() == contents.length)
			expandCapacity();
		int posn = this.find(target);
		if(posn == -1)
			throw new ElementNotFoundException("ArrayUnorderedList");
		for(int i=count; i>posn+1; i--)
		{
			contents[i] = contents[i-1];
		}
		contents[posn+1] = item;
		count ++;
	}
}

