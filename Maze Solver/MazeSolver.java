/** Maze - MazeSolver.java* Handles the solving of a maze and the * Appropriate output corresponding to the maze.** This was uploaded to Katianie.com, Feel free to use this* code and share it with others, Just give me credit ^_^.** Eddie O'Hagan* Copyright © 2009 Katianie.com*/package proj4;

import stacks.*;
import java.util.*;
public class MazeSolver 
{
	private Maze myMaze;
	private ArrayStack<Point> myStack;
	private Boolean[][] myBoolArray;
	/**
	* MazeSolver Constuctor - instantiates myMaze to the passed maze, 
	* Instantiates a new ArrayStack in which holds points, Instanties 
	* a boolean array to all false.
	* @param theMaze
	*/
	public MazeSolver(Maze theMaze)
	{
		myMaze = theMaze;
		myStack = new ArrayStack<Point>();
		myStack.push(myMaze.getStartPos());
		myBoolArray = new Boolean[myMaze.getNumRows()][myMaze.getNumColoums()];
		for(int i = 0; i < myMaze.getNumRows(); i++)
		{
			for(int x = 0; x < myMaze.getNumColoums(); x++)
			{
				myBoolArray[i][x] = false;
			}
		}
	}
	/**
	* solveMaze - determines if a maze is solvable or not.
	* @return true if the maze is solvable false if it isen't
	*/
	public boolean solveMaze()
	{	
		while(myStack.isEmpty() == false)
		{
			Point currentPoint = myStack.pop();
			if(myBoolArray[currentPoint.getRow()][currentPoint.getCol()] == false)
			{
				if(currentPoint.equals(myMaze.getFinishPos()) == false)
				{					//Look Left
					if(!myMaze.get(currentPoint.getRow() - 1,currentPoint.getCol()).equals(Square.WALL))
					{
						myStack.push(new Point(currentPoint.getRow() - 1 , currentPoint.getCol()));
					}										//Look Up
					if(!myMaze.get(currentPoint.getRow(),currentPoint.getCol() + 1).equals(Square.WALL))
					{
						myStack.push(new Point(currentPoint.getRow() , currentPoint.getCol() + 1));
					}					//Look Right
					if(!myMaze.get(currentPoint.getRow() + 1,currentPoint.getCol()).equals(Square.WALL))
					{
						myStack.push(new Point(currentPoint.getRow() + 1 , currentPoint.getCol()));
					}					//Look Down
					if(!myMaze.get(currentPoint.getRow(),currentPoint.getCol() - 1).equals(Square.WALL))
					{
						myStack.push(new Point(currentPoint.getRow() , currentPoint.getCol() - 1));
					}
					myBoolArray[currentPoint.getRow()][currentPoint.getCol()] = true;
				}
				else
				{
					return true;
				}
			}
		}		
		return false;
	}
	/**
	* boolToString - returns the solution to the maze in a graphical fashion
	* 
	* @return a String containing the path took by the solve maze method
	* reach the end.
	*/
	public String boolToString()
	{
		String temp = "";
		for(int i = 0; i < myMaze.getNumRows();i++)
		{
			temp += "\n";
			for(int x = 0; x < myMaze.getNumColoums();x++)
			{
				if(myBoolArray[i][x] == true)
				{
					temp += "O";
				}
				else
				{
					temp += "X";
				}
			}
		}
		return temp;
	}
}
