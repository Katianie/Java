package exceptionclasses;

/** Custom Exception Classes - EmptyCollectionException.java
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
public class EmptyCollectionException extends RuntimeException
{
	/**
     * Initializes an EmptyCollectionException storing an appropriate message.
	 */
	public EmptyCollectionException()
	{
		super("collection is empty");
	}

	/**
     * Initializes an EmptyCollectionException storing the type of the
     * collection (as specified by the user) along with an appropriate message.
	 */
	public EmptyCollectionException(String message)
	{
		super(message + " collection is empty");
	}
}
