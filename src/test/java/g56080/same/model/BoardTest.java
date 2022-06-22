package g56080.same.model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import g56080.same.Config;
import g56080.same.model.GridCell.GridCellColor;
import g56080.same.util.Util;

public class BoardTest{
    
    private Board board;

    @Before
    public void initBoard(){
        board = new Board(Config.MIN_LEVEL.getValue(), 0);
    }

    @Test
    public void boardConstructorTest_validParameters(){
        for(int i = Config.MIN_LEVEL.getValue(); i <= Config.MAX_LEVEL.getValue(); i++){
            for(int j = -Config.MIN_ADSIZE.getValue(); j <= Config.MAX_ADSIZE.getValue(); j++){
                boardCompositionTest(i, j);
            }
        }
    }

    @Test
    public void boardConstructorTest_invalidParameters(){
        assertThrows(IllegalArgumentException.class, () -> new Board(Config.MIN_LEVEL.getValue() - 1, 0)); // invalid level
        assertThrows(IllegalArgumentException.class, () -> new Board(Config.MAX_LEVEL.getValue() + 1, 0)); // invalid level
        assertThrows(IllegalArgumentException.class, () -> new Board(1, -Config.MIN_ADSIZE.getValue() - 1)); // invalid adSize
        assertThrows(IllegalArgumentException.class, () -> new Board(1, Config.MAX_ADSIZE.getValue() + 1)); // invalid adSize
    }

    private void boardCompositionTest(int level, int adSize){
        board = new Board(level, adSize);
        GridCell[][] grid = board.getGrid();

        // board dimensions
        assertEquals(Config.DEFAULT_GRID_HEIGHT.getValue() + adSize, grid.length);
        assertEquals(Config.DEFAULT_GRID_WIDTH.getValue() + adSize, grid[0].length);

        // board colors
        assertTrue(countColors(level, grid) <= Config.DEFAULT_NUMBER_COLORS.getValue() + level - 1);
    }

    private int countColors(int level, GridCell[][] grid){
        Set<GridCellColor> colors = new HashSet<>();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(colors.size() == Config.DEFAULT_NUMBER_COLORS.getValue() + level - 1){
                    return colors.size();
                }

                colors.add(grid[i][j].getColor());
            }
        }

        return colors.size();
    }

    @Test
    public void boardGetDimensionsTest(){ // width and height
        for(int i = -Config.MIN_ADSIZE.getValue(); i <= Config.MAX_ADSIZE.getValue(); i++){
            board = new Board(Config.MIN_LEVEL.getValue(), i);
            GridCell[][] grid = board.getGrid();
            assertEquals(board.getHeight(), grid.length);
            assertEquals(board.getWidth(), grid[0].length);
        }
    }

    @Test
    public void boardGetCellTest(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED),     new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.YELLOW),  new GridCell(GridCellColor.GREEN)},
            {new GridCell(GridCellColor.MAGENTA), new GridCell(GridCellColor.WHITE)}
        };

        board = new Board(grid);
        assertEquals(new GridCell(GridCellColor.RED), board.getCell(0, 0));
        assertEquals(new GridCell(GridCellColor.WHITE), board.getCell(board.getWidth() - 1, board.getHeight() - 1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(board.getWidth(), 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(0, board.getHeight()));
    }

    @Test
    public void boardGetLevelTest(){
        for(int i = Config.MIN_LEVEL.getValue(); i <= Config.MAX_LEVEL.getValue(); i++){
            board = new Board(i, 0);
            assertEquals(i, board.getLevel());
        }
    }

    @Test
    public void boardClickTest_validPoint(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        GridCell[][] expectedGrid = {
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.RED, false)},
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        board = new Board(grid);
        board.click(new Point(0, 0));
        assertTrue(Util.array2DEquals(expectedGrid, grid));
    }

    @Test
    public void boardClickTest_invalidPoint_zoneToSmall(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        board = new Board(grid);
        assertTrue(!board.canClick(new Point(1, 1)));
        assertThrows(IllegalArgumentException.class, () -> board.click(new Point(1, 1)));
    }

    @Test
    public void boardClickTest_invalidPoint_outOfGrid(){
        assertTrue(!board.canClick(new Point(-1, 0)));
        assertThrows(IllegalArgumentException.class, () -> board.click(new Point(-1, 0)));
        assertTrue(!board.canClick(new Point(0, -1)));
        assertThrows(IllegalArgumentException.class, () -> board.click(new Point(0, -1)));
        assertTrue(!board.canClick(new Point(board.getWidth(), 0)));
        assertThrows(IllegalArgumentException.class, () -> board.click(new Point(board.getWidth(), 0)));
        assertTrue(!board.canClick(new Point(0, board.getHeight())));
        assertThrows(IllegalArgumentException.class, () -> board.click(new Point(0, board.getHeight())));
    }

    @Test
    public void boardClickTest_invalidPoint_emptyCell(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        board = new Board(grid);
        assertTrue(!board.canClick(new Point(0, 0)));
        assertThrows(IllegalArgumentException.class, () -> board.click(new Point(0, 0)));
    } 

    @Test
    public void boardClickableZoneTest_noClick(){
        assertNull(board.getLastClickableZone());
    }

    @Test
    public void boardClickableZoneTest_afterClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        Zone expectedZone = new Zone();
        expectedZone.addAll(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 0), new Point(2, 0));

        board = new Board(grid);
        board.click(new Point(0, 0));
        Zone zone = board.getLastClickableZone();

        assertNotNull(zone);
        assertEquals(expectedZone, zone);
    }

    @Test
    public void boardClickableZoneTest_afterMultipleClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        Zone expectedZone = new Zone();
        expectedZone.addAll(new Point(0, 1), new Point(0, 2), new Point(1, 1), new Point(1, 2));
        
        board = new Board(grid);
        board.click(new Point(0, 0));
        board.update();
        board.click(new Point(0, 1));
        Zone zone = board.getLastClickableZone();

        assertNotNull(zone);
        assertEquals(expectedZone, zone);
    }

    @Test
    public void boardUpdateTest_noShift(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        GridCell[][] expectedGrid = {
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.RED, false)},
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        board = new Board(grid);
        board.click(new Point(0, 0));
        board.update();
        assertTrue(Util.array2DEquals(expectedGrid, grid));
    }

    @Test
    public void boardUpdateTest_columnShiftOnly(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        GridCell[][] expectedGrid = {
            {new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)}
        };

        board = new Board(grid);
        board.click(new Point(0, 2));
        board.update();
        assertTrue(Util.array2DEquals(expectedGrid, grid));
    }

    @Test
    public void boardUpdateTest_leftShift(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        GridCell[][] expectedGrid = {
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.RED, false)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED, false)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED, false)}
        };
        
        board = new Board(grid);
        board.click(new Point(0, 0));
        board.update();
        assertTrue(Util.array2DEquals(expectedGrid, grid));
    }

    @Test
    public void boardUpdateTest_leftAndColumnShift(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        GridCell[][] expectedGrid = {
            {new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE, false)}
        };

        board = new Board(grid);
        board.click(new Point(0, 2));
        board.update();
        assertTrue(Util.array2DEquals(expectedGrid, grid));
    }

    @Test
    public void boardCurrentClickScoreTest_oneClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
         
        board = new Board(grid);
        ScoreCounter counter = board.getScoreCounter();
        board.click(new Point(0, 0));
        assertEquals(counter.compute(5), board.getCurrentClickScore());
    }

    @Test
    public void boardCurrentClickScoreTest_multipleClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        board = new Board(grid);
        ScoreCounter counter = board.getScoreCounter();
        board.click(new Point(0, 2));
        board.update();
        board.click(new Point(0, 2));
        board.update();
        assertEquals(counter.compute(3), board.getCurrentClickScore());
    }

    @Test
    public void boardScoreTest_oneClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
         
        board = new Board(grid);
        ScoreCounter counter = board.getScoreCounter();
        board.click(new Point(0, 0));
        assertEquals(counter.compute(5), board.getScore());
        assertEquals(counter.asInt(), board.getScore());
    }

    @Test
    public void boardScoreTest_multipleClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        board = new Board(grid);
        ScoreCounter counter = board.getScoreCounter();
        board.click(new Point(0, 2));
        board.update();
        board.click(new Point(0, 2));
        board.update();
        assertEquals(counter.compute(6) + counter.compute(3), board.getScore());
        assertEquals(counter.asInt(), board.getScore());
    }

    @Test
    public void boardEmptyTest_empty(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.RED, false)},
            {new GridCell(GridCellColor.RED, false), new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false)},
            {new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false), new GridCell(GridCellColor.BLUE, false)}
        };

        board = new Board(grid);
        assertTrue(board.isEmpty());
    }
    
    @Test
    public void boardEmptyTest_notEmpty(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        board = new Board(grid);
        board.click(new Point(1, 0));
        board.update();
        board.click(new Point(0, 2));
        board.update();

        assertTrue(!board.isEmpty());
    }

    @Test
    public void boardExhaustedTest_exhausted(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.GREEN), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        board = new Board(grid);
        board.click(new Point(1, 0));
        board.update();

        assertTrue(board.isExhausted());
    }

    @Test
    public void boardExhaustedTest_notExhausted(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.GREEN), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
         
        board = new Board(grid);
        assertTrue(!board.isExhausted());
    }

    @Test
    public void boardCountCellsTest_noClick(){
        assertEquals(board.getWidth() * board.getHeight(), board.countCells());
    }

    @Test 
    public void boardCountCellsTest_oneClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.GREEN), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        board = new Board(grid);
        board.click(new Point(1, 0));
        board.update();
        Zone zone = board.getLastClickableZone();

        assertEquals(board.getWidth() * board.getHeight() - zone.getPoints().size(), board.countCells());
    }

    @Test
    public void boardCountCellsTest_multipleClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.YELLOW), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        board = new Board(grid);
        board.click(new Point(1, 0));
        board.update();
        board.click(new Point(0, 2));
        board.update();

        assertEquals(1, board.countCells());
    }


    private <T> void print2DArray(T[][] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                System.out.print(array[i][j] + " ");
            }

            System.out.println();
        }
    }
}

