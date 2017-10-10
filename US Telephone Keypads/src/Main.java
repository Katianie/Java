
public class Main 
{

	public static void main(String[] args) 
	{
		String item = "Programming Challenges are fun";
		long prod = 1;
		
		for(int i = 0; i < item.length(); i++)
		{
			char target = item.charAt(i);
			
			if(target == 'A' || target == 'a')
			{
				prod *= 2;
			}
			else if(target == 'B' || target == 'b')
			{
				prod *= 2;
			}
			else if(target == 'C' || target == 'c')
			{
				prod *= 2;
			}
			else if(target == 'D' || target == 'd')
			{
				prod *= 3;
			}
			else if(target == 'E' || target == 'e')
			{
				prod *= 3;
			}
			else if(target == 'F' || target == 'f')
			{
				prod *= 3;
			}
			else if(target == 'G' || target == 'g')
			{
				prod *= 4;
			}
			else if(target == 'H' || target == 'h')
			{
				prod *= 4;
			}
			else if(target == 'I' || target == 'i')
			{
				prod *= 4;
			}
			else if(target == 'J' || target == 'j')
			{
				prod *= 5;
			}
			else if(target == 'K' || target == 'k')
			{
				prod *= 5;
			}
			else if(target == 'L' || target == 'l')
			{
				prod *= 5;
			}
			else if(target == 'M' || target == 'm')
			{
				prod *= 6;
			}
			else if(target == 'N' || target == 'n')
			{
				prod *= 6;
			}
			else if(target == 'O' || target == 'o')
			{
				prod *= 6;
			}
			else if(target == 'P' || target == 'p')
			{
				prod *= 7;
			}
			else if(target == 'R' || target == 'r')
			{
				prod *= 7;
			}
			else if(target == 'S' || target == 's')
			{
				prod *= 7;
			}
			else if(target == 'T' || target == 't')
			{
				prod *= 8;
			}
			else if(target == 'U' || target == 'u')
			{
				prod *= 8;
			}
			else if(target == 'V' || target == 'v')
			{
				prod *= 8;
			}
			else if(target == 'W' || target == 'w')
			{
				prod *= 9;
			}
			else if(target == 'X' || target == 'x')
			{
				prod *= 9;
			}
			else if(target == 'Y' || target == 'y')
			{
				prod *= 9;
			}
		}
		
		System.out.println("Prod: " + prod);

	}

}
