import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.net.ServerSocket;

//Source From http://makemobiapps.blogspot.co.uk/p/multiple-client-server-chat-programming.html

/*
 * A chat server that delivers public and private messages.
 */
public class TCPServer {
	
	private static Timer timer = Timer.getInstance();
	StringBuilder AllActions = new StringBuilder();
	private static TCPServer tCPServer = new TCPServer();

	// The server socket.
	private static ServerSocket serverSocket = null;
	// The client socket.
	private static Socket clientSocket = null;

	// This chat server can accept up to maxClientsCount clients' connections.
	private static final int maxClientsCount = 40;
	private static final clientThread[] threads = new clientThread[maxClientsCount];

	//Our Code////////////////////////////////////////////////////////////
	public clientThread[] GetThreads() {
		return threads;
	}
	
	public static TCPServer getInstance(){
		return tCPServer;
	}
	
	private TCPServer(){}
	
	//Our code ends////////////////////////////////////////////////////////
	public static void main(String args[]) {

		// The default port number.
		int portNumber = 2222;
		if (args.length < 1) {
			System.out.println(
					"Server running on port number: " + portNumber);
		} else {
			portNumber = Integer.valueOf(args[0]).intValue();
		}

		/*
		 * Open a server socket on the portNumber (default 2222). Note that we
		 * can not choose a port less than 1023 if we are not privileged users
		 * (root).
		 */
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e);
		}

		/*
		 * Create a client socket for each connection and pass it to a new
		 * client thread.
		 */
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < maxClientsCount; i++) 
				{
					if (threads[i] == null) 
					{
						// Create new client thread
						(threads[i] = new clientThread(clientSocket, threads, i)).start();
						break;
					}
				}
				if (i == maxClientsCount) {
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					os.println("Server too busy. Try later.");
					os.close();
					clientSocket.close();
					timer.getTickThread().interrupt();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
class clientThread extends Thread {

	private String clientName = null;
	private DataInputStream inStream = null;
	private PrintStream outStream = null;
	private Socket clientSocket = null;
	private String ClientAction = null;
	private final clientThread[] threads;
	//Our Code////////////////////////////////////////////////////////////

	private int maxClientsCount;
	private Character character = new Character(3, 3);
	private Map map = Map.getInstance();
	private final int ID;

	private Parser parser = Parser.getInstance();
	

	public clientThread(Socket clientSocket, clientThread[] threads, int ID) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		this.ID = ID;
		maxClientsCount = threads.length;
	}

	public int getID(){
		return ID;
	}
	
	
	@SuppressWarnings("deprecation")
	public void run() {
		int maxClientsCount = this.maxClientsCount;
		
		clientThread[] threads = this.threads;
		
		
		
		
		try {
			/*
			 * Create input and output streams for this client.
			 */
			inStream = new DataInputStream(clientSocket.getInputStream());
			outStream = new PrintStream(clientSocket.getOutputStream());
			String name = inStream.readLine();
			
			//TODO: send the client their id for communication
			

			/* Welcome the new the client. */
			outStream.println("Welcome " + name + " Client ID: " + GetClientThread());
			
			
			
			synchronized (this) 
			{
				for (int i = 0; i < maxClientsCount; i++) 
				{
					if (threads[i] != null && threads[i] == this) 
					{
						System.out.println("Player " + name + " Joined." );
						clientName = name;
						break;
					}
				}
				for (int i = 0; i < maxClientsCount; i++) 
				{
					if (threads[i] != null && threads[i] != this) 
					{
						threads[i].outStream.println("<" + name + "> X:" +  character.getX() + " Y:" +  character.getY());
					}
				}
			}
			
			
			
			/* Process their command */
			while (true) 
			{
				String line = inStream.readLine().trim();
				String Action = null;
				boolean AlreadyInList = false;
				
				
				// Move the character
				character.moveCharacter(line);
				
				
				
				// Create a list of player names and locations
				
				// Check whether action has already been added
				
				
					
				//AllActions.append(ClientAction);
				
				// Sends number of players ins session
				if(line.equals("NUMBER_OF_PLAYERS_REQUEST"))
				{
					int numberOfCurrentPlayers = 0;
					for (int j = 0; j < maxClientsCount; j++) 
					{
						if (threads[j] != null && threads[j].clientName != null) 
						{
							numberOfCurrentPlayers++;
						}
					}
					outStream.println("[#:" + 0 + numberOfCurrentPlayers +"].");
					outStream.flush();
				}
				
				
				
				if(line.equals("PLACE_BED") || line.equals("PLACE_BOX"))
				{
					Action = line;
					map.setCell(character.getX(), character.getY(), line);
				}
				
					
					// Send player positions to all the clients
				synchronized (this) 
				{
				for (int i = 0; i < maxClientsCount; i++) 
				{
					if (threads[i] != null && threads[i].clientName != null) 
					{

						//parser.addToCommands("<" + name + "> X:" +  character.getX() + " Y:" +  character.getY() + " ACT:" + Action + ".|");
						
						ClientAction = ("{<" + threads[i].clientName + "> X:" +  threads[i].character.getX() + " Y:" +  threads[i].character.getY() + " ACT:" + Action + "." + "}");
						TCPServer.getInstance().AllActions.append(ClientAction);
						// Character Locations and actions
					
						
						outStream.println(TCPServer.getInstance().AllActions.toString());
						outStream.flush();
						
						//Clear All actions
						
						TCPServer.getInstance().AllActions.delete(0, TCPServer.getInstance().AllActions.length());
						}


					}
					}
				

				// Disconnect the client if they send the quit message
				if(line.equals("QUIT"))
				{
					synchronized (this) 
					{
						for (int i = 0; i < maxClientsCount; i++) 
						{
							if (threads[i] != null && threads[i] != this && threads[i].clientName != null) 
							{
								threads[i].outStream.println("*** The user " + name + " is leaving the game !!! ***");
							}
						}
					}
				System.out.println("Player " + name + " Leaving");
				//numberOfCurrentPlayers--;
	
				
				synchronized (this) 
				{
					for (int i = 0; i < maxClientsCount; i++) {
						if (threads[i] == this) {
							threads[i] = null;
						}
					}
				}
				/*
				 * Close the output stream, close the input stream, close the
				 * socket.
				 */
				inStream.close();
				outStream.close();
				clientSocket.close();
				}
			}
			} catch (IOException e) 
		{
		}

	}

	// Function for getting the client ID for each client thread
	public long GetClientThread() {

		return Thread.currentThread().getId();
	}

	public Character GetCharacter() {
		return character;
	}
	//Our Code Ends////////////////////////////////////////////////////////

}
