package Binary;

/** Binary - BinaryMain.java
* Here is where we calculate a binary sequince by
* using addition and subtraction. 
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/

import javax.swing.JOptionPane;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int asciNum;
		int num = 0;
		int j = 0;
		boolean solved = false;
		String toStringTemp = "";
		BinaryDidget[] binaryArray = new BinaryDidget[20];
		
		for(int i = 0; i < binaryArray.length; i++)
		{
			binaryArray[i] = new BinaryDidget(Math.pow(2.0,i),false);
		}
		
		asciNum = Integer.parseInt(JOptionPane.showInputDialog("Please enter the number you wish to convert"));
		
		while(solved == false)
		{
			if(num == asciNum)
			{
				solved = true;
			}
			
			while(num < asciNum)//adding
			{
				if(num == asciNum)
				{
					solved = true;
				}
				
				num += binaryArray[j].getMyDig();
				binaryArray[j].setMyIsOne(true);
				j++;
			}
			
			j = 0;
			
			while(num > asciNum)
			{
				if(num == asciNum)
				{
					solved = true;
				}
				
				num -= binaryArray[j].getMyDig();
				binaryArray[j].setMyIsOne(false);
				j++;
			}
			
			j = 0;
		}
		
		for(int count  = binaryArray.length - 1; count  >= 0 ; count--)
		{
			toStringTemp += binaryArray[count].toString();
		}
		
		JOptionPane.showMessageDialog(null,"Ascii Num: "+ asciNum + "\nBinary: " + toStringTemp);

	}
	


}
