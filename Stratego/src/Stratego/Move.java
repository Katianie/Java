package Stratego;

import java.io.Serializable;

public class Move implements Serializable
{
	private Position myPrevPosition;
	private Position myNextPosition;
	
	public Move() 
	{
		myPrevPosition = null;
		myNextPosition = null;
	}
	
	public Move(Position initPrevPosition, Position initNextPosition)
	{
		myPrevPosition = initPrevPosition;
		myNextPosition = initNextPosition;
	}
	
	public Move(int xi, int yi, int xf, int yf)
	{
		myPrevPosition = new Position(xi, yi);
		myNextPosition = new Position(xf, yf);
	}

	public Position getPrevPosition()
	{
		return myPrevPosition;
	}
	
	public void setPrevPosition(Position prevPos)
	{
		myPrevPosition = prevPos;
	}
	
	public Position getNextPosition()
	{
		return myNextPosition;
	}
	
	public void setNextPosition(Position nextPosition)
	{
		myNextPosition = nextPosition;
	}
	
	public String toString()
	{
		return "PrevPosition =" + myPrevPosition + "NextPosition =" + myNextPosition;
	}

}
