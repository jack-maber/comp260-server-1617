
public class ProcessCommand {

	private Parser parser = Parser.getInstance();

	public boolean processCommand(Command command, Character character) {
		boolean wantToQuit = false;
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help"))
			printHelp();
		else if (commandWord.equals("go"))
			goRoom(command);
		else if (commandWord.equals("quit")) {
			wantToQuit = quit(command);
		}
		else if (commandWord.equals("move")) {
			character.moveCharacter(command.getSecondWord());
		}
		return wantToQuit;
	}

	public void printHelp() {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at the university.");
		System.out.println();
		System.out.println("Your command words are:");
		parser.showCommands();
	}

	private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
       // Room nextRoom = currentRoom.getExit(direction);

       // if (nextRoom == null)
            System.out.println("There is no door!");
       // else {
         //   currentRoom = nextRoom;
        //    System.out.println(currentRoom.getLongDescription());
    }
	
	/**
	 * "Quit" was entered. Check the rest of the command to see whether we
	 * really quit the game. Return true, if this command quits the game, false
	 * otherwise.
	 */
	private boolean quit(Command command) {
		if (command.hasSecondWord()) {
			System.out.println("Quit what?");
			return false;
		} else
			return true; // signal that we want to quit
	}
}
