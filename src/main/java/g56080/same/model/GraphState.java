package g56080.same.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * State graph used by the game model. 
 */
public class GraphState{

    /**
     * States in which the graph can be found.
     */
    public enum State{

        /**
         * The game hasn't been started.
         */
        NOT_STARTED,

        /**
         * The game has been initialized.
         */
        GAME_STARTED,

        /**
         * The player perform an action.
         */
        PLAYER_TURN,

        /**
         * The model update its data accordingly after the user
         * action.
         */
        GAME_TURN,

        /**
         * The model checks if the game is exhausted or not.
         */
        GAME_CHECK,

        /**
         * The game is over.
         */
        GAME_OVER,

        /**
         * The last click performed by the user is wrong.
         */
        ERROR_CLICK,

        /**
         * The last executed command is wrong.
         */
        ERROR_CMD,

        /**
         * The last executed command didn't found its required click record.
         */
        ERROR_NO_CLICK,

        /**
         * This graph consumed a wrong event.
         */
        ERROR;
    }

    /**
     * Events the graph can consume to change its state.
     */
    public enum Event{

        /**
         * Initializes the game board.
         */
        GAME_INIT,

        /**
         * Shuffles the game board.
         */
        SHUFFLE_BOARD,

        /**
         * The user has performed a valid click on the game board.
         */
        USER_CLICK,

        /**
         * The user has performed a valid command.
         */
        USER_CMD,

        /**
         * The user has performed a wrong click on the game board.
         */
        USER_WRONG_CLICK,

        /**
         * The user has performed a wrong command.
         */
        USER_WRONG_CMD,

        /**
         * The user has performed a command that required a click record but no
         * click has been recorded.
         */
        USER_NO_CLICK,

        /**
         * The model has processed an error.
         */
        ERROR_PROCESSED,

        /**
         * The game board is updated.
         */
        UPDATE_GRID,

        /**
         * The game board has been updated successfully.
         */
        UPDATE_SUCCESS,

        /**
         * The game board is exhausted after having been updated.
         */
        UPDATE_FAILURE,

        /**
         * Replays a new game.
         */
        REPLAY_GAME,

        /**
         * Stops the current game.
         */
        STOP_GAME;
    }
    
    private State currentState;
    private final Map<State, Map<Event, State>> graph;

    /**
     * Constructs a new graph state. The newly created graph will be initialized with
     * the {@link g56080.same.model.GraphState.State#NOT_STARTED} state.
     */
    public GraphState(){
        currentState = State.NOT_STARTED;
        graph = new EnumMap<>(State.class);
        initGraph();
    }

    /**
     * Gets the current state of this graph.
     *
     * @return the current state of this graph.
     */
    public State getState(){
        return currentState;
    }

    /**
     * Checks if the given event can be consumed.
     *
     * @param ev the event to check for consumption
     *
     * @return true if the given event can be consumed, false otherwise.
     */
    public boolean canConsume(Event ev){
        return graph.get(currentState).get(ev) != null;
    }

    /**
     * Consumes the given event. If the given event cannot be consumed, this graph
     * will have a state of ERROR.
     *
     * @param ev the event to consume.
     */
    public void consume(Event ev){
        currentState = graph.get(currentState).get(ev);
        if(currentState == null)
            currentState = State.ERROR;
    }

    private final void initGraph(){
        graph.put(State.NOT_STARTED, new EnumMap<>(Map.of(Event.GAME_INIT, State.GAME_STARTED)));
        graph.put(State.GAME_STARTED, new EnumMap<>(Map.of(Event.SHUFFLE_BOARD, State.PLAYER_TURN)));
        graph.put(State.PLAYER_TURN, new EnumMap<>(Map.of(Event.USER_CLICK, State.GAME_TURN, 
                        Event.USER_CMD, State.GAME_TURN, 
                        Event.USER_WRONG_CLICK, State.ERROR_CLICK,
                        Event.USER_WRONG_CMD, State.ERROR_CMD,
                        Event.USER_NO_CLICK, State.ERROR_NO_CLICK,
                        Event.STOP_GAME, State.GAME_OVER)));
        graph.put(State.GAME_TURN, new EnumMap<>(Map.of(Event.UPDATE_GRID, State.GAME_CHECK)));
        graph.put(State.ERROR_CLICK, new EnumMap<>(Map.of(Event.ERROR_PROCESSED, State.PLAYER_TURN)));
        graph.put(State.ERROR_CMD, new EnumMap<>(Map.of(Event.ERROR_PROCESSED, State.PLAYER_TURN)));
        graph.put(State.ERROR_NO_CLICK, new EnumMap<>(Map.of(Event.ERROR_PROCESSED, State.PLAYER_TURN)));
        graph.put(State.GAME_CHECK, new EnumMap<>(Map.of(Event.UPDATE_SUCCESS, State.PLAYER_TURN, Event.UPDATE_FAILURE, State.GAME_OVER)));
        graph.put(State.GAME_OVER, new EnumMap<>(Map.of(Event.REPLAY_GAME, State.NOT_STARTED)));
        graph.put(State.ERROR, Map.of());
    }
}
