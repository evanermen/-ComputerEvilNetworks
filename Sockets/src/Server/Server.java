package Server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server {
	private Socket socket = null;
	public int portNumber = 1234;

	public Server() {

		try ( 
				ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out =
						new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				) {

			String inputLine, outputLine;
			out.println("Hello!");
			ServerProtocol sp = new ServerProtocol();
			while ((inputLine = in.readLine()) != null) {
				outputLine = sp.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("Bye."))
					break;
			}
		} 
		catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
					+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}



	}

	
}



