/** Queue - LinkedQueue.java* Defines a queue class, including basic queue operations. The elements* in the collection are stored in a linked list.** This was uploaded to Katianie.com, Feel free to use this* code and share it with others, Just give me credit ^_^.** Eddie O'Hagan* Copyright © 2009 Katianie.com*/package queues;
import exceptionclasses.*;
public class LinkedQueue<T> implements QueueADT<T>
{
	private Node<T> myFront;
	private Node<T> myRear;
	private int myCount;
	/**
	* Default constuctor: Sets all the instance variables to null or 0.
	*/
	public LinkedQueue()
	{
		myFront = null;
		myRear = null;
		myCount = 0;
	}
	/**
	* Dequeue: removes the element in the myFront of the queue and returns
	* it.
	*/
	public T dequeue() 
	{
		T temp;
		if(myCount == 0)
		{
			throw new EmptyCollectionException(); 
		}
		else if(myCount == 1)
		{
			temp = myFront.getItem();
						myFront = null;
						myRear = null;
		}
		else
		{
			temp = myFront.getItem();
			myFront = myFront.getNext();
		}			myCount--;		return temp;
	}
	/**
	* Enqueue: Adds a specified element at the end of the queue.
	*/
	public void enqueue(T item) 
	{
		if(myCount == 0)
		{
			myFront = new Node<T>(item);
			myRear = myFront;
		}
		else
		{
			myRear.setNext(new Node<T>(item));
			myRear = myRear.getNext();
		}				myCount++;
	}
	/**
	* front: returns the item in the node thats in the myFront of
	* the queue.
	*/
	public T front() 
	{
		return myFront.getItem();
	}
	/**
	* isEmpty: returns a boolean that indicates if the queue is empty or
	* not. True if it is empty; False if its not.
	*/
	public boolean isEmpty() 
	{
		if(myCount == 0)
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
		return myCount;
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
		Node<T> current = myFront;
		while(current.getItem().equals(oldItem) == false && current.getItem() != null)
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
		if(myCount == 0)
		{
			throw new EmptyCollectionException();
		}		
		Node<T> temp = myFront.getNext();
		Node<T> prev = myFront;		
		if(prev.getItem().equals(item))
		{
			myFront = myFront.getNext();
			tally++;
			myCount--;
		}		
		while(temp.getNext() != null)
		{
			if(temp.getItem().equals(item))
			{
				prev.setNext(temp.getNext());
				tally++;
				myCount--;
			}
			temp = temp.getNext();
			prev = prev.getNext();
		}
		if(myRear.getItem().equals(item))
		{
			myRear = null;
			myCount--;
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
		if(myCount == 0)
		{
			throw new EmptyCollectionException();
		}
		else
		{
			String temp = "";
			Node<T> current = myFront;
			for(int i = 0; i < myCount; i++)
			{
				temp += current.getItem().toString() + "\n";
				current = current.getNext();
			}
			
			return temp;
		}
	}
}
