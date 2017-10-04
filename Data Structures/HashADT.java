/** Hash Table - HashADT.java

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