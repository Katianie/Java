package Binary;

import javax.swing.JOptionPane;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		while(true)
		{
			int choice;

			String[] options = {"Convert int to binary","Convert binary to letters","Convert String to binary","Exit"};

			choice = JOptionPane.showOptionDialog(null, "Please select an option", 
					"Binary Converter", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, 
					options, options[0]);
			
			char[] asciiArray = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
					' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
					' ','!','"','#','$','%','&',' ','(',')','*','+',',','-','.','/',
					'0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?',
					'@','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
					'P','Q','R','S','T','U','V','W','X','Y','Z','[','\\',']','^','_',
					'`','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o',
					'p','q','r','s','t','u','v','w','x','y','z','{','|','}','~',' '};

			if(choice == 0)
			{
				int asciNum = Integer.parseInt(JOptionPane.showInputDialog("Please enter the number you wish to convert"));
				
				JOptionPane.showMessageDialog(null,"Ascii Num: "+ asciNum + "\nBinary: " + convertToBinary(asciNum));
			}
			else if(choice == 1)
			{
				int loopNum = 0; 
				int powerRaiser = 0;
				int beginIndex = 0;
				int endIndex = 8;
				int solutionNum = 0;
				String sequence = null;
				String tempSequence = null;
				String answer = " ";
				

				try
				{
					sequence = removeSpaces(JOptionPane.showInputDialog("Please enter the binary sequence"));
					tempSequence = sequence.substring(beginIndex, endIndex);


					while(loopNum < (sequence.length() / 8))
					{
						if(endIndex < sequence.length())
						{
							tempSequence  = sequence.substring(beginIndex, endIndex); 
						}
						else
						{
							tempSequence  = sequence.substring(beginIndex, sequence.length()); 
						}


						beginIndex = endIndex;
						endIndex += 8;


						for(int i = 7; i > 0; i--)
						{
							if(tempSequence.charAt(i)== '1')
							{
								solutionNum += Math.pow(2, powerRaiser);
							}

							powerRaiser++;
						}

						powerRaiser = 0;

						answer += asciiArray[solutionNum];
						solutionNum = 0;
						loopNum++;

					}

					JOptionPane.showMessageDialog(null, answer);
				}
				catch(StringIndexOutOfBoundsException ex)
				{
					JOptionPane.showMessageDialog(null, "Please eneter a string 8 chars or longer");
				}

			}
			else if(choice == 2)
			{
				String input = removeSpaces(JOptionPane.showInputDialog("Please enter the string you wish to convert."));
				String answer = "";
				
				for(int i = 0; i < input.length(); i++)
				{
					for(int j = 0; j < asciiArray.length; j++)
					{
						if(input.charAt(i) == asciiArray[j])
						{
							answer += convertToBinary(j) + " ";
						}
					}
					
					if(i != 0 && i % 10 == 0)
					{
						answer += "\n";
					}
					
				}
				
				JOptionPane.showMessageDialog(null, answer);
			}
			else if(choice == 3)
			{
				System.exit(0);
			}
		}
	}


	public static String removeSpaces(String sequence)
	{
		String temp = "";

		for(int i = 0; i < sequence.length(); i++)
		{
			if(!(sequence.charAt(i) == ' '))
			{
				temp += sequence.charAt(i);
			}
		}

		return temp;
	}
	
	public static String convertToBinary(int asciNum)
	{
		
		int num = 0;
		int j = 0;
		boolean solved = false;
		String toStringTemp = "";
		BinaryDidget[] binaryArray;
		
		if(asciNum > 255)
		{
			binaryArray = new BinaryDidget[20];
		}
		else
		{
			binaryArray = new BinaryDidget[8];
		}

		for(int i = 0; i < binaryArray.length; i++)
		{
			binaryArray[i] = new BinaryDidget(Math.pow(2.0,i),false);
		}


		while(solved == false)
		{
			if(num == asciNum)
			{
				solved = true;
			}

			while(num < asciNum)//adding
			{
				if(num == asciNum)
				{
					solved = true;
				}

				num += binaryArray[j].getMyDig();
				binaryArray[j].setMyIsOne(true);
				j++;
			}

			j = 0;

			while(num > asciNum)
			{
				if(num == asciNum)
				{
					solved = true;
				}

				num -= binaryArray[j].getMyDig();
				binaryArray[j].setMyIsOne(false);
				j++;
			}

			j = 0;
		}

		for(int count  = binaryArray.length - 1; count  >= 0 ; count--)
		{
			toStringTemp += binaryArray[count].toString();
		}
		
		return toStringTemp;

	}
}
