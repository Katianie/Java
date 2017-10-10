package queues;

/** Queue - PriorityQueue.java
* A child of arrayqueue. Organizes data by priority.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import googlePictures.*;

public class PriorityQueue<T> extends ArrayQueue<PriorityCachedImage>
{
	protected int myCurrentRoot;//used mostly for sorting. Holds the index number of the current root
	protected int myLeft;//index value of the left with respect to the root
	protected int myRight;//index value of the right with respect to the root
	protected int myNumComparisons;//number of comparisons done on this data structure in total
	protected int myNumTempComparisons;//number of comparisons done for jsut one particular search

	public PriorityQueue()
	{
		super();

		myCurrentRoot = 0;
		myLeft = 1;
		myRight = 2;
	}


	/**
	 * find: looks up a specified PriorityCachedImage object and trturns the index in the array in which it is found.
	 */
	public int find(PriorityCachedImage target)
	{
		myNumTempComparisons = 0;
		
		for(int i = 0; i < myNumItems; i++)
		{
			if(target.equals((PriorityCachedImage)contents[i]))
			{
				return i;
			}
			
			myNumComparisons++;
			myNumTempComparisons++;
		}
		
		return -1;
	}
	
	/**
	 * findObj - returns the object at a specified index. I made this so I can work directly on the object in the array.
	 * @param index
	 * @return
	 */
	public PriorityCachedImage findObj(int index)
	{
		return (PriorityCachedImage)contents[index];
	}
	
	public void enqueue(PriorityCachedImage target)
	{
		super.enqueue(target);
	}
	
	/**
	 * changePriority - changes the priority of a specified object and then swaps it with respect to the way
	 * priority queues are sorted.
	 * 
	 * @param target
	 * @param num
	 */
	public void changePriority(PriorityCachedImage target,int num)
	{
		int targetIndex = find(target);
		int root = (targetIndex - 1) / 2;
		PriorityCachedImage temp;
		
		if(targetIndex != -1)
		{
			target.setPriority(num);
			
			while(((PriorityCachedImage)contents[targetIndex]).getPriority() > ((PriorityCachedImage)contents[root]).getPriority())
			{	
				temp = (PriorityCachedImage)contents[root];
				contents[root] = contents[targetIndex];
				contents[targetIndex] = temp;
				
				targetIndex = find(target);
				root = (targetIndex - 1) / 2;
			}
			
		}
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		String temp = "";
		
		for(int i = 0; i < myNumItems; i++)
		{
			temp += ((PriorityCachedImage)contents[i]).toString() + "\n";
		}
		
		return temp;
	}


	public PriorityCachedImage getLeft(int currentroot)
	{
		return (PriorityCachedImage)contents[2 * currentroot + 1];
	}

	public PriorityCachedImage getRight(int currentroot)
	{
		return (PriorityCachedImage)contents[2 * currentroot + 2];
	}
	
	public int getNumComparisons()
	{
		return myNumComparisons;
	}
	public int getNumTempComparisons()
	{
		return myNumTempComparisons;
	}

}
