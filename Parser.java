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
	
	protected synchronized List<String> getCommands(){
		return commandList;
		
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
    
    protected Command getCommand() 
    {
        String inputLine = "";   // will hold the full input line
        String word1;
        String word2;
        String word3;

        System.out.print("> ");     // print prompt

        BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));
        try {
            inputLine = reader.readLine();
        }
        catch(java.io.IOException exc) {
            System.out.println ("There was an error during reading: "
                                + exc.getMessage());
        }

        StringTokenizer tokenizer = new StringTokenizer(inputLine);

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

        // note: we just ignore the rest of the input line.

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).

        if(commands.isCommand(word1))
            return new Command(word1, word2, word3);
        else
            return new Command(null, word2, word3);
    }

    /**
     * Print out a list of valid command words.
     */
    protected void showCommands()
    {
        commands.showAll();
    }
}
