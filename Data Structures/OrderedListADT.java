/** List - ListADT.java
* Defines the interface to an ordered list collection. Only
* Comparable elements are stored. The order is determined by the key-field
* of the item.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/package lists;
public interface OrderedListADT<T> extends ListADT<T>
{
   /** Adds the specified item to this list at the proper location. */
   public void add(T item);
}


