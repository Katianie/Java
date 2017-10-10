package lists;

/** List - ArrayUnorderdList.java
* Represents a linked implementation of an unordered list
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import exceptionclasses.*;

public class LinkedUnorderedList<T> extends LinkedList<T>
implements UnorderedListADT<T>
{
	/**
	 * default constructor -- 
	 * creates an empty list.
	 */
	public LinkedUnorderedList()
	{
		super();
	}

	/**
	 * addToFront --
	 * adds the specified item to the front of this list.
	 * @param item - a reference to the item to be added
	 */
	public void addToFront (T item)
	{
		contents = new Node<T>(item, contents);
		numItems++;
	}

	/*
	 * Add a new item to the end of the LinkedList
	 * @param newItem a new item to be added
	 * @author Rony Xavier and Tin Aung Khine
	 */
	/**
	 * addToRear method - Add a new item to the end of the LinkedList
	 * @param newItem - a new item to be added
	 * @author Rony Xavier and Tin Aung Khine
	 */
	public void addToRear(T newItem)
	{
		if (isEmpty())
			contents = new Node<T>(newItem);
		else
		{
			Node<T> temp = contents;
			for (int i=0; i<numItems - 2; i++)
				temp = temp.getNext();
			temp.setNext(new Node<T>(newItem));
		}
		numItems++;
	}

	/**
	 * addAfter method -- insert new element into the list
	 * after a specified element
	 * @param element is the item to be added to the list
	 * @param target new element stored after this element
	 * @throw EmptyCollectionException
	 * @throw ElementNotFoundException
	 * @author Luke Succes, Phil Ninan, Kanayochukwu Onwuama
	 */
	public void addAfter (T element, T target)
	{
		if (isEmpty())
			throw new EmptyCollectionException("LinkedList");

		Node<T> current = contents;
		boolean found = false;

		if(current.getNext() == null)
		{
			current.setNext(new Node<T>(element, current.getNext()));
		}
		else
		{
			while (!found && current != null)
			{
				if (current.getItem().equals(target))
					found = true;
				else
				{
					current = current.getNext();
				}
			}
			if (!found)
				throw new ElementNotFoundException("LinkedList");
			else
			{
				current.setNext(new Node<T>(element, current.getNext()));
			}
		}
		numItems++;
	}

	/*
	 * Name: add method
	 * Description: adds an object to a certain spot
	 * @param: int and object
	 * @return: true if added, false if not
	 * authors: Brian Cribbin, Hasan Eksi
	 */
	/**
	 * add method - adds an object to a certain spot
	 * @param posn - position after which the object is to be added
	 * @param object - element being added
	 * @return true if added, false if not
	 * @author Brian Cribbin, Hasan Eksi
	 */
	public boolean add(int posn, T object)
	{
		boolean added = false;

		//in case the list is empty
		if (isEmpty())
		{
			Node<T> contents = new Node<T>(object);
		}

		Node<T> prev = contents;
		Node<T> current = contents.getNext();


		//Assuming the first position is 1
		for (int i=1; i<posn; i++)
		{
			current = current.getNext();
			prev = prev.getNext();
		}
		prev.setNext(new Node<T>(object, current));
		numItems++;

		if (prev.getNext().getItem().equals(object))
			added = true;
		return added;
	}
}