import java.io.Serializable;

/**
 * Card: Simple class that holds data of a single card in
 * a deck of 52 cards.
 * 
 * @author Eddie
 *
 */

public class Card implements Serializable
{
	private int myNum;
	private String mySuite;
	
	public Card(int num, String suite)
	{
		myNum = num;
		mySuite = suite;
	}
	
	public int getNum()
	{
		return myNum;
	}
	
	public String getSuite()
	{
		return mySuite;
	}
	
	public void setNum(int num)
	{
		myNum = num;
	}
	
	public void setSuite(String suite)
	{
		mySuite = suite;
	}

}
