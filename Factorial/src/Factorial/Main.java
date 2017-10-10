package Factorial;

import java.math.BigDecimal;

public class Main 
{
	public static void main(String[] args) 
	{
		BigDecimal temp = new BigDecimal(0);
		
		for(int i = 1; i <= 15; i++)
		{
			System.out.println("Fact(" + i + "): " + Factorial(new BigDecimal(i)));
			temp = temp.add(Factorial(new BigDecimal(i)));
		}
		
		System.out.println("Sum of 1-15!: " + temp);
		
	}
	
	public static BigDecimal Factorial(BigDecimal n)
	{
		if(n.equals(BigDecimal.ONE))
		{
			return new BigDecimal(1);
		}
		else
		{
			return n.multiply(Factorial(n.subtract(BigDecimal.ONE)));
		}
	}

}
