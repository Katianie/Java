import java.math.BigDecimal;

public class Main 
{

	public static void main(String[] args) 
	{
		BigDecimal primeNum = new BigDecimal(0);
		BigDecimal temp = new BigDecimal(0);
		BigDecimal primeTemp = new BigDecimal(0);
		long count = 0;
		long sum = 0;
		long x = 0;
		
		//loop while all primenubers are less than 1000000
		for(long i = 2; primeNum.compareTo(new BigDecimal(1000000)) < 0; i++)
		{
			if(isPrime(i))
			{
				primeNum = new BigDecimal(i);
				primeTemp = primeNum;
				
				//add each didget and see if the number = 14, if so increment count
				for(int j = primeTemp.precision() - 1; j >= 0; j--)
				{
					temp = BigDecimal.TEN.pow(j);
					x = primeTemp.divide(temp).intValue();
					sum += x;
					primeTemp = primeTemp.remainder(temp);
					
				}
				
				//System.out.println("Debug Sum: " + sum); 
				
				if(sum == 14)
				{
					count++;
					
					System.out.println("Count: " + count);
					System.out.println("primeNum: " + primeNum.toString());
				}
	
				sum = 0;
			}
		}
		
		System.out.println("Sum: " + sum);
	}
	
	public static boolean isPrime(long num)
	{
		boolean prime = true;
		long limit = (long)Math.sqrt(num);
		
		if(num == 1)
		{
			return true;
		}
		
		for(int i = 2; i <= limit; i++)
		{
			if(num % i == 0)
			{
				prime = false;
				break;
			}
		}
		
		return prime;
	}

}
