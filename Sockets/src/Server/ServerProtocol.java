package Server;

import java.io.File;
import java.io.IOException;

public class ServerProtocol {

	private static final int WAITING = 0;
	private static final int PUT = 1;
	private static final int POST = 2;
	private int state = WAITING;
	

	public ServerProtocol() {
			
	}

	public String processInput(String inputLine) {
		String theOutput = null;

		if (state == WAITING) {
			
			String[] input = inputLine.split(" ");
			if input.size 
			if (checkDirectory(input[1])){
				String command = input[0];
				theOutput = command;
				if (command.equals("GET")){
					theOutput = "the whole txt file";
				}
				else if (command.equals("HEAD")){
					theOutput = "the head";
				}
				else if (command.equals("PUT")){
					theOutput = "Uitleg whattoput";
					state = PUT;
					
					}
				else if (command.equals("POST")){
					theOutput = "Uitleg whattopost";
					state = POST;
				}
			}

			else {theOutput = "TODO 404 ofzo Unknown directory.";}

		}

		return theOutput;
	}

	private boolean checkDirectory(String dir){
		boolean check = new File("C:/Users/Milan/git/-ComputerEvilNetworks/Sockets/src/Server/", dir).exists();
		return check;
	}
	
	private void doCommand(String command, String dir) throws IOException {
		Command commandObject = new Command(command, dir);
	}
}
