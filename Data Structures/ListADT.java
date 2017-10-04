/** List - ListADT.java* Defines the interface to a general list collection. Specific* types of lists will extend this interface to complete the* set of necessary operations.** This was uploaded to Katianie.com, Feel free to use this* code and share it with others, Just give me credit ^_^.** Eddie O'Hagan* Copyright © 2009 Katianie.com*/package lists;
public interface ListADT<T>
{
	/**  Removes and returns the first item from this list. */
	public T removeFirst();
	/**  Removes and returns the last item from this list. */
	public T removeLast();
	/**  Removes and returns the specified item from this list. */
	public T remove(T item);
	/**  Returns a reference to the first item in this list. */
	public T first();
	/**  Returns a reference to the last item in this list. */
	public T last();
	/**  Returns true if this list contains the specified target element. */
	public boolean contains(T target);
	/**  Returns true if this list is empty. */
	public boolean isEmpty();
	/**  Returns the number of items in this list. */
	public int size();
	/**  Returns a string representation of this list. */
	public String toString();
}
