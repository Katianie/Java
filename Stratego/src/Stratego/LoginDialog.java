package Stratego;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class LoginDialog extends JDialog
{
	private final int TEXTBOX_WIDTH = 5;
	
	private GameGUI myGameGUI;
	
	private CreateAccountDialog myCreateAccountDialog;
	private JLabel myLoginLabel;
	private JLabel myUsernameLabel;
	private JLabel myPasswordLabel;
	private JTextField myUsernameTextField;
	private JPasswordField myPasswordTextField;
	private JButton myOkButton;
	private JButton myCreateAccountButton;
	
	private OkHandler myOkHandler;
	private CreateAccountHandler myCreateAccountHandler;
	private WindowExitHandler myWindowExitHandler;
	private KeyboardHandler myKeyboardHandler;
	private GridBagLayout myGridBagLayout;
	
	/**
	 * LoginDialog(Default Constructor): Creates a new login dialog box with a starting location at 0,0
	 */
	public LoginDialog(GameGUI theGameGUI)
	{	
		super(theGameGUI, "Stratego Login");
		
		myGameGUI = theGameGUI;
		
		myCreateAccountDialog = new CreateAccountDialog(myGameGUI);
		myLoginLabel = new JLabel("Stratego Login");
		myUsernameLabel = new JLabel("Username");
		myPasswordLabel = new JLabel("Password");
		myUsernameTextField = new JTextField(5);
		myPasswordTextField = new JPasswordField(5);
		myOkButton = new JButton("Ok");
		myCreateAccountButton = new JButton("Create Account");
		
		myOkHandler = new OkHandler();
		myKeyboardHandler = new KeyboardHandler();
		myCreateAccountHandler = new CreateAccountHandler();
		myWindowExitHandler = new WindowExitHandler(myGameGUI);
		
		myGridBagLayout = new GridBagLayout();
		
		this.addWindowFocusListener(myWindowExitHandler);
		myOkButton.addActionListener(myOkHandler);
		myCreateAccountButton.addActionListener(myCreateAccountHandler);
		
		myPasswordTextField.addKeyListener(myKeyboardHandler);
		
		layoutDialog();
	}
	
	/**
	 * layoutDialog: Lays out all of the GUI compoentns in the DialogBox
	 */
	private void layoutDialog()
	{
		int screenWidth;
		int screenHeight;
		
		this.setModal(true);
		this.setLayout(myGridBagLayout);
		
		addJComponentToContainerUsingGBL(myLoginLabel, this, 1, 0, 1, 1);
		addJComponentToContainerUsingGBL(myUsernameLabel, this, 0, 1, 1, 1);
		addJComponentToContainerUsingGBL(myUsernameTextField, this, 2, 1, TEXTBOX_WIDTH, 1);
		addJComponentToContainerUsingGBL(myPasswordLabel, this, 0, 3, 1, 1);
		addJComponentToContainerUsingGBL(myPasswordTextField, this, 2, 3, TEXTBOX_WIDTH, 1);
		addJComponentToContainerUsingGBL(myOkButton, this, 0, 5, 1, 1);
		addJComponentToContainerUsingGBL(myCreateAccountButton, this, 2, 5, 1, 1);
		
		this.pack();
		
		screenWidth = myGameGUI.getScreenWidth();
		screenHeight = myGameGUI.getScreenHeight();
		
		//Center the Login Dialog Box
		this.setLocation((screenWidth / 2) - (this.getWidth() / 2), (screenHeight / 2) - (this.getHeight() / 2));
	}
	
	/**
	 * 
	 * addJComponentToContainerUSingGBL: Helper method used for placing components inside the dialog box
	 * 
	 * @param component
	 * @param contaner
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void addJComponentToContainerUsingGBL(JComponent component, Container contaner, int x, int y, int width, int height)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		contaner.add(component, gbc);
		
	}
	
	/**
	 * OkHandler: When user clicks the ok button, the GUI will take the data and check to see
	 * if the provided account is in the data structure or not. If it is not then the 
	 * accountNotFound label will display that the account was not found in the database.
	 * 
	 * @author Eddie
	 *
	 */
	protected class OkHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			String username = myUsernameTextField.getText();
			String password = "";
			char[] charArray = myPasswordTextField.getPassword();
			
			for(int i = 0; i < charArray.length; i++)
			{
				password += charArray[i];
			}
			
			if(myGameGUI.lookupAccount(username, password) != null)
			{
				setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The provided account was not found in the database.");
			}
		}
	}
	
	/**
	 * CreateAccountHandler: When this button is pressed, the CreateAccountDialog will appear
	 * asking for a username and password. This username and password will be stored in the 
	 * database 
	 * 
	 * @author Eddie
	 *
	 *
	 */
	protected class CreateAccountHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			myCreateAccountDialog.setVisible(true);
		}
	}
	
	protected class KeyboardHandler implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyChar() == 10)//Enter
			{
				myOkHandler.actionPerformed(null);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
//	public static void main(String[] args)
//	{
//		LoginDialog diag = new LoginDialog(null);
//		diag.setVisible(true);
//	}
	
	

}
