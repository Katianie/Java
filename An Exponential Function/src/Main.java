import java.math.BigDecimal;
import java.math.BigInteger;

import java.math.BigDecimal;

public class Main 
{
	public static void main(String[] args) 
	{
		int input = 50;
		
		System.out.println("func(" + input + "):" + func(50)); 
	}
	
	public static BigInteger func(int input)
	{
		BigDecimal top = (Power(4,input).add(Power(input,3)).add(Power(input,2)).add(Power(input,1)).subtract(Power(2,input)));
		BigDecimal bottom = Power(3,input);
		
		System.out.println("top: " + top);
		System.out.println("bottom: " + bottom);
		
		return top.toBigInteger().divide(bottom.toBigInteger()); 
	}
	public static BigDecimal Power(int x, int y)
	{
		BigDecimal temp = new BigDecimal(x);
		
		for(int i = 1; i < y; i++)
		{
			temp = temp.multiply(new BigDecimal(x));
		}
		
		return temp;
	}

}
