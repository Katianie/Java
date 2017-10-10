import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main 
{

	public static void main(String[] args) 
	{	
		try 
		{ 
			BufferedReader in = new BufferedReader(new FileReader("blackjack.txt"));
			String str;
			String temp;
			int numBlackJack = 0;
			int points = 0;
			int spacePos = 0;
			int count = 0;

			while((str = in.readLine()) != null)
			{
				do
				{
					spacePos = str.indexOf(' ');

					if(spacePos != -1)
					{
						temp = str.substring(0,spacePos);
					}
					else
					{
						temp = str;
					}

					if(count % 2 == 0)
					{
						points = 0;
					}
					
					//fixx
					if(temp.substring(0,2).equals("10"))
					{

						points += 10;
					}
					else if(temp.charAt(0) == 'J' || temp.charAt(0) == 'Q' || temp.charAt(0) == 'K')
					{
						points += 10;
					}
					else if(temp.charAt(0) == 'A')
					{
						points += 11;
					}

					if(points == 21)
					{
						numBlackJack++;
					}

					count++;

					str = str.substring(spacePos + 1);

				}while(spacePos != -1);
				
				points = 0;
				count = 0;

			}
			in.close(); 

			System.out.println("Number of Black Jacks: " + numBlackJack);

		}
		catch (IOException e) 
		{ 
			System.out.println(e.getMessage());
		} 
	}

}
