import java.io.*;

public class Main 
{

	public static void main(String[] args) 
	{	
		try { 
			BufferedReader in = new BufferedReader(new FileReader("plane.txt"));
			String str;
			long numFirstQuad = 0;
			long numSecondQuad = 0;
			long numThirdQuad = 0;
			long numFourthQuad = 0;
			int x = 0;
			int y = 0;
			int commaPos = 0;
			
			while((str = in.readLine()) != null)
			{
				commaPos = str.indexOf(',');
				x = Integer.parseInt(str.substring(0, commaPos));
				y = Integer.parseInt(str.substring(commaPos + 1));
				
				//System.out.println(str);
				
				if(x > 0 && y > 0)
				{
					numFirstQuad++;
				}
				else if(x < 0 && y > 0)
				{
					numSecondQuad++;
				}
				else if(x < 0 && y < 0)
				{
					numThirdQuad++;
				}
				else
				{
					numFourthQuad++;
				}
				
				
			}
			in.close(); 
			
			System.out.println("Number of Coordinates in First Quad: " + numFirstQuad);
			System.out.println("Number of Coordinates in Second Quad: " + numSecondQuad);
			System.out.println("Number of Coordinates in Third Quad: " + numThirdQuad);
			System.out.println("Number of Coordinates in Fourth Quad: " + numFourthQuad);
			
			
		}
		catch (IOException e) 
		{ 
			System.out.println(e.getMessage());
		} 
	}

}
