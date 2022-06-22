package g56080.same.command;

import g56080.same.dp.command.Command;
import g56080.same.view.ConsoleView;

/**
 * The shapes command.
 */
public class ShapesCommand implements Command{
    
    private final ConsoleView view;

    /**
     * Constructs a new command to display all the available board shapes using
     * the given view.
     *
     * @param view the console view used to perform the action
     */
    public ShapesCommand(ConsoleView view){
        this.view = view;
    }

    @Override
    public void execute(){
        view.displayBoardShapes();
    }

    @Override
    public void undo(){
        throw new UnsupportedOperationException("Invalid operation");
    }
}

