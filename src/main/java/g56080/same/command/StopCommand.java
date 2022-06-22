package g56080.same.command;

import g56080.same.dp.command.Command;
import g56080.same.view.ConsoleView;

/**
 * The stop command.
 */
public class StopCommand implements Command{
    
    private final ConsoleView view;

    /**
     * Constructs a new command to change the exhaustion state of the view.
     * 
     * @param view the console view used to perform the action
     */
    public StopCommand(ConsoleView view){
        this.view = view;
    }

    @Override
    public void execute(){
        view.setExhaustion(true);
        view.displayEnd();
    }

    @Override
    public void undo(){
        throw new UnsupportedOperationException("Invalid operation");
    }
}
