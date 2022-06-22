package g56080.same.model;

import java.util.regex.Pattern;

/**
 * Actions the user can perform on the application console.
 */
public enum Action{

    /**
     * The undo action to undo the last click.
     */
    UNDO("undo", "Undo the last click"), 

    /**
     * The redo action to repeat the last click.
     */
    REDO("redo", "Repeat the last click"),

    /**
     * The click action to click on the cell at the given x,y position.
     */
    CLICK("click (?<x>\\d+) (?<y>\\d+)", "Click on the cell at the given x,y position"),

    /**
     * The help action to list all the available actions.
     */
    HELP("help", "List all the available actions"),

    /**
     * The stop action to stop the current game.
     */
    STOP("stop", "Stop the current game"),

    /**
     * The decorate action to decorate the game board.
     */
    DECORATE("decorate (?<name>\\p{Alpha}+)", "Change the board appareance"),

    /**
     * The shapes action ot list the available board shapes.
     */
    SHAPES("shapes", "List the available board shapes");

    private final String regex, description;

    private Action(String regex, String description){
        this.regex = regex;
        this.description = description;
    }

    /**
     * Gets the pattern associated to this action literal. The pattern defines
     * the structure of the command to which the user has to refer when using the
     * underlying command.
     *
     * @return the pattern associated to this action literal.
     */
    public Pattern getPattern(){
        return Pattern.compile("(?i)" + regex);
    }

    /**
     * Gets the description of this action literal.
     *
     * @return the description of this action literal.
     */
    public String getDescription(){
        return description;
    }
}

