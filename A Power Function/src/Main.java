import java.math.BigDecimal;

public class Main 
{

	public static void main(String[] args) 
	{
		BigDecimal sum = new BigDecimal(0);
		BigDecimal temp;
		
		for(int i = 1; i <= 15; i++)
		{
			temp = Power(i,i+1).add(Power(i+1,i));
			System.out.println("Power(" + i + "," + (i+1) + "): " + temp);
			sum = sum.add(temp);
		}
		
		System.out.println("Sum: " + sum);
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
