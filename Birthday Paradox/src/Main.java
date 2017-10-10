import java.math.BigDecimal;

public class Main 
{

	public static void main(String[] args) 
	{
		for(int i = 1; i <= 365; i++)
		{
			double x = (1 - P(i));
			double xrounded = Math.round(x * 1000.0) / 1000.0;
			
			System.out.println("P(" + i + "): " + xrounded);
		}
	}
	
	public static double P(int n)
	{
		if(n == 1)
		{
			return 1;
		}
		else
		{
			return P(n - 1) * (365 - (n-1)) / 365;
		}
	}

}
