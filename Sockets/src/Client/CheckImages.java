package Client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class CheckImages {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public CheckImages(Socket socket, byte[] stream) throws IOException {
		ArrayList<String> imageUrls = checkForImages(new ByteArrayInputStream(stream));
		getImages(socket, imageUrls);

	}

	private ArrayList<String> checkForImages(InputStream stream) throws IOException { //assumption that the url after img= is a relative path
		ArrayList<String> imageUrls = new ArrayList<String>();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(stream));

		String inputLine; 

		while ((inputLine = in.readLine()) != null) { 
			if(inputLine.contains("<img src=")) {
				String[] tokens = inputLine.split("<img ");
				tokens = tokens[1].split("src=\"");
				inputLine = tokens[1];
				String url = inputLine.substring(inputLine.indexOf("\"") + 1);
				imageUrls.add(url);
			}
		}
		return imageUrls;
	}

	private void getImages(Socket socket, ArrayList<String> imageUrls) throws IOException {
		for(String url: imageUrls) {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println("GET /" + url + " " + "HTTP/1.0");
			out.println();

			InputStream stream = socket.getInputStream();
			byte[] streamContent = copyStream(stream);        //copies the stream to a byteArray so we can use that stream multiple times.

			ArrayList<String> info = getContentInfo(new ByteArrayInputStream(streamContent));
			String[] imageType = checkOnImage(info.get(0));
			if(imageType[0].equals("image")) {
				int length = Integer.parseInt(info.get(1));
				getImage(new ByteArrayInputStream(streamContent), length, imageType[1], url);
			}
		}
	}



	private String[] checkOnImage(String imageInfo) {
		String[] tokens = imageInfo.split("/");
		return tokens;
	}


	private ArrayList<String> getContentInfo(InputStream stream) throws IOException { // 0 = contentType, 1 = contentLength
		ArrayList<String> info = new ArrayList<String>();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(stream));

		String inputLine; 

		while ((inputLine = in.readLine()) != null) { 
			if(inputLine.contains("Content-Type:")) {
				String[] tokens =inputLine.split(": ");
				System.out.println("Content-Type: " + tokens[1]);
				info.add(tokens[1]);
			}
			if(inputLine.contains("Content-Length:")) {
				String[] tokens = inputLine.split(": ");
				System.out.println("Content-Length: " + tokens[1]);
				info.add(tokens[1]);
			}
		} 
		return info;
	}


	public void getImage(InputStream stream, int length, String kindOfImage, String name) throws IOException {
		byte[] data = new byte[10000];
		int count = stream.read(data);
		System.out.println(count);
		byte[] newArray = Arrays.copyOfRange(data, count-length, count);
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(newArray));
		File outputfile = new File("C:/Users/Milan/Pictures/" + name + "." + kindOfImage);
		ImageIO.write(img, kindOfImage, outputfile);

	}


	private byte[] copyStream(InputStream stream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int count;
		while ((count = stream.read(buffer)) > -1 ) {
			baos.write(buffer, 0, count);
		}
		baos.flush();

		return baos.toByteArray(); 
	}
}