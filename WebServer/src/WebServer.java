import java.io.*;
import java.net.*;
import java.util.*;

public final class WebServer 
{
	public static void main(String[] args) throws Exception
	{	
		//Set port number
		int port = 50034;
		
		//Establish the listen socket
		ServerSocket listenSocket = new ServerSocket(port);
		
		//Process HTTP service requests in an infinate loop
		while(true)
		{
			//Listen for TCP connection request
			Socket connectionSocket = listenSocket.accept();
			
			//Construct an object to process the HTTP request messsage
			HttpRequest request = new HttpRequest(connectionSocket);
			
			//Create a new thread to process the request
			Thread thread = new Thread(request);
			
			//Start the thread
			thread.start();
			
		}
		
	}

}
