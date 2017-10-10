package Stratego;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Player implements Serializable
{
	private GameBoard myGameBoard;
	private int myHighScore;
	private String myUsername;
	private String myPassword;
	private ArrayList<Replay> myReplays;
	
	public Player(GameBoard initGameBoard, int initHighScore, String initUsername, String initPassword)
	{
		myGameBoard = initGameBoard;
		myHighScore = initHighScore;
		myUsername = initUsername;
		myPassword = initPassword;
		myReplays = new ArrayList<Replay>();
	}
	
	public GameBoard getGameBoard()
	{
		return myGameBoard;
	}
	
	public int getHighScore()
	{
		return myHighScore;
	}
	
	public String getUsername()
	{
		return myUsername;
	}
	
	public String getPassword()
	{
		return myPassword;
	}
	
	public ArrayList<Replay> getReplays()
	{
		return myReplays;
	}
	
	public void setGameBoard(GameBoard gameBoard)
	{
		myGameBoard = gameBoard;
	}
	
	public void setHighScore(int score)
	{
		myHighScore = score;
	}
	
	public void setUsername(String username)
	{
		myUsername = username;
	}
	
	public void setPassword(String password)
	{
		myPassword = password;
	}
	
	/**
	 * addReplay: If the user chooses to save a replay, after it is constructed 
	 * it will be passed as a parameter to this function and in turn added to the
	 * list of Replays.
	 * 
	 * @param replay
	 */
	public void addReplay(Replay replay)
	{
		myReplays.add(replay);
	}
	
	/**
	 * hasPieces: Returns true if the user is still in the game ie
	 * user still has pieces to move on the board.
	 * 
	 * @return True if user still has pieces to play, false if not.
	 */
	public boolean hasPieces()
	{
		return false;
	}
	
	/**
	 * hasPlacedAllPieces: Returns true if the user has placed all of their pieces
	 * on the game board. Otherwise will return false.
	 * 
	 * @return True if user has placed all pieces, False otherwise
	 */
	public boolean hasPlacedAllPieces()
	{
		return false;
	}
	
	

}
