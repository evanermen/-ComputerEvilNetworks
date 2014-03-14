package Client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {


	public ArrayList<String> inputToServer = new ArrayList<String>();
	public Socket echoSocket = null;


	public Client() throws UnknownHostException, IOException {

		readInputToServer();  //method to connect to server

		connectToServer();  //connect for real

		readInputCommand(); //read commands

	}


	private void readInputToServer() {
		String fromUser = readInput();
		String[] tokens = fromUser.split(" ");
		this.inputToServer = new ArrayList<String>(Arrays.asList(tokens)); // gets first line of input

		checkInputToServer();
	}

	private void checkInputToServer() {
		if(this.inputToServer.size() != 2) {
			System.err.println(
					"Expected 2 arguments:  <host name> <port number>");
			System.exit(1);
		}
	}

	private void connectToServer() throws UnknownHostException, IOException {

		String hostName = inputToServer.get(0);
		int port = Integer.parseInt(inputToServer.get(1));
		try {
			this.echoSocket = new Socket(hostName, port); //makes the socket with the input from the first inputline.
			System.out.println("Connection succeeded.");

		}
		catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} 
	}


	private void readInputCommand() {
		boolean exit = false;
		while(!exit) {
			try {
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
				String fromUser = "";
				String readInput;
				while(!(readInput = readInput()).equals("")){      
					fromUser = readInput;						//reads input from second line = command to server.
				}

				out.println(fromUser);
				out.println();
				out.flush(); 
				
				String protocol = fromUser.split(" ")[2];
				if(protocol.equals("HTTP/1.0")) {
					exit = true;
				}
						
				doCommand(fromUser, echoSocket);
			}
			catch (IOException e) {
				System.err.println("IO-exception!");
				System.exit(1);
			}
		}
	}

	private void doCommand(String fromUser,	Socket socket) throws IOException {
		Command command = new Command(fromUser, socket);		
	}

	private String readInput(){
		BufferedReader in =
				new BufferedReader(new InputStreamReader(System.in));
		String fromUser;

		try {
			while ((fromUser = in.readLine()) != null) {
				return fromUser;
			}
		} catch (IOException e) {
			System.err.println(
					"IO-exception!");
			System.exit(1);
		}
		return null; //komt-ie nooit

	}

}
