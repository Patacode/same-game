package g56080.same;

import g56080.same.model.GridCell.GridCellColor;

/**
 * The application configurations.
 */
public enum Config{
    
    /**
     * The default terminal symbol used by the game board to display its cell.
     */
    DEFAULT_GRID_SYMBOL('x'),

    /**
     * The default height for the game board (its number of rows).
     */
    DEFAULT_GRID_HEIGHT(12),

    /**
     * The default width for the game board (its number of columns).
     */
    DEFAULT_GRID_WIDTH(16),

    /**
     * The default number of colors used by the game board for the lowest level (level 1).
     */
    DEFAULT_NUMBER_COLORS(3),

    /**
     * The default width used by the application scene for the GUI (in pixel).
     */
    DEFAULT_SCENE_WIDTH(1200),

    /**
     * The default height used by the application scene for the GUI (in pixel).
     */
    DEFAULT_SCENE_HEIGHT(800),

    /**
     * The width of each board game's cell for the GUI (in pixel).
     */
    GRIDCELL_WIDTH(30),

    /**
     * The height of each board game's cell for the GUI (in pixel).
     */
    GRIDCELL_HEIGHT(30),

    /**
     * The default width used by the game board for the GUI (in pixel). Equivalent to the 
     * default number of columns times the width of a grid cell.
     */
    DEFAULT_FXGRID_WIDTH(Config.DEFAULT_GRID_WIDTH.getValue() * GRIDCELL_WIDTH.getValue()),

    /**
     * The default height used by the game board for the GUI (in pixel). Equivalent to the 
     * default number of rows times the height of a grid cell.
     */
    DEFAULT_FXGRID_HEIGHT(Config.DEFAULT_GRID_HEIGHT.getValue() * GRIDCELL_HEIGHT.getValue()),

    /**
     * The minimum allowed level for the game.
     */
    MIN_LEVEL(1),

    /**
     * The maximum allowed level for the game. Deducted from the number of available colors 
     * (defined in the {@link g56080.same.model.GridCell.GridCellColor} enumeration) minus the
     * default number of colors + 1.
     */
    MAX_LEVEL(GridCellColor.values().length - Config.DEFAULT_NUMBER_COLORS.getValue() + 1),

    /**
     * The minimum size of a clickable cell's zone.
     */
    ZONE_MIN_SIZE(2),

    /**
     * The minimum additional size that can be substracted from the default game board dimensions.
     */
    MIN_ADSIZE(10),

    /**
     * The maximum additional size that can be added to the default game board dimensions.
     */
    MAX_ADSIZE(20),

    /**
     * The minimum height for the game board (its minimum number of rows).
     */
    GRID_MIN_HEIGHT(Config.DEFAULT_GRID_HEIGHT.getValue() - Config.MIN_ADSIZE.getValue()),

    /**
     * The maximum height for the game board (its maximum number of rows).
     */
    GRID_MAX_HEIGHT(Config.DEFAULT_GRID_HEIGHT.getValue() + Config.MAX_ADSIZE.getValue()),

    /**
     * The minimum width for the game board (its minimum number of columns).
     */
    GRID_MIN_WIDTH(Config.DEFAULT_GRID_WIDTH.getValue() - Config.MIN_ADSIZE.getValue()),

    /**
     * The minimum width for the game board (its minimum number of columns).
     */
    GRID_MAX_WIDTH(Config.DEFAULT_GRID_WIDTH.getValue() + Config.MAX_ADSIZE.getValue()),

    /**
     * The maximum number of times the user can undo its click on the game board.
     */
    MAX_UNDO(1);

    private final int value;

    private Config(int value){
        this.value = value;
    }

    /**
     * Gets the integer value associated to this configuation literal.
     *
     * @return the integer value associated to this enum literal.
     */
    public int getValue(){
        return value;
    }
}
