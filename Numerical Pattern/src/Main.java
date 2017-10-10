import java.math.BigDecimal;


public class Main 
{
	public static void main(String[] args) 
	{
		BigDecimal sum = new BigDecimal("0");
		
		//System.out.println(item.precision());//number length
		
		for(int i = 1; i <= 19; i++)
		{
			System.out.println(i + " ^ " + (i+1));
			
			sum = sum.add(new BigDecimal(Math.pow(i, i+1)));
			
		}
		
		System.out.println("sum: " + sum.toString());
	}

}
