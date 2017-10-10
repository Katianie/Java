package lists;

/** Node - Node.java
* Defines a node class capable of storing an Object reference
* and a reference to the next node in the linked list.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

public class Node<T>
{
  private T data; //storage for a reference to an object of type T
  private Node<T> next; //storage for the address of the next node in the list

  /**
   * default constructor --
   * initializes data and next to null.
   */
  public Node()
  {
	  data = null;
	  next = null;
  }
  
  /**
   * parameterized constructor --
   * initializes data to the user specified value; next is set to null.
   * @param newItem - the value to be stored in the node
   */
  public Node(T newItem)
  {
    data = newItem;
    next = null;
  }

  /**
   * parameterized constructor --
   * initializes data and next to user specified values.
   * @param newItem - the object reference to be stored in the node
   * @param nextItem - the reference to the next node in the list
   */
  public Node(T newItem, Node<T> nextItem)
  {
    data = newItem;
    next = nextItem;
  }

  /**
   * setItem --
   * stores a new value in data.
   * @param newItem - the object reference to be stored in this node
   */
  public void setItem(T newItem)
  {
    data = newItem;
  }

  /**
   * setNext --
   * stores a new value in next.
   * @param nextItem - the reference to be stored in next
   */
  public void setNext(Node<T> nextItem)
  {
    next = nextItem;
  }

  /**
   * getItem --
   * returns the reference stored in data.
   * @return a reference to the data stored in this node
   */
  public T getItem()
  {
    return data;
  }

  /**
   * getNext --
   * returns the reference stored in next.
   * @return the reference to the next node
   */
  public Node<T> getNext()
  {
    return next;
  }
}
