import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */


/* making a singleton class so commands can be stacked safely
 * to be executed each tick
*/
class Parser 
{
	//stores the commands to be executed on tick
	private List<String> commandList = new LinkedList<String>();
	
	TCPServer server = TCPServer.getInstance();
	
	ProcessCommand processCommand = new ProcessCommand();
	protected synchronized List<String> getCommands(){
		return commandList;
		
	}
	
	protected synchronized void executeCommands(){
		
        String word1;
        String word2;
        String word3;
        Command finalCommand;
		
		for (int i = 0; i < commandList.size(); i++){
			String startCommand = commandList.get(i);
			//get the thread ID then remove it from the command
			int commandLength = startCommand.length();
			
			//remove the client id from the command and store it
			String clientID = startCommand.substring(commandLength - 2, commandLength);
			System.out.println(clientID);
			String command = startCommand.substring(0,commandLength -2);
			System.out.println(command);
			int finalClientID = Integer.parseInt(clientID);
			
	        StringTokenizer tokenizer = new StringTokenizer(command);

	        if(tokenizer.hasMoreTokens())
	            word1 = tokenizer.nextToken();      // get first word
	        else
	            word1 = null;
	        if(tokenizer.hasMoreTokens())
	            word2 = tokenizer.nextToken();      // get second word
	        else
	            word2 = null;
	        if(tokenizer.hasMoreTokens())
	            word3 = tokenizer.nextToken();      // get second word
	        else
	            word3 = null;
	        
	        Thread[] threads = server.GetThreads();
	        Thread thread = threads[finalClientID];
	        System.out.println(threads);

	        // note: we just ignore the rest of the input line.

	        // Now check whether this word is known. If so, create a command
	        // with it. If not, create a "null" command (for unknown command).

	        
			if(commands.isCommand(word1))
				finalCommand = new Command(word1, word2, word3);
	        else
	        	finalCommand = new Command(null, word2, word3);
			//processCommand.processCommand(finalCommand, );
		}
	}
	
	protected synchronized void addToCommands(String command){
		commandList.add(command);
	}
	
	private static Parser parser = new Parser();

    private CommandWords commands;  // holds all valid command words

    private Parser() 
    {
        commands = new CommandWords();
    }
    
    public static Parser getInstance(){
    	return parser;
    }

    public String getCommandList(){
    	for(int i = 0; i < 10; i++){
    		System.out.println(commandList);
    	}
		return null;
    }
    

    /**
     * Print out a list of valid command words.
     */
    protected void showCommands()
    {
        commands.showAll();
    }
}
