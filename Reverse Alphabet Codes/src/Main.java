
public class Main 
{

	public static void main(String[] args) 
	{
		String temp = "The quick brown fox jumped over the cow";
		long sum = 0;
		
		for(int i = 0; i < temp.length(); i++)
		{
			char target = temp.charAt(i);
			
			if(target == 'A' || target == 'a')
			{
				sum += 26;
			}
			else if(target == 'B' || target == 'b')
			{
				sum += 25;
			}
			else if(target == 'C' || target == 'c')
			{
				sum += 24;
			}
			else if(target == 'D' || target == 'd')
			{
				sum += 23;
			}
			else if(target == 'E' || target == 'e')
			{
				sum += 22;
			}
			else if(target == 'F' || target == 'f')
			{
				sum += 21;
			}
			else if(target == 'G' || target == 'g')
			{
				sum += 20;
			}
			else if(target == 'H' || target == 'h')
			{
				sum += 19;
			}
			else if(target == 'I' || target == 'i')
			{
				sum += 18;
			}
			else if(target == 'J' || target == 'j')
			{
				sum += 17;
			}
			else if(target == 'K' || target == 'k')
			{
				sum += 16;
			}
			else if(target == 'L' || target == 'l')
			{
				sum += 15;
			}
			else if(target == 'M' || target == 'm')
			{
				sum += 14;
			}
			else if(target == 'N' || target == 'n')
			{
				sum += 13;
			}
			else if(target == 'O' || target == 'o')
			{
				sum += 12;
			}
			else if(target == 'P' || target == 'p')
			{
				sum += 11;
			}
			else if(target == 'Q' || target == 'q')
			{
				sum += 10;
			}
			else if(target == 'R' || target == 'r')
			{
				sum += 9;
			}
			else if(target == 'S' || target == 's')
			{
				sum += 8;
			}
			else if(target == 'T' || target == 't')
			{
				sum += 7;
			}
			else if(target == 'U' || target == 'u')
			{
				sum += 6;
			}
			else if(target == 'V' || target == 'v')
			{
				sum += 5;
			}
			else if(target == 'W' || target == 'w')
			{
				sum += 4;
			}
			else if(target == 'X' || target == 'x')
			{
				sum += 3;
			}
			else if(target == 'Y' || target == 'y')
			{
				sum += 2;
			}
			else if(target == 'Z' || target == 'z')
			{
				sum += 1;
			}
		}
		
		System.out.println("Sum: " + sum);

	}

}
