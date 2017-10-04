/** Palindrome - PalindromeMain.java
* This program takes in any string and checks to see if
* it is a palendrome or not. otto is a palendrome otter is 
* not.
*
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2009 Katianie.com
*/
package Palindrome;

import java.util.Stack;
import javax.swing.JOptionPane;

public class Main 
{
	public static void main(String[] args) 
	{
		Stack mainStack = new Stack();
		String word;
		
		try
		{
			word = JOptionPane.showInputDialog("Please enter a word to test:");	
			for(int i = 0; i < word.length(); i++)
			{
				mainStack.push(word.charAt(i));
			}
		}
		catch(NumberFormatException ex)
		{
			ex.getMessage();
		}
		
		if(isPal(mainStack) == true)
		{
			JOptionPane.showMessageDialog(null, "This word IS a Palindrome!");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The word is NOT a Palindrome.");
		}
	}

	//Returns true if the stack is equal to the reverse of the stack.
	public static boolean isPal(Stack theStack)
	{
		Stack tempStack = theStack;
		Stack compStack = new Stack();
		
		for(int i = 0; i < tempStack.size(); i++)
		{
			compStack.push(tempStack.pop());
		}
		
		if(compStack.toString().equals(tempStack.toString()) == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
