import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;


public class Main 
{

	public static void main(String[] args) 
	{
		Scanner theScan = new Scanner(System.in);
		
		System.out.print("Please enter the number of fibonaci numbers you want to generate: ");
		int n = theScan.nextInt();
		
		BigDecimal sum = new BigDecimal(0);
		BigDecimal tempFib;
		
		for(int i = 1; i <= n; i++)
		{
			tempFib = fib(i);
			System.out.println("fib(" + i + "): " + tempFib);
			sum = sum.add(tempFib);
		}
		
		System.out.println("Sum of first n fibonaci numbers: " + sum);

	}
	
	public static BigDecimal fib(int n)
	{
		//this works like an array but it has infinite size
		ArrayList<BigDecimal> myList = new ArrayList<BigDecimal>();
		
		myList.add(new BigDecimal(1));
		myList.add(new BigDecimal(1));
		
		if(n == 1 || n == 2)
		{
			return new BigDecimal(1);
		}
		else
		{
			for(int i = 2; i < n; i++)
			{
				//new value = fib(i - 1) + fib(i - 2)
				myList.add(myList.get(i - 1).add(myList.get(i - 2)));
			}
			
			return myList.get(n - 1);
		}
		
	}

}
