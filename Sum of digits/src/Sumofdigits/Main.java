package Sumofdigits;

import java.math.BigDecimal;

public class Main 
{
	public static void main(String[] args) 
	{
		int sum = 0;
		BigDecimal temp = new BigDecimal(Math.pow(2.0, 50.0));
		String tempString = temp.toPlainString();
		int[] tempArray = new int[tempString.length()];
		
		for(int i = 0; i < tempString.length(); i++)
		{
			tempArray[i] = Integer.parseInt(tempString.substring(i,i + 1));
		}
		
		for(int i = 0; i < tempArray.length; i++)
		{
			sum += tempArray[i];
		}
		
		System.out.println("Sum: " + sum);
		
	}

}
