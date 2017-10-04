/** Queue - ArrayQueue.java
* The ArrayQueue class deals with an array of objects
* in a queue like pattern.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/package queues;

import exceptionclasses.*;

public class ArrayQueue<T> 
{
	protected int myFront;
	protected int myRear;
	protected int myNumItems;
	protected Object[] myContents;
	/**
	* ArrayQueue Constuctor: Handles the assignment statments for 
	* the instance variables along with the creation of the array 
	* myContents
	*/
	public ArrayQueue()
	{
		myContents = (T[])(new Object[5]);
		myFront = 0;
		myRear = 0;
		myNumItems = 0;
	}
	public ArrayQueue(int size)
	{
		myContents = (T[])(new Object[size]);
		myFront = 0;
		myRear = 0;
		myNumItems = 0;
	}
	/**
	* enqueue: adds the specified target to the end of the queue
	* 
	* @param target
	*/
	public void enqueue(T target)
	{
		if(myNumItems >= myContents.length)
		{
			expandCapacity();
		}
		myContents[myRear]= target;
		myRear = (myRear + 1) % myContents.length;
		myNumItems++;
	}
	/**
	* dequeue: removes an element form the myFront of the array and returns it
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
			T temp = (T)myContents[myFront];
			myContents[myFront] = null;
			myNumItems--;
			myFront = (myFront + 1) % myContents.length;
			return temp;
		}
	}
	/**
	* expandCapacity: creates a new array that is double the size of the 
	* original array and copies the elements.
	*/
	public void expandCapacity()
	{
		Object[] temp = (T[])(new Object[myContents.length * 2]);
		int i = myFront;
		int j = 0;
				do		{
			temp[j] = myContents[i];
			i = (i + 1) % myContents.length;
			j++;
			
		}		while(i != myRear);
		myFront = 0;
		myRear = myNumItems;
		myContents = temp;
	}
	public boolean isEmpty()
	{
		return myNumItems == 0;
	}
	/**
	* toString: returns a string containing the objects in the array
	* from myFront to myRear
	* 
	*/
	public String toString()
	{
		String temp = "";
		int i = myFront;
		if(myNumItems == 0)
		{
			return temp;
		}
		do		{
			if(myContents[i] == null)
			{
				i++;
			}
			else
			{
				temp += myContents[i].toString() + "\n";
				i = (i + 1) % myContents.length;
			}
		}		while(i != myRear);
		return temp;
	}
}
