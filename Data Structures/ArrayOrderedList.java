package lists;

/** List - ArrayOrderdList.java
* Child class of the ArrayList
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T>
{
	/**
	 * ArrayOrderdList(default): assings default values to the parent
	 * classes Instance Variables. 
	 */
	public ArrayOrderedList()
	{
		super();
	}
	
	/**
	 * ArrayOrderdList(param): uses the provided number to create a 
	 * size for the list.
	 */
	public ArrayOrderedList(int initialCapacity)
	{
		super(initialCapacity);
	}
	
	/**
	 * add: takes the provided item and addes it depending on 
	 * the position returned by the compare to method.
	 */
	public void add(T newItem)
	{

		if(this.contents.length == this.size())
		{
			this.expandCapacity();
		}
		
		if(this.isEmpty() == true)
		{
			contents[0] = newItem;
			count++;
		}
		else
		{
			Comparable<T> temp = (Comparable<T>)newItem;
			
			if(this.isEmpty() == true)
			{
				contents[0] = newItem;
				count++;
			}
			else
			{
				int i = count - 1;
				
				while((i >= 0) && ((Comparable)contents[i]).compareTo(temp) < 0)
				{
					contents[i+1] = contents[i];
					i--;
				}
				
				contents[i+1] = newItem;
				count++;
			}

		}
		

		
	}
	
}
