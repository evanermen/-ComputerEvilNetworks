package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Command {

	public Command(String command, String dir) throws IOException {
		if(command.equals("HEAD")) {
			doHeadCommand(dir);
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






	

	}
