package Client;

import java.util.ArrayList;
import java.util.Arrays;

public class ParseCommand {

	public void parse(ArrayList<String> userInput) {
		Command command = new Command(userInput.get(0));
		parseUrl(userInput.get(1));
		parseVersion(userInput.get(2));
	}

	private void parseUrl(String string) {

		String protocol = null;
		String webpage = null;
		ArrayList<String> resource = new ArrayList<String>();

		String[] tokens = string.split(":", 2); // http: en de rest
		System.out.println(tokens[0]+ "\n" + tokens[1]);

		System.out.println("  --------------         ");
		String[] tokens2 = tokens[1].split("/");
		System.out.println(tokens2[2]+ "\n" + tokens2[3]);
		ArrayList<String> inputStrings = new ArrayList<String>(Arrays.asList(tokens2));

		protocol = tokens[0];
		webpage = tokens2[0];
		resource.addAll(1, inputStrings); // PROBLEEM

	}

	private void parseVersion(String string){
		ArrayList<String> versions = null;
		String version = null;
	
		versions.add("HTTP 1.0");
		versions.add("HTTP 1.1");

		for(String i: versions) {
			if(i.equals(string)){
				version = string;
			}
		}
		if(version == null) {
			System.err.println(
					"Expected <HTTP-version>: {HTTP 1.0, HTTP 1.1}");
			System.exit(1);
		}
	}
}

