/** CartesianandPolarCoordinates - Main.java
import javax.swing.JOptionPane;
public class Main 
	public static void main(String[] args) 
	{
		while (true)
		{
			int choice;
			String[] options = {"Cartesian to Polar","Polar to Cartesian","Exit"};
			choice = JOptionPane.showOptionDialog(null, 
												 "Cartesian and Polar Coordinates", 
												 JOptionPane.QUESTION_MESSAGE, 
												 options,
			if(choice == 0)
			{
				x = Double.parseDouble(JOptionPane.showInputDialog("Please enter the X value."));
				y = Double.parseDouble(JOptionPane.showInputDialog("Please enter the Y value."));
				r = Math.round(Math.sqrt((Math.pow(x, 2.0) + Math.pow(y, 2.0))));
				theta = Math.round( Math.toDegrees(Math.atan(y/x)) );
				JOptionPane.showMessageDialog(null, "Radius: " + r + "\n" + "Angle: " + theta + "\n" + "(" + r + " , " + theta + ")");
			}
			else if(choice == 1)
			{
				r = Double.parseDouble(JOptionPane.showInputDialog("Please enter the Radius."));
				theta = Double.parseDouble(JOptionPane.showInputDialog("Please enter the Angle in degrees."));
				x = Math.round( r * Math.cos(Math.toRadians(theta)) );
				y = Math.round( r * Math.sin(Math.toRadians(theta)) );
				JOptionPane.showMessageDialog(null, "X Value: " + x + "\n" + "Y Value: " + y + "\n" + "(" + x + " , " + y + ")");
			}
			else if(choice == 2)
			{
				System.exit(0);
			}
		}
	}
}