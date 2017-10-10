package Stratego;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Replay implements Serializable
{	
	private String myReplayName;
	private boolean myIsRunning;
	private Queue<Move> myMoves;
	private int[][] myOriginalSetup;
	private GameBoardManager myGameBoardManager;
	
	public Replay(String replayName)
	{
		myReplayName = replayName;
		myIsRunning = false;
		myMoves = new LinkedList<Move>();
		myGameBoardManager = new GameBoardManager(10,10);
		myOriginalSetup = new int[10][10];
	}
	
	/**
	 * addMove: Adds a move to the list so when the game is over we have a record
	 * of all the moves made throughout the game
	 * 
	 * @param move
	 */
	public void addMove(Move move)
	{
		myMoves.add(move);
	}
	
	/**
	 * playReplay: Takes all of the moves in the list and animates them sequentially 
	 * so we can animate the last game for the user.
	 */
	public void playReplay()
	{
		GameBoard gameBoard = GameGUI.getGameGUI().getGameBoard();
		StrategoPiece tempPiece = null;
		StrategoPiece nextPiece = null;
		Boolean changeBack = false;
		Move currentMove = null;
		Position prevPos = null;
		Position nextPos = null;
		
		myIsRunning = true;
		
		gameBoard.repaint();
		
		if(!GameGUI.getGameGUI().getPiecesAreShowing())
		{
			GameGUI.getGameGUI().setPiecesAreShowing(true);
			changeBack = true;
		}
		
		gameBoard.repaint();
		
		while(myMoves.size() > 0 && myIsRunning)
		{
			gameBoard.update(gameBoard.getGraphics());
			gameBoard.repaint();
			
			currentMove = myMoves.remove();
			prevPos = currentMove.getPrevPosition();
			nextPos = currentMove.getNextPosition();
			
			tempPiece = gameBoard.getGameBoardManager().removePiece(prevPos.getX(), prevPos.getY());
			nextPiece = gameBoard.getGameBoardManager().getPiece(nextPos.getX(), nextPos.getY());
			
			tempPiece.setX(gameBoard.calculateX(nextPos.getX()));
			tempPiece.setY(gameBoard.calculateY(nextPos.getY()));
			
			gameBoard.repaint();
			pause(1000);
			
			if(nextPiece != null)
			{
				gameBoard.getGameBoardManager().removePiece(nextPos.getX(), nextPos.getY());
				gameBoard.getGameBoardManager().putPiece(tempPiece, nextPos.getX(), nextPos.getY());
			}
			else
			{
				gameBoard.getGameBoardManager().putPiece(tempPiece, nextPos.getX(), nextPos.getY());
			}
			
			gameBoard.repaint();
			pause(1000);
			
		}
		
		if(changeBack)
		{
			GameGUI.getGameGUI().setPiecesAreShowing(false);
		}
		
		myIsRunning = false;
	}
	
	/**
	 * stopReplay: This will stop the replay from animating.
	 */
	public void stopReplay()
	{
		myIsRunning = false;
	}
	
	public String getReplayName()
	{
		return myReplayName;
	}
	
	public void setReplayName(String name)
	{
		myReplayName = name;
	}
	
	public void pause(long miliseconds)
	{
		try 
		{
			Thread.sleep(miliseconds);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * isRunning: Returns true if the replay is currently animating. Otherwise false
	 * 
	 * @return True if animating, false otherwise
	 */
	public boolean isRunning() 
	{
		return myIsRunning;
	}

	public void setIsRunning(boolean isRunning) 
	{
		myIsRunning = isRunning;
	}

	public Queue<Move> getMoves() 
	{
		return myMoves;
	}

	public void setOriginalSetup(GameBoardManager gbm)
	{
		StrategoPiece tempPiece = null;
		int temp = -1;
		
		for(int i = 0; i < myOriginalSetup.length; i++)
		{
			for(int j = 0; j < myOriginalSetup[0].length; j++)
			{
				if(gbm.getPiece(j, i) != null)
				{
					myOriginalSetup[i][j] = gbm.getPiece(j, i).getIdNum();
				}
			}
		}
		
		for(int i = 0; i < myOriginalSetup.length; i++)
		{
			for(int j = 0; j < myOriginalSetup[0].length; j++)
			{
				temp = myOriginalSetup[i][j];

				if(i != 4 && i != 5)
				{
					tempPiece = new StrategoPiece(temp,
							new Position(GameGUI.getGameGUI().getGameBoard().calculateX(j), GameGUI.getGameGUI().getGameBoard().calculateY(i)), 
							true,
							1,
							1);
				}
				else
				{
					tempPiece = null;
				}

				myGameBoardManager.putPiece(tempPiece, j, i);
			}
		}
	}
	
	public GameBoardManager getGameBoardManager()
	{
		return myGameBoardManager;
	}
	
	public String toString()
	{
		return myReplayName;
	}
}
