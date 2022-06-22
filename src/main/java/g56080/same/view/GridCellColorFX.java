package g56080.same.view;

import javafx.scene.paint.Color;

/**
 * Colors available for the GUI.
 */
public enum GridCellColorFX{

    /**
     * The red color.
     */
    RED(Color.RED),

    /**
     * The green color.
     */
    GREEN(Color.LIME),

    /**
     * The blue color.
     */
    BLUE(Color.CYAN),

    /**
     * The yellow color.
     */
    YELLOW(Color.YELLOW),

    /**
     * The magenta color.
     */
    MAGENTA(Color.MAGENTA),

    /**
     * The white color
     */
    WHITE(Color.WHITE);

    private final Color color;

    private GridCellColorFX(Color color){
        this.color = color;
    }

    /**
     * Gets the JavaFX color associated to this enum literal.
     *
     * @return the JavaFX color associated to this enum literal.
     */
    public Color getColor(){
        return color;
    }
}

