package g56080.same.controller;

import g56080.same.model.Action;
import g56080.same.model.Model;
import g56080.same.model.Point;
import g56080.same.view.View;

/**
 * The application controller used as an intermediary between view and model.
 */
public abstract class Controller{

    protected Model model;
    protected View view;

    /**
     * Starts the application. 
     */
    public abstract void start();

    /**
     * Initializes the model using the given informations.
     *
     * @param level the game level
     * @param size the game board's additional size
     */
    public void init(int level, int size){
        model.init(level, size);
    }

    /**
     * Shuffles the model.
     */
    public void shuffle(){
        model.shuffle();
    }

    /**
     * Clicks on the model using the given point.
     *
     * @param p the click point
     */
    public void click(Point p){
        model.click(p);
    }

    /**
     * Processes the given action on the model.
     *
     * @param action the action to process
     */
    public void processAction(Action action){
        model.processAction(action);
    }

    /**
     * Notifies the model that an error has been proceseed.
     */
    public void errorProcessed(){
        model.errorProcessed();
    }

    /**
     * Updates the grid of the model.
     */
    public void updateGrid(){
        model.updateGrid();
    }

    /**
     * Checks the model to see it is exhausted for the current game.
     */
    public void isFinish(){
        model.isFinish();
    }

    /**
     * Notifies the model that the user want to replay a new game.
     */
    public void replay(){
        model.replay();
    }
}

