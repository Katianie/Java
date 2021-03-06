/** UDP Web Server - Server.java
* This is an implemntation of a simple UDP Web Server.
* 
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2011 Katianie.com
*/
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class Server extends JFrame implements ActionListener 
{
	//RTP
	DatagramSocket RTPsocket; //socket to be used to send and receive UDP packets
	DatagramPacket senddp; //UDP packet containing the video frames
	InetSocketAddress ClientSocketAddr;
	InetAddress ClientIPAddr; //Client IP address
	int RTP_dest_port = 0; //destination port for RTP packets  (given by the RTSP Client)
	
	//GUI
	JLabel label;
	
	//Video
	int imagenb = 0; //image nb of the image currently transmitted
	VideoStream video; //VideoStream object used to access video frames
	static int MJPEG_TYPE = 26; //RTP payload type for MJPEG video
	static int FRAME_PERIOD = 100; //Frame period of the video to stream, in ms
	static int VIDEO_LENGTH = 500; //length of the video in frames
	Timer timer; //timer used to send the images at the video frame rate
	byte[] buf; //buffer used to store the images to send to the client 
	
	//RTSP message types	
	final static int SETUP = 3;	
	final static int PLAY = 4;	
	final static int PAUSE = 5;	
	final static int TEARDOWN = 6;	
	
	//RTSP variables
	final static int INIT = 0;
	final static int READY = 1;
	final static int PLAYING = 2;
	static int state; //RTSP Server state == INIT or READY or PLAY
	Socket RTSPsocket; //socket used to send/receive RTSP messages input and output stream filters
	static BufferedReader RTSPBufferedReader;
	static BufferedWriter RTSPBufferedWriter;
	static String VideoFileName; //video file requested from the client
	static int RTSP_ID = 123456; //ID of the RTSP session
	int RTSPSeqNb = 0; //Sequence number of RTSP messages within the session
	final static String CRLF = "\r\n";
	
	//Constructor
	public Server()	
	{
		//Initialize Frame
		super("Server");		
		
		//Initialize Timer
		timer = new Timer(FRAME_PERIOD, this);
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		
		//Allocate memory for the sending buffer
		buf = new byte[15000]; 		
		
		//Handler to close the main window
		addWindowListener(new WindowAdapter() 		
		{
			public void windowClosing(WindowEvent e) 			
			{
				//stop the timer and exit
				timer.stop();
				System.exit(0);
			}		
		});
		
		label = new JLabel("Send frame #        ", JLabel.CENTER);
		getContentPane().add(label, BorderLayout.CENTER);
	}	
	
	//Handler for timer
	public void actionPerformed(ActionEvent e) 	
	{
		//If the current image nb is less than the length of the video
		if (imagenb < VIDEO_LENGTH)
		{
			//Update current imagenb
			imagenb++;
			
			try 			
			{
				//Get next frame to send from the video, as well as its size
				int image_length = video.getnextframe(buf);
				
				//Builds an RTPpacket object containing the frame
				RTPpacket rtp_packet = new RTPpacket(MJPEG_TYPE, imagenb, imagenb*FRAME_PERIOD, buf, image_length);
				
				//Get to total length of the full rtp packet to send
				int packet_length = rtp_packet.getlength();
				
				//Retrieve the packet bitstream and store it in an array of bytes
				byte[] packet_bits = new byte[packet_length];				
				rtp_packet.getpacket(packet_bits);
				
				//Send the packet as a DatagramPacket over the UDP socket 
				senddp = new DatagramPacket(packet_bits, packet_length, ClientIPAddr, RTP_dest_port);
				RTPsocket.send(senddp);
				//System.out.println("Send frame #"+imagenb);
				
				//Print the header bitstream
				rtp_packet.printheader();
				
				//Update GUI
				label.setText("Send frame #" + imagenb);
			}
			catch(Exception ex)
			{
				System.out.println("Exception caught: "+ex);
				System.exit(0);
			}
		}
		else
		{
			//If we have reached the end of the video file, stop the timer
			timer.stop();
		}
	}
	
	//Parse RTSP Request
	private int parse_RTSP_request()
	{
		int request_type = -1;		
		
		try		
		{
			//Parse request line and extract the request_type:
			String RequestLine = RTSPBufferedReader.readLine();
			System.out.println("RTSP Server - Received from Client:");
			System.out.println(RequestLine);
			
			StringTokenizer tokens = new StringTokenizer(RequestLine);
			
			String request_type_string = tokens.nextToken();
			
			//Convert to request_type structure:
			if ((new String(request_type_string)).compareTo("SETUP") == 0)			
			{
				request_type = SETUP;			
			}
			else if ((new String(request_type_string)).compareTo("PLAY") == 0)	
			{
				request_type = PLAY;		
			}
			else if ((new String(request_type_string)).compareTo("PAUSE") == 0)
			{
				request_type = PAUSE;			
			}
			else if ((new String(request_type_string)).compareTo("TEARDOWN") == 0)			
			{
				request_type = TEARDOWN;			
			}

			if (request_type == SETUP)
			{
				//extract VideoFileName from RequestLine
				VideoFileName = tokens.nextToken();
			}

			//Parse the SeqNumLine and extract CSeq field
			String SeqNumLine = RTSPBufferedReader.readLine();
			System.out.println(SeqNumLine);
			tokens = new StringTokenizer(SeqNumLine);
			tokens.nextToken();
			RTSPSeqNb = Integer.parseInt(tokens.nextToken());
			
			//Get LastLine
			String LastLine = RTSPBufferedReader.readLine();
			System.out.println(LastLine);
			if (request_type == SETUP)
			{
				//extract RTP_dest_port from LastLine
				tokens = new StringTokenizer(LastLine);
				
				for (int i = 0; i < 3; i++)
				{
					tokens.nextToken(); //skip unused stuff
				}
				
				RTP_dest_port = Integer.parseInt(tokens.nextToken());
			}
			//else LastLine will be the SessionId line ... do not check for now.
		}
		catch(Exception ex)
		{
			System.out.println("Exception caught: "+ex);
			System.exit(0);
		}
		
		return request_type;
	}
	
	//Send RTSP Response
	private void send_RTSP_response()
	{
		try		
		{
			RTSPBufferedWriter.write("RTSP/1.0 200 OK" + CRLF);
			RTSPBufferedWriter.write("CSeq: " + RTSPSeqNb + CRLF);
			RTSPBufferedWriter.write("Session: " + RTSP_ID + CRLF);
			RTSPBufferedWriter.flush();
			
			System.out.println("RTSP Server - Sent response to Client.");
		}
		catch(Exception ex)
		{
			System.out.println("Exception caught: " + ex);
			System.exit(0);
		}
	}
	
	//main
	public static void main(String argv[]) throws Exception
	{		
		//Create a Server object		
		Server theServer = new Server();		
		int request_type;		
		boolean done = false;				
		
		//Show GUI		
		theServer.pack();		
		theServer.setVisible(true);	

		//Get RTSP socket port from the command line
		int RTSPport = Integer.parseInt(argv[0]);

		//Initiate TCP connection with the client for the RTSP session
		ServerSocket listenSocket = new ServerSocket(RTSPport);	
		listenSocket.setReceiveBufferSize(15000);		
		theServer.RTSPsocket = listenSocket.accept();		
		theServer.RTSPsocket.setSendBufferSize(15000);	
		theServer.RTSPsocket.setReceiveBufferSize(15000);	
		listenSocket.close();		
		
		//Get Client IP address		
		theServer.ClientIPAddr = theServer.RTSPsocket.getInetAddress();	
		
		//Initiate RTSPstate	
		state = INIT;		
		
		//Set input and output stream filters:
		RTSPBufferedReader = new BufferedReader(new InputStreamReader(theServer.RTSPsocket.getInputStream()) );	
		RTSPBufferedWriter = new BufferedWriter(new OutputStreamWriter(theServer.RTSPsocket.getOutputStream()) );	

		//Wait for the SETUP message from the client	
		while(done == false)		
		{			
			request_type = theServer.parse_RTSP_request(); 
			
			//blocking			
			if (request_type == SETUP)	
			{
				done = true;
				
				//Update RTSP state
				state = READY;	
				System.out.println("New RTSP state: READY");

				//Send response	
				theServer.send_RTSP_response();		
				
				//Initialize the VideoStream object:
				theServer.video = new VideoStream(VideoFileName);	
				
				//Initialize RTP socket
				theServer.RTPsocket = new DatagramSocket();
				theServer.RTPsocket.setReceiveBufferSize(15000);
				theServer.RTPsocket.setSendBufferSize(15000);	
			}		
		}		
		
		//loop to handle RTSP requests		
		while(true)		
		{			
			//parse the request	
			request_type = theServer.parse_RTSP_request();

			//blocking
			if ((request_type == PLAY) && (state == READY))
			{	
				//Send back response
				theServer.send_RTSP_response();
				
				//Start timer
				theServer.timer.start();

				//Update state
				state = PLAYING;
				System.out.println("New RTSP state: PLAYING");	
			}			
			else if ((request_type == PAUSE) && (state == PLAYING))
			{		
				//Send back response
				theServer.send_RTSP_response();	

				//Stop timer
				theServer.timer.stop();	
				
				//Update state
				state = READY;
				System.out.println("New RTSP state: READY");
			}			
			else if (request_type == TEARDOWN)
			{			
				//Send back response
				theServer.send_RTSP_response();

				//Stop timer
				theServer.timer.stop();
				
				//Close sockets
				theServer.RTSPsocket.close();
				theServer.RTPsocket.close();
				System.exit(0);
			}
		}
	}
}