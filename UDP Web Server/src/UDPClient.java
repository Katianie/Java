import java.io.*;
import java.net.*;

public class UDPClient 
{
	public static void main(String[] args)
	{		
		try 
		{	
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			DatagramSocket clientSocket = new DatagramSocket();
			DatagramPacket sendPacket;
			DatagramPacket recevePacket;
			InetAddress IPAddress = InetAddress.getByName("hostname");
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String sentence = inFromUser.readLine();
			String modifiedSentence;
			
			sendData = sentence.getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);
			
			recevePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(recevePacket);
			
			modifiedSentence = new String(recevePacket.getData());
			
			System.out.println("FROM SERVER:" + modifiedSentence);
			
			clientSocket.close();
		} 
		catch (SocketException e)
		{
			e.printStackTrace();
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
