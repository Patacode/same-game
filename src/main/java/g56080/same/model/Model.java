package g56080.same.model;

import g56080.same.dp.observer.Observable;
import g56080.same.model.GraphState.State;

/**
 * Observable model interface implemented by object dealing with the game logic. In any
 * case, when invoking a method from this interface that change its state, an 
 * IllegalStateException will be thrown if its current state doesn't allow the
 * underlying method to be invoked.
 */
public interface Model extends Observable{

    /**
     * Initializes the model using the given level (for the number of different colors) and
     * additional size (for the game board). The model change its state from 
     * {@link g56080.same.model.GraphState.State#NOT_STARTED} to
     * {@link g56080.same.model.GraphState.State#GAME_STARTED}.
     *
     * @param level the difficulty level
     * @param adSize the additional board size
     */
    void init(int level, int adSize);

    /**
     * Shuffles the cells of the model's game board. The model chaneg its state from
     * {@link g56080.same.model.GraphState.State#GAME_STARTED} to
     * {@link g56080.same.model.GraphState.State#PLAYER_TURN}.
     */
    void shuffle();

    /**
     * Clicks on the model's game board at the given position.  The model change its
     * state from {@link g56080.same.model.GraphState.State#PLAYER_TURN} to
     * {@link g56080.same.model.GraphState.State#GAME_TURN} if no error occured during 
     * this phase. If any, the state will become  {@link g56080.same.model.GraphState.State#ERROR_CLICK} 
     * to notify a wrong click.
     *
     * @param p the click point
     *
     * @return true if the click is valid, false otherwise.
     */
    boolean click(Point p);

    /**
     * Processes the given action. The model change its
     * state from {@link g56080.same.model.GraphState.State#PLAYER_TURN} to
     * {@link g56080.same.model.GraphState.State#GAME_TURN} if no error occured during 
     * this phase. If any, depending on the context, 
     * the state will either become {@link g56080.same.model.GraphState.State#ERROR_CMD} 
     * to notify that an error occured when the given action has been processed, or 
     * {@link g56080.same.model.GraphState.State#ERROR_NO_CLICK} to notify that the state
     * of the model disallow certain action to be processed (especially those requiring 
     * a click record).
     *
     * @param action the action to process
     */
    void processAction(Action action);

    /**
     * Notifies that an error has been processed. The mode change its state from
     * an error state ({@link g56080.same.model.GraphState.State#ERROR_CLICK} or
     * {@link g56080.same.model.GraphState.State#ERROR_CMD} or 
     * {@link g56080.same.model.GraphState.State#ERROR_NO_CLICK}) to 
     * {@link g56080.same.model.GraphState.State#PLAYER_TURN}.
     */
    void errorProcessed();

    /**
     * Updates the model's game board. The model change its state from
     * {@link g56080.same.model.GraphState.State#GAME_TURN} to 
     * {@link g56080.same.model.GraphState.State#GAME_CHECK}.
     */
    void updateGrid();

    /**
     * Checks if the model's game board is exhausted. The model change its
     * state from {@link g56080.same.model.GraphState.State#GAME_CHECK}
     * to {@link g56080.same.model.GraphState.State#PLAYER_TURN} if this
     * model isn't exhausted, or to {@link g56080.same.model.GraphState.State#GAME_OVER} if
     * it does.
     *
     * @return true if the game board is exhausted, false otherwise.
     */
    boolean isFinish();

    /**
     * Notifies this model to replay a new game. The model change its state from
     * {@link g56080.same.model.GraphState.State#GAME_OVER} to 
     * {@link g56080.same.model.GraphState.State#NOT_STARTED}.
     */
    void replay();







    // -------------------INFORMATIONS-----------------

    /**
     * Gets the total score of this model.
     *
     * @return the integer total score.
     */
    int getScore();

    /**
     * Gets the current click score of this model.
     *
     * @return the integer current click score.
     */
    int getCurrentScore();

    /**
     * Gets the highscore of this model. The highscore is
     * updated each time a game takes end if the total score
     * is higher than the last game.
     *
     * @return the integer highscore.
     */
    int getHighScore();

    /**
     * Gets the level of this model.
     *
     * @return the integer level.
     */
    int getLevel();

    /**
     * Gets a copy the model's game board.
     *
     * @return a copy of the model's game board.
     */
    Board getBoard();

    /**
     * Gets the current state of this model.
     *
     * @return the current state.
     */
    State getState();

    /**
     * Checks if the model's game board has been successfully
     * completed. It happens only if at the end of the game, the
     * game board is empty.
     *
     * @return true if the current game is successful, false otherwise.
     */
    boolean isSuccess();
}
