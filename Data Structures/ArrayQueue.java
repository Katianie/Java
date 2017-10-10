package queues;

/** Queue - ArrayQueue.java
* The ArrayQueue class deals with an array of objects
* in a queue like pattern.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import exceptionclasses.*;

public class ArrayQueue<T> 
{
	protected int front;
	protected int rear;
	protected int myNumItems;
	protected Object[] contents;
	
	/**
	 * ArrayQueue Constuctor: Handles the assignment statments for 
	 * the instance variables along with the creation of the array 
	 * contents
	 */
	public ArrayQueue()
	{
		contents = (T[])(new Object[5]);
		front = 0;
		rear = 0;
		myNumItems = 0;
	}
	
	public ArrayQueue(int size)
	{
		contents = (T[])(new Object[size]);
		front = 0;
		rear = 0;
		myNumItems = 0;
	}
	
	/**
	 * enqueue: adds the specified target to the end of the queue
	 * 
	 * @param target
	 */
	public void enqueue(T target)
	{
		if(myNumItems >= contents.length)
		{
			expandCapacity();
		}

		contents[rear]= target;
		rear = (rear + 1) % contents.length;
		myNumItems++;
		
	}
	/**
	 * dequeue: removes an element form the front of the array and returns it
	 * @return
	 */
	public T dequeue()
	{
		if(myNumItems == 0)
		{
			throw new EmptyCollectionException();
		}
		else
		{
			T temp = (T)contents[front];
			contents[front] = null;
			myNumItems--;
			
			front = (front + 1) % contents.length;
			
			return temp;
		}
	}
	
	/**
	 * expandCapacity: creates a new array that is double the size of the 
	 * original array and copies the elements.
	 */
	public void expandCapacity()
	{
		Object[] temp = (T[])(new Object[contents.length * 2]);
		int i = front;
		int j = 0;
		
		do{

			temp[j] = contents[i];
			i = (i + 1) % contents.length;
			j++;

		}while(i != rear);
		
		front = 0;
		rear = myNumItems;
		
		contents = temp;
		
		
	}
	
	public boolean isEmpty()
	{
		return myNumItems == 0;
	}
	
	/**
	 * toString: returns a string containing the objects in the array
	 * from front to rear
	 * 
	 */
	public String toString()
	{
		String temp = "";
		int i = front;
		
		if(myNumItems == 0)
		{
			return temp;
		}
		
		do{
			if(contents[i] == null)
			{
				i++;
			}
			else
			{
				temp += contents[i].toString() + "\n";
				i = (i + 1) % contents.length;
			}
		}while(i != rear);

		return temp;
		
	}

}
