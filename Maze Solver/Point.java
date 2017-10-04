/** Maze - Point.java
* A Postition on the Maze
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/package proj4;
public class Point 
{
	private int myRow;
	private int myCol;
	/**
	* Point Constuctor - accepts a row and a coloumn and
	* assigns the values to the proper Instance variables.
	* 
	* @param the Row position
	* @param the Coloumn position
	*/
	public Point(int row, int col)
	{
		myRow = row;
		myCol = col;
	}
	/**
	* getRow - returns the row value
	* @return the row value
	*/
	public int getRow()
	{
		return myRow;
	}
	/**
	* getRow - returns the coloumn value
	* @return the coloumn value
	*/
	public int getCol()
	{
		return myCol;
	}
	/**
	* equals - compares to points and if the points are equal
	* return true, other wise return a false
	* 
	* @param the otherPoint to be compared
	* @return a true if they are equal, false if otherwise.
	*/
	public boolean equals(Point otherPoint)
	{
		if( (myRow == otherPoint.getRow()) && (myCol == otherPoint.getCol()) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	* toString - prints out the values stored in both instance variables 
	* and then returned.
	*/
	public String toString()
	{
		return myRow + "," + myCol;
	}
}