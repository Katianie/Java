/** Hash Table - HashADT.java* Defines the behaviors of a basic Hash Table.** This was uploaded to Katianie.com, Feel free to use this* code and share it with others, Just give me credit ^_^.** Eddie O'Hagan* Copyright © 2009 Katianie.com*/package HashTables;

public interface HashADT<T>
{
	//removes an element for mthe hash table
	public T remove (T item);	
	//tests to see if a specified element is in the table
	public boolean contains(T target);
	//tests to see if the table is empty
	public boolean isEmpty();
	//returns the number of elements in the table.
	public int size();
	//prints out a string representation of all the data in the hash table
	public String toString();
}
