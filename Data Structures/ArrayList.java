package lists;

/** List - ArrayList.java
* Represents an array implementation of a list. The front of
* the list is kept at array index 0. This class will be extended
* to create a specific kind of list.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import exceptionclasses.*;

public class ArrayList<T> implements ListADT<T>
{
   protected final int DEFAULT_CAPACITY = 100;
   protected  int count;
   protected T[] contents;

  /**
   * Creates an empty list using the default capacity.
   */
  @SuppressWarnings("unchecked") 
  public ArrayList()
  {
      count = 0;
      contents = (T[])(new Object[DEFAULT_CAPACITY]);
  }

  /**
   * Creates an empty list using the specified capacity.
   * @param initialCapacity the initial size of the list as specified by the user
   */
  @SuppressWarnings("unchecked")
  public ArrayList (int initialCapacity)
  {
      count = 0;
      contents = (T[])(new Object[initialCapacity]);
  }

  /**
   * Removes and returns the last item in this list.
   * @return a reference to what was the last item in the list
   * @throws EmptyCollectionException if the list is empty
   */
  public T removeLast ()
  {
      if(count == 0)
    	  throw new EmptyCollectionException("ArrayList");
	  count --;
      return contents[count];
  }

  /**
   * Removes and returns the first item in this list.
   * @return a reference to what was the first item in the list
   * @throws EmptyCollectionException if the list is empty
   */
  public T removeFirst()
  {
	  if(count == 0)
		  throw new EmptyCollectionException("ArrayList");
	  T temp = contents[0];
	  for(int i=0; i<count-1; i++)
	  {
		  contents[i] = contents[i+1];
	  }
	  count--;
	  return temp;
  }

  /**
   * Removes and returns the specified element. The order of the
   * items in the list will be maintained by shifting the remaining
   * items.
   * @param element the item to be removed from the list
   * @return a reference to the item removed from the list
   */
  public T remove (T element)
  {
      T result;
      int index = find (element);

      if (index == -1)
         throw new ElementNotFoundException ("ArrayList");

      result = contents[index];
      count--;

      for (int i=index; i<count; i++)
    	  contents[i] = contents[i+1];
      
      contents[count] = null;

      return result;
  }

  /**
   * Returns a reference to the element at the front of this list. The element
   * is not removed from the list.
   * @return a reference to the first item in the list
   * @throws EmptyCollectionException if the list is empty
   */
  public T first()
  {
      if (isEmpty())
         throw new EmptyCollectionException ("ArrayList");

      return contents[0];
  }

  /**
   * Returns a reference to the element at the rear of this list. The element
   * is not removed from the list.
   * @return a reference to the last item in the list
   * @throws EmptyCollectionException if the list is empty
   */
  public T last()
  {
      if (isEmpty())
         throw new EmptyCollectionException ("ArrayList");

      return contents[count-1];
  }

  /**
   * Returns true if this list contains the specified element.
   * @param target a reference to the item to be located
   * @return true if the target is found; false otherwise
   */
  public boolean contains (T target)
  {
      return (find(target) != -1);
  }
  
  /**
   * getItem: returns a refrence to an item in the list.
   * The method uses the comparable interface to compare 
   * two objects to eachother.
   * @param target
   * @return
   */
  public T getItem(T target)
  {
	  for(int i = 0; i < count; i++)
	  {
		  Comparable<T> temp = (Comparable<T>)contents[i];

		  if(temp.compareTo(target) == 0)
		  {
			  return contents[i];
		  }		  
	  }
	  return null;
  }
  
  

  /**
   * Returns the array index of the specified element.
   * @param target a reference to the item to be located
   * @return the array index of the specified element if it is found; -1 if it
   * is not found
   */
  protected int find (T target)
  {
      for (int i=0; i<count; i++)
        if (target.equals(contents[i]))
               return i;

      return -1;
  }

  /**
   * Determines whether or not the list is empty.
   * @return true if this list is empty; false otherwise
   */
  public boolean isEmpty()
  {
      return (count == 0);
  }

  /**
   * Returns a count of the number of items in this list.
   * @return the number of items currently in the list
   */
  public int size()
  {
      return count;
  }

  /**
   * Returns a string representation of this list.
   * @return a reference to a String containing the items in the list
   */
  public String toString()
  {
	  String temp = "";
	  for(int i=0; i<count; i++)
	  {
		  temp += contents[i].toString() + "\n";
	  }
      return temp;
  }

  /**
   * Creates a new array to store the contents of this list with
   * twice the capacity of the old one.
   */
  @SuppressWarnings("unchecked")
  protected void expandCapacity()
  {
      T[] larger = (T[])(new Object[contents.length*2]);

      for (int i=0; i < count; i++)
         larger[i] = contents[i];

      contents = larger;
  }
}
