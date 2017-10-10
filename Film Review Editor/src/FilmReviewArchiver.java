import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * FilmReviewArchiver - Project 1
 * @author Eddie O'Hagan
 * Date: 9/27/2010
 * SBID: 107445069
 */
public class FilmReviewArchiver extends JFrame implements Serializable
{
	public static final String UNSAVED = "Unsaved ";
	public static final String ROOT_TREE_ENDING = "Film Reviews by Year";

	// DECLARE DATA STRUCTURES AND FILE INFO TO MANIPULATE 
	private TreeMap<Integer, TreeMap<String,FilmReview>> myFilmArchive;

	// DECLARE YOUR HANDLER INSTANCE VARAIBLES
	//Application exit handler
	private WindowClosingHandler myWindowClosingHandler;

	//File event handler
	private NewArchiveHandler myNewArchiveHandler;
	private OpenArchiveHandler myOpenArchiveHandler;
	private SaveArchiveHandler mySaveArchiveHandler;
	private SaveAsArchiveHandler mySaveAsArchiveHandler;
	private ExitArchiveHandler myExitArchiveHandler;

	//Editing event handlers
	private AddYearHandler myAddYearHandler;
	private DeleteYearHandler myDeleteYearHandler;
	private AddReviewHandler myAddReviewHandler;
	private EditReviewHandler myEditReviewHandler;
	private EditYearHandler myEditYearHandler;
	private EditSingleYearHandler myEditSingleYearHandler;
	private DeleteReviewHandler myDeleteReviewHandler;

	//Tree and List handlers
	private TreeSelectionHandler myTreeSelectionHandler;
	private ListSelectionHandler myListSelectionHandler;

	// DECLARE YOUR CONTROL INSTANCE VARIABLES
	//Menu
	private JMenuBar myMenuBar;
	private JMenu myFileMenu;
	private JMenuItem myNewArchiveMenuItem;
	private JMenuItem myOpenArchiveMenuItem;
	private JMenuItem mySaveArchiveMenuItem;
	private JMenuItem mySaveAsArchiveMenuItem;
	private JMenuItem myExitArchiveMenuItem;
	private JMenu myEditMenu;
	private JMenuItem myAddYearMenuItem;
	private JMenuItem myDeleteYearMenuItem;
	private JMenuItem myEditYearMenuItem;
	private JMenuItem myEditSingleYearMenuItem;
	private JMenuItem myAddReviewMenuItem;
	private JMenuItem myEditReviewMenuItem;
	private JMenuItem myDeleteReviewMenuItem;

	//North of Frame - File Controls
	private JPanel myNorthPanel;
	private JToolBar myFileToolBar;
	private JButton myNewArchiveButton;
	private JButton myOpenArchiveButton;
	private JButton mySaveArchiveButton;
	private JButton mySaveAsArchiveButton;
	private JButton myExitButton;

	//North of Frame - Editing Controls
	private JToolBar myEditToolBar;
	private JButton myAddYearButton;
	private JButton myDeleteYearButton;
	private JButton myAddReviewButton;
	private JButton myEditReviewButton;
	private JButton myEditYearButton;
	private JButton myEditSingleYearButton;
	private JButton myDeleteReviewButton;

	//Work Area Split Pane - Center of Frame
	private JSplitPane myWorkAreaSplitPane;

	//West of Split Pane - The Tree
	private DefaultTreeModel myYearTreeModel;
	private JTree myYearTree;
	private JScrollPane myYearTreeScrollPane;

	//East of Split Pane - The Reviews List
	private DefaultListModel myReviewsListModel;
	private JList myReviewsList;
	private JScrollPane myReviewsListScrollPane;

	private File mySelectedFile;
	private boolean mySavedSinceLastEdit;

	// FROM CONSTRUCTOR, INIT YOUR GUI
	//		- CONSTRUCT YOUR CONTROLS
	//		- LAYOUT YOUR CONTROLS
	//		- CONSTRUCT EVENT HANDLER OBJECTS
	//		- REGISTER YOUR LISTENERS WITH YOUR CONTROLS
	public FilmReviewArchiver()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image logoImage = tk.createImage("Logo.png");

		layoutGUI();
		initDefaultArchive();

		this.setTitle("Film Review Archiver");
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setIconImage(logoImage);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		//Register Listeners
		this.addWindowListener(myWindowClosingHandler);
		myNewArchiveMenuItem.addActionListener(myNewArchiveHandler);
		myNewArchiveButton.addActionListener(myNewArchiveHandler);
		myOpenArchiveMenuItem.addActionListener(myOpenArchiveHandler);
		myOpenArchiveButton.addActionListener(myOpenArchiveHandler);
		mySaveArchiveMenuItem.addActionListener(mySaveArchiveHandler);
		mySaveArchiveButton.addActionListener(mySaveArchiveHandler);
		mySaveAsArchiveMenuItem.addActionListener(mySaveAsArchiveHandler);
		mySaveAsArchiveButton.addActionListener(mySaveAsArchiveHandler);
		myExitArchiveMenuItem.addActionListener(myExitArchiveHandler);
		myExitButton.addActionListener(myExitArchiveHandler);
		myAddYearButton.addActionListener(myAddYearHandler);
		myDeleteYearButton.addActionListener(myDeleteYearHandler);
		myAddReviewButton.addActionListener(myAddReviewHandler);
		myEditYearButton.addActionListener(myEditYearHandler);
		myEditYearMenuItem.addActionListener(myEditYearHandler);
		myEditSingleYearButton.addActionListener(myEditSingleYearHandler);
		myEditSingleYearMenuItem.addActionListener(myEditSingleYearHandler);
		myEditReviewButton.addActionListener(myEditReviewHandler);
		myDeleteReviewButton.addActionListener(myDeleteReviewHandler);
		myYearTree.addTreeSelectionListener(myTreeSelectionHandler);
		myReviewsList.addListSelectionListener(myListSelectionHandler);
	}

	public void layoutGUI()
	{	
		FlowLayout northPanelLayout;
		
		//Construct all Menu Controls
		myMenuBar = new JMenuBar();
		myFileMenu = new JMenu("File");
		myNewArchiveMenuItem = new JMenuItem("New Archive");
		myOpenArchiveMenuItem = new JMenuItem("Open Archive");
		mySaveArchiveMenuItem = new JMenuItem("Save Archive");
		mySaveAsArchiveMenuItem = new JMenuItem("Save As Archive");
		myExitArchiveMenuItem = new JMenuItem("Exit");
		myEditMenu = new JMenu("Edit");
		myAddYearMenuItem = new JMenuItem("Add Year");
		myDeleteYearMenuItem = new JMenuItem("Delete Year");
		myAddReviewMenuItem = new JMenuItem("Add Review");
		myEditYearMenuItem = new JMenuItem("Edit Year");
		myEditSingleYearMenuItem = new JMenuItem("Edit Single Year");
		myEditReviewMenuItem = new JMenuItem("Edit Review");
		myDeleteReviewMenuItem = new JMenuItem("Delete Review");

		//Construct Handlers
		myWindowClosingHandler = new WindowClosingHandler();
		myNewArchiveHandler = new NewArchiveHandler();
		myOpenArchiveHandler = new OpenArchiveHandler();
		mySaveArchiveHandler = new SaveArchiveHandler();
		mySaveAsArchiveHandler = new SaveAsArchiveHandler();
		myExitArchiveHandler = new ExitArchiveHandler();
		myAddYearHandler = new AddYearHandler();
		myDeleteYearHandler = new DeleteYearHandler();
		myAddReviewHandler = new AddReviewHandler();
		myEditReviewHandler = new EditReviewHandler();
		myEditYearHandler = new EditYearHandler();
		myEditSingleYearHandler = new EditSingleYearHandler();
		myDeleteReviewHandler = new DeleteReviewHandler();
		myTreeSelectionHandler = new TreeSelectionHandler();
		myListSelectionHandler = new ListSelectionHandler();

		//Arrange the Menu
		myMenuBar.add(myFileMenu);
		myFileMenu.add(myNewArchiveMenuItem);
		myFileMenu.add(myOpenArchiveMenuItem);
		myFileMenu.addSeparator();
		myFileMenu.add(mySaveArchiveMenuItem);
		myFileMenu.add(mySaveAsArchiveMenuItem);
		myFileMenu.addSeparator();
		myFileMenu.addSeparator();
		myFileMenu.add(myExitArchiveMenuItem);
		myMenuBar.add(myEditMenu);
		myEditMenu.add(myAddYearMenuItem);
		myEditMenu.add(myDeleteYearMenuItem);
		myEditMenu.addSeparator();
		myEditMenu.add(myAddReviewMenuItem);
		myEditMenu.add(myEditYearMenuItem);
		myEditMenu.add(myEditReviewMenuItem);
		myEditMenu.add(myEditSingleYearMenuItem);
		myEditMenu.add(myDeleteReviewMenuItem);
		this.setJMenuBar(myMenuBar);


		//Construct the File Tool Bar
		myFileToolBar = new JToolBar();
		myNewArchiveButton = new JButton("New Archive");
		myOpenArchiveButton = new JButton("Open Archive");
		mySaveArchiveButton = new JButton("Save Archive");
		mySaveAsArchiveButton = new JButton("Save As Archive");
		myExitButton = new JButton("Exit");

		//Arrange the File Tool Bar
		northPanelLayout = new FlowLayout(FlowLayout.CENTER);
		myFileToolBar.setLayout(northPanelLayout);
		myFileToolBar.add(myNewArchiveButton);
		myFileToolBar.add(myOpenArchiveButton);
		myFileToolBar.add(mySaveArchiveButton);
		myFileToolBar.add(mySaveAsArchiveButton);
		myFileToolBar.add(myExitButton);

		//Construct the Edit Tool Bar
		myEditToolBar = new JToolBar();
		myAddYearButton = new JButton("Add Year");
		myDeleteYearButton = new JButton("Delete Year");
		myAddReviewButton = new JButton("Add Review");
		myEditReviewButton = new JButton("Edit Review");
		myEditYearButton = new JButton("Edit Year");
		myEditSingleYearButton = new JButton("Edit Single Year");
		myDeleteReviewButton = new JButton("Delete Review");

		//Arrange the Edit Tool Bar
		myEditToolBar.setLayout(northPanelLayout);
		myEditToolBar.add(myAddYearButton);
		myEditToolBar.add(myEditYearButton);
		myEditToolBar.add(myDeleteYearButton);
		myEditToolBar.add(myAddReviewButton);
		myEditToolBar.add(myEditReviewButton);
		myEditToolBar.add(myEditSingleYearButton);
		myEditToolBar.add(myDeleteReviewButton);

		//Some controls should start out disabled
		myDeleteYearButton.setEnabled(false);
		myAddReviewButton.setEnabled(false);
		myAddReviewMenuItem.setEnabled(false);
		myEditReviewButton.setEnabled(false);
		myEditReviewMenuItem.setEnabled(false);
		myEditYearButton.setEnabled(false);
		myEditYearMenuItem.setEnabled(false);
		myEditSingleYearMenuItem.setEnabled(false);
		myEditSingleYearButton.setEnabled(false);
		myDeleteReviewButton.setEnabled(false);
		myDeleteReviewMenuItem.setEnabled(false);

		//Add the Tool Bars to the North Panel
		myNorthPanel = new JPanel();
		myNorthPanel.add(myFileToolBar);
		myNorthPanel.add(myEditToolBar);

		//Construct West Side of Split Pane Controls
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Films");
		myYearTreeModel = new DefaultTreeModel(root);
		myYearTree = new JTree(myYearTreeModel);
		myYearTreeScrollPane = new JScrollPane(myYearTree);

		//Construct East Side of Split Pane Controls
		myReviewsListModel = new DefaultListModel();
		myReviewsList = new JList(myReviewsListModel);
		myReviewsListScrollPane = new JScrollPane(myReviewsList);

		//Construct and Arrange Work Area Split Pane - For Center of Frame
		myWorkAreaSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		myWorkAreaSplitPane.add(myYearTreeScrollPane);
		myWorkAreaSplitPane.add(myReviewsListScrollPane);	
		myWorkAreaSplitPane.setDividerLocation(350);

		//Increase the Font Size of the Tree and the List
		Font workAreaFont = new Font(Font.SERIF, Font.BOLD, 18);
		myYearTree.setFont(workAreaFont);
		myReviewsList.setFont(workAreaFont);

		//Arrange all the regions inside the frames
		this.add(myNorthPanel, BorderLayout.NORTH);
		this.add(myWorkAreaSplitPane, BorderLayout.CENTER);
	}

	public void initDefaultArchive()
	{
		DefaultMutableTreeNode defaultRoot;
		myFilmArchive = new TreeMap<Integer, TreeMap<String, FilmReview>>();

		//Update the display(title bar, tree, list)
		this.setTitle(UNSAVED);
		myYearTreeModel.setRoot(null);
		defaultRoot = new DefaultMutableTreeNode(UNSAVED + ROOT_TREE_ENDING);
		myYearTreeModel.setRoot(defaultRoot);
		mySelectedFile = null;
		myReviewsListModel.removeAllElements();

		//Its empty so start as if we have saved before
		mySavedSinceLastEdit = true;

		//Some controls should start out disabled
		myDeleteYearButton.setEnabled(false);
		myAddReviewButton.setEnabled(false);
		myAddReviewMenuItem.setEnabled(false);
		myEditReviewButton.setEnabled(false);
		myEditReviewMenuItem.setEnabled(false);
		myEditYearMenuItem.setEnabled(false);
		myEditYearButton.setEnabled(false);
		myDeleteReviewButton.setEnabled(false);
		myDeleteReviewMenuItem.setEnabled(false);
	}

	public boolean verifySave()
	{
		if(mySelectedFile != null)
		{
			saveArchive(mySelectedFile);
			return true;
		}
		else
		{
			int selection = JOptionPane.showConfirmDialog(FilmReviewArchiver.this, "Would you like to save your archive first?", 
					"Save Current Archive?", JOptionPane.YES_NO_CANCEL_OPTION);

			if(selection == JOptionPane.YES_OPTION)
			{
				promptToSave();
			}
			else if(selection == JOptionPane.NO_OPTION)
			{
				initDefaultArchive();
				return true;
			}

			return false;
		}
	}

	/**
	 * This method saves the current film review archive to
	 * the fileToSave File. It uses Object Serialization
	 * to do so.
	 */
	public void saveArchive(File fileToSave)
	{
		FileOutputStream fos;
		ObjectOutputStream oos;
		DefaultMutableTreeNode root;

		try
		{
			if(fileToSave.getName().contains(".fra"))
			{
				fos = new FileOutputStream(fileToSave);
			}
			else
			{
				fos = new FileOutputStream(fileToSave + ".fra");
			}			

			oos = new ObjectOutputStream(fos);
			oos.writeObject(myFilmArchive);
			oos.flush();
			oos.close();

			mySelectedFile = fileToSave;
			mySavedSinceLastEdit = true;
			this.setTitle(mySelectedFile.getName());
			root = (DefaultMutableTreeNode) myYearTreeModel.getRoot();
			root.setUserObject(mySelectedFile.getName() + ROOT_TREE_ENDING);
			myYearTree.repaint();
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(this, "Error Saving File", 
					"Error Writing to " + mySelectedFile + " Perhaps File is In Use by another program", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * This method asks the user for the name of the
	 * file to save our archive to.
	 */
	public void promptToSave()
	{
		JFileChooser jfc = new JFileChooser(".");
		FRAFileFilter fraff = new FRAFileFilter();
		int selection;

		jfc.setFileFilter(fraff);
		selection = jfc.showSaveDialog(this);

		if(selection == JOptionPane.OK_OPTION)
		{
			File file = jfc.getSelectedFile();
			saveArchive(file);
		}

	}

	//Displays window for opening file
	public void promptToOpen()
	{
		JFileChooser jfc = new JFileChooser(".");
		FRAFileFilter fraff = new FRAFileFilter();
		int selection;

		jfc.setFileFilter(fraff);
		selection = jfc.showOpenDialog(this);

		if(selection == JOptionPane.OK_OPTION)
		{
			File file = jfc.getSelectedFile();
			loadArchive(file);
		}
	}

	/**
	 * This method reads an archive from the fileToLoad file
	 * and loads it into the filmArchive variable. Note that
	 * we use Object Serialization for this.
	 */	
	public void loadArchive(File fileToLoad)
	{
		FileInputStream fis;
		ObjectInputStream ois;

		try
		{
			fis = new FileInputStream(fileToLoad);
			ois = new ObjectInputStream(fis);
			myFilmArchive = (TreeMap<Integer, TreeMap<String, FilmReview>>) (ois.readObject());
			mySelectedFile = fileToLoad;
			mySavedSinceLastEdit = true;
			loadArchiveIntoGUI();
		}
		catch(ClassNotFoundException ex)
		{
			// THIS MUST MEAN THERE IS A PROGRAMMING ERROR, OR THE
			// USER SELECTED A FILE THAT IS NOT IN THE PROPER FORMAT
			JOptionPane.showMessageDialog(this, "Error Reading File", "Error Reading " + mySelectedFile + " - File in Wrong Format", 
					JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException ex)
		{
			// THIS COULD BE A FEW DIFFERENT PROBLEMS. PERHAPS THIS
			// FILE DOESN'T EXIST
			JOptionPane.showMessageDialog(this, "Error Reading File", "Error Reading " + mySelectedFile + " - Perhaps File Does Not Exist?", 
					JOptionPane.ERROR_MESSAGE);	
		}
	}

	/**
	 * This method loads the current film archive into
	 * the tree and list, removing what was previously
	 * there.
	 */
	public void loadArchiveIntoGUI()
	{
		DefaultMutableTreeNode root = null;
		DefaultMutableTreeNode nodeToAdd = null;
		TreePath pathToNode;
		Iterator<Integer> keys;

		//Empty the tree (Garbage collection lawl)
		myYearTreeModel.setRoot(null);

		//Make our new root
		root = new DefaultMutableTreeNode(mySelectedFile.getName() + ROOT_TREE_ENDING);
		myYearTreeModel.setRoot(root);

		//Load the tree
		keys = myFilmArchive.keySet().iterator();
		while(keys.hasNext())
		{
			Integer i = keys.next();
			nodeToAdd = new DefaultMutableTreeNode(i);
			myYearTreeModel.insertNodeInto(nodeToAdd, root, root.getChildCount());
		}

		//Make the root node the selected node
		if(nodeToAdd != null)
		{
			pathToNode = new TreePath(myYearTreeModel.getPathToRoot(nodeToAdd));
			myYearTree.scrollPathToVisible(pathToNode);
		}
		
		pathToNode = new TreePath(myYearTreeModel.getPathToRoot(root));
		myYearTree.setSelectionPath(pathToNode);

		this.setTitle(mySelectedFile.getName());

		//Since the root is selected and not a year, empty the list
		myReviewsListModel.removeAllElements();
	}

	/**
	 * This method talks to the tree and gives us the year
	 * of the currently selected node. If no year node is
	 * selected, -1 is returned.
	 */
	public int getSelectedYear()
	{
		int year;
		TreePath path;
		Object userObject;
		Object[] pathNodesAsObjects;
		DefaultMutableTreeNode selectedNode;

		//Get the path from the root to the selected node
		path = myYearTree.getSelectionPath();

		//If no node is selected return -1
		if(path == null)
		{
			return -1;
		}

		//If the root is selected return -1
		pathNodesAsObjects = path.getPath();

		if(pathNodesAsObjects.length == 1)
		{
			return -1;
		}

		//Otherwise return the year of the selected tree node
		selectedNode = (DefaultMutableTreeNode) pathNodesAsObjects[1];
		userObject = selectedNode.getUserObject();
		year = (Integer)userObject;

		return year;
	}

	/**
	 * This method talks to the list and returns the currently
	 * selected FilmReview.
	 */
	public FilmReview getSelectedReview()
	{
		FilmReview selectedFilmReview = (FilmReview) myReviewsList.getSelectedValue();
		return selectedFilmReview;
	}

	/**
	 * This method returns the node currently selected in the tree.
	 */
	public DefaultMutableTreeNode getSelectedNode()
	{
		TreePath selectionPath = myYearTree.getSelectionPath();
		return (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
	}

	/**
	 * This helps us setup the GUI. When a user selects a year from
	 * the tree we want to display those reviews in our list. This method
	 * implements this for us.
	 */
	public void loadSelectedYearReviewsIntoList()
	{
		int selectedYear = getSelectedYear();

		if(selectedYear > -1)
		{
			TreeMap<String, FilmReview> reviews = myFilmArchive.get(selectedYear);

			//Empty the list
			myReviewsListModel.removeAllElements();

			//Load the list
			Iterator<FilmReview> it = reviews.values().iterator();
			while(it.hasNext())
			{
				FilmReview reviewToAdd = it.next();
				myReviewsListModel.addElement(reviewToAdd);
			}
		}
	}

	/**
	 * This method adds the newReview argument to the current
	 * archive. In order to work properly, a year must currently
	 * be selected in the tree. We'll use the selected tree node
	 * to determine the year to put this movie inside the archive.
	 * Note that this method will also update the data in the list
	 * to include the newly added review.
	 */
	public void addReview(FilmReview newReview)
	{
		int year = getSelectedYear();

		//Only do this if a tree node(year) is selected
		if(year > -1)
		{
			TreeMap<String, FilmReview> reviewsForYear = myFilmArchive.get(year);

			//Dont duplicate
			if(reviewsForYear.containsKey(newReview.toString()))
			{
				JOptionPane.showMessageDialog(	FilmReviewArchiver.this,
						"That Film has already been reviewed - review not added",
						"Warning - Film to Added - Duplicate Review",
						JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				//Now add the review to the archive
				reviewsForYear.put(newReview.toString(), newReview);

				//We made a change
				mySavedSinceLastEdit = false;

				//Update list display
				boolean found = false;
				for(int i = 0; (i < myReviewsListModel.size() && !found); i++)
				{
					System.out.println("myReviewsListModel" + myReviewsListModel);
					FilmReview testReview = (FilmReview) myReviewsListModel.getElementAt(i);

					if(newReview.toString().compareTo(testReview.toString()) < 0)
					{
						// WE FOUND THE LOCATION, INSERT IT
						// REMEMBER, ALL DATA MUST BE INSERTED
						// USING THE MODEL, NOT THE GUI COMPONENT
						// ITSELF

						myReviewsListModel.insertElementAt(newReview, i);
						found = true;
					}
				}

				if(!found)
				{
					myReviewsListModel.insertElementAt(newReview, myReviewsListModel.size());
				}
			}
		}
	}

	/**
	 * This method will make sure that a review that gets changed
	 * will be reloaded into its appropriate data structures. The
	 * reason for this is we are using the results of toString as
	 * keys for our data.
	 */
	public void updateEditedReview()
	{
		//Reload all the data
		int year = getSelectedYear();
		TreeMap<String, FilmReview> reviews = myFilmArchive.get(year);
		reloadTreeMap(reviews);

		//And redisplay it
		myReviewsList.repaint();

		//We changed out data
		mySavedSinceLastEdit = false;
	}

	/**
	 * This method makes sure all data is properly sorted,
	 * even after a review is edited in some way that might
	 * change its toString results, and thus the order of the
	 * structure.
	 */
	public void reloadTreeMap(TreeMap treeMap)
	{
		//Get all the data currently in the tree and put it in a memory location
		TreeMap<String, FilmReview> tempTreeMap = new TreeMap<String, FilmReview>();
		FilmReview review;

		Iterator<FilmReview> reviewsIt = treeMap.values().iterator();
		while(reviewsIt.hasNext())
		{
			review = reviewsIt.next();
			tempTreeMap.put(review.toString(), review);
		}

		//Clear the tree
		treeMap.clear();

		//And now put it back
		reviewsIt = tempTreeMap.values().iterator();
		while(reviewsIt.hasNext())
		{
			review = reviewsIt.next();
			treeMap.put(review.toString(), review);
		}
	}

	//DEFINE AN INNER CLASS FOR EACH EVENT HANDLER
	//Handles the Window Closing
	protected class WindowClosingHandler extends WindowAdapter
	{
		public void windowClosing(WindowEvent arg0)
		{
			if(!mySavedSinceLastEdit)
			{
				boolean exitProgram = verifySave();
				if(exitProgram)
				{
					System.exit(0);
				}
			}
			else
			{
				System.exit(0);
			}
		}
	}

	//Handles the New Archive Button
	protected class NewArchiveHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if(!mySavedSinceLastEdit)
			{
				boolean startNewArchive = verifySave();
				if(startNewArchive)
				{
					initDefaultArchive();
					mySelectedFile = null;
				}

			}
			else
			{
				initDefaultArchive();
				mySelectedFile = null;
			}
		}
	}

	//Handles the Open Archive Button
	protected class OpenArchiveHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if(!mySavedSinceLastEdit)
			{
				boolean openArchive = verifySave();
				if(openArchive)
				{
					promptToOpen();
				}
			}
			else
			{
				promptToOpen();
			}
		}
	}

	//Handles the Save Archive Button
	protected class SaveArchiveHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if(!mySavedSinceLastEdit)
			{
				if(mySelectedFile != null)
				{
					saveArchive(mySelectedFile);
				}
				else
				{
					promptToSave();
				}
			}
		}
	}

	//Handles the Save As Archive Button
	protected class SaveAsArchiveHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			promptToSave();
		}
	}

	//Handles the Exit Button
	protected class ExitArchiveHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if(!mySavedSinceLastEdit)
			{
				boolean exitProgram = verifySave();
				if(exitProgram)
				{
					System.exit(0);
				}
			}
			else
			{
				System.exit(0);
			}
		}
	}

	//Handles when a user selects a node in the tree
	protected class TreeSelectionHandler implements TreeSelectionListener
	{
		public void valueChanged(TreeSelectionEvent arg0)
		{
			TreeMap<String, FilmReview> reviews;
			Iterator<FilmReview> it;
			int year = getSelectedYear();

			if(year == -1)
			{
				//No year selected, must be root, so deactivate some controls
				myDeleteYearButton.setEnabled(false);
				myDeleteYearMenuItem.setEnabled(false);
				myAddReviewButton.setEnabled(false);
				myAddReviewMenuItem.setEnabled(false);
				myEditYearMenuItem.setEnabled(false);
				myEditYearButton.setEnabled(false);
			}
			else
			{
				myDeleteYearButton.setEnabled(true);
				myDeleteYearMenuItem.setEnabled(true);
				myAddReviewButton.setEnabled(true);
				myAddReviewMenuItem.setEnabled(true);
				myEditYearMenuItem.setEnabled(true);
				myEditYearButton.setEnabled(true);

				//Empty the list since we're changing the year
				myReviewsListModel.removeAllElements();

				//Load film reviews for the selected year
				reviews = myFilmArchive.get(year);
				it = reviews.values().iterator();

				while(it.hasNext())
				{
					FilmReview fr = it.next();
					myReviewsListModel.addElement(fr);
				}
			}
		}
	}

	//Handles when user selects an item in the list
	protected class ListSelectionHandler implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent arg0)
		{
			FilmReview selectedReview = getSelectedReview();

			if(selectedReview == null)
			{
				myEditReviewButton.setEnabled(false);
				myEditReviewMenuItem.setEnabled(false);
				myEditSingleYearButton.setEnabled(false);
				myEditSingleYearMenuItem.setEnabled(false);
				myDeleteReviewButton.setEnabled(false);
				myDeleteReviewMenuItem.setEnabled(false);
			}
			else
			{
				myEditReviewButton.setEnabled(true);
				myEditReviewMenuItem.setEnabled(true);
				myEditSingleYearButton.setEnabled(true);
				myEditSingleYearMenuItem.setEnabled(true);
				myDeleteReviewButton.setEnabled(true);
				myDeleteReviewMenuItem.setEnabled(true);
			}
		}
	}

	//Handles when Add Year button is clicked
	protected class AddYearHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			int yearToAdd;
			int yearToTest;
			boolean found;
			TreeMap archiveForYear;
			DefaultMutableTreeNode yearToAddNode;
			DefaultMutableTreeNode root;
			DefaultMutableTreeNode nodeToTest;
			String yearTextToAdd = JOptionPane.showInputDialog(FilmReviewArchiver.this, "Enter the Year to Add"
					,"Year To Add");

			try
			{
				//Make sure we have a valid year
				yearToAdd = Integer.parseInt(yearTextToAdd);

				//Make sure we're not adding duplicate
				if(myFilmArchive.containsKey(yearToAdd))
				{
					JOptionPane.showMessageDialog(	FilmReviewArchiver.this, "Duplicate Years not Allowed - Year not added",
							"Warning - Year already in Archive", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					//Make a tree for the new year's review
					archiveForYear = new TreeMap();
					myFilmArchive.put(yearToAdd, archiveForYear);
					mySavedSinceLastEdit = false;

					yearToAddNode = new DefaultMutableTreeNode(yearToAdd);
					root = (DefaultMutableTreeNode)myYearTreeModel.getRoot();

					//First one added
					if(root.getChildCount() == 0)
					{
						myYearTreeModel.insertNodeInto(yearToAddNode, root, 0);
					}
					else
					{
						//Otherwise we have to find the correct place
						found = false;
						for(int i = 0; (i < root.getChildCount() && !found); i++)
						{
							nodeToTest = (DefaultMutableTreeNode) root.getChildAt(i);
							yearToTest = (Integer)nodeToTest.getUserObject();

							if(yearToTest > yearToAdd)
							{
								myYearTreeModel.insertNodeInto(yearToAddNode, root, i);
								found = true;
							}
						}

						//Place it all the way at the end
						if(!found)
						{
							myYearTreeModel.insertNodeInto(yearToAddNode, root, root.getChildCount());
						}
					}

					//Update the display in the tree and list
					TreeNode[] path = yearToAddNode.getPath();
					TreePath pathToScroll = new TreePath(path);
					myYearTree.scrollPathToVisible(pathToScroll);
					myYearTree.setSelectionPath(pathToScroll);
					myReviewsListModel.removeAllElements();
				}
			}
			catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(FilmReviewArchiver.this, "Invalid Entry for Year", "Error - Invalid Year Input",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	//Handles when the Delete Year button is clicked
	protected class DeleteYearHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			DefaultMutableTreeNode selectedNode = getSelectedNode();
			int year;
			int selection = JOptionPane.showConfirmDialog(FilmReviewArchiver.this,
					"Are you sure you want to delete all reviews for " + selectedNode.getUserObject() + "?",
					"Delete all reviews for " + selectedNode.getUserObject() + "?",
					JOptionPane.YES_NO_OPTION);

			if(selection == JOptionPane.YES_OPTION)
			{
				year = getSelectedYear();
				myFilmArchive.remove(year);
				myYearTreeModel.removeNodeFromParent(selectedNode);
				myReviewsListModel.removeAllElements();
				mySavedSinceLastEdit = false;
			}

		}
	}

	//Handles when the Add Review button is clicked
	protected class AddReviewHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			int selectedYear = getSelectedYear();

			if(selectedYear == -1)
			{
				JOptionPane.showMessageDialog(FilmReviewArchiver.this,
						"You must select a year to add a review",
						"Select a Year First",
						JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				FilmReviewEntryDialog filmReviewEntryDialog = new FilmReviewEntryDialog(FilmReviewArchiver.this,
						selectedYear, 5);

				filmReviewEntryDialog.setVisible(true);
			}
		}
	}

	//Handles when the Edit Review button is clicked
	protected class EditReviewHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			FilmReview selectedReview = getSelectedReview();

			if(selectedReview == null)
			{
				JOptionPane.showMessageDialog(FilmReviewArchiver.this,
						"You must select a review to edit",
						"Select a Review First",
						JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				FilmReviewEntryDialog filmReviewEntryDialog = new FilmReviewEntryDialog(FilmReviewArchiver.this, selectedReview, 5);

				filmReviewEntryDialog.setVisible(true);
			}

			updateEditedReview();
		}
	}

	//Handles when the Edit year button is clicked
	protected class EditYearHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			int yearToTest;
			int currentYear = getSelectedYear();
			int originalYear = getSelectedYear();
			boolean found = false;
			DefaultMutableTreeNode currentNode = getSelectedNode();
			DefaultMutableTreeNode yearToAddNode;
			DefaultMutableTreeNode root;
			DefaultMutableTreeNode nodeToTest;
			TreeMap<String, FilmReview> reviewsForYear = myFilmArchive.get(originalYear);
			String yearTextToAdd = JOptionPane.showInputDialog("Enter the new Year", currentYear);

			try
			{
				//Make sure we have a valid year
				currentYear = Integer.parseInt(yearTextToAdd);
				
				//Remove original year
				myFilmArchive.remove(originalYear);
				myYearTreeModel.removeNodeFromParent(currentNode);
				mySavedSinceLastEdit = false;

				//Insert new year
				Iterator<FilmReview> it = reviewsForYear.values().iterator();
				while(it.hasNext())
				{
					//Not sure if this cast is necessary, but just to be safe
					FilmReview temp = (FilmReview)it.next();
					temp.setYearReleased(currentYear);
				}
				
				myFilmArchive.put(currentYear, (TreeMap<String, FilmReview>)reviewsForYear);
				myReviewsList.repaint();
				yearToAddNode = new DefaultMutableTreeNode(currentYear);
				root = (DefaultMutableTreeNode)myYearTreeModel.getRoot();

				//First one added
				if(root.getChildCount() == 0)
				{
					myYearTreeModel.insertNodeInto(yearToAddNode, root, 0);
				}
				else
				{
					//Otherwise we have to find the correct place
					found = false;
					for(int i = 0; (i < root.getChildCount() && !found); i++)
					{
						nodeToTest = (DefaultMutableTreeNode) root.getChildAt(i);
						yearToTest = (Integer)nodeToTest.getUserObject();

						if(yearToTest > currentYear)
						{
							myYearTreeModel.insertNodeInto(yearToAddNode, root, i);
							found = true;
						}
					}

					//Place it all the way at the end
					if(!found)
					{
						myYearTreeModel.insertNodeInto(yearToAddNode, root, root.getChildCount());
					}
				}

				//Update the display in the tree and list
				TreeNode[] path = yearToAddNode.getPath();
				TreePath pathToScroll = new TreePath(path);
				myYearTree.scrollPathToVisible(pathToScroll);
				myYearTree.setSelectionPath(pathToScroll);
			}
			catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(FilmReviewArchiver.this, "Invalid Entry for Year", "Error - Invalid Year Input",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Handles when the Edit Single Year button is clicked
	//This will only edit a single year for a review
	public class EditSingleYearHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			FilmReview selectedReview = getSelectedReview();
			String whatYear = JOptionPane.showInputDialog("Enter the new Year for this review.", selectedReview.getYearReleased());
			int year = 0;
			
			if(selectedReview == null)
			{
				JOptionPane.showMessageDialog(FilmReviewArchiver.this,
						"You must select a review to edit",
						"Select a Review First",
						JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				try
				{
					year = Integer.parseInt(whatYear);
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(FilmReviewArchiver.this,
							"Please enter a valid year",
							"Invalid Year",
							JOptionPane.ERROR_MESSAGE);
				}
				
				selectedReview.setYearReleased(year);
			}
			
			updateEditedReview();
		}
	}
	
	//Handles when the Delete Review button is clicked
	protected class DeleteReviewHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			FilmReview selectedReview = getSelectedReview();
			int selection;
			int selectedYear;
			TreeMap reviews;

			selection = JOptionPane.showConfirmDialog(	FilmReviewArchiver.this,
					"Are you sure you want to delete your review for " + selectedReview.getFilmTitle() + "?",
					"Delete " + selectedReview.getFilmTitle() + "?",
					JOptionPane.YES_NO_OPTION);

			if(selection == JOptionPane.YES_OPTION)
			{
				selectedYear = getSelectedYear();
				reviews = myFilmArchive.get(selectedYear);
				reviews.remove(selectedReview.toString());
				loadSelectedYearReviewsIntoList();
				mySavedSinceLastEdit = false;
			}

			updateEditedReview();
		}
	}

	/**
	 * This is where the application starts. It simply makes
	 * our frame and starts it up.
	 */
	public static void main(String[] args) 
	{
		FilmReviewArchiver frame = new FilmReviewArchiver();
		frame.setVisible(true);
	}
}