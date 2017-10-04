/** Stacks - ArrayStack.java
* Defines the behaviors of a basic stack.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/package stacks;

public interface StackADT<T>
{
	//Adds one item to the top of the stack
	public void push(T item);
	//Removes and returns the top item from the stack
	public T pop();
	//Returns the top-most item on the stack without removing it
	public T peek();
	//Returns the position of the target in the stack
	public int search(T target);
	//Determines whether or not the stack is empty
	public boolean isEmpty();
	//Determines how many items in in the stack
	public int size();
	//Returns a string representing the state of the stack
	public String toString();
}
