import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
/**
 * This is our drawing surface for the MapDemo application.
 * We will render the contents of the MapManager onto this
 * panel. We'll also listen for mouse events on this panel
 * to control scrolling and map editing.
 */
public class MapPanel extends JPanel
{
	// THIS IS THE TILE THE USER CHOSE AND WILL BE USED FOR MAP EDITING
	private int activeTileID;
	private boolean editingTiledLayer;
	
	// HERE'S A POINTER TO THE FRAME, WHICH WE MAY NEED TO TALK TO
	private TheMapper mainGUI;
	
	// THIS WILL HANDLE MOUSE CLICK MAP EDITING
	private MouseHandler mouseHandler;
	
	
	private KeyHandler keyHandler;
	
	// THIS WILL HANDLE SCROLLING AND MAP EDITING
	private MouseMotionHandler mouseMotionHandler;
	
	private String columnString;
	private String rowString;
	private String activeIDString;
	private String csvString;
	private int myActiveImageID;
	/**
	 * This is the only constructor available, it initializes the
	 * panel by setting up the mouse event listener.
	 * 
	 */
	
	public int getActiveImageID() 
	{
		return myActiveImageID;
	}

	public void setActiveImageID(int activeImageID) 
	{
		myActiveImageID = activeImageID;
	}

	public MapPanel(TheMapper initMainGUI)
	{
		mainGUI = initMainGUI;
		mouseMotionHandler = new MouseMotionHandler();
		addMouseMotionListener(mouseMotionHandler);
		mouseHandler = new MouseHandler();
		keyHandler = new KeyHandler();
		addMouseListener(mouseHandler);
		addKeyListener(keyHandler);
		
		// SET THE DEFAUL IMAGE FOR EDITING
		activeTileID = 0;
		editingTiledLayer = true;
	}

	/**
	 * 
	 */
	public void setActiveTileAndLayer(int initActiveTileID, boolean initEditingTiledLayer)
	{
		activeTileID = initActiveTileID;
		editingTiledLayer = initEditingTiledLayer;
	}
	
	/**
	 * This method does all map rendering. It does so using the
	 * tile set and map layout as specified in the application's
	 * map manager.
	 */
	public void paintComponent(Graphics g)
	{
		// ONLY TRY TO RENDER IF THIS PANEL HAS BEEN SIZED
		// AND LAID OUT INSIDE THE FRAME ALREADY
		if (getWidth() > 0)
		{
			// THIS IS THE APPLICATION'S MAP WE'RE RENDERING
			MapManager map = mainGUI.getMapData();
			
			// ALL RENDERING WILL GO HERE
			
			// MAYBE THE USER RESIZED THE FRAME, AND THUS THE PANEL
			map.setViewportSize(getWidth(), getHeight());
			
			// LET'S GET TEMP VALUES TO USE IN OUR CALCULATIONS
			int minCol = map.calculateMinVisibleColumn();
			int maxCol = map.calculateMaxVisibleColumn();
			int minRow = map.calculateMinVisibleRow();
			int maxRow = map.calculateMaxVisibleRow();
			
			// HERE ARE THE TOP AND LEFT EDGE LOCATIONS
			// OF THE MINIMUM TILES WE NEED TO RENDER
			int leftEdgeOfMinCol = minCol * map.getTileWidth();
			int topEdgeOfMinRow = minRow * map.getTileHeight();
			
			// WE'LL USE x TO CALCULATE THE x-AXIS SCREEN
			// COORDINATE OF EACH TILE TO BE RENDERED
			int x = leftEdgeOfMinCol - map.getViewportX();

			// RENDER ALL THE TILES THAT ARE VISIBLE
			// IN THE OUTER LOOP WE'LL COUNT OFF THE VISIBLE COLUMNS
			for (int i = minCol; i <= maxCol; i++)
			{
				// WE'LL USE y TO CALCULATE THE y-AXIS SCREEN
				// COORDINATE OF EACH TILE TO BE RENDERED
				int y = topEdgeOfMinRow - map.getViewportY();
				
				// AND IN THIS INNER LOOP WE'LL COUNT OFF THE VISIBLE ROWS
				for (int j = minRow; j <= maxRow; j++)
				{
					// RENDER THE TILE AT LOCATION (i, j)
					int imageID = map.getTileAtMapLocation(i, j);
					Image image = map.getImageWithID(imageID);
					g.drawImage(image, x, y, null);
					
					// AND MOVE ON TO THE NEXT ROW
					y += map.getTileHeight();
				}
				
				// AND MOVE ON TO THE NEXT COLUMN
				x += map.getTileWidth();
			}
			
			// NOW RENDER THE SPARSE LAYER
			LinkedList<MapManager.SparseTile> tilesToRender = map.getSparseTilesInViewport();
			Iterator<MapManager.SparseTile> it = tilesToRender.iterator();
			while(it.hasNext())
			{
				MapManager.SparseTile tileToRender = it.next();
				Image image = tileToRender.getImage();
				int tileX = tileToRender.getMapX() - map.getViewportX();
				int tileY = tileToRender.getMapY() - map.getViewportY();
				g.drawImage(image, tileX, tileY, null);
			}
		}
	}

	/**
	 * This class will serve as an event handler to respond to
	 * all mouse interactions with the panel. 
	 */
	class MouseMotionHandler implements MouseMotionListener
	{
		// THIS CONTROLS THE PERCENTAGE OF THE PANEL THAT WILL
		// TRIGGER SCROLLING IN A GIVEN DIRECTION
		static final double THRESHOLD = .1;
		
		// THIS CONTROLS HOW MUCH WE WILL INCREMENT
		// THE VIEWPORT WHEN WE SCROLL
		static final int INC = 5;

		/**
		 * This method responds to when the user presses the mouse button
		 * on the panel and drags it across the panel. This allows for
		 * the user to edit the map.
		 */
		public void mouseDragged(MouseEvent me)
		{
			// HERE'S THE APPLICATION'S MAP
			MapManager map = mainGUI.getMapData();

			// x,y IS WHERE THE MOUSE WAS JUST DRAGGED ON THE PANEL.
			int x = me.getX();
			int y = me.getY();

			if (editingTiledLayer)
			{
				
				// col,row IS THE LOCATION IN THE MAP
				int col = map.calculateColumnAtLocation(x + map.getViewportX());
				int row = map.calculateRowAtLocation(y + map.getViewportY());

				// WHICH MOUSE BUTTON IS BEING USED FOR MOUSE DRAGGING?
				int mouseModifiers = me.getModifiers();
				map.setTileAtMapLocation(activeTileID, row, col);
			/*	
				columnString = new Integer(col).toString();
				rowString = new Integer(row).toString();
				activeIDString = new Integer(activeTileID).toString();
				csvString = rowString + ";" + columnString + ";" + activeIDString + ",";
				*/
			}
			else
			{
				map.putSparseTile(activeTileID, x + map.getViewportX(), y + map.getViewportY());
			}
			// SINCE WE LIKELY JUST CHANGED THE MAP, LET'S REDRAW IT
			repaint();
		}

		/**
		 * This method responds to when the user moves the mouse while it
		 * is over the panel. This will control map scrolling.
		 */
		public void mouseMoved(MouseEvent me)
		{
			
			/*
			// HERE'S THE APPLICATION'S MAP
			MapManager map = mainGUI.getMapData();

			// WE'LL KEEP TRACK OF WHETHER WE EVEN SCROLL OR NOT BECAUSE
			// MOST OF THE TIME THE MOUSE MOVES WE WILL NOT NEED TO SCROLL
			boolean xUnchanged = false;
			
			// IF THE MOUSE IS OVER THE LEFT THRESHOLD, SCROLL LEFT
			// IF THE MOUSE IS OVER THE RIGHT THRESHOLD, SCROLL RIGHT
			// SCROLLING IS SIMPLY DONE BY MOVING THE VIEWPORT
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
				repaint();*/
		}
	}
	
	class KeyHandler implements KeyListener
	{
		public void keyPressed(KeyEvent ke) 	
		{
			
			
	
			
			int keyCode = ke.getKeyCode();
			
			
			if (keyCode == KeyEvent.VK_UP)
			{
				mainGUI.mapData.moveViewportY(-20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_DOWN)
			{
				mainGUI.mapData.moveViewportY(20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_LEFT)
			{
				mainGUI.mapData.moveViewportX(-20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_RIGHT)
			{
				mainGUI.mapData.moveViewportX(20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_W)
			{
				mainGUI.mapData.moveViewportY(-20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_S)
			{
				mainGUI.mapData.moveViewportY(20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_A)
			{
				mainGUI.mapData.moveViewportX(-20);
				repaint();
			}
			
			if (keyCode == KeyEvent.VK_D)
			{
				mainGUI.mapData.moveViewportX(20);
				repaint();
			}
			
			
			
		
		}

		public void keyReleased(KeyEvent ke) 	{}
		public void keyTyped(KeyEvent ke) 		{}		
	}
	
	class MouseHandler implements MouseListener
	{
		public void mouseClicked(MouseEvent me) 
		{
			MapPanel.this.mouseMotionHandler.mouseDragged(me);
		}

		public void mouseEntered(MouseEvent me)		{}
		public void mouseExited(MouseEvent me)		{}
		public void mousePressed(MouseEvent me)		{}
		public void mouseReleased(MouseEvent me)	{}
	}
	/*
	public String csvInfo()
	{
		
		return csvString;
	}
	*/
}