import java.io.*;
import java.net.*;
import java.util.*;

public final class HttpRequest implements Runnable
{
	private static final String myCRLF = "\r\n";
	private Socket mySocket;
	private byte[] buffer = new byte[1024]; //Construct a 1K buffer to hold bytes on their way to the socket
	
	public HttpRequest(Socket socket) throws Exception
	{
		mySocket = socket;
	}
	
	private String contentType(String fileName)
	{
		if(fileName.endsWith(".htm") || fileName.endsWith(".html")) 
		{
			return "text/html";
		}
		
		if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) 
		{
			return "image/jpeg";
		}
		
		if(fileName.endsWith(".gif")) 
		{
			return "image/gif";
		}
		
		if(fileName.endsWith(".txt"))
		{
			return "text/plain";
		}
		
		return "application/octet-stream";

	}
	
	private static void sendBytes(byte[] buffer, FileInputStream fis, OutputStream os) throws Exception
	{
		
		
		int bytes = 0;

		
		while( (bytes = fis.read(buffer)) != -1 )
		{
			os.write(buffer, 0, bytes);
			
			for(int i = 0; i < buffer.length; i++)
			{
				System.out.print((char)buffer[i]);
			}
		}
	}

	private void processRequest() throws Exception
	{
		String fileName = null;
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		
		long numOfBytes = 0;
		File theFile = null;
		FileInputStream fis = null;
		boolean fileExists = true;

		//Get refrence to sockets input and output streams
		InputStream is = mySocket.getInputStream();
		DataOutputStream os = new DataOutputStream(mySocket.getOutputStream());

		//Setup input stream filters
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		//Get the request line of the HTTP request message
		String requestLine = br.readLine();
		StringTokenizer tokens = new StringTokenizer(requestLine);

		System.out.println("requestLine:" + requestLine);
		
		//Skip over the request which should be GET
		if(tokens.nextToken().equals("GET"))
		{
			fileName = tokens.nextToken();
			
			if(fileName.startsWith("/") == true)
			{
				fileName = fileName.substring(1);
			}
				
			System.out.println("fileName:" + fileName);
			
			theFile = new File(fileName);
			numOfBytes = theFile.length();
			
			//File path to make sure we are in the directory we want
			//System.out.println(theFile.getAbsolutePath());
			
			//Prepend a ./ so that file request is within the current directory
			fileName = "./" + fileName;

			try
			{
				fis = new FileInputStream(fileName);
			}
			catch(FileNotFoundException e)
			{
				fileExists = false;
			}

			if(fileExists)
			{
				statusLine = tokens.nextToken() + " 200 Document Follows";
				contentTypeLine = "Content-Type: " + contentType(fileName) + myCRLF;
				entityBody = fis.toString();
			}
			else
			{
				statusLine = "HTTP/1.1 404 Not Found";
				contentTypeLine = "";
				entityBody = "<HTML>" + "" +
							"<HEAD><TITLE>Not Found</TITLE></HEAD>" +
							"<BODY>Not Found</BODY></HTML>";
			}
			
			
			//Send the status line to the browser
			os.writeBytes(statusLine);
			
			//Send the size of the file to browser
			os.writeBytes("Content-Length: " + numOfBytes);
			
			//Send the content type line
			os.writeBytes(contentTypeLine);
			
			//Send a blank line to indicate the end of the header lines
			os.writeBytes(myCRLF);
			
			System.out.println("statusLine:" + statusLine);
			System.out.println("Content-Length: " + numOfBytes);
			System.out.println("contentTypeLine:" + contentTypeLine);
			
			if(fileExists)
			{
				buffer = new byte[(int) numOfBytes];
				sendBytes(buffer, fis, os);
				fis.close();
			}
			else
			{
				os.writeBytes("404 File Not Found");
			}
			


		}
		else //request was not GET
		{
			statusLine = "HTTP/1.1 501 Not Implemented";
			contentTypeLine = "";
			entityBody = "<HTML>" + 
						"<HEAD><TITLE>Not Implemented</TITLE></HEAD>" +
						"<BODY>501 Not Implemented</BODY></HTML>";
			
			os.writeBytes(statusLine);
			
			//Send the content type line
			os.writeBytes(contentTypeLine);
			
			//Send a blank line to indicate the end of the header lines
			os.writeBytes(myCRLF);
		}

		os.close();
		br.close();
		mySocket.close();

	}

	public void run() 
	{
		try
		{
			processRequest();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}



}
