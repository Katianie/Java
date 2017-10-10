package queues;

/** Queue - LinkedQueue.java
* Defines a queue class, including basic queue operations. The elements
* in the collection are stored in a linked list.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import exceptionclasses.*;

public class LinkedQueue<T> implements QueueADT<T>
{
	private Node<T> front;
	private Node<T> rear;
	private int count;
	
	/**
	 * Default constuctor: Sets all the instance variables to null or 0.
	 */
	public LinkedQueue()
	{
		front = null;
		rear = null;
		count = 0;
	}

	/**
	 * Dequeue: removes the element in the front of the queue and returns
	 * it.
	 */
	public T dequeue() 
	{
		T temp;
		
		if(count == 0)
		{
			throw new EmptyCollectionException(); 
		}
		else if(count == 1)
		{
			temp = front.getItem();
			front = null;
			rear = null;
			count--;
			return temp;
		}
		else
		{
			temp = front.getItem();
			front = front.getNext();
			count--;
			return temp;
		}
	}

	/**
	 * Enqueue: Adds a specified element at the end of the queue.
	 */
	public void enqueue(T item) 
	{
		if(count == 0)
		{
			front = new Node<T>(item);
			rear = front;
			count++;
		}
		else
		{
			rear.setNext(new Node<T>(item));
			rear = rear.getNext();
			count++;
		}
		
	}

	/**
	 * Front: returns the item in the node thats in the front of
	 * the queue.
	 */
	public T front() 
	{
		return front.getItem();

	}

	/**
	 * isEmpty: returns a boolean that indicates if the queue is empty or
	 * not. True if it is empty; False if its not.
	 */
	public boolean isEmpty() 
	{
		if(count == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Size: returns the size of the queue.
	 */
	public int size() 
	{
		return count;
	}
	
	/**
	 * replaceItem: replaces a specified item(oldItem) with the other
	 * specified item(newItem).
	 * 
	 * @param oldItem
	 * @param newItem
	 */
	public void replaceItem(T oldItem, T newItem)
	{
		Node<T> current = front;
		
		while(!current.getItem().equals(oldItem) &&
				current.getItem() != null)
		{
			current = current.getNext();
		}
		
		if(current.getItem() == null)
		{
			throw new ElementNotFoundException("LinkedQueue");
		}
		else
		{
			current.setItem(newItem);
		}
	}
	
	/**
	 * removeAll: Removes everything in the queue.
	 */
	public void removeAll(T item)
	{
		int tally = 0;
		if(count == 0)
		{
			throw new EmptyCollectionException();
		}
		Node<T> temp = front.getNext();
		Node<T> prev = front;
		if(prev.getItem().equals(item))
		{
			front = front.getNext();
			tally++;
			count--;
		}
		while(temp.getNext() != null)
		{
			if(temp.getItem().equals(item))
			{
				prev.setNext(temp.getNext());
				tally++;
				count--;
			}
			temp = temp.getNext();
			prev = prev.getNext();
		}
		if(rear.getItem().equals(item))
		{
			rear = null;
			count--;
		}
		if(tally == 0)
		{
			throw new ElementNotFoundException("LinkedQueue");
		}
	}
	
	/**
	 * toString: Displays a string containing all the elements in the
	 * queue.
	 */
	public String toString()
	{
		if(count == 0)
		{
			throw new EmptyCollectionException();
		}
		else
		{
			String temp = "";
			Node<T> current = front;
			
			for(int i = 0; i < count; i++)
			{
				temp += current.getItem().toString() + "\n";
				current = current.getNext();
			}
			
			return temp;
		}
		
		
	}
	
	

}
