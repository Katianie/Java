package Stratego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements StrategoGame, Serializable
{
	private final int[] myPiecesAvailable = {1, 1, 2, 3, 4, 4, 4, 5, 8, 1, 6, 1};
	private int[] myPieces = {1, 1, 2, 3, 4, 4, 4, 5, 8, 1, 6, 1};
	private int[] myAiPieces = {1, 1, 2, 3, 4, 4, 4, 5, 8, 1, 6, 1};
	private Image myBackgroundImage;
	private Vector<Image> myPieceImages;
	private Replay myCurrentReplay;
	private Move myCurrentMove;
	private int myActiveImageID;

	//Used so later on we dont have to change the underling classes to change the type of game
	private GameGUI myGameGUI;

	//Data for GameBoard
	private GameBoardManager myGameBoardManager;
	private boolean myEnemyPiecesShown;

	// DIMENSIONS OF THE GAME BOARD GRID
	private int myColumns;
	private int myRows;
	private int myGridBorderSize;

	// THE MOUSE HANDLER RESPONDS TO ALL MOUSE EVENTS
	private MouseHandler myMouseEventHandler;
	private MouseMotionHandler myMouseMotionHandler;

	//Piece variables for dragging motion
	private StrategoPiece myPieceBeingDragged;
	private int myFormerColumnOfPieceBeingDragged;
	private int myFormerRowOfPieceBeingDragged;

	private boolean myGameHasStarted;
	private boolean myPlayersTurn;
	private VSDialog myVSDialog;

	private int myOriginalCol;
	private int myOriginalRow;

	public GameBoard(GameGUI initGameGUI, int initColumns, int initRows, Image initBackgroundImage, Vector<Image> initPieceImages) throws IllegalGridDimensionException
	{
		int gridCellWidth;
		int gridCellHeight;
		// FIRST LET'S MAKE SURE THE DATA PROVIDED
		// MAKES SENSE FOR RENDERING OUR GRID

		// FIRST, WE CANNOT HAVE NEGATIVE SIZED COLUMNS OR ROWS
		if (initColumns < 0)
		{
			throw new IllegalGridDimensionException("ERROR - Illegal Number of Columns: " + initColumns);
		}
		else if (initRows < 0)
		{
			throw new IllegalGridDimensionException("ERROR - Illegal Number of Rows: " + initRows);
		}

		// NOW LET'S MAKE SURE THE GRID CELL SIZE
		// MATCHES THE SIZE OF THE PIECE IMAGES
		gridCellWidth = initBackgroundImage.getWidth(null)/initColumns;
		gridCellHeight = initBackgroundImage.getHeight(null)/initRows;

		if (gridCellWidth != initPieceImages.get(0).getWidth(null))
		{
			throw new IllegalGridDimensionException("ERROR - Grid Cell Size does not match Piece image size");
		}
		else if (gridCellHeight != initPieceImages.get(0).getHeight(null))
		{
			throw new IllegalGridDimensionException("ERROR - Grid Cell Size does not match Piece image size");
		}

		// IF WE'VE MADE IT THIS FAR THEN EVERYTHING'S FINE
		// SO INITIALIZE ALL OF OUR INSTANCE VARIABLES
		// USING THE CUSTOM ARGUMENTS
		myGameGUI = initGameGUI;
		myColumns = initColumns;
		myRows = initRows;
		myBackgroundImage = initBackgroundImage;
		myPieceImages = initPieceImages;

		// NOW CONSTRUCT OUR GAME BOARD USING THE
		// APPROPRIATE DIMENSIONS
		myGameBoardManager = new GameBoardManager(myColumns, myRows);
		myGridBorderSize = 2;

		myCurrentReplay = new Replay("New Replay");
		myCurrentMove = new Move();

		// CONSTRUCT AND REGISTER OUR MOUSE EVENT HANDLERS
		myMouseEventHandler = new MouseHandler();
		myMouseMotionHandler = new MouseMotionHandler();
		addMouseListener(myMouseEventHandler);
		addMouseMotionListener(myMouseMotionHandler);

		// AND INITIAZLIE OUR DRAGGED PIECE AS NOT YET IN USE
		myPieceBeingDragged = null;
		myOriginalCol = -1;
		myOriginalRow = -1;
		myFormerColumnOfPieceBeingDragged = -1;
		myFormerRowOfPieceBeingDragged = -1;

		myGameHasStarted = false;
		myPlayersTurn = true;
		myEnemyPiecesShown = false;

		this.setPreferredSize(new Dimension(640, 640));
	}

	/**
	 * Compare: Takes two StrategoPiece's as parameters and compares the two to see which is stronger.
	 * The stronger piece will be returned.
	 * 
	 * @param StrategoPiece
	 * @param StrategoPiece
	 * @return stronger piece
	 */
	public StrategoPiece Compare(StrategoPiece playerPiece, StrategoPiece aiPiece)
	{
		if(playerPiece.getIdNum() == 11)
		{
			myGameGUI.setWinner("Ai");
			myGameGUI.setGameIsOver(true);
			myGameGUI.gameOver();
			return aiPiece;
		}
		else if(aiPiece.getIdNum() == 11)
		{
			myGameGUI.setWinner(myGameGUI.getPlayer().getUsername());
			myGameGUI.setGameIsOver(true);
			myGameGUI.gameOver();
			return playerPiece;
		}

		if(playerPiece.getIdNum() == aiPiece.getIdNum())
		{
			return new StrategoPiece(99, null, false, 0, -1);//indicate that there was a draw
		}

		if((playerPiece.getIdNum() >= 0 && aiPiece.getIdNum() >= 0) && (playerPiece.getIdNum() <= 7 && aiPiece.getIdNum() <= 7))
		{
			if(playerPiece.getIdNum() < aiPiece.getIdNum())
			{
				return playerPiece;
			}
			else
			{
				return aiPiece;
			}
		}

		if(playerPiece.getIdNum() == 8)
		{
			if(aiPiece.getIdNum() == 10)
			{
				return playerPiece;
			}
			else
			{
				return aiPiece;
			}
		}
		else if(aiPiece.getIdNum() == 8)
		{
			if(playerPiece.getIdNum() == 10)
			{
				return aiPiece;
			}
			else
			{
				return playerPiece;
			}
		}

		if(playerPiece.getIdNum() == 9)
		{
			if(aiPiece.getIdNum() == 0)
			{
				return playerPiece;
			}
			else
			{
				return aiPiece;
			}
		}
		else if(aiPiece.getIdNum() == 9)
		{
			if(playerPiece.getIdNum() == 0)
			{
				return aiPiece;
			}
			else
			{
				return playerPiece;
			}
		}

		if(playerPiece.getIdNum() == 10 && aiPiece.getIdNum() != 8)
		{
			return playerPiece;
		}
		else if(aiPiece.getIdNum() == 10 && playerPiece.getIdNum() != 8)
		{
			return aiPiece;
		}

		return null;

	}

	/**
	 * calculateColumn: This method determines and returns what column in the grid the xPixel argument
	 * is in.
	 * 
	 * @param xPixel
	 * @return column
	 */
	public int calculateColumn(int xPixel)
	{
		int w = getWidth();
		int cellWidth = w / myColumns;
		int column = xPixel / cellWidth;

		return column;
	}

	/**
	 * calculateRow: This method determines and returns what row in the grid the yPixel argument
	 * is in.
	 * 
	 * @param yPixel
	 * @return row
	 */
	public int calculateRow(int yPixel)
	{
		int h = getHeight();
		int cellHeight = h / myRows;
		int row = yPixel / cellHeight;

		return row;
	}

	/**
	 * calculateX: This method determines and returns what the x coordinate of the first pixel is
	 * for the column argument.
	 * 
	 * @param column
	 * @return x
	 */
	public int calculateX(int column)
	{
		int w = getWidth();
		int cellWidth = w / myColumns;
		int x = column * cellWidth;

		return x;
	}

	/**
	 * calculateY: This method determines and returns what the y coordinate of the first pixel is
	 * for the row argument.
	 * 
	 * @param row
	 * @return y
	 */
	public int calculateY(int row)
	{
		int h = getHeight();
		int cellHeight = h / myRows;
		int y = row * cellHeight;

		return y;
	}

	//gets called when you hit new game
	public void resetPieces()
	{
		for(int i = 0; i < myPiecesAvailable.length; i++)
		{
			myPieces[i] = myPiecesAvailable[i];
			myAiPieces[i] = myPiecesAvailable[i];
		}

		myGameBoardManager.reset();
	}

	/**
	 * anyPiecesLeft: Returns true if the player still has pieces to place
	 * 
	 * @return true or false
	 */
	public boolean anyPiecesLeft()
	{
		for(int i = 0; i < myPieces.length; i++)
		{
			if(myPieces[i] > 0)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * anyAiPiecesLeft: Returns true if the AI still has pieces to place
	 * 
	 * @return true or false
	 */
	public boolean anyAiPiecesLeft()
	{
		for(int i = 0; i < myAiPieces.length; i++)
		{
			if(myAiPieces[i] > 0)
			{
				return true;
			}
		}

		return false;
	}

	/** 
	 * piecesLeft: returns true if for a given catagorey(Marshal,bomb,etc) still has
	 * pieces that could be placed
	 * 
	 * @param i
	 * @return true of false
	 */
	public boolean piecesLeft(int i)
	{
		if(myPieces[i] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * aiPiecesLeft: returns true if for a given catagorey still has pieces
	 * that could be placed for the AI
	 * 
	 * @param i
	 * @return true or false
	 */
	public boolean aiPiecesLeft(int i)
	{
		if(myAiPieces[i] > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void decrementPieces(int num)
	{
		myPieces[num]--;
	}

	public void incrementPieces(int num)
	{
		myPieces[num]++;
	}

	public void decrementAiPieces(int num)
	{
		myAiPieces[num]--;
	}

	public void incrementAiPieces(int num)
	{
		myAiPieces[num]++;
	}

	public int getOriginalCol()
	{
		return myOriginalCol;
	}

	public int getOriginalRow()
	{
		return myOriginalRow;
	}

	public boolean getPlayersTurn()
	{
		return myPlayersTurn;
	}

	public void setPlayersTurn(boolean turn)
	{
		myPlayersTurn = turn;
	}

	public Replay getCurrentReplay()
	{
		return myCurrentReplay;
	}

	public void setCurrentReplay(Replay aReplay)
	{
		myCurrentReplay = aReplay;
	}


	/**
	 * paintComponent: This method does all the rendering of our board including the background map,
	 * the grid lines, and the pieces.
	 * 
	 * @param Graphics
	 */
	public void paintComponent(Graphics g)
	{
		StrategoPiece piece;
		Image imageToUse;
		int iW;
		int iH;
		int x;
		int y;

		// CLEAR THE BACKGROUND, NOT NECESSARY IN THIS
		// CASE BECAUSE WE'RE GOING TO DRAW OUR BACKGROUND
		// IMAGE OVER IT ANYWAY, BUT A GOOD HABIT 
		// TO GET INTO
		super.paintComponent(g);

		// RENDER THE BACKGROUND IMAGE
		g.drawImage(myBackgroundImage, 0, 0, null);
		g.setColor(Color.RED);

		//Draw the grid borders
		for(int col = 0; col < myColumns; col++)
		{
			x = calculateX(col);

			g.fillRect(x, 0, myGridBorderSize, getHeight());
		}

		for(int row = 0; row < myRows; row++)
		{
			y = calculateY(row);

			g.fillRect(0, y, getWidth(), myGridBorderSize);
		}

		// RENDER ALL THE PIECES ON THE BOARD
		// INSIDE THEIR PROPER GRID CELL
		for (int i = 0; i < myColumns; i++)
		{
			for (int j = 0; j < myRows; j++)
			{
				piece = myGameBoardManager.getPiece(i, j);

				// ONLY RENDER THE PIECE IF IT'S THERE
				if (piece != null)
				{
					if(!myEnemyPiecesShown)
					{
						if(piece.isShown())
						{
							imageToUse = myPieceImages.get(piece.getIdNum());
						}
						else
						{
							imageToUse = myPieceImages.get(12);
						}
					}
					else
					{
						imageToUse = myPieceImages.get(piece.getIdNum());
					}

					g.drawImage(imageToUse, piece.getX(), piece.getY(), null);
				}
			}
		}

		// RENDER THE PIECE BEING DRAGGED, IF IT EXISTS
		if (myPieceBeingDragged != null)
		{
			// GET THE IMAGE INFO
			imageToUse = myPieceImages.get(myPieceBeingDragged.getIdNum());
			iW = imageToUse.getWidth(null);
			iH = imageToUse.getHeight(null);

			// AND RENDER IT WHERE IT IS AS IT IS BEING DRAGGED
			g.drawImage(imageToUse, myPieceBeingDragged.getX()-(iW/2), myPieceBeingDragged.getY()-(iH/2), null);
		}

	}

	public Image getImage(int idNum)
	{
		return myPieceImages.get(idNum);
	}

	/**
	 * gamesOver: This method is to be called when the ending game state has been reached. It will reflect
	 * this call back to the main application for a custom response.
	 */
	public void gameOver()
	{
		myGameGUI.gameOver();
	}

	/**
	 * putBackDraggedPiece: This method would be called when the user tries to place a dragged piece 
	 * into an occupied cell or to an illegal location, like outside the panel.
	 */
	public void putBackDraggedPiece() 
	{
		// IS THERE EVEN A PIECE BEING DRAGGED RIGHT NOW?
		// WE'RE JUST PROTECTING AGAINST ERROR HERE
		if (myPieceBeingDragged != null)
		{
			// PUT IT BACK WHERE IT BELONGS
			myGameBoardManager.putPiece(myPieceBeingDragged, myFormerColumnOfPieceBeingDragged, myFormerRowOfPieceBeingDragged);

			// REMEMBER TO CORRECT IT'S X,Y COORDINATES
			// USING THE CELL LOCATION
			myPieceBeingDragged.setX(calculateX(myFormerColumnOfPieceBeingDragged));
			myPieceBeingDragged.setY(calculateY(myFormerRowOfPieceBeingDragged));

			// NOW THERE IS NO PIECE BEING DRAGGED
			resetPieceBeingDragged();

			// REDRAW THE PANEL WITH THE PIECE BACK
			// WHERE IT BELONGS
			repaint();
		}
	}

	/**
	 * align the dragged piece after its 
	 */
	public void alignDraggedPiece(int x, int y)
	{
		// IS THERE EVEN A PIECE BEING DRAGGED RIGHT NOW?
		// WE'RE JUST PROTECTING AGAINST ERROR HERE
		if (myPieceBeingDragged != null)
		{	
			myGameBoardManager.putPiece(myPieceBeingDragged, calculateColumn(x), calculateRow(y));

			myPieceBeingDragged.setX(calculateX(calculateColumn(x)));
			myPieceBeingDragged.setY(calculateY(calculateRow(y)));

			// NOW THERE IS NO PIECE BEING DRAGGED
			resetPieceBeingDragged();

			// REDRAW THE PANEL WITH THE PIECE BACK
			// WHERE IT BELONGS
			repaint();
		}
	}

	/**
	 * resetPieceBeingDragged: This method resets the piece that is being dragged to nothing. This method should be 
	 * called when changing state to no piece being dragged.
	 */
	public void resetPieceBeingDragged()
	{
		// RESET IT TO NOTHING BEING DRAGGED
		myPieceBeingDragged = null;
		myFormerColumnOfPieceBeingDragged = -1;
		myFormerRowOfPieceBeingDragged = -1;

	}

	/**
	 * updateDraggedPiece: This method allows us to move the location of the piece being dragged for rendering purposes.
	 * Note that it will not permit us to drag the piece off the panel.
	 * 
	 * @param x
	 * @param y
	 */
	public void updateDraggedPiece(int x, int y)
	{
		// MAKE SURE IT'S ON THE PANEL
		if ((x >= 0) && (y >= 0) && (x < myBackgroundImage.getWidth(null)) && (y < myBackgroundImage.getHeight(null)))
		{
			myPieceBeingDragged.setX(x);
			myPieceBeingDragged.setY(y);
		}
		else
		{
			putBackDraggedPiece();
		}
	}

	public Image getBackgroundImage() 
	{
		return myBackgroundImage;
	}

	public void setBackgroundImage(Image img) 
	{
		myBackgroundImage = img;
	}

	public Vector<Image> getPieceImages() 
	{
		return myPieceImages;
	}

	public void setPieceImages(Vector<Image> pieceImages) 
	{
		myPieceImages = pieceImages;
	}

	public int getActiveImageID() 
	{
		return myActiveImageID;
	}

	public void setActiveImageID(int activeImageID) 
	{
		myActiveImageID = activeImageID;
	}

	public GameBoardManager getGameBoardManager() 
	{
		return myGameBoardManager;
	}

	public void setGameBoardManager(GameBoardManager gameBoardManager) 
	{
		myGameBoardManager = gameBoardManager;
	}

	public int getColumns() 
	{
		return myColumns;
	}

	public void setColumns(int columns) 
	{
		myColumns = columns;
	}

	public int getRows() 
	{
		return myRows;
	}

	public void setRows(int rows) 
	{
		myRows = rows;
	}

	public int getGridBorderSize() 
	{
		return myGridBorderSize;
	}

	public boolean getEnemyPiecesShown()
	{
		return myEnemyPiecesShown;
	}

	public void setEnemyPiecesShown(boolean isShown)
	{
		myEnemyPiecesShown = isShown;
	}

	public void setGridBorderSize(int gridBorderSize) 
	{
		myGridBorderSize = gridBorderSize;
		repaint();
	}

	public StrategoPiece getPieceBeingDragged() 
	{
		return myPieceBeingDragged;
	}

	public void setPieceBeingDragged(StrategoPiece piece, int col, int row)
	{
		// MAKE SURE WE'RE NOT DRAGGING IT OUT OF THE GUI
		if ((col >= 0) && (row >= 0) && (col < myColumns) && (row < myRows))
		{
			myPieceBeingDragged = piece;
			myFormerColumnOfPieceBeingDragged = col;
			myFormerRowOfPieceBeingDragged = row;
		}
	}

	public int getFormerColumnOfPieceBeingDragged() 
	{
		return myFormerColumnOfPieceBeingDragged;
	}

	public void setFormerColumnOfPieceBeingDragged(int formerColumnOfPieceBeingDragged) 
	{
		myFormerColumnOfPieceBeingDragged = formerColumnOfPieceBeingDragged;
	}

	public int getFormerRowOfPieceBeingDragged() 
	{
		return myFormerRowOfPieceBeingDragged;
	}

	public void setFormerRowOfPieceBeingDragged(int formerRowOfPieceBeingDragged) 
	{
		myFormerRowOfPieceBeingDragged = formerRowOfPieceBeingDragged;
	}

	public boolean gameHasStarted() 
	{
		return myGameHasStarted;
	}

	public void setGameHasStarted(boolean gameHasStarted) 
	{
		myGameHasStarted = gameHasStarted;
	}

	public VSDialog getVSDialog() 
	{
		return myVSDialog;
	}

	public void setVSDialog(VSDialog vsDialog) 
	{
		myVSDialog = vsDialog;
	}

	/**
	 * MouseHandler: This is where the game board will interact with the mouse. We need 
	 * to Be able to detect when the player is initaly putting their pieces down and the
	 * mouse released method will allow us to check where the user released their piece
	 * to determine whether it was dropped on top of a piece to attack it or if it was 
	 * placed in a valid place to begin with
	 * 
	 * @author Eddie
	 *
	 */
	protected class MouseHandler implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) { }

		@Override
		public void mouseEntered(MouseEvent arg0) { }

		@Override
		public void mouseExited(MouseEvent arg0) 
		{
			if(getPieceBeingDragged() != null)
			{
				putBackDraggedPiece();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			StrategoPiece pieceToPlace;
			int column;
			int row;
			int imageID;
			int x;
			int y;		
			// ARE WE CLICKING ON A CELL THAT ALREADY HAS A PIECE?
			column = calculateColumn(e.getX());
			row = calculateRow(e.getY());

			if(!myCurrentReplay.isRunning())
			{
				//If the player is still setting up their pieces and the game has not started
				if(!myGameGUI.getGameHasStarted())
				{
					if(e.getModifiers() == 4 && myGameBoardManager.isOccupied(column, row))
					{
						StrategoPiece tempPiece = myGameBoardManager.getPiece(column, row); 
						myGameBoardManager.removePiece(column, row);
						incrementPieces(tempPiece.getIdNum());
						repaint();
					}
					else if (!myGameBoardManager.isOccupied(column, row) && row >= 6) //ONLY PLACE PIECES IN EMPTY CELLS
					{
						// FIGURE OUT WHAT IMAGE WE'RE PLACING
						imageID = getActiveImageID();

						// FIGURE OUT WHERE TO PUT IT
						x = calculateX(column);
						y = calculateY(row);

						if(piecesLeft(imageID))
						{
							// MAKE THE PIECE for player
							pieceToPlace = new StrategoPiece(imageID, new Position(x, y), true, 0, 0);

							// AND PUT THE PIECE IN THE GAME BOARD
							myGameBoardManager.putPiece(pieceToPlace, column, row);

							decrementPieces(imageID);
						}

						//check to see if ai and player has placed 	all their pieces

						// THE PANEL DRAWS WHATEVER IS IN THE GAME BOARD
						repaint();
					}

					//Set the piece being dragged to the one just clicked in the current cell
					else
					{	
						pieceToPlace = myGameBoardManager.getPiece(column, row);
						setPieceBeingDragged(pieceToPlace, column, row);
						myGameBoardManager.removePiece(column, row);
					}

					if(!anyPiecesLeft())
					{
						myPlayersTurn = true;
						myGameGUI.setStartGameEnabled(true);
					}
					else
					{
						myGameGUI.setStartGameEnabled(false);
					}
				}
				else if(myGameGUI.getGameHasStarted() && myPlayersTurn) //if the player has finished placing their pieces then go here
				{
					StrategoPiece piece = myGameBoardManager.getPiece(column, row); 

					myCurrentMove.setPrevPosition(new Position(column,row));

					//check to see if current cell has something in it and belongs to the player
					if(myGameBoardManager.isOccupied(column, row) && piece.getOwner() == 0)
					{
						if(piece.getIdNum() != 10 && piece.getIdNum() != 11)
						{
							myOriginalCol = column;
							myOriginalRow = row;

							setPieceBeingDragged(piece, column, row);
							myGameBoardManager.removePiece(column, row);
						}
					}
				}
			}


		}

		@Override
		public void mouseReleased(MouseEvent me) 
		{
			//calculate the row and column where the mouse was released
			StrategoPiece piece = getPieceBeingDragged();
			AI theAI = myGameGUI.getAI();

			if(!myCurrentReplay.isRunning())
			{
				if(piece != null)
				{
					int col = calculateColumn(me.getX());
					int row = calculateRow(me.getY());
					int x = calculateX(col);
					int y = calculateY(row);

					if(myGameBoardManager.isOccupied(col,row) && myGameBoardManager.getPiece(col, row).getOwner() == 0)
					{
						putBackDraggedPiece();
					}

					//make sure where we are dropping out piece that there is'ent a piece already there 
					if(!myGameGUI.getGameHasStarted())
					{
						if(myGameBoardManager.isOccupied(col, row))
						{
							putBackDraggedPiece();
						}
						else if(row < 6)
						{
							putBackDraggedPiece();
						}
						else if(row >= 6)
						{
							piece.setX(x);
							piece.setY(y);

							myGameBoardManager.putPiece(piece, col, row);

							resetPieceBeingDragged();
						}
					}
					else
					{
						int distCol = Math.abs(myOriginalCol - col);
						int distRow = Math.abs(myOriginalRow - row);

						myCurrentMove.setNextPosition(new Position(col, row));

						if(myCurrentMove.getPrevPosition() != null && myCurrentMove.getNextPosition() != null)
						{
							myCurrentReplay.addMove(myCurrentMove);
						}

						myCurrentMove = new Move();

						if((distRow == piece.getMoveableDistance() || distCol == piece.getMoveableDistance()) && !(distRow == 1 && distCol == 1))
						{
							if(!myGameBoardManager.isOccupied(col, row))
							{
								if(theAI.ContainsDiscoverdPiece(piece))
								{
									theAI.RemoveDiscoverdPiece(piece);

									piece.setX(x);
									piece.setY(y);

									myGameBoardManager.putPiece(piece, col, row);

									theAI.AddDiscoverdPiece(piece);
								}
								else
								{
									piece.setX(x);
									piece.setY(y);

									myGameBoardManager.putPiece(piece, col, row);
								}

								repaint();

							}
							else if(myGameBoardManager.isOccupied(col, row) && myGameBoardManager.getPiece(col, row).getOwner() == 1)
							{
								StrategoPiece aiPiece = myGameBoardManager.getPiece(col, row);
								StrategoPiece winner = Compare(piece, aiPiece);
								Image pieceImage = myPieceImages.get(piece.getIdNum());
								Image aiPieceImage = myPieceImages.get(aiPiece.getIdNum());

								if(piece.equals(winner))
								{	
									VSDialog diag = new VSDialog(myGameGUI, pieceImage, aiPieceImage,"Player " + winner.getPieceName(winner.getIdNum()) + "(" + (winner.getIdNum() + 1) + ")");

									myGameBoardManager.removePiece(col, row);

									piece.setX(x);
									piece.setY(y);

									myGameBoardManager.putPiece(piece, col, row);

									theAI.AddDiscoverdPiece(piece);
								}
								else if(aiPiece.equals(winner))
								{
									VSDialog diag = new VSDialog(myGameGUI, pieceImage, aiPieceImage,"AI " + winner.getPieceName(winner.getIdNum()) + "(" + (winner.getIdNum() + 1) + ")");

									myGameBoardManager.removePiece(col, row);

									aiPiece.setX(x);
									aiPiece.setY(y);

									myGameBoardManager.putPiece(aiPiece, col, row);
								}
								else
								{
									VSDialog diag = new VSDialog(myGameGUI, pieceImage, aiPieceImage, "Tie");

									myGameBoardManager.removePiece(col, row);

									piece.setX(x);
									piece.setY(y);

									myGameBoardManager.putPiece(piece, col, row);

									myGameBoardManager.removePiece(col, row);
								}
							}

							repaint();

							myPlayersTurn = false;

							resetPieceBeingDragged();

							myGameGUI.getAI().move();
						}
						else //Not valid
						{
							putBackDraggedPiece();
						}
					}
				}
			}

			repaint();
		}

	}

	/**
	 * MouseMotionHandler: This is where we will take care of dragging events.
	 * We need to make the user feel as if they are actualy dragging a piece to a new
	 * location, this is where we will handle the mouse dragging.
	 * 
	 * @author Eddie
	 *
	 */
	protected class MouseMotionHandler implements MouseMotionListener
	{

		@Override
		public void mouseDragged(MouseEvent mouse) 
		{
			StrategoPiece piece = getPieceBeingDragged();
			int x;
			int y;

			if(!myCurrentReplay.isRunning())
			{
				if (piece != null)
				{
					x = mouse.getX();
					y = mouse.getY();

					updateDraggedPiece(x, y);

					repaint();
				}
			}

		}

		@Override
		public void mouseMoved(MouseEvent arg0) 
		{ 

		}

	}
}

