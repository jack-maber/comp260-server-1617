import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javax.swing.JTextArea;

public class TCPClientWorker implements Runnable {

	private Socket client;
	private JTextArea textArea;

	// Constructor
	TCPClientWorker(Socket client, JTextArea textArea) {
		this.client = client;
		this.textArea = textArea;
	}

	public void run() {

		BufferedReader in = null;
		PrintWriter out = null;
		String line = in.readLine();
		
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}

		while (true) {
			try {
				// Send data back to client
				out.println(line);
				// Append data to text area
				textArea.append(line);
			} catch (IOException e) {
				System.out.println("Read failed");
			
					System.exit(-1);
			}
		}
	public synchronized void appendText(line){
	    textArea.append(line);
	}
	

	  }

	protected void finalize() {
		// Objects created in run method are finalized when
		// program terminates and thread exits
		try {
			server.close();
		} catch (IOException e) {
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

}
