/** UDP Web Server - RTPpacket.java
* This is an implemntation of a simple UDP Web Server.
* The RTPpacket class represents a single UDP packet.
* 
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2011 Katianie.com
*/

public class RTPpacket
{
	//size of the RTP header:
	static int HEADER_SIZE = 12;

	//Fields that compose the RTP header
	public int Version;
	public int Padding;
	public int Extension;
	public int CC;
	public int Marker;
	public int PayloadType;
	public int SequenceNumber;
	public int TimeStamp;
	public int Ssrc;
	
	//Bitstream of the RTP header
	public byte[] header;
	
	//size of the RTP payload
	public int payload_size;
	
	//Bitstream of the RTP payload
	public byte[] payload;
	
	//Constructor of an RTPpacket object from header fields and payload bitstream
	public RTPpacket(int PType, int Framenb, int Time, byte[] data, int data_length)	
	{
		//Fill by default header fields:
		Version = 2;
		Padding = 0;
		Extension = 0;
		CC = 0;
		Marker = 0;
		Ssrc = 0;

		//Fill changing header fields:
		SequenceNumber = Framenb;
		TimeStamp = Time;
		PayloadType = PType;
		
		//Fill the header array of byte with RTP header fields		
		header = new byte[HEADER_SIZE];
		header[0] = new Integer( ((Version << 6)|(Padding << 5)|(Extension << 6)|CC ) ).byteValue();
		header[1] = new Integer( (Marker << 7)|PayloadType ).byteValue();
		header[2] = new Integer( SequenceNumber >> 8 ).byteValue();
		header[3] = new Integer( SequenceNumber ).byteValue();
		for(int i = 0; i < 4; i++)
		{
		    header[7-i] = new Integer( TimeStamp >> (8*i) ).byteValue();
		}
		for(int i = 0; i < 4; i++)
		{
		    header[11-i] = new Integer( Ssrc >> (8*i) ).byteValue();
		}

		//Fill the payload bitstream:
		payload_size = data_length;
		payload = new byte[data_length];
		
		//Fill payload array of byte from data (given in parameter of the constructor)
		for(int i = 0; i < payload_size; i++)
		{
			payload[i] = data[i];
			//System.out.println("payload[i]" + payload[i]);
		}
		
		printheader();
	}
	
	//Constructor of an RTPpacket object from the packet bistream 
	public RTPpacket(byte[] packet, int packet_size)
	{
		Version = 2;
		Padding = 0;
		Extension = 0;
		CC = 0;
		Marker = 0;
		Ssrc = 0;		
		
		//Check if total packet size is lower than the header size
		if (packet_size >= HEADER_SIZE) 
		{
			//Get the header bitsream:
			header = new byte[HEADER_SIZE];
			for (int i = 0; i < HEADER_SIZE; i++)			
			{
				header[i] = packet[i];			
			}

			//Get the payload bitstream:
			payload_size = packet_size - HEADER_SIZE;
			payload = new byte[payload_size];
			for (int i = HEADER_SIZE; i < packet_size; i++)			
			{
				payload[i-HEADER_SIZE] = packet[i];			
			}

			//Interpret the changing fields of the header:
			PayloadType = header[1] & 127;
			SequenceNumber = unsigned_int(header[3]) + 256 * unsigned_int(header[2]);
			TimeStamp = unsigned_int(header[7]) + 256 * unsigned_int(header[6]) + 65536 * unsigned_int(header[5]) + 16777216 * unsigned_int(header[4]);
		}
	}
	
	//Return the payload bistream of the RTPpacket and its size
	public int getpayload(byte[] data) 
	{
		for (int i = 0; i < payload_size; i++)		
		{
			data[i] = payload[i];		
		}

		return payload_size;
	}
	
	//Return the length of the payload
	public int getpayload_length() 	
	{
		return payload_size;
	}
	
	//Return the total length of the RTP packet
	public int getlength() 	
	{
		return payload_size + HEADER_SIZE;
	}
	
	//Returns the packet bitstream and its length
	public int getpacket(byte[] packet)
	{
		//Construct the packet = header + payload
		for (int i = 0; i < HEADER_SIZE; i++)		
		{
			packet[i] = header[i];		
		}	
		
		for (int i = 0; i < payload_size; i++)		
		{
			packet[i + HEADER_SIZE] = payload[i];		
		}
		
		//return total size of the packet
		return payload_size + HEADER_SIZE;
	}
	
	public int gettimestamp() 	
	{
		return TimeStamp;
	}
	
	public int getsequencenumber() 	
	{
		return SequenceNumber;
	}
	
	//getpayloadtype
	public int getpayloadtype() 	
	{
		return PayloadType;
	}
	
	//Print headers without the SSRC
	public void printheader()
	{
		for (int i = 0; i < (HEADER_SIZE - 4); i++)
		{
			for (int j = 7; j >= 0; j--)
			{
				if ( ((1<<j) & header[i] ) != 0 )
				{
					System.out.print("1");
				}
				else
				{
					System.out.print("0");
				}
			}
			System.out.print(" ");
		}
		System.out.println();
	}
	
	//Return the unsigned value of 8-bit integer nb
	public static int unsigned_int(int nb) 	
	{
		if (nb >= 0)		
		{
			return nb;		
		}
		else		
		{
			return 256 + nb;		
		}
	}
}
