package proj4;

/** Maze - Square.java
* Handles all 4 of the types of enums that could be in a Maze
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

public enum Square 
{
	WALL ('#'),
	SPACE ('.'),
	START ('o'),
	FINISH ('*');
	
	private final char myChar;
	
	private Square(char lett)
	{
		myChar = lett;
	}
	
	public char getChar()
	{
		return myChar;
	}
	
	/**
	 * fromChar - converts a given character to a corresponding Square
	 * 
	 * @param the character that you wish to convert
	 * @return a square corresponding to the character
	 */
	public static Square fromChar(char target)
	{
		if(target == '#')
		{
			return WALL;
		}
		else if(target == '.')
		{
			return SPACE;
		}
		else if(target == 'o')
		{
			return START;
		}
		else
		{
			return FINISH;
		}
	}
	
	/**
	 * equals - compares two Squares and if they are equal the
	 * method will return true; otherwise false.
	 * 
	 * @param the otherSquare being compared.
	 * @return true if the Squares are equal, false if they are not.
	 */
	public Boolean equals(Square otherSquare)
	{
		if(this.myChar == otherSquare.myChar)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * toString - converts/returns the provided character being stored 
	 * in the variable myChar to a string.
	 * 
	 * @return a string corresponding to the character in the Square.
	 */
	public String toString()
	{
		if(myChar == '#')
		{
			return "#";
		}
		else if(myChar == '.')
		{
			return ".";
		}
		else if(myChar == 'o')
		{
			return "o";
		}
		else
		{
			return "*";
		}
	}
	
	

}