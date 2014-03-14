package Client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Command {

	ArrayList<String> commandList = new ArrayList<String>();
	String commandString = null;
	Socket socket;

	public Command(String inputCommand, Socket socket) throws IOException {
		this.socket = socket;
		addCommands();
		checkString(inputCommand);
		doCommand();
	}

	private void addCommands() {
		commandList.add("HEAD");
		commandList.add("GET");
		commandList.add("PUT");
		commandList.add("POST");
	}

	private void checkString(String inputCommand) {
		String[] tokens = inputCommand.split(" ");
		for(String command: commandList) {
			if(tokens[0].equals(command)){
				this.commandString = tokens[0];
			}
		}

		System.out.println(this.commandString);
		if(this.commandString == null) {
			System.err.println(
					"Expected <HTTP-command>: {HEAD, GET, PUT, POST}");
		}
	}

	private void doCommand() throws IOException { //executes command depending on input
		InputStream stream = socket.getInputStream();
		byte[] streamContent = copyStream(stream);        //copies the stream to a byteArray so we can use that stream multiple times.

		
		
		if(commandString.equals("HEAD")) {
			doHeadCommand(new ByteArrayInputStream(streamContent));
		}
		else if(commandString.equals("GET")) {
			doGetCommand(streamContent);
		}
		else if(commandString.equals("PUT")) {
			doPutCommand(new File(""));
		}
		else if(commandString.equals("POST")) {
			doPostCommand();
		}
	}


	private void doHeadCommand(ByteArrayInputStream stream) throws IOException { //HEAD command only prints the head of the html
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));

		String inputLine; 

		while ((inputLine = in.readLine()) != null) { 
			System.out.println(inputLine); 
			
		} 
	}

	private void doGetCommand(byte[] streamContent) throws IOException { //check on images and prints response of server
		getImages(streamContent); //check if there are images on the page

		BufferedReader in = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(streamContent)));

		String inputLine; 

		while ((inputLine = in.readLine()) != null) { //print output server
			System.out.println(inputLine); 
		}
	}

	private void getImages(byte[] stream) throws IOException {
		CheckImages checkImages = new CheckImages(socket, stream);		
	}

	private void doPutCommand(File html) {
		replaceHtmlFile(html);
	}

	private void replaceHtmlFile(File html) {
			
	}


	private void doPostCommand() {
		BufferedReader in =
				new BufferedReader(new InputStreamReader(System.in));
		String fromUser = "New Input: ";
		String inputLine;
		try {
			while ((inputLine = in.readLine()) != null && !(in.readLine().equals(""))) {
				fromUser = fromUser.concat("/n" + inputLine);
			}
		} catch (IOException e) {
			System.err.println(
					"IO-exception!");
			System.exit(1);
		}
		saveText(fromUser);
	}

	private void saveText(String fromUser) {
			
	}



	private byte[] copyStream(InputStream stream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		double beginTime = System.currentTimeMillis();
		
		int count;
		
		while ((count = stream.read(buffer)) > -1 && (System.currentTimeMillis() - beginTime) < 3000) { //For some reason if I type HTTP 1.1 it gets stuck in this loop.
			baos.write(buffer, 0, count);
		}
		baos.flush();

		return baos.toByteArray(); 
	}


}
