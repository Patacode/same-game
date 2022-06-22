package g56080.same.command;

import java.util.Deque;

import g56080.same.dp.command.Command;
import g56080.same.model.Board;
import g56080.same.model.GameModel;
import g56080.same.model.GraphState.Event;
import g56080.same.model.Point;

/**
 * The click command.
 */
public class ClickCommand implements Command{
    
    private final GameModel model;
    private final Point p;

    /**
     * Constructs a new command to click on the game board using the given model and click point. 
     * The model is used to provide an interface on which to perform the click action.
     *
     * @param model the game model used to reflect the command action
     * @param p the click point
     */
    public ClickCommand(GameModel model, Point p){
        this.model = model;
        this.p = p;
    }

    @Override
    public void execute(){
        if(p == null){
            model.getGraphState().consume(Event.USER_NO_CLICK);
            model.notifyObservers();
        } else model.click(p);
    }

    @Override
    public void undo(){
        Deque<Board> history = model.getBoardHistory();
        if(!history.isEmpty()){
            model.changeBoard(history.pop());
            model.getGraphState().consume(Event.USER_CMD);
        } else model.getGraphState().consume(Event.USER_WRONG_CMD);

        model.notifyObservers();
    }
}
