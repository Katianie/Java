import java.awt.Image;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Vector;
/**
 * This class manages all the data associated with a scrollable
 * 2D map. It stores the tile set, the viewport dimensions and
 * location, and information about the tile layout. Note that as
 * it currently exists it only represents one layer of tiles.
 */
public class MapManager
{
	// THE TILE SET
	private Vector<Image> tiles;
	
	// THE MAP LAYOUT FOR THE BOTTOM LAYER - EACH int IN THIS ARRAY IS AN IMAGE
	// INDEX IN THE tiles VECTOR
	private int[][] map;
	
	// TILE DIMENSIONS
	private int tileWidth;
	private int tileHeight;
	
	// COLUMNS AND ROWS OF TILES IN THE MAP
	private int columns;
	private int rows;
	
	// THE TILE SET FOR THE SPARSE LAYER (TOP LAYER)
	private Vector<Image> sparseLayerTileSet;
	private Vector<SparseTile> sparseLayerTiles;
	private int sparseColumns;
	private int sparseRows;
	private int sparseMinTileWidth;
	private int sparseMinTileHeight;
	private int sparseMaxTileWidth;
	private int sparseMaxTileHeight;
	private LinkedList<SparseTile> sparseTilesRenderList;
	
	// INFORMATION ABOUT THE VIEWPORT
	private int viewportX;
	private int viewportY;
	private int viewportWidth;
	private int viewportHeight;

	/**
	 * This is the only constructor provided, and to use it one must
	 * provide a valid tile set with at least one image.
	 */
	public MapManager(	Vector<Image> tileImages,
						int initTileWidth,
						int initTileHeight,
						int initRows, 
						int initColumns,
						Vector<Image> sparseLayerImages) throws IllegalMapManagerException
	{
		// TENTATIVELY INITIALIZE ALL INSTANCE VARIABLES
		tiles = tileImages;
		tileWidth = initTileWidth;
		tileHeight = initTileHeight;
		rows = initRows;
		columns = initColumns;
		sparseLayerTileSet = sparseLayerImages;

		// PROTECT THIS CLASS FROM GETTING BAD DATA WE'LL REGRET LATER ON
		// REMEMBER, FEEDBACK IS MOST VALUABLE WHEN GIVEN AT THE TIME AN ERROR OCCURS
		if (tileImages == null)
			throw new IllegalMapManagerException("Illegal Map - Tile Set Vector Cannot Be Null");
		
		if (tileImages.size() == 0)
			throw new IllegalMapManagerException("Illegal Map - Tile Set Vector Must Have At Least One Image");
		
		if ((tileWidth <= 0) || (tileHeight <= 0) || (initColumns <= 0) || (initRows <= 0))
			throw new IllegalMapManagerException("Illegal Map - All Map Size Parameters Must Be Larger Than 0");
		
		if (tiles.size() == 0)
			throw new IllegalMapManagerException("Illegal Map - You Must Provide At Least One Image Tile");

		// WE CAN USE THE DATA TO INITIALIZE A DEFAULT MAP
		// IT WILL SET THE IMAGE AT VECTOR INDEX 0 AS THE DEFAULT
		// THROUGHOUT THE ENTIRE MAP
		map = new int[rows][columns];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				map[i][j] = 0;	// DEFAULT IMAGE

		// WE'LL ONLY MAKE A SECOND LAYER IF WE HAVE A SECOND LAYER TILE SET
		if (sparseLayerTileSet.size() > 0)
		{
			sparseLayerTiles = new Vector<SparseTile>();
			sparseMinTileWidth = 0;
			sparseMinTileHeight = 0;
			sparseMaxTileWidth = 0;
			sparseMaxTileHeight = 0;
			
			Iterator<Image> it = sparseLayerTileSet.iterator();
			if (it.hasNext())
			{
				Image image = it.next();
				int w = image.getWidth(null);
				int h = image.getHeight(null);

				sparseMinTileWidth = w;
				sparseMinTileHeight = h;
				sparseMaxTileWidth = w;
				sparseMaxTileHeight = h;
				
				while(it.hasNext())
				{
					image = it.next();
					w = image.getWidth(null);
					h = image.getHeight(null);
					
					if (w < sparseMinTileWidth)
						sparseMinTileWidth = w;
					if (h < sparseMinTileHeight)
						sparseMinTileHeight = h;
					if (w > sparseMaxTileWidth)
						sparseMaxTileWidth = w;
					if (h > sparseMaxTileHeight)
						sparseMaxTileHeight = h;
				}
				
				// NOW CALCULATE THE NUMBER OF SPARSE ROWS AND COLUMNS
				// THERE COULD BE USING THE SMALLEST IMAGE AS OUR GRID
				sparseColumns = this.getMapWidth()/sparseMinTileWidth;
				sparseRows = this.getMapHeight()/sparseMinTileHeight;
			}
			sparseTilesRenderList = new LinkedList<SparseTile>();
		}
		
		// RESET THE VIEWPORT, WE MAY CHOOSE TO MOVE THIS LATER
		viewportX = 0;
		viewportY = 0;
		viewportWidth = 0;
		viewportHeight = 0;
	}
	
	// ACCESSOR METHODS
	public int getTileWidth()		{ return tileWidth; 			}
	public int getTileHeight()		{ return tileHeight; 			}
	public int getColumns()			{ return columns; 				}
	public int getRows()			{ return rows;					}
	public int getMapWidth()		{ return tileWidth * columns;	}
	public int getMapHeight()		{ return tileHeight * columns;	}
	public int getViewportX()		{ return viewportX;				}
	public int getViewportY()		{ return viewportY;				}
	public int getViewportWidth()	{ return viewportWidth;			}
	public int getViewportHeight()	{ return viewportHeight;		}
	
	public int getNumSparseTiles() { return sparseLayerTiles.size(); }
	public Iterator<SparseTile> sparseTilesIterator() { return sparseLayerTiles.iterator(); }

	public int getTileAtMapLocation(int mapRow, int mapColumn)
	{
		return map[mapRow][mapColumn];
	}

	public Image getImageWithID(int id)
	{
		return tiles.get(id);
	}	

	// MUTATOR METHODS
	public void setViewportSize(int initViewportWidth, int initViewportHeight)
	{
		viewportWidth = initViewportWidth;
		viewportHeight = initViewportHeight;
	}

	/**
	 * This mutator method is particularly important.
	 * It lets us change a tile in our map.
	 */
	public void setTileAtMapLocation(int tileID, int mapColumn, int mapRow)
	{
		if ((mapColumn >=0) 
				&& (mapRow >= 0)
				&& (mapColumn < columns) 
				&& (mapRow < rows))
		map[mapRow][mapColumn] = tileID;
	}

	/**
	 * For the current map width, this returns the column
	 * number of the tile at pixel column x.
	 */
	public int calculateColumnAtLocation(int x)
	{
		if ((x < 0) || (x >= getMapWidth()))
			return -1;
		else
			return x/tileWidth;
	}
	
	/**
	 * For the current map height, this returns the row
	 * number of the tile at pixel row y.
	 */
	public int calculateRowAtLocation(int y)
	{
		if ((y < 0) || (y >= getMapHeight()))
			return -1;
		else
			return y/tileHeight;
		
	}

	/**
	 * According to the current viewport location and dimensions,
	 * this will return the left-most tile column number that is visible
	 * inside the viewport.
	 */
	public int calculateMinVisibleColumn()
	{
		return calculateColumnAtLocation(viewportX);
	}
	
	/**
	 * According to the current viewport location and dimensions,
	 * this will return the right-most tile column number that is visible
	 * inside the viewport.
	 */
	public int calculateMaxVisibleColumn()
	{
		return calculateColumnAtLocation(viewportX + viewportWidth - 1);
	}
	
	/**
	 * According to the current viewport location and dimensions,
	 * this will return the top-most tile row number that is visible
	 * inside the viewport.
	 */
	public int calculateMinVisibleRow()
	{
		return calculateRowAtLocation(viewportY);
	}
	
	/**
	 * According to the current viewport location and dimensions,
	 * this will return the bottom-most tile column number that is visible
	 * inside the viewport.
	 */
	public int calculateMaxVisibleRow()
	{
		return calculateRowAtLocation(viewportY + viewportHeight - 1);
	}

	/**
	 * 
	 */
	public void putSparseTile(int imageID, int x, int y)
	{
		Image image = sparseLayerTileSet.get(imageID);
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		int minColumn = calculateSparseColumn(x);
		int minRow = calculateSparseRow(y);
		int maxColumn = (x + w)/sparseMinTileWidth;
		int maxRow = (y + h)/sparseMinTileHeight;
		boolean occupied = sparseCellRangeIsOccupied(minColumn, minRow, maxColumn, maxRow);
		if (!occupied)
		{
			SparseTile tileToAdd = new SparseTile(imageID, minColumn, minRow, w, h);
			sparseLayerTiles.add(tileToAdd);
		}
	}
	
	/**
	 * 
	 */
	public boolean sparseCellRangeIsOccupied(int minCol, int minRow, int maxCol, int maxRow)
	{
		// NOW TEST TO SEE IF THIS WOULD OVERLAP AN EXISTING SPARSE TILE
		Iterator<SparseTile> it = sparseLayerTiles.iterator();
		while(it.hasNext())
		{
			SparseTile tileToTest = it.next();
			if (sparseTileOverlapsRange(tileToTest, minCol, minRow, maxCol, maxRow))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	public boolean sparseTileOverlapsRange(SparseTile testTile, int minCol, int minRow, int maxCol, int maxRow)
	{
		int tileMaxCol = testTile.getMaxColumn();
		int tileMaxRow = testTile.getMaxRow();
		if (testTile.column > maxCol)
			return false;
		else if (testTile.row > maxRow)
			return false;
		else if (tileMaxCol < minCol)
			return false;
		else if (tileMaxRow < minRow)
			return false;
		else
			return true;
	}
		
	/**
	 * 
	 */
	public int calculateSparseColumn(int x)
	{
		return x/sparseMinTileWidth;
	}
	
	/**
	 * 
	 */
	public int calculateSparseRow(int y)
	{
		return y/sparseMinTileHeight;
	}
	
	/**
	 * 
	 */
	public LinkedList<SparseTile> getSparseTilesInViewport()
	{
		sparseTilesRenderList.clear();

		// NOW TEST THE SPARSE LAYER TO FIND THE ONES IN THE VIEWPORT
		Iterator<SparseTile> it = sparseLayerTiles.iterator();
		while(it.hasNext())
		{
			SparseTile tileToTest = it.next();
			if (sparseTileIsInViewport(tileToTest))
			{
				sparseTilesRenderList.add(tileToTest);
			}
		}
		
		return sparseTilesRenderList;
	}
	
	/**
	 * 
	 */
	public boolean sparseTileIsInViewport(SparseTile testTile)
	{
		int tileX = testTile.getMapX();
		int tileY = testTile.getMapY();
		if (tileX > (viewportX + viewportWidth))
			return false;
		else if ((tileX + testTile.width) < viewportX)
			return false;
		else if (tileY > (viewportY + viewportHeight))
			return false;
		else if ((tileY + testTile.height) < viewportY)
			return false;
		else
			return true;
	}
	
	/**
	 * This method is for moving the viewport side to side. It will not allow
	 * the viewport to go outside the world either to the left (less than 0 on the map)
	 * or right (off the right-most edge of the map). It will simply clamp values
	 * at the extremes.
	 */
	public void moveViewportX(int inc)
	{
		viewportX += inc;
		int mapWidth = getMapWidth();
		if (viewportX < 0)
			viewportX = 0;
		else if (viewportX > (mapWidth - viewportWidth))
			viewportX = mapWidth - viewportWidth - 1;
	}
	
	/**
	 * This method is for moving the viewport up and down. It will not allow
	 * the viewport to go outside the world either to the top (less than 0 on the map)
	 * or bottom (off the bottom-most edge of the map). It will simply clamp values
	 * at the extremes.
	 */
	public void moveViewportY(int inc)
	{
		viewportY += inc;
		int mapHeight = getMapHeight();
		if (viewportY < 0)
			viewportY = 0;
		else if (viewportY > (mapHeight - viewportHeight))
			viewportY = mapHeight - viewportHeight - 1;
	}
	
	public class SparseTile
	{
		private int imageID;
		private int column;
		private int row;
		private int width;
		private int height;
		
		public SparseTile(	int initImageID,
							int initColumn,
							int initRow,
							int initWidth,
							int initHeight)
		{
			imageID = initImageID;
			column = initColumn;
			row = initRow;
			width = initWidth;
			height = initHeight;
		}

		public int getMapX() 
		{
			return column * MapManager.this.sparseMinTileWidth;
		}

		public int getMapY() 
		{
			return row * MapManager.this.sparseMinTileHeight;
		}
		
		public int getImageID()
		{
			return imageID;
		}
		
		public Image getImage() 
		{
			return MapManager.this.sparseLayerTileSet.get(imageID);
		}
		
		/**
		 * 
		 */
		public int getMaxColumn()
		{
			int minX = column * sparseMinTileWidth;
			int maxX = minX + width - 1;
			return (maxX/MapManager.this.sparseMinTileWidth);
		}
		
		/**
		 * 
		 */
		public int getMaxRow()
		{
			int minY = row * sparseMinTileHeight;
			int maxY = minY + height - 1;
			return (maxY/MapManager.this.sparseMinTileHeight);
		}
	}
}