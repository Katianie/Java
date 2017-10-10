import java.math.BigDecimal;


public class Main 
{

	public static void main(String[] args) 
	{
		System.out.println(f(105).multiply(g(105)));
	}
	
	public static BigDecimal f(int x)
	{
		return new BigDecimal(50 * (int)Math.pow(x, 3) - 46 * (int)Math.pow(x, 2) + 25 * x - 14);
	}
	
	public static BigDecimal g(int x)
	{
		return new BigDecimal(49 * (int)Math.pow(x, 3) + 45 * (int)Math.pow(x, 2) - 20 * (int)Math.pow(x, 3) + 195 * x - 4);
	}

}
