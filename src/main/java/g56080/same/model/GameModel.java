package g56080.same.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import g56080.same.Config;
import g56080.same.dp.command.Command;
import g56080.same.dp.observer.*;
import g56080.same.model.GraphState.Event;
import g56080.same.model.GraphState.State;
import g56080.same.command.ClickCommand;

/**
 * The classical implementation of the model interface used by the application
 * to play the game.
 */
public class GameModel implements Model{

    private final GraphState graph;
    private final Set<Observer> observers;
    private Board board;
    private Deque<Board> boardHistory;
    private Point lastClickedPoint;
    private int highScore;

    /**
     * Constructs a new model.
     */
    public GameModel(){
        graph = new GraphState();
        observers = new HashSet<>();
        boardHistory = new ArrayDeque<>();
    }

    @Override
    public void addObserver(Observer obs){
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs){
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(){
        observers.forEach(obs -> obs.update());
    }

    @Override
    public void init(int level, int size){
        if(!graph.canConsume(Event.GAME_INIT))
            throw new IllegalStateException("Invalid state");
        
        board = new Board(level, size);
        boardHistory.clear();
        lastClickedPoint = null;
        graph.consume(Event.GAME_INIT);
        notifyObservers();
    }

    // for unit tests only
    void init(GridCell[][] grid){
        if(!graph.canConsume(Event.GAME_INIT))
            throw new IllegalStateException("Invalid state");
        
        board = new Board(grid);
        boardHistory.clear();
        lastClickedPoint = null;
        graph.consume(Event.GAME_INIT);
        notifyObservers();
    }

    @Override
    public void shuffle(){
        if(!graph.canConsume(Event.SHUFFLE_BOARD))
            throw new IllegalStateException("Invalid state");

        board.shuffle();
        graph.consume(Event.SHUFFLE_BOARD);
        notifyObservers();
    }

    // for unit tests only
    void fakeShuffle(){
        if(!graph.canConsume(Event.SHUFFLE_BOARD))
            throw new IllegalStateException("Invalid state");

        graph.consume(Event.SHUFFLE_BOARD);
        notifyObservers();
    }

    @Override
    public boolean click(Point p){
        if(!graph.canConsume(Event.USER_CLICK) || !graph.canConsume(Event.USER_WRONG_CLICK))
            throw new IllegalStateException("Invalid state");

        lastClickedPoint = p;
        if(board.canClick(p)){
            boardHistory.push(new Board(board));
            if(Config.MAX_UNDO.getValue() > -1 && boardHistory.size() > Config.MAX_UNDO.getValue())
                boardHistory.removeLast();
            board.click(p);
            graph.consume(Event.USER_CLICK);
            notifyObservers();
            return true;
        }

        graph.consume(Event.USER_WRONG_CLICK);
        notifyObservers();

        return false;
    }

    @Override
    public void processAction(Action action){
        if(!graph.canConsume(Event.USER_CMD) 
                || !graph.canConsume(Event.USER_NO_CLICK) 
                || !graph.canConsume(Event.USER_WRONG_CMD) 
                || !graph.canConsume(Event.STOP_GAME))
            throw new IllegalStateException("Invalid state");

        Command command = new ClickCommand(this, lastClickedPoint);
        switch(action){
            case STOP:
                graph.consume(Event.STOP_GAME);
                notifyObservers();
                break;
            case REDO:
                command.execute();
                break;
            case UNDO: command.undo();
        }
    }

    @Override
    public void errorProcessed(){
        if(!graph.canConsume(Event.ERROR_PROCESSED))
            throw new IllegalStateException("Invalid state");

        graph.consume(Event.ERROR_PROCESSED);
        notifyObservers();
    }

    @Override
    public void updateGrid(){
        if(!graph.canConsume(Event.UPDATE_GRID))
            throw new IllegalStateException("Invalid state");

        board.update();
        graph.consume(Event.UPDATE_GRID);
        notifyObservers();
    }

    @Override
    public boolean isFinish(){
        if(!graph.canConsume(Event.UPDATE_FAILURE) || !graph.canConsume(Event.UPDATE_SUCCESS))
            throw new IllegalStateException("Invalid state");

        if(board.isExhausted()){
            graph.consume(Event.UPDATE_FAILURE);
            if(board.getScore() > highScore)
                highScore = board.getScore();
            notifyObservers();
            return true;
        }

        graph.consume(Event.UPDATE_SUCCESS);
        notifyObservers();
        return false;
    }

    @Override
    public void replay(){
        if(!graph.canConsume(Event.REPLAY_GAME))
            throw new IllegalStateException("Invalid state");

        graph.consume(Event.REPLAY_GAME);
        notifyObservers();
    }

    @Override
    public boolean isSuccess(){
        return board.isEmpty();
    }

    @Override
    public int getScore(){
        return board.getScore();
    }

    @Override
    public int getCurrentScore(){
        return board.getCurrentClickScore();
    }

    @Override
    public int getHighScore(){
        return highScore;
    }

    @Override
    public int getLevel(){
        return board.getLevel();
    }

    @Override
    public Board getBoard(){
        return new Board(board);
    }

    @Override
    public State getState(){
        return graph.getState();
    }

    /**
     * Gets the graph state of this model. The graph 
     * state is the object dedicated to control the state
     * of this model.
     *
     * @return the graph state of this model.
     */
    public GraphState getGraphState(){
        return graph;
    }

    /**
     * Gets the board history of this model. Each time
     * a successful click occurs on this model's game board, 
     * before updating it, a backup of it is recorded into a
     * LIFO stack.
     *
     * @return the board history of this model.
     */
    public Deque<Board> getBoardHistory(){
        return boardHistory;
    }

    /**
     * Sets the model's game board to the given board.
     *
     * @param board the new board to affect to this model
     */
    public void changeBoard(Board board){
        this.board = board;
    }
}

