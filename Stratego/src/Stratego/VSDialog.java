package Stratego;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class VSDialog extends JDialog
{
	private GameGUI myGameGUI;
	
	
	private JLabel myWhoWonLabel;
	private JButton myOkButton;
	private ImagePanel myCenterPanel;
	private JPanel mySouthPanel;
	
	private Image myStatigoPieceImage;
	private Image myStatigoPieceImageAI;
	
	private OkHandler myOkButtonHandler;
	private WindowOpenHandler myWindowOpenHandler;
	
	private BorderLayout myBorderLayout;
	private FlowLayout myFlowLayout;
	
	/**
	 * VSDialog: Creates a new dialog with the two images provided
	 * 
	 * @param img1
	 * @param img2
	 */
	public VSDialog(GameGUI initGameGUI, Image img1, Image img2, String winner)
	{
		super();
		
		myGameGUI = initGameGUI;
		
		myStatigoPieceImage = img1;
		myStatigoPieceImageAI = img2;
		
		myWhoWonLabel = new JLabel("The winner is " + winner);
		myOkButton = new JButton("Ok");
		
		myCenterPanel = new ImagePanel(myStatigoPieceImage, myStatigoPieceImageAI);
		mySouthPanel = new JPanel();
		
		myOkButtonHandler = new OkHandler();
		myWindowOpenHandler = new WindowOpenHandler();
		
		myBorderLayout = new BorderLayout();
		myFlowLayout = new FlowLayout();
		
		layoutGUI();
		
		this.setVisible(true);

	}
	
	private void layoutGUI()
	{
		myCenterPanel.setLayout(myFlowLayout);
		mySouthPanel.setLayout(new BorderLayout());
		this.setLayout(myBorderLayout);
		
		this.setModal(true);
		
		mySouthPanel.add(myWhoWonLabel, BorderLayout.CENTER);
		mySouthPanel.add(myOkButton, BorderLayout.SOUTH);
		
		this.add(myCenterPanel, BorderLayout.CENTER);
		this.add(mySouthPanel, BorderLayout.SOUTH);
		
		myOkButton.addActionListener(myOkButtonHandler);
		this.addWindowListener(myWindowOpenHandler);
		this.addWindowStateListener(myWindowOpenHandler);
		

		this.setSize(167, 150);
		
		
		if(myGameGUI != null)
		{
			this.setLocation((myGameGUI.getScreenWidth() / 2)  - (this.getWidth() / 2), (myGameGUI.getScreenHeight() / 2) - (this.getHeight() / 2));
		}
		else
		{
			this.setLocation(0, 0);
		}
		
		//this.setResizable(false);
		
	}
	
	
	public Image getStatigoPieceImage() 
	{
		return myStatigoPieceImage;
	}
	
	public void setStatigoPieceImage(Image statigoPieceImage) 
	{
		myStatigoPieceImage = statigoPieceImage;
	}
	public Image getStatigoPieceImageAI() 
	{
		return myStatigoPieceImageAI;
	}
	
	public void setStatigoPieceImageAI(Image statigoPieceImageAI) 
	{
		myStatigoPieceImageAI = statigoPieceImageAI;
	}
	
	
	/**
	 * OkHandler: When user clicks the ok button the window will close and the game will
	 * resume.
	 * 
	 * @author Eddie
	 *
	 */
	protected class OkHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			setVisible(false);
		}
	}
	
	protected class WindowOpenHandler extends WindowAdapter implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) 
		{
			repaint();
		}
		
		@Override
		public void windowOpened(WindowEvent e)
		{
			try 
			{
				Thread.sleep(100);
				
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			
			
			repaint();
			
		}
		
		
	}
	
//	public static void main(String[] args)
//	{
//		Toolkit tk = Toolkit.getDefaultToolkit();
//		Image img1 = tk.getImage("back.png");
//		Image img2 = tk.getImage("General.png");
//		VSDialog diag = new VSDialog(null, img1, img2, "(2)General");
//		
//	}
}
