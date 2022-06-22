package g56080.same.command;

import g56080.same.dp.command.Command;
import g56080.same.view.ConsoleView;

/**
 * The decorate command.
 */
public class DecorateCommand implements Command{
    
    private final ConsoleView view;
    private final String shape;

    /**
     * Constructs a new command to decorate the game board using the given view and shape.
     * The view is used to provide an interface on which to perform the decorate action.
     *
     * @param view the console view used to apply the decoration
     * @param shape the board shape
     */
    public DecorateCommand(ConsoleView view, String shape){
        this.view = view;
        this.shape = shape;
    }

    @Override
    public void execute(){
        if(!view.isValidBoardShape(shape)){
            System.out.println("Invalid board shape. Please retry !");
        } else{
            System.out.println("Board shape successfully changed !");
            view.setBoardShape(shape);
            view.displayBoard(shape);
        }
    }

    @Override
    public void undo(){
        throw new UnsupportedOperationException("Invalid operation");
    }
}
