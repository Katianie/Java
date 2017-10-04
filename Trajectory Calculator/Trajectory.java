/** Trajectory - Main.java
* This is an implemntation of the 2D projectile motion formula used 
* in physics.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/package Trajectory;
import javax.swing.JOptionPane;
public class Main {
	public static void main(String[] args) 
	{	
		try
		{			int gridSize = Integer.parseInt(JOptionPane.showInputDialog("Enter the grid size (recommended is 50)"));			double vel = Double.parseDouble(JOptionPane.showInputDialog("Enter the Initial Velocity"));			double mass = Double.parseDouble(JOptionPane.showInputDialog("Enter the Mass"));			double theta = Double.parseDouble(JOptionPane.showInputDialog("Enter the Angle(theta)"));			double x = 0;			double y = 0;			double highestY = 0;
			char[][] theArray = new char[gridSize][gridSize];
			for(int i = 0; i < theArray.length; i++)
			{
				for(int j = 0; j < theArray.length; j++)
				{
					theArray[i][j] = '.';
				}
			}
			for(double time = 0; y >= 0; time += 0.1)
			{
				x = xFunc(theta, vel, mass, time);
				y = yFunc(theta, vel, mass, time);
				System.out.println("X =" + x + " Y =" + y + " t =" + time);
				if(y >= 0 && x >= 0)
				{
					theArray[((gridSize - 1) - (int)y)][(int)x] = 'X';
					if(highestY < y)
					{
						highestY = y;
					}
				}
				else
				{
					theArray[(gridSize - 1)][(int)x] = '_';
				}
			}
			System.out.println(toString(theArray));
		}		catch(NumberFormatException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public static double xFunc(double theta, double velocity, double mass, double time)
	{
		return (velocity * Math.cos(theta)) * time;
	}
	public static double yFunc(double theta, double velocity, double mass, double time)
	{
		return (velocity * Math.sin(theta) * time) - (mass * 9.8 * (time * time));
	}	
	public static String toString(char[][] arr)
	{
		String temp = "";
		for(int i = 0; i < arr.length; i++)
		{
			temp += "\n";
			for(int j = 0; j < arr.length; j++)
			{
				temp += arr[i][j];
			}
		}
		return temp;
	}
}
