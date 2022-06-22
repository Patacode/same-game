package g56080.same.model;

import g56080.same.view.GridCellColorConsole;
import g56080.same.view.GridCellColorFX;

/**
 * A game board cell identified by a color and a fill state.
 */
public class GridCell{
    
    /**
     * Available colors for a grid cell.
     */
    public enum GridCellColor{

        /**
         * The red color.
         */
        RED,

        /**
         * The green color.
         */
        GREEN,

        /**
         * The blue color.
         */
        BLUE,

        /**
         * The yellow color.
         */
        YELLOW,

        /**
         * The magenta color.
         */
        MAGENTA,

        /**
         * The white color.
         */
        WHITE;
    }

    private final GridCellColor color;
    private boolean isFilled;

    /**
     * Constructs a new grid cell using the given color. A newly created
     * grid cell is filled by default.
     *
     * @param color the color of the grid cell
     */
    public GridCell(GridCellColor color){
        this.color = color;
        isFilled = true;
    }

    /**
     * Constructs a copy of the given grid cell.
     *
     * @param cell the grid cell to copy
     */
    public GridCell(GridCell cell){
        color = cell.color;
        isFilled = cell.isFilled;
    }

    // for unit tests only
    GridCell(GridCellColor color, boolean isFilled){
        this.color = color;
        this.isFilled = isFilled;
    }

    /**
     * Gets the enumeration color of this grid cell.
     *
     * @return the enumeration color of this grid cell.
     */
    public GridCellColor getColor(){
        return color;
    }

    /**
     * Gets the terminal color associated to the color of this
     * grid cell. The terminal color use the ANSI standard.
     *
     * @return the terminal color of this grid cell.
     */
    public GridCellColorConsole getColorConsole(){
        return GridCellColorConsole.valueOf(color.toString());
    }

    /**
     * Gets the GUI color associated to the color of this
     * grid cell.
     *
     * @return the GUI color of this grid cell.
     */
    public GridCellColorFX getColorFX(){
        return GridCellColorFX.valueOf(color.toString());
    }

    /**
     * Checks if this grid cell is filled.
     *
     * @return true if this grid cell is filled, false otherwise.
     */
    public boolean isFilled(){
        return isFilled;
    }

    /**
     * Sets the fill state of this grid cell.
     *
     * @param isFilled the fill state to assign to this grid cell
     */
    public void setState(boolean isFilled){
        this.isFilled = isFilled;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof GridCell){
            GridCell cell = (GridCell) obj;
            return cell.isFilled == isFilled && cell.color == color;
        }

        return false;
    }

    @Override
    public String toString(){
        return isFilled ? color.toString() : " ";
    }
}
