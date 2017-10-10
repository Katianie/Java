import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 * GameGUI: Main GUI window for the whole application. This is where
 * the magic happens.
 * 
 * @author Eddie
 *
 * API:http://www.katianie.com/doc2
 */

public class GameGUI extends JFrame
{
	private static final String myDatabaseFilePath = "database.txt";
	private static GameGUI myGameGUI;

	private int myNumCardsDelt;

	private boolean myLoginConfirmed;
	private JPanel myPlayerPanel;
	private JPanel myAIPanel;
	private JPanel myButtonPanel;
	private JButton myHitButton; 
	private JButton myFoldButton;
	private JButton myDealButton;  
	private ArrayList<Card> myDeck; 
	private ArrayList<Player> myAccounts; 
	private Player myPlayer;
	private Player myAI;
	private LoginDialog myLoginDialog;
	private JLabel myPlayerScoreLabel;
	private JLabel myAiScoreLabel;
	private JLabel myPlayerHandLabel;
	private JLabel myAIHandLabel;

	//layouts
	private FlowLayout myFlowLayout;
	private BorderLayout myBorderLayout;

	//Handlers
	private HitButtonHandler myHitHandler;
	private FoldButtonHandler myFoldHandler;
	private DealButtonHandler myDealHandler;
	private WindowExitHandler myWindowExitHandler;

	/**
	 * getGameGUI: when called, if it is the first time it is being called, it
	 * will call the constructor to create the object. Otherwise it will only
	 * return the already constructed object. 
	 * 
	 * @return a GameGUI refrence
	 */
	public static GameGUI getGameGUI()
	{
		if(myGameGUI == null)
		{
			myGameGUI = new GameGUI();
		}

		return myGameGUI;
	}

	private GameGUI()
	{
		super("Black Jack App");
		
		myPlayerPanel = new JPanel();
		myAIPanel = new JPanel();
		myButtonPanel = new JPanel();
		myHitButton = new JButton("Hit");
		myFoldButton = new JButton("Fold");
		myDealButton = new JButton("Deal");
		myDeck = new ArrayList<Card>();
		
		myAccounts = new ArrayList<Player>();
		readData();
		myLoginConfirmed = false;

		myPlayer = new Player("Eddie", "Eddie", 0);
		myAI = new Player("House", "House", 0);
		myNumCardsDelt = 0;

		myLoginDialog = new LoginDialog(this);
		myPlayerScoreLabel = new JLabel("Player Score:");
		myAiScoreLabel = new JLabel("AI Score:");
		myPlayerHandLabel = new JLabel();
		myAIHandLabel = new JLabel();

		myBorderLayout = new BorderLayout();
		myFlowLayout = new FlowLayout();
		
		myHitHandler = new HitButtonHandler();
		myFoldHandler = new FoldButtonHandler();
		myDealHandler = new DealButtonHandler();
		myWindowExitHandler = new WindowExitHandler(this);

		myHitButton.addActionListener(myHitHandler);
		myFoldButton.addActionListener(myFoldHandler);
		myDealButton.addActionListener(myDealHandler);

		myHitButton.setEnabled(false);
		myFoldButton.setEnabled(false);
		
		this.setLayout(myBorderLayout);
		this.addWindowListener(myWindowExitHandler);
		this.setSize(800, 200);

		layoutGUI();
		if(myLoginConfirmed)
		{
			this.setVisible(true);
		}
	}
	
	/**
	 * layoutGUI: sets layouts and adds components to to GUI
	 */
	private void layoutGUI()
	{
		myButtonPanel.setLayout(myFlowLayout);
		myButtonPanel.add(myHitButton);
		myButtonPanel.add(myFoldButton);
		myButtonPanel.add(myDealButton);

		myPlayerPanel.setLayout(myFlowLayout);
		myPlayerPanel.add(myPlayerScoreLabel);

		myAIPanel.setLayout(myFlowLayout);
		myAIPanel.add(myAiScoreLabel);

		this.add(myAIPanel, myBorderLayout.NORTH);
		this.add(myButtonPanel, myBorderLayout.CENTER);
		this.add(myPlayerPanel, myBorderLayout.SOUTH);
	}

	/**
	 * setCurrentPlayer: Used to specify the current player after logging in
	 * 
	 * @param player
	 */
	public void setCurrentPlayer(Player player)
	{
		myPlayer = player;
		myPlayerScoreLabel.setText("Player Score:" + myPlayer.getScore() + "");
	}

	public Player getCurrentPlayer()
	{
		return myPlayer;
	}

	/**
	 * findAccount: takes in a username and password and checks to see if it is in our
	 * arraylist. If it is then return it and make note of it. If it is not found return null.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Player findAccount(String username, String password)
	{
		Player temp;

		for(int i = 0; i < myAccounts.size(); i++)
		{
			temp = myAccounts.get(i);

			if(temp.getUsername().equals(username) && temp.getPassword().equals(password))
			{
				myPlayer = temp;
				myLoginConfirmed = true;
				return temp;
			}
		}

		return null;
	}

	/**
	 * addAccount: adds an account to the accounts database
	 * @param username
	 * @param password
	 */
	public void addAccount(String username, String password)
	{
		myAccounts.add(new Player(username, password, 0));
	}

	/**
	 * compareCards: takes two cards and returns wich one is 
	 * higher in number. If they are equal then it will return null
	 * 
	 * @param cardOne
	 * @param cardTwo
	 * @return better card
	 */
	public Card compareCards(Card cardOne, Card cardTwo)
	{
		if(cardOne.getNum() > cardTwo.getNum())
		{
			return cardOne;
		}
		else if(cardOne.getNum() < cardTwo.getNum())
		{
			return cardTwo;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * printCards: creates a string that represents all of the cards in the deck
	 * 
	 * @return string with deck data
	 */
	public String printCards()
	{
		String acc = "";
		Card temp = null;
		
		for(int i = 0; i < myDeck.size(); i++)
		{
			temp = myDeck.get(i);
			acc += temp.getNum() + "of" + temp.getSuite() + "\n";
		}
		
		return acc;
	}

	/**
	 * initDeck: creates a deck of 52 cards, 4 suites
	 */
	public void initDeck()
	{
		for(int i = 0; i < 52; i++)
		{
			if(i >= 0 && i < 13)
			{
				if(i >= 11)
				{
					myDeck.add(new Card(10, "Spades"));
				}
				else
				{
					myDeck.add(new Card(i + 1, "Spades"));
				}
			}
			else if(i >= 13 && i < 26)
			{
				if(i >= 24)
				{
					myDeck.add(new Card(10, "Hearts"));
				}
				else
				{
					myDeck.add(new Card((i % 13) + 1, "Hearts"));
				}
			}
			else if(i >= 26 && i < 39)
			{
				if(i >= 37)
				{
					myDeck.add(new Card(10, "Hearts"));
				}
				else
				{
					myDeck.add(new Card((i % 13) + 1, "Hearts"));
				}
			}
			else if(i >= 39 && i < 52)
			{
				if(i >= 50)
				{
					myDeck.add(new Card(10, "Hearts"));
				}
				else
				{
					myDeck.add(new Card((i % 13) + 1, "Hearts"));
				}
			}
		}
	}

	/**
	 * shuffleDeck: takes all the cards in the deck and puts them in random
	 * positions back in the deck.
	 */
	public void shuffleDeck()
	{
		Random rand = new Random();

		for (int i = myDeck.size() - 1; i > 0; i--)
		{
			int n = rand.nextInt(i + 1);
			Card temp = myDeck.get(i);

			myDeck.add(i, myDeck.get(n));
			myDeck.add(n, temp);
		}

	}


	/**
	 * writeData: Takes in a file as a parameter and writes the data to a text file.
	 * 
	 */
	public void writeData()
	{
		FileOutputStream fos;
		ObjectOutputStream oos;

		try
		{	
			fos = new FileOutputStream(new File(myDatabaseFilePath));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(myAccounts);
			oos.close();
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(this, "Error Saving File", 
					"Error Writing to " + myDatabaseFilePath + " Perhaps File is In Use by another program", 
					JOptionPane.ERROR_MESSAGE);			
		}
	}

	/**
	 * readData: Reads in all of the data in the file and assigns it to its proper locations
	 * 
	 * @param aFile
	 */
	public void readData()
	{
		FileInputStream fis;
		ObjectInputStream ois;

		try
		{
			fis = new FileInputStream(new File(myDatabaseFilePath));
			ois = new ObjectInputStream(fis);
			myAccounts = (ArrayList<Player>) (ois.readObject());
			ois.close();

		}	
		catch(ClassNotFoundException ex)
		{
			// THIS MUST MEAN THERE IS A PROGRAMMING ERROR, OR THE
			// USER SELECTED A FILE THAT IS NOT IN THE PROPER FORMAT
			JOptionPane.showMessageDialog(this, "Error Reading File - File in Wrong Format", 
					"Error Reading " + myDatabaseFilePath + " - File in Wrong Format", 
					JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException ex)
		{
			// THIS COULD BE A FEW DIFFERENT PROBLEMS. PERHAPS THIS
			// FILE DOESN'T EXIST
			JOptionPane.showMessageDialog(this, "Error Reading File  - Perhaps File Does Not Exist?", 
					"Error Reading " + myDatabaseFilePath + " - Perhaps File Does Not Exist?", 
					JOptionPane.ERROR_MESSAGE);	
		}
	}

	/**
	 * HitButtonHandler: when the player clicks the hit button, we take a card from the deck and put
	 * it into the player's hand and the ai's hand.
	 * 
	 * @author Eddie O'Hagan
	 *
	 */
	protected class HitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			//give player a card
			myPlayer.AddCardToHand(myDeck.remove(myNumCardsDelt));
			myNumCardsDelt++;
			
			//give house a card
			myAI.AddCardToHand(myDeck.remove(myNumCardsDelt));
			myNumCardsDelt++;
			
			myPlayerHandLabel.setText("Players current Hand: " + myPlayer.displayHand());
			myAIHandLabel.setText("Houses current Hand: " + myAI.displayHand());
			
			myPlayerPanel.add(myPlayerHandLabel);
			myAIPanel.add(myAIHandLabel);
			
			if(myPlayer.getCardTotal() > 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "You bust - House wins 5$");
				
				myAI.setScore(myAI.getScore() + 5);
				myAiScoreLabel.setText("AI Score:" +  + myAI.getScore() + " ");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else if(myAI.getCardTotal() > 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "House bust - Player wins 5$");
				
				myPlayer.setScore(myPlayer.getScore() + 5);
				myPlayerScoreLabel.setText("Player Score:" + myPlayer.getScore() + " ");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else if(myPlayer.getCardTotal() == 21 && myAI.getCardTotal() == 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "Draw - No one wins or loses.");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else if (myPlayer.getCardTotal() == 21 && myAI.getCardTotal() != 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "Player wins 5$");
				
				myPlayer.setScore(myPlayer.getScore() + 5);
				myPlayerScoreLabel.setText("Player Score:" + myPlayer.getScore() + " ");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else if (myPlayer.getCardTotal() != 21 && myAI.getCardTotal() == 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "House wins 5$");
				
				myAI.setScore(myAI.getScore() + 5);
				myAiScoreLabel.setText("AI Score:" +  myAI.getScore() + " ");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
		}
	}

	/**
	 * FoldButtonHandler: If the player clicks the fold button, the game is ended
	 * and no points are awarded to anyone. Deal button is now able to be clicked
	 * again.
	 * 
	 * @author Eddie
	 *
	 */
	protected class FoldButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			JOptionPane.showMessageDialog(myGameGUI, "You Fold");
			
			myPlayer.setHand(new ArrayList<Card>());
			myAI.setHand(new ArrayList<Card>());
			
			myPlayerPanel.add(myPlayerHandLabel);
			myAIPanel.add(myAIHandLabel);
			
			myDealButton.setEnabled(true);
			myFoldButton.setEnabled(false);
			myHitButton.setEnabled(false);
		}
	}

	/**
	 * DealButtonHandler: Starts the game whenever the button is clicked. Each
	 * Player receives 2 cards initlay. We also test for black jack here because
	 * it is possible to get black jack with the initial 2 cards.
	 * 
	 * @author Eddie
	 *
	 */
	protected class DealButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			initDeck();
			shuffleDeck();
			
			//Give player initaly 2 cards
			for(int i = 0; i < 2; i++)
			{
				if(myNumCardsDelt < myDeck.size())
				{
					myPlayer.AddCardToHand(myDeck.remove(myNumCardsDelt));
					myNumCardsDelt++;
				}
			}
			
			//Give AI initaly 2 cards
			for(int i = 0; i < 2; i++)
			{
				if(myNumCardsDelt < myDeck.size())
				{
					myAI.AddCardToHand(myDeck.remove(myNumCardsDelt));
					myNumCardsDelt++;
				}
			}
			
			myPlayerHandLabel.setText("Players current Hand: " + myPlayer.displayHand());
			myAIHandLabel.setText("Houses current Hand: " + myAI.displayHand());
			
			myPlayerPanel.add(myPlayerHandLabel);
			myAIPanel.add(myAIHandLabel);
			
			if(myPlayer.getCardTotal() > 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "You bust - House wins 5$");
				
				myAI.setScore(myAI.getScore() + 5);
				myAiScoreLabel.setText("AI Score:" + myAI.getScore() + " ");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else if(myAI.getCardTotal() > 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "House bust - Player wins 5$");
				
				myPlayer.setScore(myPlayer.getScore() + 5);
				myPlayerScoreLabel.setText("Player Score:" +  myPlayer.getScore() + " ");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else if(myPlayer.getCardTotal() == 21 && myAI.getCardTotal() == 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "Draw - No one wins or loses.");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else if (myPlayer.getCardTotal() == 21 && myAI.getCardTotal() != 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "Player wins 5$");
				
				myPlayer.setScore(myPlayer.getScore() + 5);
				myPlayerScoreLabel.setText("Player Score:" +  myPlayer.getScore() + " ");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else if (myPlayer.getCardTotal() != 21 && myAI.getCardTotal() == 21)
			{
				JOptionPane.showMessageDialog(myGameGUI, "House wins 5$");
				
				myAI.setScore(myAI.getScore() + 5);
				myAiScoreLabel.setText("AI Score:" +  myAI.getScore() + " ");
				
				myPlayer.setHand(new ArrayList<Card>());
				myAI.setHand(new ArrayList<Card>());
				
				myDealButton.setEnabled(true);
				myFoldButton.setEnabled(false);
				myHitButton.setEnabled(false);
			}
			else
			{
				myDealButton.setEnabled(false);
				myHitButton.setEnabled(true);
				myFoldButton.setEnabled(true);
			}
			
			setSize(800,201);
		}
		
		
	}

	public static void main(String[] args)
	{
		GameGUI theGameGUI = GameGUI.getGameGUI();
		theGameGUI.setVisible(true);
	}



}
