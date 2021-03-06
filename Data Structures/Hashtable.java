package HashTables;

/** Hash Table - Hashtable.java
* A Hash Table data stucture that implements the HashADT 
* interface
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright � 2009 Katianie.com
*/

import lists.*;
import exceptionclasses.*;

public class Hashtable<T> implements HashADT<T>
{
	protected int myTableSize;//contains the size of the hash table
	protected int myNumItems;//number of elements in the table
	protected int myNumComparisons;//number of comparisons ever done by this data structure
	protected int myNumTempComparisons;//number of comparisons done in one specific test
	protected Object[] myTable;//an array that holds objects

	/**
	 * Hash Table - Default Constructor: Assigns default values for the instance variables in
	 * Hash Table class.
	 */
	public Hashtable()
	{
		myTableSize = 11;
		myNumItems = 0;
		myTable  = new Object[myTableSize];	
		myNumComparisons = 0;
		myNumTempComparisons = 0;


		for(int i = 0; i < myTable.length; i++)
		{
			myTable[i] = new LinkedUnorderedList<T>();
		}
	}

	/**
	 * Hash Table - Parameterized Constructor: Assigns t values for the instance variables in
	 * Hash Table class along with a size provided for the size of the Hash Table.
	 */
	public Hashtable(int size)
	{
		myTableSize = size;
		myNumItems = 0;
		myTable  = new Object[myTableSize];	
		myNumComparisons = 0;
		myNumTempComparisons = 0;

		for(int i = 0; i < myTable.length; i++)
		{
			myTable[i] = new LinkedUnorderedList<T>();
		}
	}

	/**
	 * Hash Table - Remove Method: Removes a specified element form the table.
	 */
	public T remove (T item)
	{
		int hashnum = item.hashCode() % myTable.length;

		if(((LinkedUnorderedList<T>)myTable[hashnum]).contains(item))
		{
			return ((LinkedUnorderedList<T>)myTable[hashnum]).remove(item);

		}
		else
		{
			return null;
		}

	}

	/**
	 * Hash Table - Contains Method - Tests to see if a specified element is in the table. 
	 */
	public boolean contains(T target)
	{
		int hashnum = Math.abs(target.hashCode() % myTable.length);
		
		if(((LinkedUnorderedList<T>)myTable[hashnum]).contains(target))
		{
			myNumTempComparisons = ((LinkedUnorderedList<T>)myTable[hashnum]).getNumTempComparisons();
			myNumComparisons += myNumTempComparisons;
			return true;
		}
		else
		{
			myNumTempComparisons = ((LinkedUnorderedList<T>)myTable[hashnum]).getNumTempComparisons();
			myNumComparisons += myNumTempComparisons;
			return false;
		}

	}
	
	/**
	 * Hash Table - Is Empty Method: returns true if myNumItems = 0. Otherwise returns false.
	 */
	public boolean isEmpty()
	{
		return (myNumItems == 0);
	}

	/**
	 * Hash Table - Size Method: returns the value of myNumItems.
	 */
	public int size()
	{
		return myNumItems;
	}
	
	/**
	 * Hash Table - Get Num Temp Comparisons: returns the number of comparisons done in just one search.
	 */
	public int getNumTempComparisons()
	{
		return myNumTempComparisons;
	}
	
	/**
	 * Hash Table - Get Num Comparisons: returns the number of comparisons done in total.
	 */
	public int getNumComparisons()
	{
		return myNumComparisons;
	}
	
	/**
	 * Hash Table - Set Num Comparisons: Sets the number of comparisons to a provided parameter.
	 */
	public void setNumComparisons(int num)
	{
		myNumComparisons = num;
	}
	
	/**
	 * Hash Table - To String: Prints out all the data in the Hash Table in a string readable form.
	 */
	public String toString()
	{
		String temp = "";
		
		for(int i = 0; i < myTable.length; i++)
		{
			temp += (((LinkedUnorderedList<T>)myTable[i]).toString());
		}

		return temp;
	}

}
