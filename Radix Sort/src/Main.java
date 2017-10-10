import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main 
{
	public static final int inputSize = 1000;

	public static void main(String[] args) 
	{
		Random rand = new Random();
		Integer[] inputArray = new Integer[inputSize]; //integers to be sorted
		Queue[] theArray = new Queue[10]; //must be 10 (0 - 9)
		int large = -1; //number of didgets of the longest number in the array
		int chopIndex = 0; //we have to parse the integer so this keeps track of where to chop the integer
		int temp = 0; //this is used so we can continusuly chop the particular integer we are working on
		int parsedIndex = -1; //this is the index where we are going to put the integer 

		for(int j = 0; j < inputArray.length; j++)
		{
			inputArray[j] = rand.nextInt(inputSize);
		}
		
		large = largest(inputArray);
		
		for(int j = 0; j < 10; j++)
		{
			theArray[j] = new LinkedList<Integer>();
		}

		while(chopIndex < large)
		{
			for(int i = 0; i < inputArray.length; i++)
			{
				temp = inputArray[i];

				temp = temp / (int)Math.pow(10.0, (double)chopIndex);
				
				parsedIndex = (int)temp % 10;
			    theArray[parsedIndex].offer((Integer)inputArray[i]);
			}

			inputArray = convertArray((Queue[])theArray);
			chopIndex++;

		}
		
		for(int i = 0; i < inputArray.length; i++)
		{
			System.out.print(inputArray[i] + " ");
		}
		
	}

	//converts an array of queues to a single integer array
	public static Integer[] convertArray(Queue[] theArray)
	{
		Integer[] tempArray = new Integer[inputSize];
		int count = 0;

		for(int i = 0; i < theArray.length; i++)
		{
			while(!theArray[i].isEmpty())
			{
				tempArray[count] = Integer.parseInt(theArray[i].poll().toString());
				count++;
			}
		}
		
		return tempArray;

	}

	public static int largest(Integer[] input)
	{
		int largest = Integer.toString(input[0]).length();

		for(int i = 0; i < input.length; i++)
		{
			if(largest < Integer.toString(input[i]).length())
			{
				largest = Integer.toString(input[i]).length();
			}
		}

		return largest;
	}


}
