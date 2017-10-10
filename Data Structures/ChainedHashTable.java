package HashTables;

/** Hash Table - ChainedHashTable.java
* Defines a Hash Table that adds elements to a particular list
* basied off of the hash code produced by the object being added.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import lists.*;

public class ChainedHashTable<T> extends Hashtable<T> implements UnorderdChainedHashADT<T>
{
	/**
	 * Chained Hash Table - Default Constructor: Calls the constructor in the Hash Table 
	 * class.
	 */
	public ChainedHashTable() 
	{
		super();
	}

	/**
	 * Chained Hash Table - Parameterized Constructor: Calls the constructor in the Hash Table 
	 * class and provides a size of the table.
	 */
	public ChainedHashTable(int size)
	{
		super(size);
	}
	
	/**
	 * Chained Hash Table - Add: Adds a specified item to the table. By using the hash function we
	 * can decide what list in particular to ad the element to. 
	 */
	public void add(T item)
	{	
		int hash = Math.abs(item.hashCode() % myTable.length);
		myNumItems++;
		((LinkedUnorderedList<T>)myTable[hash]).addToFront(item);
	}

}
