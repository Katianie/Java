/** Maze - Maze.java* Handles the reading in of a Maze to be solved* from a text file** This was uploaded to Katianie.com, Feel free to use this* code and share it with others, Just give me credit ^_^.** Eddie O'Hagan* Copyright © 2009 Katianie.com*/package proj4;
import java.util.*;
public class Maze 
{
	private int myRows;
	private int myColoums;
	private Point myStart;
	private Point myFinish;
	private Square[][] my2DArray;
	/**
	* Maze Consutctor - Reads in a scanner paramater so the maze can
	* be read in. Instance variables become instantiated here with 
	* values corresponding to what is being read in.
	* 
	* @param scanParam - Isntantiated in the application class. Allows
	* for easyer acess of the text file being read in.
	*/
	public Maze(Scanner scanParam)
	{	
		String line;
		myRows = scanParam.nextInt();
		myColoums = scanParam.nextInt();
		my2DArray = new Square[myRows][myColoums];
		scanParam.nextLine();
		for(int i = 0; i < myRows; i++)
		{
			line = scanParam.nextLine();
			for(int x = 0; x < myColoums; x++)
			{
				my2DArray[i][x] = Square.fromChar(line.charAt(x));
				if(my2DArray[i][x].getChar() == 'o')
				{
					myStart = new Point(i,x);
				}
				else if(my2DArray[i][x].getChar() == '*')
				{
					myFinish = new Point(i,x);
				}
			}
		}
	}	
	/**
	* getNumRows - returns the number of rows in the maze.
	* @return the number of rows in the maze.
	*/
	public int getNumRows()
	{
		return myRows;
	}
	/**
	* getNumColoums - returns the number of coloums in the
	* maze.
	* @return the number of coloums in the maze.
	*/
	public int getNumColoums()
	{
		return myColoums;
	}
	/**
	* getStartPos - returns the starting position in the Maze.
	* @return the starting position in the Maze.
	*/
	public Point getStartPos()
	{
		return myStart;
	}
	/**
	* getFinishPos - returns the finishing position in the Maze.
	* @return the finishing position in the Maze.
	*/
	public Point getFinishPos()
	{
		return myFinish;
	}
	/**
	* get - returns the Square at the specified location.
	* @param row
	* @param col
	* @return the Square at the specified location.
	*/
	public Square get(int row, int col)
	{
		return my2DArray[row][col];
	}
	/**
	* get - returns the Square at the specified location.
	* @param row
	* @param col
	* @return the Square at the specified location.
	*/
	public Square get(Point pt)
	{
		return my2DArray[pt.getRow()][pt.getCol()];
	}
	/**
	* toString - prints out the maze that was read in
	* @return a string contaning the maze that was 
	* read in.
	*/
	public String toString()
	{
		String temp = "";
		for(int i = 0; i < myRows; i++)
		{
			for(int x = 0; x < myColoums; x++)
			{
				temp += my2DArray[i][x].toString();
			}
			temp+= "\n";
		}
		return temp;
	}
}
