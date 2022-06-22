package g56080.same.model;

import g56080.same.model.GridCell.GridCellColor;
import org.junit.Test;
import static org.junit.Assert.*;

public class GridCellTest{
    
    @Test
    public void gridCellConstructorTest(){
        GridCell cell = new GridCell(GridCellColor.RED);
        assertTrue(cell.isFilled());
        assertEquals(GridCellColor.RED, cell.getColor());
    }

    @Test
    public void gridCellFilledTest(){
        GridCell cell = new GridCell(GridCellColor.RED);
        cell.setState(false); // unfilled
        assertTrue(!cell.isFilled());
    }

    @Test
    public void gridCellComparisonTest_differentColor(){
        GridCell redCell = new GridCell(GridCellColor.RED);
        GridCell greenCell = new GridCell(GridCellColor.GREEN);

        assertNotEquals(redCell, greenCell);
    }

    @Test
    public void gridCellComparisonTest_sameColor_differentState(){
        GridCell filledCell = new GridCell(GridCellColor.RED);
        GridCell unfilledCell = new GridCell(GridCellColor.RED, false);
        
        assertNotEquals(filledCell, unfilledCell);
    }

    @Test
    public void gridCellComparisonTest_sameColor_sameState(){
        GridCell firstCell = new GridCell(GridCellColor.RED);
        GridCell secondCell = new GridCell(GridCellColor.RED);

        assertEquals(firstCell, secondCell);
    }
}
