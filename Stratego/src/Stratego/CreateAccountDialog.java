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

import Stratego.LoginDialog.KeyboardHandler;

public class CreateAccountDialog extends JDialog
{
	private GameGUI myGameGUI;
	
	private JLabel myUsernameLabel;
	private JTextField myUsernameTextField;
	private JLabel myPasswordLabel;
	private JTextField myPasswordTextField;
	private JButton myOkButton;
	private JButton myCancelButton;
	
	private OkHandler myOkHandler;
	private KeyboardHandler myKeyboardHandler;
	private CancelHandler myCancelHandler;
	private WindowExitHandler myWindowExitHandler;
	
	private GridBagLayout myGridBagLayout;
	
	public CreateAccountDialog(GameGUI theGameGUI)
	{
		myGameGUI = theGameGUI;
		
		myUsernameLabel = new JLabel("Username");
		myUsernameTextField = new JTextField();
		myPasswordLabel = new JLabel("Password");
		myPasswordTextField = new JTextField();
		myOkButton = new JButton("Ok");
		myCancelButton = new JButton("Cancel");
		
		myOkHandler = new OkHandler();
		myCancelHandler = new CancelHandler();
		myKeyboardHandler = new KeyboardHandler();
		myWindowExitHandler = new WindowExitHandler(myGameGUI);
		
		this.addWindowListener(myWindowExitHandler);
		myOkButton.addActionListener(myOkHandler);
		myCancelButton.addActionListener(myCancelHandler);
		
		myGridBagLayout = new GridBagLayout();
		
		layoutGUI();
	}
	
	private void layoutGUI()
	{
		int screenWidth;
		int screenHeight;
		
		this.addWindowFocusListener(myWindowExitHandler);
		this.setLayout(myGridBagLayout);
		this.setModal(true);
		
		addJComponentToContainerUsingGBL(myUsernameLabel, this, 0, 0, 1, 1);
		addJComponentToContainerUsingGBL(myUsernameTextField, this, 1, 0, 1, 1);
		addJComponentToContainerUsingGBL(myPasswordLabel, this, 0, 1, 1, 1);
		addJComponentToContainerUsingGBL(myPasswordTextField, this, 1, 1, 1, 1);
		addJComponentToContainerUsingGBL(myOkButton, this, 0, 2, 1, 1);
		addJComponentToContainerUsingGBL(myCancelButton, this, 1, 2, 1, 1);
		
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
	
	public JLabel getUsernameLabel() 
	{
		return myUsernameLabel;
	}

	public void setUsernameLabel(JLabel usernameLabel) 
	{
		myUsernameLabel = usernameLabel;
	}

	public JTextField getUsernameTextField() 
	{
		return myUsernameTextField;
	}

	public void setUsernameTextField(JTextField usernameTextField) 
	{
		myUsernameTextField = usernameTextField;
	}

	public JLabel getPasswordLabel() 
	{
		return myPasswordLabel;
	}

	public void setPasswordLabel(JLabel passwordLabel) 
	{
		myPasswordLabel = passwordLabel;
	}

	public JTextField getPasswordTextField() 
	{
		return myPasswordTextField;
	}

	public void setPasswordTextField(JTextField passwordTextField) 
	{
		myPasswordTextField = passwordTextField;
	}

	public JButton getOkButton() 
	{
		return myOkButton;
	}

	public void setOkButton(JButton okButton) 
	{
		myOkButton = okButton;
	}

	public JButton getCancelButton() 
	{
		return myCancelButton;
	}

	public void setMyCancelButton(JButton cancelButton) 
	{
		myCancelButton = cancelButton;
	}

	/**
	 * OkHandler: When the user clicks the ok button the new information will be added to
	 * the data base.
	 * 
	 * @author Eddie
	 *
	 */
	protected class OkHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			String username = myUsernameTextField.getText();
			String password = myPasswordTextField.getText();
			
			myGameGUI.addAccount(username, password);
			
			setVisible(false);
		}
		
	}
	
	/**
	 * CancelHandler: If the user clicks the cancel button, they will be brought back to 
	 * the LoginScreen.
	 * 
	 * @author Eddie
	 *
	 */
	protected class CancelHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			setVisible(false);
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
//		CreateAccountDialog diag = new CreateAccountDialog();
//		diag.setVisible(true);
//	}

}
