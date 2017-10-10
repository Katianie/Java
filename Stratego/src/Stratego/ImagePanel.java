package Stratego;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	private Image myImage;
	private Image myAIImage;
	
	public ImagePanel(Image img, Image img2)
	{
		super();
		myImage = img;
		myAIImage = img2;
		
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(myImage, 0, 0, myImage.getWidth(null), myAIImage.getHeight(null), null);
		g.drawImage(myAIImage, myImage.getWidth(null) + 20, 0, myAIImage.getWidth(null), myAIImage.getHeight(null), null);
	}
	
	public Image getImage()
	{
		return myImage;
	}
	
	public Image getAIImage()
	{
		return myAIImage;
	}
	
	
	public void setImage(Image img)
	{
		myImage = img;
		repaint();
	}
}
