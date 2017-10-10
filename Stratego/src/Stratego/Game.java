package Stratego;

public class Game implements StrategoGame 
{
	private GameGUI myGameGUI;
	private boolean myGameIsBeingPlayed;
	private Replay myCurrentReplay;
	
	
	/**
	 * Game(constructor): Initializes out GameGUI, whether or not it is being played, and a way
	 * for us to keep track of the moves that have been going on during the game.
	 */
	public Game()
	{
		myGameGUI = GameGUI.getGameGUI();
	}
	
	/**
	 * gameOver: Note that this method must be defined because this class implements GridGame. 
	 * It allows the GameBoard class to call this method without having to be changed
	 * if we were to use it with another game application.
	 *
	 * This particular implementation will determine if either player has found the flag or
	 * if one of the user's is all out of pieces. If either one of these occur then the game
	 * of Stratego is considered over
	 *
	 * @see Stratego.StrategoGame#gameOver()
	 */
	public void gameOver()
	{
		
	}
	
	public static void main(String[] args)
	{
		Game theGame = new Game();
	}

}
