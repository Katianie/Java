/** Queue - QueueADT.java
* Defines the behaviors of a basic queue.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/package queues;

public interface QueueADT<T>
{
	/**  Adds one item to the rear of the queue. */
	public void enqueue(T newItem);
	/**  Removes and returns the item at the front of the queue. */
	public T dequeue();
	/**  Returns without removing the item at the front of the queue. */
	public T front();
	/**  Determines whether or not the queue is empty. */
	public boolean isEmpty();
	/**  Returns the number of items in the queue. */
	public int size();
	/**  Returns a string representing the state of the queue. */
	public String toString();
}
