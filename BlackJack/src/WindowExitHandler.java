import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * WindowExitHandler: When the user clicks the X in the top right of the GUI
 * to close the application, they will be asked if they relly want to quit or
 * not. If they choose to quit then ALL of the data will be written to a text
 * file at this time.
 * 
 * Note:Both windowClosing() and actionPerformed() do the same exact thing. The
 * Main reason we need both is because of the user selecting Exit from the
 * File menu or the user clicks the X in the top right.
 * 
 * @author Eddie
 *
 */
public class WindowExitHandler extends WindowAdapter implements ActionListener
{
	private GameGUI myGameGUI;
	
	public WindowExitHandler(GameGUI theGameGUI)
	{
		myGameGUI = theGameGUI;
	}
	
	/**
	 * close: Takes care of writing all the player data to the database.
	 */
	public void close()
	{
		myGameGUI.writeData();
		
		System.exit(0);
	}
	
	public void windowClosing(WindowEvent arg0)
	{
		close();
	}

	public void actionPerformed(ActionEvent e) 
	{
		close();
	}

}
