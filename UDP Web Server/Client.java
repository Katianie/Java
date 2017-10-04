/** UDP Web Server - Client.java
* This is an implemntation of a simple UDP Web Server.
* The Client class is responsible for both creating 
* the GUI and contains the main method.
* 
* This was uploaded to Katianie.com, Feel free to use this
* code and share it with others, Just give me credit ^_^.
*
* Eddie O'Hagan
* Copyright © 2011 Katianie.com
*/
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class Client
{	
	//"Carrage return line feed"	
	final static String CRLF = "\r\n";	
	Timer timer; //timer used to receive data from the UDP socket	
	byte[] buf; //buffer used to store data received from the server 	
	
	//Simple GUI
	JFrame f = new JFrame("Client");
	JButton setupButton = new JButton("Setup");
	JButton playButton = new JButton("Play");
	JButton pauseButton = new JButton("Pause");
	JButton tearButton = new JButton("Teardown");
	JPanel mainPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JLabel iconLabel = new JLabel();
	ImageIcon icon;
	ArrayList<Integer> myList;
	
	//RTP variables:
	DatagramPacket rcvdp; //UDP packet received from the server
	DatagramSocket RTPsocket; //socket to be used to send and receive UDP packets
	static int RTP_RCV_PORT = 25000; //port where the client will receive the RTP packets

	//RTSP states 
	final static int INIT = 0;
	final static int READY = 1;
	final static int PLAYING = 2;	static int MJPEG_TYPE = 26; //RTP payload type for MJPEG video
	static int state; //RTSP state == INIT or READY or PLAYING
	Socket RTSPsocket; //socket used to send/receive RTSP messages
	
	//Input and output stream filters
	static BufferedReader RTSPBufferedReader;
	static BufferedWriter RTSPBufferedWriter;
	static String VideoFileName; //video file to request to the server
	int RTSPSeqNb = 0; //Sequence number of RTSP messages within the session
	int RTSPid = 0; //ID of the RTSP session (given by the RTSP Server)
	
	//Constructor
	public Client() 	
	{
		//Build the GUI
		//Frame
		f.addWindowListener(new WindowAdapter() 		
		{
			public void windowClosing(WindowEvent e) 			
			{
				System.exit(0);
			}
		});
		
		//Buttons
		buttonPanel.setLayout(new GridLayout(1,0));
		buttonPanel.add(setupButton);
		buttonPanel.add(playButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(tearButton);		
		setupButton.addActionListener(new setupButtonListener());
		playButton.addActionListener(new playButtonListener());
		pauseButton.addActionListener(new pauseButtonListener());
		tearButton.addActionListener(new tearButtonListener());
		
		//Image display label
		iconLabel.setIcon(null);
		
		//Frame layout
		mainPanel.setLayout(null);
		mainPanel.add(iconLabel);
		mainPanel.add(buttonPanel);
		iconLabel.setBounds(0,0,380,280);
		buttonPanel.setBounds(0,280,380,50);		//Position and size the frame.
		f.getContentPane().add(mainPanel, BorderLayout.CENTER);
		f.setSize(new Dimension(390,370));
		f.setVisible(true);
		
		//Initialize timer
		timer = new Timer(20, new timerListener());
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		
		//Allocate enough memory for the buffer used to receive data from the server
		buf = new byte[15000];    
		myList = new ArrayList<Integer>();
	}
	
	class setupButtonListener implements ActionListener	
	{
		public void actionPerformed(ActionEvent e)		
		{
			//System.out.println("Setup Button pressed !");      
			if (state == INIT) 
			{
				//Initialize non-blocking RTPsocket that will be used to receive data
				try				
				{
					//Construct a new DatagramSocket to receive RTP packets from the server, on port RTP_RCV_PORT
					RTPsocket = new DatagramSocket(RTP_RCV_PORT);
					
					//Set TimeOut value of the socket to 5msec.
					RTPsocket.setSoTimeout(5);					
					RTPsocket.setSendBufferSize(500000000);
					RTPsocket.setReceiveBufferSize(500000000);
				}
				catch (SocketException se)
				{
					System.out.println("Socket exception: "+se);
					System.exit(0);
				}
				
				//Initialize RTSP sequence number
				RTSPSeqNb = 1;
				
				//Send SETUP message to the server
				send_RTSP_request("SETUP");
				
				//Wait for the response 
				if (parse_server_response() != 200)
				{
					System.out.println("Invalid Server Response");
				}
				else 
				{
					//Change RTSP state and print new state 
					state = READY;
					System.out.println("New RTSP state: READY");
				}
			}
			//else if state != INIT then do nothing
		}
	}
	
	//Handler for Play button
	class playButtonListener implements ActionListener 	
	{
		public void actionPerformed(ActionEvent e)		
		{
			//System.out.println("Play Button pressed !"); 
			if (state == READY) 
			{
				//increase RTSP sequence number
				RTSPSeqNb++;
				
				//Send PLAY message to the server
				send_RTSP_request("PLAY");
				
				//Wait for the response 
				if (parse_server_response() != 200)
				{
					System.out.println("Invalid Server Response");
				}
				else 
				{
					//Change RTSP state and print out new state
					state = PLAYING;
					System.out.println("New RTSP state: PLAYING");
					
					//start the timer
					timer.start();
				}
			}
			//else if state != READY then do nothing
		}
	}
	
	//Handler for Pause button
	class pauseButtonListener implements ActionListener 	
	{
		public void actionPerformed(ActionEvent e)		
		{
			//System.out.println("Pause Button pressed !");   			
			if (state == PLAYING) 
			{
				//Increase RTSP sequence number
				RTSPSeqNb++;
				
				//Send PAUSE message to the server
				send_RTSP_request("PAUSE");
				
				//Wait for the response 
				if (parse_server_response() != 200)
				{
					System.out.println("Invalid Server Response");
				}
				else 
				{
					//Change RTSP state and print out new state
					state = READY;
					System.out.println("New RTSP state: READY");
					
					//Stop the timer
					timer.stop();
				}
			}
			//else if state != PLAYING then do nothing
		}
	}
	
	//Handler for Teardown button
	class tearButtonListener implements ActionListener 	
	{
		public void actionPerformed(ActionEvent e)		
		{
			//System.out.println("Teardown Button pressed !");  
			//Increase RTSP sequence number
			RTSPSeqNb++;
			
			//Send TEARDOWN message to the server
			send_RTSP_request("TEARDOWN");
			
			//Wait for the response 
			if (parse_server_response() != 200)
			{
				System.out.println("Invalid Server Response");
			}
			else 
			{     
				//Change RTSP state and print out new state
				state = INIT;
				System.out.println("New RTSP state: INIT");
				
				//Stop the timer
				timer.stop();
				
				//Exit
				System.exit(0);
			}
		}
	}
	
	//Handler for timer
	class timerListener implements ActionListener 	
	{
		public void actionPerformed(ActionEvent e) 		
		{
			//Construct a DatagramPacket to receive data from the UDP socket
			rcvdp = new DatagramPacket(buf, buf.length);
			
			try			
			{
				//Receive the DP from the socket :
				RTPsocket.receive(rcvdp);
				
				//Create an RTPpacket object from the DP
				RTPpacket rtp_packet = new RTPpacket(rcvdp.getData(), rcvdp.getLength());
				
				if(myList.contains(rtp_packet.getsequencenumber()) == false)
				{
					//Print important header fields of the RTP packet received: 
					System.out.println("Got RTP packet with SeqNum # " + rtp_packet.getsequencenumber() +
									   "TimeStamp " + rtp_packet.gettimestamp() + 
									   "ms, of type " + rtp_packet.getpayloadtype() );
									   
					myList.add(rtp_packet.getsequencenumber());
					
					//Print header bitstream:
					rtp_packet.printheader();
					
					//Get the payload bitstream from the RTPpacket object
					int payload_length = rtp_packet.getpayload_length();
					byte [] payload = new byte[payload_length];
					rtp_packet.getpayload(payload);

					//Get an Image object from the payload bitstream
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Image image = toolkit.createImage(payload, 0, payload_length);
					
					//Display the image as an ImageIcon object.
					icon = new ImageIcon(image);
					iconLabel.setIcon(icon);
				}
				else
				{
					System.out.println( "Got Duplicate RTP packet with SeqNum # " + rtp_packet.getsequencenumber() );
				}
			}
			catch (InterruptedIOException iioe)
			{
				//System.out.println("Nothing to read");
			}
			catch (IOException ioe) 
			{
				System.out.println("Exception caught: "+ioe);
			}
		}
	}

	//Parse Server Response
	private int parse_server_response() 
	{
		int reply_code = 0;
		
		try		
		{
			//parse status line and extract the reply_code:
			String StatusLine = RTSPBufferedReader.readLine();
			
			System.out.println("RTSP Client - Received from Server:");
			System.out.println(StatusLine);
			
			StringTokenizer tokens = new StringTokenizer(StatusLine);			
			
			tokens.nextToken(); //skip over the RTSP version			
			
			reply_code = Integer.parseInt(tokens.nextToken());
			
			//if reply code is OK get and print the 2 other lines
			if (reply_code == 200)
			{
				String SeqNumLine = RTSPBufferedReader.readLine();
				System.out.println(SeqNumLine);
				
				String SessionLine = RTSPBufferedReader.readLine();
				System.out.println(SessionLine);
				
				//if state == INIT gets the Session Id from the SessionLine
				tokens = new StringTokenizer(SessionLine);
				
				tokens.nextToken(); //skip over the Session:
				
				RTSPid = Integer.parseInt(tokens.nextToken());
			}
			else
			{
				System.out.println("reply_code:" + reply_code);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception caught : "+ex);
			System.exit(0);
		}	
		
		return reply_code;
	}
	
	//Send RTSP Request
	private void send_RTSP_request(String request_type)
	{
		try		
		{
			//Use the RTSPBufferedWriter to write to the RTSP socket
			//Write the request line:
			RTSPBufferedWriter.write(request_type + " " + VideoFileName + " RTSP/1.0" + CRLF);
			
			//Write the CSeq line: 
			RTSPBufferedWriter.write("CSeq: " + RTSPSeqNb + CRLF);
			
			//Check if request_type is equal to "SETUP" and in this case write the Transport 
			//line advertising to the server the port used to receive the RTP packets RTP_RCV_PORT
			if (request_type.equalsIgnoreCase("SETUP"))
			{
				RTSPBufferedWriter.write("Transport: RTP/UDP; client_port= " + RTP_RCV_PORT+CRLF);
			}
			else
			{
				RTSPBufferedWriter.write("Session: " + RTSPid + "\n");
			}
			
			RTSPBufferedWriter.flush();
		}
		catch(Exception ex)
		{
			System.out.println("Exception caught : "+ex);
			System.exit(0);
		}
	}	
	
	public static void main(String argv[]) throws Exception	
	{		
		//Create a Client object		
		Client theClient = new Client();		
		
		//Get server RTSP port and IP address from the command line
		int RTSP_server_port = Integer.parseInt(argv[1]);
		String ServerHost = argv[0];
		InetAddress ServerIPAddr = InetAddress.getByName(ServerHost);
		
		//Get video filename to request:
		VideoFileName = argv[2];		
		
		//Establish a TCP connection with the server to exchange RTSP messages
		theClient.RTSPsocket = new Socket(ServerIPAddr, RTSP_server_port);
		theClient.RTSPsocket.setSendBufferSize(15000);
		theClient.RTSPsocket.setReceiveBufferSize(15000);

		//Set input and output stream filters:
		RTSPBufferedReader = new BufferedReader(new InputStreamReader(theClient.RTSPsocket.getInputStream()) );
		RTSPBufferedWriter = new BufferedWriter(new OutputStreamWriter(theClient.RTSPsocket.getOutputStream()) );

		//init RTSP state:
		state = INIT;
	}
}

