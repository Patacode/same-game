package g56080.same.view;

import javafx.scene.paint.Color;

/**
 * Themes available for the application running in a GUI.
 */
public enum Theme{
    
    /**
     * The default theme.
     */
    DEFAULT(
        new Color(0x0D / 255., 0x15 / 255., 0x18 / 255., 1.), 
        new Color(0x08 / 255., 0x0B / 255., 0x0C / 255., 1.)
    ),

    /**
     * The forest theme (green based).
     */
    FOREST(
        new Color(0x26 / 255., 0x34 / 255., 0x09 / 255., 1.),
        new Color(0x54 / 255., 0x67 / 255., 0x2D / 255., 1.)
    ),

    /**
     * The see theme (blue based).
     */
    SEE(
        new Color(0x09 / 255., 0X10 / 255., 0X34 / 255., 1.),
        new Color(0x0A / 255., 0X22 / 255., 0X99 / 255., 1.)
    ),

    /**
     * The ruby theme (red based).
     */
    RUBY(
        new Color(0x34 / 255., 0X09 / 255., 0X0C / 255., 1.),
        new Color(0x95 / 255., 0X0E / 255., 0X17 / 255., 1.)
    );


    private final Color primary, secondary;

    private Theme(Color primary, Color secondary){
        this.primary = primary;
        this.secondary = secondary;
    }

    /**
     * Gets the primary color of this theme literal.
     *
     * @return the primary color of this theme literal.
     */
    public Color getPrimaryColor(){
        return primary;
    }

    /**
     * Gets the secondary color of this theme literal.
     *
     * @return the secondary color of this theme literal.
     */
    public Color getSecondaryColor(){
        return secondary;
    }
}

