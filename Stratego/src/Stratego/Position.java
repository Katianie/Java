package Stratego;

import java.io.Serializable;

public class Position implements Serializable
{
	private int myX;
	private int myY;
	
	public Position(int x, int y)
	{
		myX = x;
		myY = y;
	}
	
	public int getX()
	{
		return myX;
	}
	
	public void setX(int x)
	{
		myX = x;
	}
	
	public int getY()
	{
		return myY;
	}

	public void setY(int y)
	{
		myY = y;
	}
	
	public boolean equals(Position pos)
	{
		return this.myX == pos.myX && 
				this.myY == pos.myY;
	}
	
	public String toString()
	{
		return "(" +myX + "," + myY + ")";
	}
}
