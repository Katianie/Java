/** UDP Web Server - VideoStream.java
* This is an implemntation of a simple UDP Web Server.
* 
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2011 Katianie.com
*/
import java.io.*;

public class VideoStream 
{
	FileInputStream fis; //video file
	int frame_nb; //current frame nb
	
	//Constructor
	public VideoStream(String filename) throws Exception	
	{
		fis = new FileInputStream(filename);
		frame_nb = 0;
	}
	
	//Returns the next frame as an array of byte and the size of the frame.
	public int getnextframe(byte[] frame) throws Exception
	{
		int length = 0;
		String length_string;
		byte[] frame_length = new byte[5];
		
		//Read the current frame length.
		fis.read(frame_length,0,5);
		
		//convert frame_length to integer.
		length_string = new String(frame_length);
		length = Integer.parseInt(length_string);		
		
		return fis.read(frame,0,length);
	}
}
