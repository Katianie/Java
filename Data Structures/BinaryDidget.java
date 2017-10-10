package Binary;

/** Binary - BinaryDidget.java
* This is the Code that defines a BinaryDidget object.
* All a Binary Didget is; is a num that is 2^x and a boolean
* that is eather 1 or 0.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

public class BinaryDidget 
{
	private double myDig; //The number of the actual didget in base 10 ( 2^0 = mydig)
	private boolean myIsOne;//true if the didget is a 1 in base 2, false if 0
	
	public BinaryDidget(double dig,boolean isOne)
	{
		myDig = dig;
		myIsOne = isOne;
	}

	public double getMyDig() 
	{
		return myDig;
	}

	public void setMyDig(int myDig) 
	{
		this.myDig = myDig;
	}

	public boolean isMyIsOne() 
	{
		return myIsOne;
	}

	public void setMyIsOne(boolean myIsOne) 
	{
		this.myIsOne = myIsOne;
	}
	
	public String toString()
	{
		String temp = "";
		
		if(myIsOne == true)
		{
			temp = "1";
		}
		else
		{
			temp = "0";
		}
		
		return temp;
	}

}
