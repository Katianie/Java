package Stratego;

import java.awt.Image;
import java.io.Serializable;

public class StrategoPiece implements Serializable
{
	private static int myUniqueIdNum = 0;
	
	private int myIdNum;
	private Position myPosition;
	private boolean myIsShown;
	private int myMoveableDistance;
	private int myOwner; //0 to indicate players piece, 1 to indicate ai piece
	
	public StrategoPiece(int initIdNum, Position initPosition, boolean initIsShown, int moveableDistance, int owner)
	{
		myIdNum = initIdNum;
		myPosition = initPosition;
		myIsShown = initIsShown;
		myOwner = owner;
		myMoveableDistance = 1;
		
		myUniqueIdNum++;

	}
	
	/**
	 * getPieceName: Returns game piece name for corresponding idNum
	 * 
	 * @param idNum
	 * @return String
	 */
	public String getPieceName(int idNum)
	{
		if(idNum == 0)
		{
			return "Marshall";
		}
		else if(idNum == 1)
		{
			return "General";
		}
		else if(idNum == 2)
		{
			return "Colonel";
		}
		else if(idNum == 3)
		{
			return "Major";
		}
		else if(idNum == 4)
		{
			return "Captain";
		}
		else if(idNum == 5)
		{
			return "Lieutenant";
		}
		else if(idNum == 6)
		{
			return "Sergeant";
		}
		else if(idNum == 7)
		{
			return "Miner";
		}
		else if(idNum == 8)
		{
			return "Scout";
		}
		else if(idNum == 9)
		{
			return "Spy";
		}
		else if(idNum == 10)
		{
			return "Bomb";
		}
		else if(idNum == 11)
		{
			return "Flag";
		}
		else if(idNum == 12)
		{
			return "Back of Card";
		}
		else
		{
			return "No Name";
		}
			
	}
	
	public int getUniqueIdNum()
	{
		return myUniqueIdNum;
	}
	
	public Position getPosition()
	{
		return myPosition;
	}
	
	public int getX()
	{
		return myPosition.getX();
	}
	
	public int getY()
	{
		return myPosition.getY();
	}
	
	public boolean isShown()
	{
		return myIsShown;
	}
	
	public int getIdNum()
	{
		return myIdNum;
	}
	
	public int getMoveableDistance()
	{
		return myMoveableDistance;
	}
	
	public int getOwner()
	{
		return myOwner;
	}
	
	public void setPosition(Position pos)
	{
		myPosition = pos;
	}
	
	public void setX(int x)
	{
		myPosition.setX(x);
	}
	
	public void setY(int y)
	{
		myPosition.setY(y);
	}
	
	public void setIsShown(boolean shown)
	{
		myIsShown = shown;
	}
	
	public void setIdNum(int num)
	{
		myIdNum = num;
	}
	
	public void setMoveableDistance(int distance)
	{
		myMoveableDistance = distance;
	}
	
	public void setOwner(int owner)
	{
		myOwner = owner;
	}
	
	public boolean equals(StrategoPiece aPiece)
	{
		return (this.getIdNum() == aPiece.getIdNum()) && (this.getX() == aPiece.getX()) && (this.getY() == aPiece.getY());
	}

	public boolean equalsOriginal(StrategoPiece aPiece)
	{
		return this.myUniqueIdNum == aPiece.myUniqueIdNum;
	}
	
	public String toString()
	{
		return "UniqueID:" + myUniqueIdNum + "\n" + "Id:"  + myIdNum + "\n" + "Position:" + myPosition + "\n" + "Is Shown:" + myIsShown + "\n" + "Moveable Distance:" + myMoveableDistance + "\n" + "Owner:" + myOwner;
	}
	

}
