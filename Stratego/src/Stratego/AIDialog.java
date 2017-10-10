package Stratego;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AIDialog extends JDialog
{
	private String mySelectedAI;
	
	private GameGUI myGameGUI;
	
	private JLabel myAiSelectionLabel;
	private JPanel myRadioPanel;
	private JRadioButton myEasyRadioButton;
	private JRadioButton myHardRadioButton;
	private JButton myOkButton;
	
	private OkHandler myOkHandler;
	
	private GridBagLayout myGridBagLayout;
	private GridLayout myGridLayout;
	
	/**
	 * AIDialog: Constructs an AIDialog with default values
	 */
	public AIDialog(GameGUI theGameGUI)
	{
		mySelectedAI = "Not Selected";
		
		myGameGUI = theGameGUI;
		
		myAiSelectionLabel = new JLabel("Please select what type of AI you would like to V.S.");
		myRadioPanel = new JPanel();
		myEasyRadioButton = new JRadioButton("Easy AI");
		myHardRadioButton = new JRadioButton("Hard AI");
		myOkButton = new JButton("Ok");
		
		myOkHandler = new OkHandler();		
		myGridBagLayout = new GridBagLayout();
		myGridLayout = new GridLayout(0, 1);
		
		layoutGUI();
		
		myOkButton.addActionListener(myOkHandler);
	}
	
	private void layoutGUI()
	{
		ButtonGroup group = new ButtonGroup();
		int screenWidth;
		int screenHeight;
		
		this.setLayout(myGridBagLayout);
		this.setModal(true);
		
		myRadioPanel.setLayout(myGridLayout);
		
		group.add(myEasyRadioButton);
		group.add(myHardRadioButton);
		
		myRadioPanel.add(myEasyRadioButton);
		myRadioPanel.add(myHardRadioButton);
		
		addJComponentToContainerUsingGBL(myAiSelectionLabel, this, 0, 0, 1, 1);
		addJComponentToContainerUsingGBL(myRadioPanel, this, 0, 1, 1, 1);
		addJComponentToContainerUsingGBL(myOkButton, this, 0, 4, 1, 1);

		this.pack();
		
		screenWidth = myGameGUI.getScreenWidth();
		screenHeight = myGameGUI.getScreenHeight();
		
		//Center the Login Dialog Box
		this.setLocation((screenWidth / 2) - (this.getWidth() / 2), (screenHeight / 2) - (this.getHeight() / 2));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
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
	
	public String getSelectedAI()
	{
		return mySelectedAI;
	}
	
	/**
	 * OkHandler: When user clicks the ok button the window will close and the Ai will then
	 * put all of its pieces on the game board and the game will begin.
	 * 
	 * @author Eddie
	 *
	 */
	protected class OkHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			AI gameAI = myGameGUI.getAI();
			
			if(myEasyRadioButton.isSelected())
			{
				mySelectedAI = "Easy";
				setVisible(false);
			}
			else if(myHardRadioButton.isSelected())
			{
				mySelectedAI = "Hard";
				setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please select an AI that you would like to V.S.");
			}
			
			gameAI.setDifficulty(mySelectedAI);
			myGameGUI.setAI(gameAI);
		}
		
		
	}
	
//	public static void main(String[] args)
//	{
//		AIDialog diag = new AIDialog(null);
//		diag.setVisible(true);
//	}
	
}
