import java.io.Serializable;
import java.util.ArrayList;

/**
 * Player: Defines a player object with a username, password, score,
 * and a current hand of cards. All this is stored in a data base
 * of an arraylist of Players.
 * 
 * @author Eddie
 *
 */

public class Player implements Serializable
{
	private String myUsername;
	private String myPassword;
	private int myScore;
	private ArrayList<Card> myHand;
	
	public Player(String username, String password, int score)
	{
		myUsername = username;
		myPassword = password;
		myScore = score;
		myHand = new ArrayList<Card>();
	}
	
	public String getUsername()
	{
		return myUsername;
	}
	
	public String getPassword()
	{
		return myPassword;
	}
	
	public int getScore()
	{
		return myScore;
	}
	
	public void setUsername(String username)
	{
		myUsername = username;
	}
	
	public void setPassword(String password)
	{
		myPassword = password;
	}
	
	public void setScore(int score)
	{
		myScore = score;
	}
	
	public ArrayList<Card> getHand()
	{
		return myHand;
	}
	
	public void setHand(ArrayList<Card> hand)
	{
		myHand = hand;
	}
	
	/**
	 * AddCardToHand: Used when the player hits or the game is started.
	 * This method literally adds the card to the players hand.
	 * 
	 * @param aCard
	 */
	public void AddCardToHand(Card aCard)
	{
		myHand.add(aCard);
	}
	
	public Card getCard(int index)
	{
		return myHand.get(index);
	}
	
	/**
	 * getCardTotal: takes the sum of all the cards you have so we can 
	 * check for example to see if the player has hit 21 and won the
	 * game.
	 * 
	 * @return card total
	 */
	public int getCardTotal()
	{
		Card temp;
		int sum = 0;
		
		for(int i = 0; i < myHand.size(); i++)
		{
			temp = myHand.get(i);
			sum += temp.getNum();
		}
		
		return sum;
	}
	
	/**
	 * displayHand: Takes all the cards in the players hand and creates
	 * a string representation of those cards 
	 * 
	 * @return String displaying hand info
	 */
	public String displayHand()
	{
		String acc = "";
		Card tempCard = null;
		
		for(int i = 0; i < myHand.size(); i++)
		{
			tempCard = myHand.get(i);
			acc += tempCard.getNum() + " of " + tempCard.getSuite() + " \n";
		}
		
		return acc;
	}
	
	
}
