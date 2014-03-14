package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
		if(command.equals("POST")) {
			doPostCommand(input, dir);
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
	
	private void doPutCommand(String dir, String newFile) throws IOException {
		Path from = new File("C:/Users/Milan/git/-ComputerEvilNetworks/Sockets/src/Server/" + newFile).toPath(); //convert from File to Path
		Path to = Paths.get(dir); //convert from String to Path
		Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
	}

	private void doPostCommand(String input, String dir) {
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:/Users/Milan/git/-ComputerEvilNetworks/Sockets/src/Server/" + dir, true)))) {
		    out.println(input);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}



	

	}
