package Stratego;

import java.io.Serializable;

public class GameBoardManager implements Serializable
{
	private StrategoPiece[][] myPieces;
	private int myNumPieces;
	
	/**
	 * GameBoardManager: This Constructor will provide a means to  construct a game board with a customizable
	 * number of columns and rows. This method will initialize all data structures for use.
	 * 
	 * @param columns
	 * @param rows
	 */
	public GameBoardManager(int columns, int rows)
	{
		myPieces = new StrategoPiece[columns][rows];
		myNumPieces = 0;
		
		reset();
	}

	/**
	 * isFilled: returns true if all 80 game pieces(40 each person) are currently on the board
	 * 
	 * @return true if the board has max number of pieces on it
	 */
	public boolean isFilled()
	{
		// RETURN true IF TEH BOARD IS FULL
		// false OTHERWISE
		return myNumPieces == (myPieces.length * myPieces[0].length);
	}
	
	/**
	 * getPiece: This accessor method is for getting an individual piece out of the grid. It will
	 * examine the (column, row) cell. If it is empty it will return null, else it will return the
	 * piece found there.
	 * 
	 * @param column
	 * @param row
	 * @return StrategoPiece
	 */
	public StrategoPiece getPiece(int column, int row)
	{
		// IF (column, row) IS OUT OF BOUNDS
		// RETURN null SINCE THERE WOULD BE NO PIECE
		if ((column < 0) ||	(row < 0) || (column >= myPieces.length) ||	(row >= myPieces[0].length))
		{
			return null;
		}
		else
		{
			// RETURN THE VALID PIECE
			return myPieces[column][row];
		}
	}
	
	/**
	 * isOccupied: Returns true if the given col,row position has a StrategoPiece in it.
	 * 
	 * @param column
	 * @param row
	 * @return boolean
	 */
	public boolean isOccupied(int column, int row)
	{
		if(column >= 0 && row >= 0 && column < myPieces.length && row < myPieces[0].length)
		{
			return myPieces[column][row] != null;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * This mutator method will put the piece argument at location (column, row) only if it is
	 * currently not occupied and the game has not started, incrementing the counter. 
	 * If a piece is already at that location, it will place nothing. 
	 * This method returns a boolean representing true for success (it could place the piece) or false
	 * for failure (it could not).
	 * 
	 * @param StrategoPiece
	 * @param column
	 * @param row
	 * @return boolean
	 */
	public boolean putPiece(StrategoPiece piece, int column, int row)
	{
		// IF NO PIECE IS THERE
		if (myPieces[column][row] == null)
		{
			// PLACE THIS ONE
			myPieces[column][row] = piece;

			// INC THE COUNTER
			myNumPieces++;

			// SUCCESS
			return true;
		}
		else // A PIECE IS ALREADY THERE, WE CAN'T PLACE THIS ONE
		{
			System.out.println("PutPiece(): piece was not added sucessfuly" + piece.getPosition().toString());
			return false;
		}
		
		
	}
	
	/**
	 * removePiece: This method removes the piece found at (column, row) from the grid and returns, 
	 * decrementing the counter. If no piece is found at that location, null is returned.
	 * 
	 * @param column
	 * @param row
	 * @return StrategoPeice
	 */
	public StrategoPiece removePiece(int column, int row)
	{
		// GET THE PIECE AT (column, row)
		StrategoPiece piece = myPieces[column][row];

		// REMOVE IT IF IT'S A VALID PIECE
		if (piece != null)
		{
			// DEC THE COUNTER
			myNumPieces--;

			// REMOVE IT FROM THE GRID
			myPieces[column][row] = null;
		}
		else
		{
			System.out.println("RemovePiece():Attempt to remove null piece" + "col:" + column + "row:" + row);
			return null;
		}
		// AND RETURN IT, THIS MAY BE null
		// IF THERE WAS NO PIECE AT (column, row)
		return piece;
	}
	
	/**
	 * reset: This method empties the grid, setting all contents to zero and resetting the counter.
	 */
	public void reset()
	{
		// RESET THE COUNTER
		myNumPieces = 0;

		// AND null OUT ALL THE PIECES IN THE GRID
		for (int i = 0; i < myPieces.length; i++)
		{
			for (int j = 0; j < myPieces[0].length; j++)
			{
				myPieces[i][j] = null;
			}
		}
	}
	
	public String printBoard()
	{
		String temp = "";
		
		for (int i = 0; i < myPieces.length; i++)
		{
			for (int j = 0; j < myPieces[0].length; j++)
			{
				if(myPieces[j][i] != null)
				{
					temp += "Piece" + "\t";
				}
				else
				{
					temp += "Null" + "\t";
				}
			}
			
			temp += "\n";
		}
		
		return temp;
	}
}
