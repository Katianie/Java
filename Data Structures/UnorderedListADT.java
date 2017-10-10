package lists;

/** List - ListADT.java
* Defines the interface to an unordered list collection. Elements
* are stored in any order the user desires.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

public interface UnorderedListADT<T> extends ListADT<T>
{
   /**  Adds the specified item to the front of this list. */
   public void addToFront (T item);

   /**  Adds the specified item to the rear of this list. */
   public void addToRear (T item);

   /**  Adds the specified item after the specified target. */
   public void addAfter (T item, T target);
}
