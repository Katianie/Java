import java.awt.Color;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;

//import Stratego.GameGUI.ComboListSelectionHandler;

/**
 * This example program will demonstrate how we can manipulate
 * a scrollable 2D map of tiles using mouse dragging.
 */
public class TheMapper extends JFrame
{
	private File selectedFile;
	private boolean savedSinceLastEdit;
	
	// HERE'S THE MAP WE'RE EDITING
	public MapManager mapData;
	private JScrollPane scrollPane;
	
	// HERE'S WHERE WE RENDER THE MAP
	private MapPanel mapPanel;
	private DefaultComboBoxModel comboBoxModel;
	private JComboBox comboBox;
	private GridLayout gridLayout;
	// THIS WILL HANDLE OUR KEY PRESSES
	private KeyHandler keyHandler;

	// IT SUCKS THAT JAVA'S CURSOR IS IMMUTABLE
	private Vector<Cursor> cursors;

	// FILE MENU BAR
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newMapMenuItem;
	private JMenuItem loadMapMenuItem;
	private JMenuItem saveMapMenuItem;
	private JMenuItem exitMenuItem;
	
	// FILE EVENT HANDLERS
	private NewMapHandler newMapHandler;
	private LoadMapHandler loadMapHandler;
	private SaveMapHandler saveMapHandler;
	private ExitHandler exitHandler;
	private ComboListSelectionHandler myComboListSelectionHandler;
	
	
	private int myScreenWidth;
	private int myScreenHeight;
	
	
	
	private JPanel mainPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel eastPanel;
	private ComboBoxPanel comboPanel;
	private JToolBar comboToolBar;
	private JToolBar drawBar;
	private BorderLayout borderLayout;
	
	
	public Vector<Image> tiledLayerTileSet;
	public Vector<Image> sparseLayerTileSet;
	public Vector<Image> setWeWant;
	
	//public Vector<String> nameStorage;
	public String[] filenames;
	/**
	 * Default Constructor, we open the frame to 900 X 700 so
	 * that we can print some output if we need to do debugging.
	 * When released, we'd probably have it fit the screen.
	 */
	public TheMapper()
	{
		// SETUP AND TITLE BAR
		super("Map Demo");
		
		// SET FRAME DIMENSIONS
		this.setSize(900, 700);

		// CLOSE THE APPLICATION IF SOMEONE HITS THE FRAME'S X BUTTON
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// INITIALIZE A DEFAULT MAP
		loadDefaultMap();
		
		// PLACE ALL NECESSARY COMPONENTS
		layoutGUI();
		
		// SETUP IMAGE SELECTION
		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);

		// USED FOR FILE SAVING
		selectedFile = null;
		savedSinceLastEdit = false;
	}
	
	/**
	 * 
	 */
	public void mapChanged()
	{
		savedSinceLastEdit = false;
	}
	
	/**
	 * This method uses a binary stream to read in map data from
	 * a file and load it into our Map Manager.
	 */
	public void loadMap(File mapFile)
	{
		
		// I'M LOADING THE IMAGES EACH TIME, NOT SMART, BUT
		// THAT'S BECAUSE WE'RE ALWAYS USING THE SAME IMAGES
		// WE COULD CHANGE THIS TO USE DIFFERENT TILE SETS EASILY
		tiledLayerTileSet = this.loadTiledTileSet();
		//setWeWant = tiledLayerTileSet;
		sparseLayerTileSet = this.loadSparseTileSet();
		loadCursors(tiledLayerTileSet, sparseLayerTileSet);
		//DataInputStream dis;
		BufferedReader buff;
		
		try
		{
			//FileInputStream fis = new FileInputStream(mapFile);
			//dis = new DataInputStream(fis);
			
			buff = new BufferedReader(new FileReader(mapFile));

			
			// LET'S USE A SIMPLE BINARY FILE FORMAT
			// - TILED LAYER TILE WIDTH
			// - TILED LAYER TILE HEIGHT
			// - TILED LAYER COLUMNS
			// - TILED LAYER ROWS
			// - TILED LAYER MAP
			// - NUM SPARSE TILES
			// - SPARSE TILES
			//int tileWidth = dis.readInt();
			//int tileHeight = dis.readInt();
			
			int columns = mapData.getColumns();
			//dis.readChar();
			int rows = mapData.getRows();
			
			
			
			mapData = new MapManager(tiledLayerTileSet, 64, 64, columns, rows, sparseLayerTileSet);
			
			
			for (int i = 0; i < columns; i++)
			{
				for (int j = 0; j < rows; j++)
				{
					//dis.readChar();
					int id = Integer.parseInt(buff.readLine());
					mapData.setTileAtMapLocation(id, i, j);
				}
			}
			
			/*int numSparseTiles = dis.readInt();
			for (int i = 0; i < numSparseTiles; i++)
			{
				int id = dis.readInt();
				int x = dis.readInt();
				int y = dis.readInt();
				mapData.putSparseTile(id, x, y);
			}*/
			mapPanel.repaint();
			buff.close();
		}
		catch(IOException ioe)
		{
			JOptionPane.showMessageDialog(this, "Error reading " + mapFile.getName());
		}
		catch(IllegalMapManagerException imme)
		{
			JOptionPane.showMessageDialog(this, mapFile.getName() + " loaded with illegal map data");
		}
		
	}
		
	public void saveTiledMap(File mapFile)
	{
		
		try
		{
			
			
			//FileOutputStream fos = new FileOutputStream(mapFile);
			//DataOutputStream dos = new DataOutputStream(fos);
			BufferedWriter buff;
			// LET'S USE A SIMPLE BINARY FILE FORMAT
			// - TILED LAYER TILE WIDTH
			// - TILED LAYER TILE HEIGHT
			// - TILED LAYER COLUMNS
			// - TILED LAYER ROWS
			// - TILED LAYER MAP
			// - NUM SPARSE TILES
			// - SPARSE TILES
			
			PrintWriter out = new PrintWriter(mapFile);
			/*out.println("Current Tiled Layer Tile Width is " + mapData.getTileWidth());
			out.println("Current Tiled Layer Tile Height is " + mapData.getTileHeight());
			out.println("Current Number of Columns are " + mapData.getColumns());
			out.println("Current Number or Rows are " + mapData.getRows());
			out.println();
			*/
			
			
			/*
			dos.writeInt(mapData.getColumns());
			dos.writeChar('\n');
			dos.writeInt(mapData.getRows());
			*/
			/*
			out.print(mapData.getColumns());
			out.println();
			out.print(mapData.getRows());
			*/
			
			for (int i = 0; i < mapData.getRows(); i++)
			{
				for (int j = 0; j < mapData.getColumns(); j++)
				{
					
					out.print(mapData.getTileAtMapLocation(j,i));
					out.println();
					/*
					dos.writeChar('\n');
					dos.writeInt(mapData.getTileAtMapLocation(j, i));	
					*/
				}
			}
			/*
			out.println();
			out.println("There are currently " + mapData.getNumSparseTiles() + " Sparse Tiles" +
				         " on the map.");
			Iterator<MapManager.SparseTile> it = mapData.sparseTilesIterator();
			while(it.hasNext())
			{
				MapManager.SparseTile st = it.next();
	
				out.println(st.getMapY() + ";" + st.getMapX() + ";" + st.getImageID() + ",");
			
			
			}
			*/
			//dos.close();
			out.close();
			
		}
		catch(IOException ioe)
		{
			JOptionPane.showMessageDialog(this, "Error Saving to " + mapFile.getName());
		}
	}
	
	
	
	public void saveSparseMap(File mapFile)
	{
		
		try
		{
			
			
			FileOutputStream fos = new FileOutputStream(mapFile);
			PrintStream out = new PrintStream(fos);
			
			// LET'S USE A SIMPLE BINARY FILE FORMAT
			// - TILED LAYER TILE WIDTH
			// - TILED LAYER TILE HEIGHT
			// - TILED LAYER COLUMNS
			// - TILED LAYER ROWS
			// - TILED LAYER MAP
			// - NUM SPARSE TILES
			// - SPARSE TILES
			
			
			/*out.println("Current Tiled Layer Tile Width is " + mapData.getTileWidth());
			out.println("Current Tiled Layer Tile Height is " + mapData.getTileHeight());
			out.println("Current Number of Columns are " + mapData.getColumns());
			out.println("Current Number or Rows are " + mapData.getRows());
			out.println();
			*/
			
			/*
			for (int i = 0; i < mapData.getRows(); i++)
			{
				for (int j = 0; j < mapData.getColumns(); j++)
				{
					out.println(mapData.getTileAtMapLocation(j, i));
		
				}
			}*/
			
			Iterator<MapManager.SparseTile> it = mapData.sparseTilesIterator();
			while(it.hasNext())
			{
				MapManager.SparseTile st = it.next();
	
				out.println(st.getMapX());
				out.println(st.getMapY());
			
			
			}
			
			out.close();
			
		}
		catch(IOException ioe)
		{
			JOptionPane.showMessageDialog(this, "Error Saving to " + mapFile.getName());
		}
	}
	
	
	
	/**
	 * This method loads the images for the tiled tile set.
	 */
		
		
	public Vector<Image> loadTiledTileSet()
	{
		// WE'LL NEED THIS TO LOAD IMAGES
		Toolkit tk = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);
		Vector<Image> tiles = new Vector<Image>();
		//nameStorage = new Vector<String>();
	
		
		
		File images = new File("./images"); // Directory is just a list of files
		
		if(images.isDirectory()){ 
			filenames = images.list();
			for(int i = 0; i < filenames.length ; ++i)
			{
				if(filenames[i].contains(".png"))
				{
					
					//nameStorage.add(filenames[i].toString());   
					
					Image something = tk.createImage(filenames[i]);
					
					tracker.addImage(something, i);
					try { tracker.waitForAll(); }
					catch(InterruptedException ie) {}
					tiles.add(something);
				}
			}
		}
		
		
		/*for{
		// LOAD OUR TILED IMAGES
			Image im1 = tk.createImage("wall.png");
			Image im2 = tk.createImage("blind.png");
			Image im3 = tk.createImage("Im3.png");
			Image im4 = tk.createImage("Im4.png");
			Image im5 = tk.createImage("Im5.png");
			
			// MAKE SURE THEY'RE LOADED INTO MEMORY BEFORE CONTINUING
			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(im1, 0);
			tracker.addImage(im2, 1);
			tracker.addImage(im3, 2);
			tracker.addImage(im4, 3);
			tracker.addImage(im5, 4);
		}*/
		

		// ADD THEM TO OUR TILE SET
		
		
		return tiles;
	}

	/**
	 * This method loads the images for the sparse layer tile set.
	 */
	public Vector<Image> loadSparseTileSet()
	{
		// LOAD OUR SPARSE LAYER IMAGES
		Image im6 = this.loadImageWithColorKey("still.png", 64, 224, 224);
		Image im7 = this.loadImageWithColorKey("Im7.png", 64, 224, 224);
		Image im8 = this.loadImageWithColorKey("Im8.png", 64, 224, 224);
		Image im9 = this.loadImageWithColorKey("Im9.png", 64, 224, 224);
		Image im10 = this.loadImageWithColorKey("Im10.png", 64, 224, 224);
		
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(im6, 6);
		tracker.addImage(im7, 7);
		tracker.addImage(im8, 8);
		tracker.addImage(im9, 9);
		tracker.addImage(im10, 10);
		
		Vector<Image> sparseTileSet = new Vector<Image>();
		sparseTileSet.add(im6);
		sparseTileSet.add(im7);
		sparseTileSet.add(im8);
		sparseTileSet.add(im9);
		sparseTileSet.add(im10);
		
		try { tracker.waitForAll(); }
		catch(InterruptedException ie) {}

		return sparseTileSet;
	}

	/**
	 * This method sets up all our cursors, which will display
	 * as the currently selected image tile.
	 */
	public void loadCursors(Vector<Image> tiledTileSet, Vector<Image> sparseTileSet)
	{
		// MAKE A CUSTOM CURSOR
		Point hotSpot = new Point(0,0);
		cursors = new Vector<Cursor>();
		Iterator<Image> it = tiledTileSet.iterator();
		while (it.hasNext())
		{
			Image image = it.next();
			cursors.add(Toolkit.getDefaultToolkit().createCustomCursor(image, hotSpot, "Map Editing Cursor"));
		}
		
		it = sparseTileSet.iterator();
		while (it.hasNext())
		{
			Image image = it.next();
			cursors.add(Toolkit.getDefaultToolkit().createCustomCursor(image, hotSpot, "Map Editing Cursor"));
		}
		setCursor(cursors.get(0));				
	}

	/**
	 * This method loads a default map for when a new file is created,
	 * like when the application first starts.
	 */
	public void loadDefaultMap()
	{
		Vector<Image> tiledTileSet = loadTiledTileSet();
		setWeWant = tiledTileSet;
		Vector<Image> sparseTileSet = loadSparseTileSet();
		loadCursors(tiledTileSet, sparseTileSet);
		
		// INITIALIZE OUR MAP, BY DEFAULT ALL TILES WILL
		// START AS THE TILE AT INDEX 0 IN THE TILE SET VECTOR
		try
		{
			mapData = new MapManager( tiledTileSet, 64, 64, 40, 40, sparseTileSet);
		}
		catch(IllegalMapManagerException imme)
		{
			JOptionPane.showMessageDialog(this, imme.getMessage(), imme.getMessage(), JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	/**
	 * This method is for layout out all components inside our frame.
	 */
	public void layoutGUI()
	{
		
		
		
		// ADD WE HAVE AT THE MOMENT IS OUR MAP PANEL
		mainPanel = new JPanel();
		
		mapPanel = new MapPanel(this);
		//scrollPane = new JScrollPane(mapPanel);
		
		
		// SETUP THE MENU BAR
		comboPanel = new ComboBoxPanel();
		comboToolBar = new JToolBar();
		drawBar = new JToolBar();
		comboBoxModel = new DefaultComboBoxModel();
		comboBox = new JComboBox(comboBoxModel);
		menuBar = new JMenuBar();
		borderLayout = new BorderLayout();
		mainPanel.setLayout(borderLayout);
		northPanel = new JPanel();
		southPanel = new JPanel();
		eastPanel = new JPanel();
		fileMenu = new JMenu("File");
		newMapMenuItem = new JMenuItem("New Map");
		loadMapMenuItem = new JMenuItem("Load Map");
		saveMapMenuItem = new JMenuItem("Save Map");
		exitMenuItem = new JMenuItem("Exit");
		menuBar.add(fileMenu);
		fileMenu.add(newMapMenuItem);
		fileMenu.add(loadMapMenuItem);
		fileMenu.add(saveMapMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		gridLayout = new GridLayout(0, 1);
		newMapHandler = new NewMapHandler();
		loadMapHandler = new LoadMapHandler();
		saveMapHandler = new SaveMapHandler();
		exitHandler = new ExitHandler();
		myComboListSelectionHandler = new ComboListSelectionHandler();
		newMapMenuItem.addActionListener(newMapHandler);
		loadMapMenuItem.addActionListener(loadMapHandler);
		saveMapMenuItem.addActionListener(saveMapHandler);
		exitMenuItem.addActionListener(exitHandler);
		comboBox.addItemListener(myComboListSelectionHandler);
		northPanel.add(menuBar);
		
		//
		comboToolBar.setLayout(gridLayout);
		//comboToolBar.add(comboPanel);
		comboToolBar.addSeparator();
	
		//
		
		//
		//drawBar.setLayout(gridLayout);
		//drawBar.add(mapPanel);
		//drawBar.addSeparator();
	
		//
		
		//southPanel.add(mapPanel);
		eastPanel.add(comboPanel);
		//System.out.println("eoifniofwenoieno");
		//southPanel.add(drawBar);
		//this.setJMenuBar(menuBar);
		//this.setLayout();
		
		menuBar.setBackground(Color.yellow);
		//this.add(northPanel, borderLayout.NORTH);
		comboToolBar.setPreferredSize(new Dimension(125,500));
		drawBar.setPreferredSize(new Dimension(500,900));
		mainPanel.add(northPanel, borderLayout.NORTH);
		mainPanel.add(mapPanel, borderLayout.CENTER);
		mainPanel.add(eastPanel, borderLayout.EAST);
		this.add(mainPanel);
		
		
		
		
		
		
		/*mapPanel.addFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		        mapPanel.requestFocusInWindow();
		    }
		});*/
		
		/*
		Toolkit tk = Toolkit.getDefaultToolkit();
		myScreenWidth = tk.getScreenSize().width;
		myScreenHeight = tk.getScreenSize().height;
		GridBagConstraints c = new GridBagConstraints();

		//Center the GUI to the center of the screen but make y = 0 so the gui dosent fall below start bar
		this.setLocation((myScreenWidth / 2) - (700 / 2), 0);

		//When app starts, what dialog boxes should be shown.
		

		//Certen buttons will be hidden untill the user needs them


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
		
		*/
	}

	/**
	 * Accessor method for getting the map data. We provide this for the
	 * panel, since its mouse handlers will need to change the map. This
	 * allows us to share the map between the frame and the panel.
	 */
	public MapManager getMapData()
	{
		return mapData;
	}

	/**
	 * This method is for loading images for the sparse layer. It will make
	 * all pixels with the color key transparent.
	 */
	public Image loadImageWithColorKey(String fileName, int redKey, int greenKey, int blueKey)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.createImage(fileName);
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(img, 0);
		try { tracker.waitForAll(); }
		catch(Exception e ) { e.printStackTrace(); }
		BufferedImage imageToLoad = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = imageToLoad.getGraphics();
		g.drawImage(img, 0,	0,	null);
		
		// NOW MAKE ALL PIXELS WITH COLOR (64, 224, 224) TRANSPARENT
		WritableRaster raster = imageToLoad.getRaster();
		DataBuffer pixels = raster.getDataBuffer();
		int[] dummy = null;
		for (int i = 0; i < raster.getWidth(); i++)
		{
			for (int j = 0; j < raster.getHeight(); j++)
			{
				int[] pixel = raster.getPixel(i, j, dummy);
				if ((pixel[0] == redKey)
					&& (pixel[1] == greenKey)
					&& (pixel[2] == blueKey))
				{
					pixel[3] = 0;
					raster.setPixel(i, j, pixel);
				}
			}
		}		
		return imageToLoad;
	}
	
	/**
	 * This method asks the user for the name of the
	 * file to save our archive to.
	 */
	public void promptToSaveTiled()
	{
		
		
		JFileChooser jfc = new JFileChooser(".");
		int selection = jfc.showSaveDialog(this);
		
		
		
		// WE'LL ONLY DO SOMETHING IF THE USER PRESSED OK
		if (selection == JOptionPane.OK_OPTION)
		{
			File file = jfc.getSelectedFile();
			this.saveTiledMap(file);
		}
	}	
	
	public void promptToSaveSparse()
	{
		
		
		JFileChooser jfc = new JFileChooser(".");
		int selection = jfc.showSaveDialog(this);
		
		
		
		// WE'LL ONLY DO SOMETHING IF THE USER PRESSED OK
		if (selection == JOptionPane.OK_OPTION)
		{
			File file = jfc.getSelectedFile();
			this.saveSparseMap(file);
		}
	}	
	
	/**
	 * This method is for saving our archive. If we know
	 * where to save it we will do so, otherwise we'll have
	 * to ask the user for a file name.
	 */
	public boolean verifyTiledSave()
	{
		// IF WE ALREADY KNOW WHERE WE ARE SAVING IT
		if (selectedFile != null)
		{
			saveTiledMap(selectedFile);
			return true;
		}
		// OR ELSE WE NEED TO ASK THE USER FOR A FILE NAME
		else
		{
			int selection = JOptionPane.showConfirmDialog(	this,
				"Would you like to save your Tiled map first?",
				"Save Current Archive?",
				JOptionPane.YES_NO_CANCEL_OPTION);
			
			// THIS IS FOR A NEW FILE
			if (selection == JOptionPane.YES_OPTION)
			{
				promptToSaveTiled();
			}
			// IN CASE THE USER DECIDES AGAINST SAVING THE CURRENT FILE
			else if (selection == JOptionPane.NO_OPTION)
			{
				this.loadDefaultMap();
				return true;
			}
			// IF THE USER HIT CANCEL, WE'LL DO NOTHING		
			return false;
		}
	}	
	
	public boolean verifySparseSave()
	{
		// IF WE ALREADY KNOW WHERE WE ARE SAVING IT
		if (selectedFile != null)
		{
			saveSparseMap(selectedFile);
			return true;
		}
		// OR ELSE WE NEED TO ASK THE USER FOR A FILE NAME
		else
		{
			int selection = JOptionPane.showConfirmDialog(	this,
				"Would you like to save your Sparse map first?",
				"Save Current Archive?",
				JOptionPane.YES_NO_CANCEL_OPTION);
			
			// THIS IS FOR A NEW FILE
			if (selection == JOptionPane.YES_OPTION)
			{
				promptToSaveSparse();
			}
			// IN CASE THE USER DECIDES AGAINST SAVING THE CURRENT FILE
			else if (selection == JOptionPane.NO_OPTION)
			{
				this.loadDefaultMap();
				return true;
			}
			// IF THE USER HIT CANCEL, WE'LL DO NOTHING		
			return false;
		}
	}	
	

	// THIS WILL HANDLE OUR KEY PRESSES FOR SELECTING
	// THE TILE TO USE FOR MAP EDITING
	class KeyHandler implements KeyListener
	{
		public void keyPressed(KeyEvent ke) 	
		{
			
			/*
			
			int panelWidth = getWidth();
			int x = me.getX();
			double percentX = ((double)x)/((double)panelWidth);
			if (percentX > (1-THRESHOLD))
				map.moveViewportX(INC);
			else if (percentX < THRESHOLD)
				map.moveViewportX(-INC);
			else
				xUnchanged = true;

			// IF THE MOUSE IS OVER THE TOP THRESHOLD, SCROLL UP
			// IF THE MOUSE IS OVER THE BOTTOM THRESHOLD, SCROLL DOWN
			// AGAIN, WE SIMPLY MOVE THE VIEWPORT
			boolean yUnchanged = false;
			int panelHeight = getHeight();
			int y = me.getY();
			double percentY = ((double)y)/((double)panelHeight);
			if (percentY > (1-THRESHOLD))
				map.moveViewportY(INC);
			else if (percentY < THRESHOLD)
				map.moveViewportY(-INC);
			else
				yUnchanged = true;

			// ONLY REDRAW THE MAP IF WE MOVED THE VIEWPORT
			if (xUnchanged && yUnchanged)
				return;
			else
				repaint();
			
			
			
			*/
			
			
			int keyCode = ke.getKeyCode();
			
			
			if (keyCode == KeyEvent.VK_UP)
			{
				mapData.moveViewportY(-20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_DOWN)
			{
				mapData.moveViewportY(20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_LEFT)
			{
				mapData.moveViewportX(-20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_RIGHT)
			{
				mapData.moveViewportX(20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_W)
			{
				mapData.moveViewportY(-20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_S)
			{
				mapData.moveViewportY(20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_A)
			{
				mapData.moveViewportX(-20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_D)
			{
				mapData.moveViewportX(20);
				repaint();
			}
			
			
			
			/*if ((keyCode >= KeyEvent.VK_0)
					&&
				(keyCode <= KeyEvent.VK_9))
			{
				Cursor cursorToUse;
				boolean editTiledLayer;
				int imageIndexToUse;
				if (keyCode == KeyEvent.VK_0)
				{
					cursorToUse = cursors.get(9);
					editTiledLayer = false;
					imageIndexToUse = 4;
					TheMapper.this.menuBar.setBackground(Color.pink);
				}
				else
				{
					int key = keyCode - 0X31;
					cursorToUse = cursors.get(key);					
					if ((keyCode >= KeyEvent.VK_1) && (keyCode <= KeyEvent.VK_5))
					{
						TheMapper.this.menuBar.setBackground(Color.yellow);
						editTiledLayer = true;
						imageIndexToUse = key;
					}
					else
					{
						TheMapper.this.menuBar.setBackground(Color.pink);
						editTiledLayer = false;
						imageIndexToUse = key - 5;
					}
				}
				TheMapper.this.setCursor(cursorToUse);
				mapPanel.setActiveTileAndLayer(imageIndexToUse, editTiledLayer);
			}*/
		}

		public void keyReleased(KeyEvent ke) 	{}
		public void keyTyped(KeyEvent ke) 		{}		
	}

	// THIS WILL HANDLE CREATING A NEW MAP
	class NewMapHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			if (!savedSinceLastEdit)
			{
				boolean startNewTiledArchive = verifyTiledSave();
				if (startNewTiledArchive)
				{
					TheMapper.this.loadDefaultMap();
					mapPanel.repaint();
					selectedFile = null;
				}
			}
			else
			{
				TheMapper.this.loadDefaultMap();
				mapPanel.repaint();
				selectedFile = null;
			}			
		}
	}

	// THIS WILL HANDLE LOADING AN EXISTING MAP
	class LoadMapHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if (!savedSinceLastEdit)
			{
				boolean openTiledArchive = verifyTiledSave();
				if (openTiledArchive)
				{
					promptToLoad();
				}
			}
			else
			{
				promptToLoad();
			}			
		}

		public void promptToLoad()
		{
			JFileChooser jfc = new JFileChooser(".");
			int selection = jfc.showOpenDialog(TheMapper.this);
			if (selection == JOptionPane.OK_OPTION)
			{
				File file = jfc.getSelectedFile();
				TheMapper.this.loadMap(file);
			}
		}			
	}
	
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
				selectedIndex = comboBoxModel.getIndexOf(selection);
				mapPanel.setActiveImageID(selectedIndex);
			}
		}
	}

	// THIS WILL HANDLE SAVING THE CURRENT MAP
	class SaveMapHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			if (!savedSinceLastEdit)
			{
				if (TheMapper.this.selectedFile != null)
				{
					TheMapper.this.saveTiledMap(TheMapper.this.selectedFile);
				}
				else
				{
					TheMapper.this.promptToSaveTiled();
				}
			}
		}
	}

	// THIS WILL HANDLE EXITING THE APPLICATION
	class ExitHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			if (!savedSinceLastEdit)
			{
				boolean exitTiledProgram = verifyTiledSave();
				if (exitTiledProgram)
				{
					System.exit(0);
				}
			}
			else
				System.exit(0);			
		}
	}
	
	/*class CSVFileFilter extends FileFilter
	{
		public boolean accept(File file) 
		{
			return file.getName().endsWith(".txt");
		}

		public String getDescription() 
		{
			return ".txt";
		}
	}*/
	
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
	
	

	
	protected class ComboBoxPanel extends JPanel
	                          implements ActionListener {
	    JLabel picture;
	    JComboBox tileList;
	    Vector<String> mod = new Vector<String>();
	    public ComboBoxPanel() {
	    	
	        super(new BorderLayout());
	       // System.out.println("I got here");
	        

	        //Create the combo box, select the item at index 4.
	        //Indices start at 0, so 4 specifies the pig.
	        for(int i = 0; i < filenames.length; ++ i)
	        {
	        	
	        	if(filenames[i].contains(".png"))
	        	{
	        		mod.add(filenames[i]);
	        	}
			        tileList = new JComboBox(mod);
			        tileList.addActionListener(this);
		
			        //Set up the picture.
			        picture = new JLabel();
			        picture.setHorizontalAlignment(JLabel.CENTER);
			        updateLabel(filenames[tileList.getSelectedIndex()]);
			        picture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		
			        //The preferred size is hard-coded to be the width of the
			        //widest image and the height of the tallest image + the border.
			        //A real program would compute this.
			        picture.setPreferredSize(new Dimension(177, 122+10));
		
			        //Lay out the demo.
			        add(tileList, BorderLayout.PAGE_START);
			        add(picture, BorderLayout.PAGE_END);
			        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			        
	        	
	        }
	        
	        //test();
	    }
	    
	    
	    /*public void test()
	    {
	    	System.out.println("I got here also");
	        
	    	wqeqwe
	    	
	    	
	    	
	    	if(filenames != null)
	    	{
	    	
		        for(int i = 0; i < TheMapper.this.filenames.length; ++ i)
		        {
		        	
		        	String tileName = TheMapper.this.filenames[i];
		        	//System.out.println(tileName);
		        	updateLabel(tileName);
		        }
	    	}
	    }*/
	    
	    /** Listens to the combo box. */
	    public void actionPerformed(ActionEvent e) {
	    	
	
	    	JComboBox cb = (JComboBox)e.getSource();
	        String tileName = (String)cb.getSelectedItem();
	        updateLabel(tileName);
	        Cursor currentCursor = cursors.get(tileList.getSelectedIndex());
	        TheMapper.this.setCursor(currentCursor);
			mapPanel.setActiveTileAndLayer(tileList.getSelectedIndex(), true);
			mapPanel.requestFocus();

	        repaint();
	        
	        /*if(filenames != null)
	    	{
	    	
		        for(int i = 0; i < TheMapper.this.filenames.length; ++ i)
		        {
		        	if(filenames[i].contains(".png"))
		        	{
			        	String tileName = TheMapper.this.filenames[i];
			        	//System.out.println(tileName);
			        	updateLabel(tileName);
		        	}
		        }
	    	}*/
	    }

	    protected void updateLabel(String name) {
	    	if(name.contains(".png"))
	    	{
		        ImageIcon icon = new ImageIcon(name);
		        picture.setIcon(icon);
		        repaint();
		        
		        if (icon != null) {
		            picture.setText(null);
		        }
	    	}
	    }

	    /** Returns an ImageIcon, or null if the path was invalid. */
	   /* protected ImageIcon createImageIcon(String path) {
	        java.net.URL imgURL = ComboBoxPanel.class.getResource(path);
	        System.out.println(imgURL);
	        if (imgURL != null) {
	            return new ImageIcon(imgURL);
	        } else {
	            System.err.println("Couldn't find file: " + path);
	            return null;
	        }
	    }*/

	    /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     */
	    /*private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("ComboBoxDemo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Create and set up the content pane.
	        JComponent newContentPane = new ComboBoxDemo();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }

	    public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }*/
	}
	
	/**
	 * This is where the application starts. It constructs a frame and
	 * then opens it in event handing mode.
	 */
	public static void main(String[] args)
	{
		TheMapper frame = new TheMapper();
		frame.setVisible(true);
	}
}