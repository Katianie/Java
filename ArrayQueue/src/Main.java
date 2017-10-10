
public class Main 
{

	public static void main(String[] args) 
	{
		ArrayQueue<Integer> theArray = new ArrayQueue<Integer>();
		
		theArray.enqueue(1);
		theArray.enqueue(2);
		theArray.enqueue(3);
		theArray.enqueue(4);
		theArray.enqueue(5);
		
		System.out.println(theArray.toString());
		
		

	}

}
