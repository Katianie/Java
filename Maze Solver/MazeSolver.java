/** Maze - MazeSolver.java

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
				{
					if(!myMaze.get(currentPoint.getRow() - 1,currentPoint.getCol()).equals(Square.WALL))
					{
						myStack.push(new Point(currentPoint.getRow() - 1 , currentPoint.getCol()));
					}
					if(!myMaze.get(currentPoint.getRow(),currentPoint.getCol() + 1).equals(Square.WALL))
					{
						myStack.push(new Point(currentPoint.getRow() , currentPoint.getCol() + 1));
					}
					if(!myMaze.get(currentPoint.getRow() + 1,currentPoint.getCol()).equals(Square.WALL))
					{
						myStack.push(new Point(currentPoint.getRow() + 1 , currentPoint.getCol()));
					}
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