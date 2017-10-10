package Binary;

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
