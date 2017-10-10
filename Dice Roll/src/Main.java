import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main 
{

	public static void main(String[] args) 
	{	
		try { 
			BufferedReader in = new BufferedReader(new FileReader("dice.txt"));
			String str;
			int spacePos = 0;
			int count = 0;
			int d1 = 0;
			int d2 = 0;
			
			while((str = in.readLine()) != null)
			{
				spacePos = str.indexOf(' ');
				
				d1 = Integer.parseInt(str.substring(0,spacePos));
				d2 = Integer.parseInt(str.substring(spacePos + 1));
				
				if(d1 == d2)
				{
					count++;
				}
				
				
			}
			in.close(); 
			
			System.out.println("Number of double rolls: " + count);
			
		}
		catch (IOException e) 
		{ 
			System.out.println(e.getMessage());
		} 
	}

}
