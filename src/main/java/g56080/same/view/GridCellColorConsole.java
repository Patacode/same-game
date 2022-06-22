package g56080.same.view;

/**
 * Colors available for the terminal application usign the ANSI standard. 
 */
public enum GridCellColorConsole{

    /**
     * The red color.
     */
    RED(AnsiColor.RED),

    /**
     * The green color.
     */
    GREEN(AnsiColor.GREEN),

    /**
     * The blue color.
     */
    BLUE(AnsiColor.BLUE),

    /**
     * The yellow color.
     */
    YELLOW(AnsiColor.YELLOW),

    /**
     * The magenta color.
     */
    MAGENTA(AnsiColor.MAGENTA),

    /**
     * The white color.
     */
    WHITE(AnsiColor.WHITE);

    private final AnsiColor color;

    private GridCellColorConsole(AnsiColor color){
        this.color = color;
    }

    /**
     * Gets the AnsiColor associated with this enum literal.
     *
     * @return the ansi color associated with this enum literal.
     */
    public AnsiColor getColor(){
        return color;
    }
}

