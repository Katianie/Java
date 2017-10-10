package Stratego;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;


public class AI 
{
	private String myDifficulty;
	private ArrayList<StrategoPiece> myPiecesDiscoverd;
	private GameGUI myGameGUI;
	private GameBoard myGameBoard;
	private ArrayList<Move> myAvailablePositions;

	public AI(GameGUI initGameGUI, String initDifficulty)
	{
		myGameGUI = initGameGUI;
		myGameBoard = myGameGUI.getGameBoard();
		myDifficulty = initDifficulty;
		myPiecesDiscoverd = new ArrayList<StrategoPiece>();
		myAvailablePositions = new ArrayList<Move>(); //*change from position to move so we can keep track of where we were and where we could go.
	}

	public String getDifficulty()
	{
		return myDifficulty;
	}

	public void setDifficulty(String diff)
	{
		myDifficulty = diff;
	}

	public ArrayList<StrategoPiece> getPiecesDiscoverd()
	{
		return myPiecesDiscoverd;
	}

	public void AddDiscoverdPiece(StrategoPiece aPiece)
	{
		myPiecesDiscoverd.add(aPiece);
	}

	public StrategoPiece RemoveDiscoverdPiece(StrategoPiece aPiece)
	{
		for(int i = 0; i < myPiecesDiscoverd.size(); i++)
		{
			if(myPiecesDiscoverd.get(i).equals(aPiece))
			{
				return myPiecesDiscoverd.remove(i);
			}
		}

		return null;
	}

	public boolean ContainsDiscoverdPiece(StrategoPiece aPiece)
	{
		for(int i = 0; i < myPiecesDiscoverd.size(); i++)
		{
			if(myPiecesDiscoverd.get(i).equals(aPiece))
			{
				return true;
			}
		}

		return false;
	}


	/**
	 * move: This is where all the AI logic will take place. Depending on the difficulty,
	 * the AI will act differently. If the AI is set to Hard then it will use the list
	 * for remembering the pieces it has seen. Then it will determine what piece should be moved
	 * where.
	 */
	public void move()
	{
		ArrayList<Move> tempList = new ArrayList<Move>();

		Random rand = new Random();
		Move theMove = null;

		StrategoPiece thePiece = null;
		StrategoPiece nextPiece = null;
		StrategoPiece winnerPiece = null;

		Image thePieceImage;
		Image nextPieceImage;

		String str = "";
		int randNum = -1;
		boolean pieceHasMoved = false;

		if(!myGameBoard.getPlayersTurn())//if its not the players turn
		{
			for(int row = 0; row < 10; row++)
			{
				for(int col = 0; col < 10; col++)
				{
					//check to see if there is a piece in the current location and see if it belongs to ai and make sure its not a bomb or the flag
					if(myGameBoard.getGameBoardManager().getPiece(col, row) != null && myGameBoard.getGameBoardManager().getPiece(col, row).getOwner() == 1 && myGameBoard.getGameBoardManager().getPiece(col, row).getIdNum() != 10 && myGameBoard.getGameBoardManager().getPiece(col, row).getIdNum() != 11)
					{
						//we want to make sure that when we check up down left right
						//we dont go out of bounds.
						if((col > 0 && col < 9) && (row > 0 && row < 9))
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col - 1, row) || myGameBoard.getGameBoardManager().getPiece(col - 1, row).getOwner() == 0)//left of piece it is also valid if the players piece is in that location so we can attack
							{
								myAvailablePositions.add(new Move(col, row, col - 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row - 1) || myGameBoard.getGameBoardManager().getPiece(col, row - 1).getOwner() == 0)//north of piece it is also valid if the players piece is in that location so we can attack
							{
								myAvailablePositions.add(new Move(col, row, col, row - 1));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col + 1, row) || myGameBoard.getGameBoardManager().getPiece(col + 1, row).getOwner() == 0)//right of piece it is also valid if the players piece is in that location so we can attack
							{
								myAvailablePositions.add(new Move(col, row, col + 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row + 1) || myGameBoard.getGameBoardManager().getPiece(col, row + 1).getOwner() == 0)//south of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row + 1));
							}
						}
						else if((col == 0) && (row > 0 && row < 9))//check for the pieces that are on left wall
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row - 1) || myGameBoard.getGameBoardManager().getPiece(col, row - 1).getOwner() == 0)//north of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row - 1));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col + 1, row) || myGameBoard.getGameBoardManager().getPiece(col + 1, row).getOwner() == 0)//right of piece
							{
								myAvailablePositions.add(new Move(col, row, col + 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row + 1) || myGameBoard.getGameBoardManager().getPiece(col, row + 1).getOwner() == 0)//south of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row + 1));
							}
						}
						else if((col == 9) && (row > 0 && row < 9))//check for the pieces that are on right wall
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col - 1, row) || myGameBoard.getGameBoardManager().getPiece(col - 1, row).getOwner() == 0)//left of piece
							{
								myAvailablePositions.add(new Move(col, row, col - 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row - 1) || myGameBoard.getGameBoardManager().getPiece(col, row - 1).getOwner() == 0)//north of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row - 1));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row + 1) || myGameBoard.getGameBoardManager().getPiece(col, row + 1).getOwner() == 0)//south of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row + 1));
							}
						}
						else if((col > 0 && col < 9) && (row == 0))//check north wall
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col - 1, row) || myGameBoard.getGameBoardManager().getPiece(col - 1, row).getOwner() == 0)//left of piece
							{
								myAvailablePositions.add(new Move(col, row, col - 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col + 1, row) || myGameBoard.getGameBoardManager().getPiece(col + 1, row).getOwner() == 0)//right of piece
							{
								myAvailablePositions.add(new Move(col, row, col + 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row + 1) || myGameBoard.getGameBoardManager().getPiece(col, row + 1).getOwner() == 0)//south of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row + 1));
							}
						}
						else if((col > 0 && col < 9) && (row == 9))//check south wall
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col - 1, row) || myGameBoard.getGameBoardManager().getPiece(col - 1, row).getOwner() == 0)//left of piece
							{
								myAvailablePositions.add(new Move(col, row, col - 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row - 1) || myGameBoard.getGameBoardManager().getPiece(col, row - 1).getOwner() == 0)//north of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row - 1));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col + 1, row) || myGameBoard.getGameBoardManager().getPiece(col + 1, row).getOwner() == 0)//right of piece
							{
								myAvailablePositions.add(new Move(col, row, col + 1, row));
							}
						}
						else if(col == 0 && row ==0)//check top left corner
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col + 1, row) || myGameBoard.getGameBoardManager().getPiece(col + 1, row).getOwner() == 0)//right of piece
							{
								myAvailablePositions.add(new Move(col, row, col + 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row + 1) || myGameBoard.getGameBoardManager().getPiece(col, row + 1).getOwner() == 0)//south of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row + 1));
							}
						}
						else if(col == 9 && row == 0)//check top right corner
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col - 1, row)|| myGameBoard.getGameBoardManager().getPiece(col - 1, row).getOwner() == 0)//left of piece
							{
								myAvailablePositions.add(new Move(col, row, col - 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row + 1) || myGameBoard.getGameBoardManager().getPiece(col, row + 1).getOwner() == 0)//south of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row + 1));
							}
						}
						else if(col == 0 && row == 9)//check bottom left corner
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row - 1) || myGameBoard.getGameBoardManager().getPiece(col, row - 1).getOwner() == 0)//north of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row - 1));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col + 1, row) || myGameBoard.getGameBoardManager().getPiece(col + 1, row).getOwner() == 0)//right of piece
							{
								myAvailablePositions.add(new Move(col, row, col + 1, row));
							}
						}
						else if(col == 9 && row == 9)//check bottom right corner
						{
							if(!myGameBoard.getGameBoardManager().isOccupied(col - 1, row) || myGameBoard.getGameBoardManager().getPiece(col - 1, row).getOwner() == 0)//left of piece
							{
								myAvailablePositions.add(new Move(col, row, col - 1, row));
							}
							if(!myGameBoard.getGameBoardManager().isOccupied(col, row - 1) || myGameBoard.getGameBoardManager().getPiece(col, row - 1).getOwner() == 0)//north of piece
							{
								myAvailablePositions.add(new Move(col, row, col, row - 1));
							}

						}

					}
				}
			}
		}

		//Remove duplicates of pieces that might have been discovered more than once.
		for(int i = 0; i < myAvailablePositions.size(); i++)
		{
			Move temp = myAvailablePositions.get(i);

			if(!tempList.contains(temp))
			{
				tempList.add(temp);
			}


		}

		myAvailablePositions = tempList; //overwrite old array		

		if(myAvailablePositions.size() > 0)
		{
			randNum = rand.nextInt(myAvailablePositions.size());//generate a random num from 0 to size of moveable pieces

			theMove = myAvailablePositions.remove(randNum);//remove that position and store it

			thePiece = myGameBoard.getGameBoardManager().getPiece(theMove.getPrevPosition().getX(), theMove.getPrevPosition().getY());//get the piece at that location
			nextPiece = myGameBoard.getGameBoardManager().getPiece(theMove.getNextPosition().getX(), theMove.getNextPosition().getY());
			
			if(myDifficulty.equals("Hard") &&  myPiecesDiscoverd.size() > 0)
			{
				StrategoPiece tempPiece;

				for(int i = 0; i < myPiecesDiscoverd.size() && !pieceHasMoved; i++)
				{
					tempPiece = myPiecesDiscoverd.get(i);
					
					if(nextPiece != null)
					{
						if(tempPiece.equals(nextPiece))
						{	
							thePieceImage = myGameGUI.getGameBoard().getPieceImages().get(thePiece.getIdNum());
							nextPieceImage = myGameGUI.getGameBoard().getPieceImages().get(tempPiece.getIdNum());

							winnerPiece = myGameBoard.Compare(nextPiece, thePiece);

							if(winnerPiece.getIdNum() == 99)//draw
							{
								VSDialog diag = new VSDialog(myGameGUI, thePieceImage, nextPieceImage, "Tie");

								myGameBoard.getGameBoardManager().removePiece(theMove.getNextPosition().getX(), theMove.getNextPosition().getY());
								myGameBoard.getGameBoardManager().removePiece(theMove.getPrevPosition().getX(), theMove.getPrevPosition().getY());

								myGameBoard.repaint();

								myPiecesDiscoverd.remove(i);
								pieceHasMoved = true;
							}
							else if(thePiece.equals(winnerPiece))
							{
								VSDialog diag = new VSDialog(myGameGUI, thePieceImage, nextPieceImage, winnerPiece.getPieceName(winnerPiece.getIdNum()) + "(" + (winnerPiece.getIdNum() + 1) + ")");
								
								myGameBoard.getGameBoardManager().removePiece(theMove.getNextPosition().getX(), theMove.getNextPosition().getY());
								myGameBoard.getGameBoardManager().removePiece(theMove.getPrevPosition().getX(), theMove.getPrevPosition().getY());

								thePiece.setX(myGameBoard.calculateX(theMove.getNextPosition().getX()));
								thePiece.setY(myGameBoard.calculateY(theMove.getNextPosition().getY()));

								myGameBoard.getGameBoardManager().putPiece(thePiece, theMove.getNextPosition().getX(),theMove.getNextPosition().getY());//move the piece to this location

								myPiecesDiscoverd.remove(i);
								pieceHasMoved = true;
							}

						}
					}

					myGameBoard.repaint();
				}	
			}

			while(nextPiece != null && ContainsDiscoverdPiece(nextPiece))
			{
				randNum = rand.nextInt(myAvailablePositions.size());//generate a random num from 0 to size of moveable pieces

				theMove = myAvailablePositions.remove(randNum);//remove that position and store it

				thePiece = myGameBoard.getGameBoardManager().getPiece(theMove.getPrevPosition().getX(), theMove.getPrevPosition().getY());//get the piece at that location
				nextPiece = myGameBoard.getGameBoardManager().getPiece(theMove.getNextPosition().getX(), theMove.getNextPosition().getY());

			}

			//attack phase
			if(!pieceHasMoved && nextPiece != null && nextPiece.getOwner() == 0)//check to see if the piece at that location is the player piece
			{
				winnerPiece = myGameBoard.Compare(nextPiece, thePiece);

				thePieceImage = myGameGUI.getGameBoard().getPieceImages().get(thePiece.getIdNum());
				nextPieceImage = myGameGUI.getGameBoard().getPieceImages().get(nextPiece.getIdNum());

				if(winnerPiece.getIdNum() != 99)
				{
					VSDialog diag = new VSDialog(myGameGUI, thePieceImage, nextPieceImage, winnerPiece.getPieceName(winnerPiece.getIdNum()) + "(" + (winnerPiece.getIdNum() + 1) + ")");
				}
				else
				{
					VSDialog diag = new VSDialog(myGameGUI, thePieceImage, nextPieceImage, "Tie");
				}


				myGameBoard.repaint();

				if(winnerPiece.getIdNum() == 99)//draw
				{
					myGameBoard.getGameBoardManager().removePiece(theMove.getNextPosition().getX(), theMove.getNextPosition().getY());
					myGameBoard.getGameBoardManager().removePiece(theMove.getPrevPosition().getX(), theMove.getPrevPosition().getY());

					pieceHasMoved = true;
				}
				else if(thePiece.equals(winnerPiece))//if thePiece(aiPiece) is the winner
				{
					myGameBoard.getGameBoardManager().removePiece(theMove.getNextPosition().getX(), theMove.getNextPosition().getY());
					myGameBoard.getGameBoardManager().removePiece(theMove.getPrevPosition().getX(), theMove.getPrevPosition().getY());

					thePiece.setX(myGameBoard.calculateX(theMove.getNextPosition().getX()));
					thePiece.setY(myGameBoard.calculateY(theMove.getNextPosition().getY()));

					myGameBoard.getGameBoardManager().putPiece(thePiece, myGameBoard.calculateColumn(theMove.getNextPosition().getX()), myGameBoard.calculateRow(theMove.getNextPosition().getY()));//move the piece to this location

					pieceHasMoved = true;
				}
				else if(nextPiece.equals(winnerPiece))//if nextPiece(playerPiece) is winner
				{	
					myPiecesDiscoverd.add(nextPiece);
					myGameBoard.getGameBoardManager().removePiece(theMove.getPrevPosition().getX(), theMove.getPrevPosition().getY());

					nextPiece.setX(myGameBoard.calculateX(theMove.getNextPosition().getX()));
					nextPiece.setY(myGameBoard.calculateY(theMove.getNextPosition().getY()));
					
					myGameBoard.getGameBoardManager().putPiece(nextPiece, myGameBoard.calculateColumn(theMove.getNextPosition().getX()), myGameBoard.calculateRow(theMove.getNextPosition().getY()));

					pieceHasMoved = true;
				}
				else
				{
					myGameBoard.resetPieceBeingDragged();
				}

				myGameBoard.repaint();
			}
			else if(!pieceHasMoved)
			{
				myGameBoard.getGameBoardManager().removePiece(theMove.getPrevPosition().getX(), theMove.getPrevPosition().getY());//move the piece to this location

				thePiece.setX(myGameBoard.calculateX(theMove.getNextPosition().getX()));
				thePiece.setY(myGameBoard.calculateY(theMove.getNextPosition().getY()));

				myGameBoard.getGameBoardManager().putPiece(thePiece, theMove.getNextPosition().getX(), theMove.getNextPosition().getY());//move the piece to this location
			}

			myGameBoard.getCurrentReplay().addMove(theMove);

			theMove = new Move();
			
			myGameBoard.resetPieceBeingDragged();

			myGameBoard.repaint(); //redraw the bord to show the change

			myGameBoard.setPlayersTurn(true); //its now the players turn

			myAvailablePositions.removeAll(myAvailablePositions);//reset the list because shit has changed
		}
	}
}
