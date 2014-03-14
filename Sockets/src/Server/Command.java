package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Command {

	public Command(String command, String dir) throws IOException {
		if(command.equals("HEAD")) {
			doHeadCommand(dir);
		}
		if(command.equals("GET")) {
			doGetCommand(dir);
		}
		if(command.equals("PUT")) {
			doPutCommand(dir, newFile);
		}
	}

	private void doHeadCommand(String dir) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Milan/git/-ComputerEvilNetworks/Sockets/src/Server/" + dir));
		String line;
		boolean print = false;
		while ((line = br.readLine()) != null) {
			if(print == true) {
				Server.print(line);
			}
			if(line.equals("<HEAD>")) {
				print = true;
			}
			if(line.equals("</HEAD>")) {
				print = false;
			}

		}
		br.close();
	}

	
	private void doGetCommand(String dir) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Milan/git/-ComputerEvilNetworks/Sockets/src/Server/" + dir));
		String line;
		while ((line = br.readLine()) != null) {			
			Server.print(line);
		}
		br.close();
	}
	
	private void doPutCommand(String dir, String newFile) {
		File temp = new File("C:/Users/Milan/git/-ComputerEvilNetworks/Sockets/src/Server/" + newFile);
		
		if(temp.exists())
		  temp.delete();
	}

	



	

	}
