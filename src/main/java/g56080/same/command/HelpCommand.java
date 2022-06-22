package g56080.same.command;

import g56080.same.dp.command.Command;
import g56080.same.view.ConsoleView;

/**
 * The help command. 
 */
public class HelpCommand implements Command{
    
    private final ConsoleView view;

    /**
     * Constructs a new command to display all the available commands with their
     * respective description using the given view.
     *
     * @param view the console view used to perform the action
     */
    public HelpCommand(ConsoleView view){
        this.view = view;
    }

    @Override
    public void execute(){
        view.displayActions();
    }

    @Override
    public void undo(){
        throw new UnsupportedOperationException("Invalid operation");
    }
}

