package Stratego;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;
import java.awt.Component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class GameGUI extends JFrame implements StrategoGame
{
	private final int CONSOLE_WIDTH = 10;
	private final int CONSOLE_HEIGHT = 5;
	private static final String myDatabaseFilePath = "database.txt";
	private static final String myGameBoardSetupFileName = "GameBoardSetupInfo.txt";

	private static GameGUI myGameGUI;

	private String myWinner;
	private Boolean myPiecesAreShowing;

	//private int[][] myOriginalSetup;

	private GameBoard myGameBoard;
	private Player myPlayer;
	private AI myAI;
	private boolean myLoginConfirmed;
	private boolean myGameHasStarted;
	private boolean myGameIsOver;
	private ArrayList<Player> myAccounts;
	private Replay myCurrentSelectedReplay;

	//GUI Components
	private JMenuBar myMenuBar;
	private JMenu myFileMenu;
	private JMenuItem myNewGameMenuItem;
	private JMenuItem myStartGameMenuItem;
	private JMenuItem myPlayReplayMenuItem;
	private JMenuItem myStopReplayMenuItem;
	private JMenuItem myForfeitMenuItem;
	private JMenuItem myChangeAccountPasswordMenuItem;
	private JMenuItem myPutPiecesRandomlyMenuItem;
	private JMenuItem myExitMenuItem;
	private JList myListofReplays;
	private JComboBox myPieceSelectionComboBox;
	private JToolBar myNorthButtonToolBar;
	private JToolBar myEastButtonToolBar;
	private JToolBar myWestButtonToolBar;
	private JButton myNewGameButton;
	private JButton myStartGameButton;
	private JButton myPlayReplayButton;
	private JButton myStopReplayButton;
	private JButton myForfeitButton;
	private JButton myPutPiecesRandomlyButton;
	private JButton myChangeAccountPasswordButton;
	private JPanel myNorthPanel;
	private JPanel myWestPanel;
	private JPanel myEastPanel;
	private JTextArea myConsoleWindow;
	private JScrollPane myConsoleScrollPane;
	private JTextField myConsoleEntryBox;

	//Screen Dimensions
	private int myScreenWidth;
	private int myScreenHeight;
	private int myGUIWidth;
	private int myGUIHeight;

	//Data used for graphical representations of various components 
	private DefaultListModel myReplayListModel;
	private DefaultComboBoxModel myPieceModel;

	//Dialog boxes used throughout the application
	private AIDialog myAIDialog;
	private LoginDialog myLoginDialog;

	//Layouts used by the main gui
	private FlowLayout myFlowLayout;
	private GridLayout myGridLayout;
	private BorderLayout myBorderLayout;
	private GridBagLayout myGridBagLayout;

	//Event Handlers used for buttons in the GUI
	private NewGameHandler myNewGameHandler;
	private StartGameHandler myStartGameHandler;
	private PlayReplayHandler myPlayReplayHandler;
	private StopReplayHandler myStopReplayHandler;
	private ChangeAccountPasswordHandler myChangeAccountPasswordHandler;
	private PutPiecesRandomlyHandler myPutPiecesRadomlyHandler;
	private ForfeitHandler myForfeitHandler;
	private WindowExitHandler myWindowExitHandler;
	private ComboListSelectionHandler myComboListSelectionHandler;
	private ConsoleHandler myConsoleHandler;
	private ListSelectionHandler myListSelectionHandler;

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
		super("Left 4 Dead Stratego");

		Toolkit tk = Toolkit.getDefaultToolkit();
		BufferedReader reader = initReader();

		myReplayListModel = new DefaultListModel();
		myPieceModel = new DefaultComboBoxModel();

		myMenuBar = new JMenuBar();
		myFileMenu = new JMenu("File");
		myNewGameMenuItem = new JMenuItem("New Game");
		myStartGameMenuItem = new JMenuItem("Start Game");
		myPlayReplayMenuItem = new JMenuItem("Play Replay");
		myStopReplayMenuItem = new JMenuItem("Stop Replay");
		myForfeitMenuItem = new JMenuItem("Forfeit");
		myChangeAccountPasswordMenuItem = new JMenuItem("Change Account Password");
		myPutPiecesRandomlyMenuItem = new JMenuItem("Put Pieces Randomly");
		myExitMenuItem = new JMenuItem("Exit");
		myListofReplays = new JList(myReplayListModel);
		myPieceSelectionComboBox = new JComboBox(myPieceModel);
		myNorthButtonToolBar = new JToolBar();
		myWestButtonToolBar = new JToolBar();
		myEastButtonToolBar = new JToolBar();
		myNewGameButton = new JButton("New Game");
		myStartGameButton = new JButton("Start Game");
		myPlayReplayButton = new JButton("Play Replay");
		myStopReplayButton = new JButton("Stop Replay");
		myForfeitButton = new JButton("Forfeit");
		myPutPiecesRandomlyButton = new JButton("Put Pieces Randomly");
		myChangeAccountPasswordButton = new JButton("Change Account Password");

		myNorthPanel = new JPanel();
		myWestPanel = new JPanel();
		myEastPanel = new JPanel();
		myConsoleWindow = new JTextArea(CONSOLE_HEIGHT, CONSOLE_WIDTH);
		myConsoleScrollPane = new JScrollPane(myConsoleWindow);
		myConsoleEntryBox = new JTextField(CONSOLE_WIDTH);

		//Read in the account data structure that was written to a file
		myAccounts = new ArrayList<Player>();
		readData();
		myLoginConfirmed = false;
		myGameHasStarted = false;
		myGameIsOver = false;
		myWinner = "No Winner";
		myPiecesAreShowing = false;

		myScreenWidth = tk.getScreenSize().width;
		myScreenHeight = tk.getScreenSize().height;

		//myOriginalSetup = new int[10][10];

		//Carefuly calculated so the game board fits the pieces properly
		myGUIWidth = 895;
		myGUIHeight = 720;

		myAIDialog = new AIDialog(this);
		myLoginDialog = new LoginDialog(this);

		myFlowLayout = new FlowLayout();
		myBorderLayout = new BorderLayout();
		myGridLayout = new GridLayout(0,1);
		myGridBagLayout = new GridBagLayout();

		myNewGameHandler = new NewGameHandler();
		myStartGameHandler = new StartGameHandler();
		myPlayReplayHandler = new PlayReplayHandler();
		myStopReplayHandler = new StopReplayHandler();
		myChangeAccountPasswordHandler = new ChangeAccountPasswordHandler();
		myPutPiecesRadomlyHandler = new PutPiecesRandomlyHandler();
		myForfeitHandler = new ForfeitHandler();
		myWindowExitHandler = new WindowExitHandler(this);
		myConsoleHandler = new ConsoleHandler();
		myListSelectionHandler = new ListSelectionHandler();

		myComboListSelectionHandler = new ComboListSelectionHandler();
		myPieceSelectionComboBox.addItemListener(myComboListSelectionHandler);

		//Setup listeners
		this.addWindowListener(myWindowExitHandler);

		myConsoleEntryBox.addActionListener(myConsoleHandler);
		myListofReplays.addListSelectionListener(myListSelectionHandler);

		myNewGameButton.addActionListener(myNewGameHandler);
		myStartGameButton.addActionListener(myStartGameHandler);
		myPlayReplayButton.addActionListener(myPlayReplayHandler);
		myStopReplayButton.addActionListener(myStopReplayHandler);
		myChangeAccountPasswordButton.addActionListener(myChangeAccountPasswordHandler);
		myPutPiecesRandomlyButton.addActionListener(myPutPiecesRadomlyHandler);
		myForfeitButton.addActionListener(myForfeitHandler);

		myNewGameMenuItem.addActionListener(myNewGameHandler);
		myStartGameMenuItem.addActionListener(myStartGameHandler);
		myPlayReplayMenuItem.addActionListener(myPlayReplayHandler);
		myStopReplayMenuItem.addActionListener(myStopReplayHandler);
		myChangeAccountPasswordMenuItem.addActionListener(myChangeAccountPasswordHandler);
		myPutPiecesRandomlyMenuItem.addActionListener(myPutPiecesRadomlyHandler);
		myForfeitMenuItem.addActionListener(myForfeitHandler);
		myExitMenuItem.addActionListener(myWindowExitHandler);	

		try
		{
			setupGameGUI(reader);
		}
		catch(IOException ioe)
		{
			JOptionPane.showMessageDialog(this, 
					"ERROR - " + myGameBoardSetupFileName + " has an Invalid File Format - EXITING",
					"ERROR - Invalid File Format",
					JOptionPane.ERROR_MESSAGE);
		}

		layoutGUI();


		if(myLoginConfirmed)
		{
			this.setVisible(true);
			myGameBoard.setVisible(false);

			for(int i = 0; i < myPlayer.getReplays().size(); i++)
			{
				myReplayListModel.add(i, myPlayer.getReplays().get(i));
			}
		}
	}


	/**
	 * layoutGUI: Takes care of positioning all of the GUI components
	 */
	public void layoutGUI()
	{
		GridBagConstraints c = new GridBagConstraints();

		//Center the GUI to the center of the screen but make y = 0 so the gui dosent fall below start bar
		this.setLocation((myScreenWidth / 2) - (myGUIWidth / 2), 0);

		//When app starts, what dialog boxes should be shown.
		myLoginDialog.setVisible(true);

		//Certen buttons will be hidden untill the user needs them
		myStartGameButton.setEnabled(false);
		myPlayReplayButton.setEnabled(false);
		myStopReplayButton.setEnabled(false);
		myForfeitButton.setEnabled(false);
		myPutPiecesRandomlyButton.setEnabled(false);

		//Hide the combo box till new game is pressed
		myPieceSelectionComboBox.setVisible(false);

		//Same thing with menu items.
		myStartGameMenuItem.setEnabled(false);
		myPlayReplayMenuItem.setEnabled(false);
		myStopReplayMenuItem.setEnabled(false);
		myForfeitMenuItem.setEnabled(false);
		myPutPiecesRandomlyMenuItem.setEnabled(false);

		//Game GUI Size
		this.setSize(myGUIWidth, myGUIHeight);

		//File Menu Setup
		myMenuBar.add(myFileMenu);
		myFileMenu.add(myNewGameMenuItem);
		myFileMenu.add(myStartGameMenuItem);
		myFileMenu.addSeparator();
		myFileMenu.add(myPutPiecesRandomlyMenuItem);
		myFileMenu.addSeparator();
		myFileMenu.add(myPlayReplayMenuItem);
		myFileMenu.add(myStopReplayMenuItem);
		myFileMenu.addSeparator();
		myFileMenu.add(myForfeitMenuItem);
		myFileMenu.add(myChangeAccountPasswordMenuItem);
		myFileMenu.addSeparator();
		myFileMenu.add(myExitMenuItem);

		//Button Toolbar Setup
		myNorthButtonToolBar.setLayout(myFlowLayout);
		myNorthButtonToolBar.add(myNewGameButton);
		myNorthButtonToolBar.add(myStartGameButton);
		myNorthButtonToolBar.add(myPlayReplayButton);
		myNorthButtonToolBar.add(myStopReplayButton);
		myNorthButtonToolBar.add(myChangeAccountPasswordButton);
		myNorthButtonToolBar.add(myPutPiecesRandomlyButton);
		myNorthPanel.add(myNorthButtonToolBar);

		//Set size of East components
		myEastButtonToolBar.setPreferredSize(new Dimension(125,500));

		//East panel setup
		myEastButtonToolBar.setLayout(myGridLayout);
		myEastButtonToolBar.add(myPieceSelectionComboBox);
		myEastButtonToolBar.addSeparator();
		myEastButtonToolBar.add(myListofReplays);
		myEastButtonToolBar.addSeparator();
		myEastButtonToolBar.add(myForfeitButton);
		myEastPanel.add(myEastButtonToolBar);

		//West panel setup
		myConsoleWindow.setEditable(false);
		myWestPanel.setLayout(myGridLayout);

		myWestButtonToolBar.setLayout(myGridBagLayout);
		c.gridwidth = GridBagConstraints.REMAINDER;
		myWestButtonToolBar.add(myConsoleScrollPane, c);
		myWestButtonToolBar.add(myConsoleEntryBox, c);
		myWestPanel.add(myWestButtonToolBar);

		//Main setup
		this.setJMenuBar(myMenuBar);
		this.add(myNorthPanel, myBorderLayout.NORTH);
		this.add(myGameBoard, myBorderLayout.CENTER);
		this.add(myEastPanel, myBorderLayout.EAST);
		this.add(myWestPanel, myBorderLayout.WEST);
	}


	public void setupGameGUI(BufferedReader reader)throws IOException
	{
		Image backgroundImage = initBackgroundImage(reader);
		Vector<Image> pieceImages = new Vector<Image>();
		PieceComboBoxRenderer pcbr;

		// LOAD THE GAME BOARD DIMENSIONS
		int columns;
		int rows;

		try
		{
			// ONE LINE EACH IN THE SETUP FILE
			columns = Integer.parseInt(reader.readLine());
			rows = Integer.parseInt(reader.readLine());
		}
		catch(NumberFormatException nfe)
		{
			// REFLECT AN IMPROPER LINE BACK AS AN IOException
			// TO OUR SHARED ERROR HANDLER IN THE CALLING METHOD
			throw new IOException("Illegal File Format");
		}

		// LOAD THE PIECE IMAGES
		pieceImages = initPieceImages(reader);

		// INITIALIZE THE IMAGE BOARD PANEL
		try
		{
			// REMEMBER, THIS IS A CUSTOMLY SIZED GAME
			// BOARD GRID, WHERE THE FILE PROVIDES THE SIZE
			myGameBoard = new GameBoard( this, 
					columns,
					rows,
					backgroundImage, 
					pieceImages);
		}
		catch(IllegalGridDimensionException igde)
		{
			// MAKE SURE THE USER SIZED THE ARTWORK PROPERLY
			// WE DON'T WANT A MESS WHEN WE'RE RENDERING
			JOptionPane.showMessageDialog(	this, 
					igde.getMessage(), 
					"ERROR - Invalid Grid Dimenions",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		// NOTE THAT WE NEED TO USE OUR OWN CUSTOM RENDERER
		// IN ORDER TO VIEW IMAGES INSIDE OUR JComboBox
		pcbr = new PieceComboBoxRenderer();
		myPieceSelectionComboBox.setRenderer(pcbr);
		myPieceSelectionComboBox.setMaximumRowCount(5);

		// LOAD THE PIECE IMAGES INTO THE COMBO BOX MODEL
		loadPieceComboBoxModel(pieceImages);

	}

	/**
	 * This method places the images in the Vector argument
	 * into the combo box's model, which will update the
	 * display of the combo box itself.
	 */
	public void loadPieceComboBoxModel(Vector<Image> images)
	{
		// GO THROUGH THE VECTOR IMAGES ONE AT A TIME
		Iterator<Image> it = images.iterator();
		Image image;

		while (it.hasNext())
		{
			image = it.next();
			myPieceModel.addElement(image);
		}
	}

	/**
	 * This method is used for loading a piece image, which
	 * will be placed on top of the background image. We will want
	 * these images to have transparent backgrounds so that we can
	 * still see part of the map underneath. This is done by using
	 * a color key for the images, where that key represents pixels
	 * to be transparent. In this method, we make those pixels
	 * fully transparent. Note that transparency could also be
	 * achieved by specifying an alpha channel value for each pixel
	 * using a tool like PhotoShop.
	 */
	public Image loadImageWithColorKey(String fileName, int redKey, int greenKey, int blueKey)
	{
		// FIRST LOAD THE IMAGE
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(fileName);
		BufferedImage imageToLoad;
		Graphics g;
		WritableRaster raster;
		int[] dummy;
		int[] pixel;

		// AND WAIT FOR IT TO BE FULLY LOADED
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(img, 0);

		try
		{
			tracker.waitForAll(); 
		}
		catch(Exception e )
		{ 
			e.printStackTrace(); 
		}

		// DID THE IMAGE LOAD? IF AN INCORRECT FILE NAME
		// WAS PROVIDED WE WILL HAVE AN IMAGE WITH DIMENSIONS
		// OF -1 x -1, SO LET'S CHECK AND SEE
		// IF IT'S INVALID, LET'S TELL THE CALLING METHOD
		// BY RETURNING null
		if (img.getWidth(null) < 0)
		{
			return null;
		}

		// NOW DRAW THE LOADED IMAGE INTO A BufferedImage,
		// A TYPE THAT LET'S US EDIT THE CONTENTS OF THE IMAGE
		imageToLoad = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g = imageToLoad.getGraphics();
		g.drawImage(img, 0,	0,	null);

		// NOW MAKE ALL PIXELS WITH COLOR (200, 100, 100) TRANSPARENT
		// THAT COLOR VALUE REPRESENTS OUR COLOR KEY
		raster = imageToLoad.getRaster();
		dummy = null;

		for (int i = 0; i < raster.getWidth(); i++)
		{
			for (int j = 0; j < raster.getHeight(); j++)
			{
				// THIS WILL BE AN ARRAY OF SIZE 4
				// RED, GREEN, BLUE, ALPHA
				pixel = raster.getPixel(i, j, dummy);

				if ((pixel[0] == redKey) && (pixel[1] == greenKey) && (pixel[2] == blueKey))
				{
					// THIS IS SETTING THE ALPHA CHANNEL FOR
					// THIS PIXEL TO 0, MEANING IT WILL BE
					// FULLY TRANSPARENT, i.e. INVISIBLE
					pixel[3] = 0;
					raster.setPixel(i, j, pixel);
				}
			}
		}		
		// SEND BACK THE IMAGE WITH THE
		// KEY PIXELS FULLY TRANSPARENT
		return imageToLoad;
	}

	public BufferedReader initReader()
	{
		// OUR READER WILL READ FROM THE SETUP FILE
		BufferedReader br = null;
		FileReader fr = null;
		try
		{
			// THE SETUP FILE TELLS THE APP
			// HOW TO CUSTOMIZE THE GUI AND GAME BOARD
			fr = new FileReader(myGameBoardSetupFileName);
			br = new BufferedReader(fr);
		}
		catch(IOException ioe)
		{
			// IF THE GAME BOARD CAN'T BE FOUND
			// THERE IS NOT USE IN GOING ON, INFORM
			// THE USER AND EXIT
			JOptionPane.showMessageDialog(this, 
					"ERROR - " + myGameBoardSetupFileName + " was not found",
					"ERROR - Setup file not found",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return br;
	}

	/**
	 * This method loads all the piece images at once and
	 * returns a vector containing them all.
	 */
	public Vector<Image> initPieceImages(BufferedReader br) throws IOException
	{
		// THIS IS THE VECTOR WE'RE GOING TO FILL
		Vector<Image> pieceImages = new Vector<Image>();
		Image imageToLoad;
		int testWidth;
		int testHeight;

		// FIRST GET THE COLOR KEY
		String line = br.readLine();
		String[] color = line.split(",");
		int r = Integer.parseInt(color[0]);
		int g = Integer.parseInt(color[1]);
		int b = Integer.parseInt(color[2]);

		// ALL PIECES SHOULD BE THE SAME SIZE
		// WE'LL EXTRACT THE SIZE OF THE FIRST ONE
		// AND THROW AWAY ANY THAT ARE DIFFERENT
		int firstPieceWidth = -1;
		int firstPieceHeight = -1;
		boolean pieceImageSizesAllTheSame = true;
		line = br.readLine();


		// ONE IMAGE NAME FOR EACH LINE IN THE SETUP FILE
		while (line != null)
		{
			// LOAD THE IMAGE, MAKING THE PIXELS WITH THE
			// COLOR KEY TRANSPARENT
			// NOTE THAT THE loadImageWithColorKey USES
			// THE MediaTracker TO MAKE SURE EACH IMAGE
			// IS FULLY LOADED BEFORE RETURNING
			imageToLoad = loadImageWithColorKey(line, r, g, b);

			// WHAT IF THE IMAGE FILE DIDN'T LOAD PROPERLY?
			if (imageToLoad == null)
			{
				// GRACEFUL DEGRADATION, LET THE APPLICATION
				// GO ON WITHOUT THIS ONE FILE, BUT LET THE
				// USER KNOW ABOUT THE PROBLEM. IT'S LIKELY
				// THE IMAGE NAME OR PATH TO THE FILE WAS
				// WRITTEN INCORRECTLY IN THE SETUP FILE
				JOptionPane.showMessageDialog(this, 
						"WARNING - Note that " + line + " could not be loaded",
						"Problem loading image",
						JOptionPane.WARNING_MESSAGE);
			}
			// THIS IS FOR IMAGES THAT DID LOAD PROPERLY
			else
			{
				// IS IT THE FIRST IMAGE IN THE GROUP?
				if (firstPieceWidth == -1)
				{
					// LET'S MAKE THAT SIZE THE EXPECTATION
					// FOR ALL IMAGES IN THE GROUP
					firstPieceWidth = imageToLoad.getWidth(null);
					firstPieceHeight = imageToLoad.getHeight(null);

					// AND THEN ADD IT TO THE VECTOR WE'RE LOADING
					pieceImages.add(imageToLoad);
				}
				// FOR ALL IMAGES OTHER THAN THE FIRST
				else
				{
					// LET'S FIRST MAKE SURE IT'S THE SAME
					// SIZE AS THE FIRST. BY THE WAY, IF 
					// THE FIRST ONE IS THE WRONG SIZE THEN
					// WE REALLY HAVE A PROBLEM
					testWidth = imageToLoad.getWidth(null);
					testHeight = imageToLoad.getHeight(null);

					// DO WE HAVE AN IMAGE SIZED DIFFERENTLY
					// FROM THE FIRST ONE?
					if ((testWidth != firstPieceWidth) && (testHeight != firstPieceHeight))
					{
						// THIS IS A FLAG TO INFORM THE USER
						// OF THE PROBLEM AT THE END
						pieceImageSizesAllTheSame = false;						
					}
					// NO PROBLEM WITH THIS ONE
					else
					{
						// LOAD IT IN THE VECTOR
						pieceImages.add(imageToLoad);						
					}
				}
			}
			// AND GET THE NEXT LINE 
			// (GAME PIECE IMAGE FILE NAME)
			line = br.readLine();
		}

		// IF ONE OF THE PIECES IS A DIFFERENT
		// SIZE, LET THE USER KNOW, BUT LET'S
		// TRY TO KEEP THE APPLICATION GOING
		if (!pieceImageSizesAllTheSame)
		{
			// DISPLAY AN INFORMATIVE WARNING
			JOptionPane.showMessageDialog(this,
					"WARNING: not all image sizes were the same, some did not load",
					"Image Size Variation",
					JOptionPane.WARNING_MESSAGE);
		}
		// SEND BACK OUR VECTOR OF THE LOADED PIECE IMAGES
		return pieceImages;
	}

	/**
	 * This method uses the shared file reader to
	 * extract and then load the background image
	 * for the game board.
	 */
	public Image initBackgroundImage(BufferedReader br) throws IOException
	{
		// FIRST LOAD THE IMAGE
		MediaTracker mt = new MediaTracker(this);
		Toolkit tk = Toolkit.getDefaultToolkit();
		String bgFileName = br.readLine();
		Image bg = tk.getImage(bgFileName);

		// MAKE SURE IT FULLY LOADS FIRST
		mt.addImage(bg, 0);
		try 
		{ 
			mt.waitForID(0);	
		}
		catch(InterruptedException ie)
		{
			// THIS WOULD BE AN UNUSUAL ERROR, WE'LL
			// PRINT THE STACK TRACE IN CASE IT DOES
			// TO FIGURE OUT THE SOURCE OF THE ERROR
			ie.printStackTrace();
		}

		// AND NOW INFORM THE USER OF AN ERROR
		// IF THE IMAGE DID NOT LOAD
		if (bg.getWidth(null) < 0)
		{
			JOptionPane.showMessageDialog(this, 
					"WARNING - Note that " + bgFileName + " could not be loaded",
					"Problem loading background image",
					JOptionPane.WARNING_MESSAGE);			
		}
		return bg;
	}

	public String getWinner()
	{
		return myWinner;
	}

	public void setWinner(String winner)
	{
		myWinner = winner;
	}

	public boolean getGameIsOver()
	{
		return myGameIsOver;
	}

	public void setGameIsOver(boolean isOver)
	{
		myGameIsOver = isOver;
	}

	public int getScreenWidth()
	{
		return myScreenWidth;
	}

	public int getScreenHeight()
	{
		return myScreenHeight;
	}

	public int getGUIWidth()
	{
		return myGUIWidth;
	}

	public int getGUIHeight()
	{
		return myGUIHeight;
	}

	public boolean getLoginConfirmed()
	{
		return myLoginConfirmed;
	}

	public void setLoginConfirmed(boolean loginConfirmed)
	{
		myLoginConfirmed = loginConfirmed;
	}

	public String getDatabaseFilePath()
	{
		return myDatabaseFilePath;
	}

	public AI getAI()
	{
		return myAI;
	}

	public void setAI(AI newAI)
	{
		myAI = newAI;
	}

	public Boolean getPiecesAreShowing()
	{
		return myPiecesAreShowing;
	}

	public void setPiecesAreShowing(boolean isShowing)
	{
		myPiecesAreShowing = isShowing;
		myGameBoard.repaint();
	}

	public void addAccount(String username, String password)
	{
		myAccounts.add(new Player(null, 0, username, password));
	}

	public boolean getGameHasStarted()
	{
		return myGameHasStarted;
	}

	public void setGameHasStarted(boolean hasStarted)
	{
		myGameHasStarted = hasStarted;
	}

	public Player getPlayer()
	{
		return myPlayer;
	}

	public void setPlayer(Player player)
	{
		myPlayer = player;
	}

	public void setStartGameEnabled(boolean enabled)
	{
		myStartGameButton.setEnabled(enabled);
		myStartGameMenuItem.setEnabled(enabled);
	}

	public GameBoard getGameBoard()
	{
		return myGameBoard;
	}

	public void setGameBoard(GameBoard gameBoard)
	{
		myGameBoard = gameBoard;
	}

	public void gameOver() 
	{
		int choice = -1;

		if(myGameIsOver)
		{
			JOptionPane.showMessageDialog(this, "Game Over \n The Winner is " + myWinner);

			if(myPlayer.getUsername().equals(myWinner))
			{
				myPlayer.setHighScore(myPlayer.getHighScore() + 100);
			}

			choice = JOptionPane.showConfirmDialog(null, "Would you like to save your replay?", "Save Replay", JOptionPane.YES_NO_OPTION);

			if(choice == 0)//yes
			{
				myGameBoard.getCurrentReplay().setReplayName("Replay " + myPlayer.getReplays().size());

				myPlayer.addReplay(myGameBoard.getCurrentReplay());

				myReplayListModel.addElement(myGameBoard.getCurrentReplay());
			}
			else if(choice == 1)//no
			{
				//Dont need to do anything
			}


			myGameBoard.resetPieceBeingDragged();
			myGameBoard.resetPieces();
			myGameBoard.repaint();

			myNewGameButton.setEnabled(true);
			myNewGameMenuItem.setEnabled(true);

			myForfeitButton.setEnabled(false);
			myForfeitMenuItem.setEnabled(false);

			myGameHasStarted = false;
		}
	}

	/**
	 * lookupAccount: takes in a username and password and checks to see if it is in our
	 * arraylist. If it is then return it and make note of it. If it is not found return null.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Player lookupAccount(String username, String password)
	{
		Player temp;

		for(int i = 0; i < myAccounts.size(); i++)
		{
			temp = myAccounts.get(i);

			if(temp.getUsername().equals(username) && temp.getPassword().equals(password))
			{
				myLoginConfirmed = true;
				myPlayer = temp;

				return temp;
			}
		}

		return null;
	}

	/**
	 * verifyExit: Prompts the user to ask if they are sure they want to quit. If yes then the GUI will
	 * close
	 * 
	 * @return true if user wants to exit otherwise false.
	 */
	public boolean verifyExit()
	{
		return false;
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
	 * promptToSaveReplay: Asks user if they would like to save the replay.
	 * This will be asked at the end of the game.
	 */
	public void promptToSaveReplay()
	{

	}

	/**
	 * getSelectedReplay: Returns the replay that is currently selected in the JList
	 * 
	 * @return Replay
	 */
	public Replay getSelectedReplay()
	{
		return null;
	}

	/**
	 * NewGameHandler: Handles when the NewGameButton or NewGameMenuItem is pressed. 
	 * When fired, the GameBoard will be created and the user will be alowed to put
	 * pieces onto the GameBoard.
	 *  
	 * @author Eddie
	 *
	 */
	protected class NewGameHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{	
			myAI = new AI(myGameGUI, "Null");

			myGameBoard.resetPieces();
			myGameBoard.repaint();

			myPieceModel.removeElementAt(myPieceModel.getSize() - 1);

			//Newgame button will be hidden because we dont want the user to reset the bord this way
			myNewGameButton.setEnabled(false);
			myNewGameMenuItem.setEnabled(false);

			//Some buttons and components are now available
			myAIDialog.setVisible(true);
			myGameBoard.setVisible(true);
			myPutPiecesRandomlyButton.setEnabled(true);
			myPutPiecesRandomlyMenuItem.setEnabled(true);

			//Make the combobox visible 
			myPieceSelectionComboBox.setVisible(true);

			//We dont want the user to resize because rendering will screw up
			myGameGUI.setResizable(false);		
		}
	}

	/**
	 * StartGameHandler: After the user has placed all of their pieces onto the game board,
	 * The user will now have the option to start the game. The user will be prompted
	 * with a message box asking what type of AI they would like to go against.
	 * Once a selection has been made and the user pressed ok on the AIDialog box,
	 * The game will have started.
	 * 
	 * @author Eddie
	 *
	 */
	protected class StartGameHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			//Place pieces for AI
			Random rand = new Random();
			File aiPieceSetup = new File("AI_IPS.txt");
			GameBoardManager gbManager = myGameBoard.getGameBoardManager();
			StrategoPiece pieceToPlace = null;
			BufferedReader br = null;
			String[] premadeArray = new String[21];
			int currChar = 0;
			int currentId = -1;
			int x = -1;
			int y = -1;

			myAI.setDifficulty(myAIDialog.getSelectedAI());

			myGameHasStarted = true;
			myGameBoard.setPlayersTurn(true);

			myStartGameButton.setEnabled(false);
			myStartGameMenuItem.setEnabled(false);

			myPutPiecesRandomlyButton.setEnabled(false);
			myPutPiecesRandomlyMenuItem.setEnabled(false);

			myForfeitButton.setEnabled(true);
			myForfeitMenuItem.setEnabled(true);

			try 
			{
				br = new BufferedReader(new FileReader(aiPieceSetup));
				String text = "";
				int i = 0;

				for(int j = 0; j < premadeArray.length; j++)
				{
					premadeArray[j] = "";
				}

				while ((text = br.readLine()) != null)
				{
					if(!text.equals(""))
					{
						premadeArray[i] += text;
					}
					else
					{
						i++;
					}
				}
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}

			for(int j = 0; j < premadeArray.length; j++)
			{
				System.out.println(premadeArray[j]);
			}

			if(myAI.getDifficulty().equals("Hard"))
			{
				String pick = premadeArray[rand.nextInt(premadeArray.length - 1)];
				//we have to place the pieces in reverse because the ai orientation is mirrored
				for(int row = 3; row >= 0; row--)
				{
					for(int col = 9; col >= 0; col--)
					{
						x = myGameBoard.calculateX(col);
						y = myGameBoard.calculateY(row);

						if(pick.charAt(currChar) == 'B')
						{
							currentId = 10;
						}
						else if(pick.charAt(currChar) == 'S')
						{
							currentId = 9;
						}
						else if(pick.charAt(currChar) == 'F')
						{
							currentId = 11;
						}
						else
						{
							currentId = Integer.parseInt(pick.charAt(currChar) + "") - 1;
						}


						if(myGameBoard.aiPiecesLeft(currentId) && !gbManager.isOccupied(col, row))
						{
							// MAKE THE PIECE
							pieceToPlace = new StrategoPiece(currentId, new Position(x, y), false, 0, 1);

							// AND PUT THE PIECE IN THE GAME BOARD
							gbManager.putPiece(pieceToPlace, col, row);

							myGameBoard.decrementAiPieces(currentId);

							myGameBoard.repaint();

							currChar++;
						}
					}
				}
			}
			else //If difficulty is easy then just place the pieces randomly
			{
				while(myGameBoard.anyAiPiecesLeft())
				{
					for(int row = 0; row < 4; row++)
					{
						for(int col = 0; col < 10; col++)
						{
							x = myGameBoard.calculateX(col);
							y = myGameBoard.calculateY(row);

							currentId = rand.nextInt(12);

							if(myGameBoard.aiPiecesLeft(currentId) && !gbManager.isOccupied(col, row))
							{
								// MAKE THE PIECE
								pieceToPlace = new StrategoPiece(currentId, new Position(x, y), false, 0, 1);

								// AND PUT THE PIECE IN THE GAME BOARD
								gbManager.putPiece(pieceToPlace, col, row);

								myGameBoard.decrementAiPieces(currentId);

								myGameBoard.repaint();
							}
						}
					}
				}
			}

			myGameBoard.getCurrentReplay().setOriginalSetup(myGameBoard.getGameBoardManager());
			

		}
	}

	/**
	 * PlayReplayHandler: If a game is currently not in progress and the user
	 * has a replay selected in the JList, then they will be able to watch the
	 * replay of the game.
	 * 
	 * @author Eddie
	 *
	 */
	protected class PlayReplayHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{	
			myGameBoard.setVisible(true);
			
			myStopReplayButton.setEnabled(true);
			myStopReplayMenuItem.setEnabled(true);
			
			myGameBoard.resetPieces();

			myGameBoard.repaint();
			
			myGameBoard.setGameBoardManager(myGameBoard.getCurrentReplay().getGameBoardManager());
			
			myGameBoard.repaint();
			
			myCurrentSelectedReplay.playReplay();
		}
	}

	
	/**
	 * StopReplayHandler: If a replay is currently being played, the user 
	 * will have the option to stop the replay.
	 * 
	 * @author Eddie
	 *
	 */
	protected class StopReplayHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			
		}
	}

	protected class ListSelectionHandler implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e) 
		{
			myCurrentSelectedReplay = (Replay) myListofReplays.getSelectedValue();

			if(myCurrentSelectedReplay != null)
			{
				myPlayReplayButton.setEnabled(true);
				myPlayReplayMenuItem.setEnabled(true);
			}

		}
	}

	/**
	 * ChangeAccountPasswordHandler: If the user chooses to, they can change the password
	 * currently associated with their account. This will change the player object.
	 * 
	 * @author Eddie
	 *
	 */
	protected class ChangeAccountPasswordHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			String pass = JOptionPane.showInputDialog(myGameGUI, "Please enter your new password.");

			myPlayer.setPassword(pass);
		}
	}

	/**
	 * PutPiecesRandomlyHandler: User can select this option if they wish to 
	 * place the remaining pieces randomly on the board. Note that this will
	 * not change the pieces that were already pressed prior to this event.
	 * 
	 * @author Eddie
	 *
	 */
	protected class PutPiecesRandomlyHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)			
		{
			Random rand = new Random();
			GameBoardManager gbManager = myGameBoard.getGameBoardManager();
			StrategoPiece pieceToPlace = null;
			int currentId = -1;
			int x = -1;
			int y = -1;

			while(myGameBoard.anyPiecesLeft())
			{
				for(int row = 6; row < 10; row++)
				{
					for(int col = 0; col < 10; col++)
					{
						x = myGameBoard.calculateX(col);
						y = myGameBoard.calculateY(row);

						currentId = rand.nextInt(12);

						if(myGameBoard.piecesLeft(currentId) && !gbManager.isOccupied(col, row))
						{
							// MAKE THE PIECE
							pieceToPlace = new StrategoPiece(currentId, new Position(x, y), true, 0, 0);

							// AND PUT THE PIECE IN THE GAME BOARD
							gbManager.putPiece(pieceToPlace, col, row);

							myGameBoard.decrementPieces(currentId);

							myGameBoard.repaint();
						}
					}
				}
			}

			myStartGameButton.setEnabled(true);
			myStartGameMenuItem.setEnabled(true);
		}
	}

	protected class ConsoleHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			String text = myConsoleEntryBox.getText();
			myConsoleWindow.append(text + "\n");

			if(text.equals("stats"))
			{
				myConsoleWindow.append("Player Score:" + myPlayer.getHighScore());
			}
			else if(text.equals("blacksheepwall"))
			{
				myConsoleWindow.append("Cheat Activated.");

				myPiecesAreShowing = !myPiecesAreShowing;
				myGameBoard.setEnemyPiecesShown(myPiecesAreShowing);

				myGameBoard.repaint();
			}
			else if(text.equals("help"))
			{
				myConsoleWindow.append("stats - Displays your overall score." + "\n" 
						+ "blacksheepwall - Allows you to see the AI's cards" + "\n");
			}
			else
			{
				myConsoleWindow.append("Unknown Command. Type help for a list of commands");
			}


			myConsoleEntryBox.selectAll();

			//Make sure the new text is visible, even if there
			//was a selection in the text area.
			myConsoleWindow.setCaretPosition(myConsoleWindow.getDocument().getLength());

		}
	}

	/**
	 * ForfeitHandler: If a game is currently in progress and the user chooses to,
	 * they can give up. They will lose some points to their overall score but
	 * this will also end the game. Note that this will not ask if the user wants to
	 * save the replay or not.
	 * 
	 * @author Eddie
	 *
	 */
	protected class ForfeitHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			myGameBoard.resetPieceBeingDragged();
			myGameBoard.resetPieces();
			myGameBoard.repaint();

			myNewGameButton.setEnabled(true);
			myNewGameMenuItem.setEnabled(true);

			myForfeitButton.setEnabled(false);
			myForfeitMenuItem.setEnabled(false);

			myGameHasStarted = false;
		}
	}

	/**
	 * This class makes sure that the panel is aware
	 * of what image should be used for placement.
	 */
	protected class ComboListSelectionHandler implements ItemListener
	{
		Image selection;
		int selectedIndex;
		/**
		 * This method gets called for all interactions
		 * with the combo box.
		 */
		public void itemStateChanged(ItemEvent ie) 
		{
			/**
			 * BUT WE'RE ONLY INTERESTED IN THE SELECTION
			 * OF A NEW ITEM IN THE LIST
			 */
			if (ie.getStateChange() == ItemEvent.SELECTED)	
			{
				// UPDATE THE PANEL SO IT USES
				// THE CORRECT IMAGE FOR PLACEMENT
				selection = (Image)ie.getItem();
				selectedIndex = myPieceModel.getIndexOf(selection);
				myGameBoard.setActiveImageID(selectedIndex);
			}
		}
	}

	/**
	 * This class provides a custom renderer for 
	 * the combo box. This is necessary in order
	 * to have the combo box display images.
	 */
	protected class PieceComboBoxRenderer extends JLabel implements ListCellRenderer
	{
		ImageIcon icon;
		Border etchedBorder;

		/**
		 * This constructor makes sure our images are
		 * centered inside the drop down list.
		 */
		public PieceComboBoxRenderer()
		{
			setOpaque(true);
			setHorizontalAlignment(CENTER);
			setVerticalAlignment(CENTER);
		}

		/**
		 * This is where the renderer does it work. This method
		 * will be called for each option visible in the combo
		 * box at any time. Each time it simply returns a Component
		 * encapsulating the image for that item. I prefer it with
		 * a border as well.
		 */
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
		{
			// SET THIS COMPONENT'S ICON
			icon = new ImageIcon((Image)value);
			setIcon(icon);

			// AND GIVE IT A BORDER TO DISTINGUISH
			// BETWEEN THE DIFFERENT ICONS
			etchedBorder = BorderFactory.createEtchedBorder();
			setBorder(etchedBorder);

			// THIS IS A JLabel DESCENDANT AND WILL
			// BE RENDERED INSIDE OUR COMBO BOX
			return this;
		}
	}

}
