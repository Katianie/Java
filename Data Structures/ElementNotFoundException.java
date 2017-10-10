package exceptionclasses;

/** Custom Exception Classes - ElementNotFoundException.java
* This is an example on how to create your own exception classes
* in java.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/


@SuppressWarnings("serial")
public class ElementNotFoundException extends RuntimeException
{
	/**
	 * Initializes an ElementNotFoundException storing an appropriate message along with the type
	 * of the collection (as specified by the user).
	 */
	public ElementNotFoundException (String collection)
	{
		super ("The target element is not in this " + collection);
	}
}
