package g56080.same.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import g56080.same.model.GraphState.State;
import g56080.same.model.GridCell.GridCellColor;
import g56080.same.util.Util;


public class GameModelTest{
    
    private GameModel model;

    @Before
    public void initModel(){
        model = new GameModel(); // to NOT_STARTED
    }

    @Test
    public void modelStateTest_notStarted_gameCreation(){
        assertEquals(State.NOT_STARTED, model.getState());
    }

    @Test
    public void modelStateTest_notStarted_afterReplay(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        model.isFinish();
        model.click(new Point(0, 2));
        model.updateGrid();
        model.isFinish();
        model.replay();
        assertEquals(State.NOT_STARTED, model.getState());
    }

    @Test
    public void modelStateTest_gameStarted(){
        model.init(1, 0);
        assertEquals(State.GAME_STARTED, model.getState());
    }

    @Test
    public void modelStateTest_playerTurn_firstRound(){
        model.init(1, 0);
        model.shuffle();
        assertEquals(State.PLAYER_TURN, model.getState());
    }

    @Test
    public void modelStateTest_playerTurn_secondRound(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.shuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        model.isFinish();
        assertEquals(State.PLAYER_TURN, model.getState());
    }

    @Test
    public void modelStateTest_playerTurn_afterErrorProcessing(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.shuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        model.isFinish();
        model.click(new Point(0, 0));
        model.errorProcessed();

        assertEquals(State.PLAYER_TURN, model.getState());
    }

    @Test
    public void modelStateTest_gameTurn(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.shuffle();
        model.click(new Point(0, 0));
        assertEquals(State.GAME_TURN, model.getState());
    }

    @Test
    public void modelStateTest_gameCheck(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.shuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        assertEquals(State.GAME_CHECK, model.getState());
    }

    @Test 
    public void modelStateTest_gameOver(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        model.isFinish();
        model.click(new Point(0, 2));
        model.updateGrid();
        model.isFinish();
        assertEquals(State.GAME_OVER, model.getState());
    }

    @Test
    public void modelStateTest_errorClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        model.isFinish();
        model.click(new Point(0, 0));
        assertEquals(State.ERROR_CLICK, model.getState());
    }

    @Test
    public void modelStateTest_errorCmd(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        model.isFinish();
        model.processAction(Action.UNDO);
        model.updateGrid();
        model.isFinish();
        model.processAction(Action.UNDO);
        assertEquals(State.ERROR_CMD, model.getState());
    }

    @Test
    public void modelStateTest_errorNoClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        model.init(grid);
        model.fakeShuffle();
        model.processAction(Action.REDO);
        assertEquals(State.ERROR_NO_CLICK, model.getState());
    }

    @Test
    public void modelStateTest_wrongInit(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid); // to SHUFFLE_BOARD
        assertThrows(IllegalStateException.class, () -> model.init(1, 0));

        model.fakeShuffle(); // to PLAYER_TURN
        assertThrows(IllegalStateException.class, () -> model.init(1, 0));

        model.processAction(Action.REDO); // to ERROR_NO_CLICK
        assertThrows(IllegalStateException.class, () -> model.init(1, 0));

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        assertThrows(IllegalStateException.class, () -> model.init(1, 0));

        model.updateGrid(); // to GAME_CHECK
        assertThrows(IllegalStateException.class, () -> model.init(1, 0));

        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.REDO); // to ERROR_CLICK
        assertThrows(IllegalStateException.class, () -> model.init(1, 0));

        model.errorProcessed(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to ERROR_CMD
        assertThrows(IllegalStateException.class, () -> model.init(1, 0));

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.click(new Point(0, 1)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to GAME_OVER
        assertThrows(IllegalStateException.class, () -> model.init(1, 0));
    }

    @Test
    public void modelStateTest_wrongShuffle(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        assertThrows(IllegalStateException.class, () -> model.shuffle());

        model.init(grid); // to SHUFFLE_BOARD
        model.fakeShuffle(); // to PLAYER_TURN
        assertThrows(IllegalStateException.class, () -> model.shuffle());

        model.processAction(Action.REDO); // to ERROR_NO_CLICK
        assertThrows(IllegalStateException.class, () -> model.shuffle());

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        assertThrows(IllegalStateException.class, () -> model.shuffle());

        model.updateGrid(); // to GAME_CHECK
        assertThrows(IllegalStateException.class, () -> model.shuffle());
        
        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.REDO); // to ERROR_CLICK
        assertThrows(IllegalStateException.class, () -> model.shuffle());

        model.errorProcessed(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to ERROR_CMD
        assertThrows(IllegalStateException.class, () -> model.shuffle());

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.click(new Point(0, 1)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to GAME_OVER
        assertThrows(IllegalStateException.class, () -> model.shuffle());
    }

    @Test
    public void modelStateTest_wrongClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));

        model.init(grid); // to SHUFFLE_BOARD
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));

        model.fakeShuffle(); // to PLAYER_TURN
        model.processAction(Action.REDO); // to ERROR_NO_CLICK
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_CHECK
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));

        model.updateGrid(); // to GAME_CHECK
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));

        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.REDO); // to ERROR_CLICK
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));

        model.errorProcessed(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to ERROR_CMD
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.click(new Point(0, 1)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to GAME_OVER
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));
    }

    @Test
    public void modelStateTest_wrongProcessAction(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        assertThrows(IllegalStateException.class, () -> model.processAction(Action.UNDO));

        model.init(grid); // to SHUFFLE_BOARD
        assertThrows(IllegalStateException.class, () -> model.processAction(Action.UNDO));

        model.fakeShuffle(); // to PLAYER_TURN
        model.processAction(Action.REDO); // to ERROR_NO_CLICK
        assertThrows(IllegalStateException.class, () -> model.processAction(Action.UNDO));

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_CHECK
        assertThrows(IllegalStateException.class, () -> model.click(new Point(0, 0)));

        model.updateGrid(); // to GAME_CHECK
        assertThrows(IllegalStateException.class, () -> model.processAction(Action.UNDO));

        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.REDO); // to ERROR_CLICK
        assertThrows(IllegalStateException.class, () -> model.processAction(Action.UNDO));

        model.errorProcessed(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to ERROR_CMD
        assertThrows(IllegalStateException.class, () -> model.processAction(Action.UNDO));

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.click(new Point(0, 1)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to GAME_OVER
        assertThrows(IllegalStateException.class, () -> model.processAction(Action.UNDO));
    }

    @Test
    public void modelStateTest_wrongErrorProcessed(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
            
        assertThrows(IllegalStateException.class, () -> model.errorProcessed());

        model.init(grid); // to SHUFFLE_BOARD
        assertThrows(IllegalStateException.class, () -> model.errorProcessed());

        model.fakeShuffle(); // to PLAYER_TURN
        assertThrows(IllegalStateException.class, () -> model.errorProcessed());

        model.click(new Point(0, 0)); // to GAME_TURN
        assertThrows(IllegalStateException.class, () -> model.errorProcessed());

        model.updateGrid(); // to GAME_CHECK
        assertThrows(IllegalStateException.class, () -> model.errorProcessed());

        model.isFinish(); // to PLAYER_TURN
        model.click(new Point(0, 1)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to GAME_OVER
        assertThrows(IllegalStateException.class, () -> model.errorProcessed());
    }

    @Test
    public void modelStateTest_wrongIsFinish(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        assertThrows(IllegalStateException.class, () -> model.isFinish());

        model.init(grid); // to SHUFFLE_BOARD
        assertThrows(IllegalStateException.class, () -> model.isFinish());

        model.fakeShuffle(); // to PLAYER_TURN
        assertThrows(IllegalStateException.class, () -> model.isFinish());

        model.processAction(Action.REDO); // to ERROR_NO_CLICK
        assertThrows(IllegalStateException.class, () -> model.isFinish());

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        assertThrows(IllegalStateException.class, () -> model.isFinish());

        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.REDO); // to ERROR_CLICK
        assertThrows(IllegalStateException.class, () -> model.isFinish());

        model.errorProcessed();
        model.processAction(Action.UNDO); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to ERROR_CMD
        assertThrows(IllegalStateException.class, () -> model.isFinish());

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.click(new Point(0, 1)); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to GAME_OVER
        assertThrows(IllegalStateException.class, () -> model.isFinish());
    }

    @Test
    public void modelStateTest_wrongReplay(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        assertThrows(IllegalStateException.class, () -> model.replay());

        model.init(grid); // to SHUFFLE_BOARD
        assertThrows(IllegalStateException.class, () -> model.replay());

        model.fakeShuffle(); // to PLAYER_TURN
        assertThrows(IllegalStateException.class, () -> model.replay());

        model.processAction(Action.REDO); // to ERROR_NO_CLICK
        assertThrows(IllegalStateException.class, () -> model.replay());

        model.errorProcessed(); // to PLAYER_TURN
        model.click(new Point(0, 0)); // to GAME_TURN
        assertThrows(IllegalStateException.class, () -> model.replay());

        model.updateGrid(); // to GAME_CHECK
        assertThrows(IllegalStateException.class, () -> model.replay());

        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.REDO); // to ERROR_CLICK
        assertThrows(IllegalStateException.class, () -> model.replay());
        
        model.errorProcessed();
        model.processAction(Action.UNDO); // to GAME_TURN
        model.updateGrid(); // to GAME_CHECK
        model.isFinish(); // to PLAYER_TURN
        model.processAction(Action.UNDO); // to ERROR_CMD
        assertThrows(IllegalStateException.class, () -> model.replay());
    }


    @Test
    public void modelRedoActionTest_validRedo(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 1));
        model.updateGrid();
        model.isFinish();
        model.processAction(Action.REDO);
        model.updateGrid();
        assertTrue(model.isFinish());
    }

    @Test
    public void modelRedoActionTest_invalidRedo_errorClick(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        model.isFinish();
        model.processAction(Action.REDO);
        assertEquals(State.ERROR_CLICK, model.getState());
    }

    @Test
    public void modelUndoActionTest(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        GridCell[][] expectedGrid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        
        };

        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 0));
        model.updateGrid();
        model.isFinish();
        model.processAction(Action.UNDO);
        assertTrue(Util.array2DEquals(expectedGrid, model.getBoard().getGrid()));
    }

    @Test
    public void modelSuccessTest_valid(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 1));
        model.updateGrid();
        model.isFinish();
        model.processAction(Action.REDO);
        model.updateGrid();
        assertTrue(model.isSuccess());
    }

    @Test
    public void modelSuccessTest_invalid(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.YELLOW)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 1));
        model.updateGrid();
        model.isFinish();
        model.processAction(Action.REDO);
        model.updateGrid();
        assertTrue(!model.isSuccess());
    }

    @Test
    public void modelHighScoreTest_oneGame(){
        GridCell[][] grid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.YELLOW)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        playGame(grid);
        assertEquals(26, model.getHighScore());
    }

    @Test
    public void modelHighScoreTest_multipleGame(){
        GridCell[][] firstGrid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.YELLOW)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };

        GridCell[][] secondGrid = {
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED), new GridCell(GridCellColor.RED)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)},
            {new GridCell(GridCellColor.RED), new GridCell(GridCellColor.BLUE), new GridCell(GridCellColor.BLUE)}
        };
        
        int firstHighScore, secondHighScore;

        playGame(firstGrid); // highScore is 26
        firstHighScore = model.getHighScore();
        model.replay(); 
        playGame(secondGrid); // highScore is 32
        secondHighScore = model.getHighScore();

        assertTrue(firstHighScore < secondHighScore);
        assertEquals(32, model.getHighScore());
    }

    private void playGame(GridCell[][] grid){
        model.init(grid);
        model.fakeShuffle();
        model.click(new Point(0, 1));
        model.updateGrid();
        model.isFinish();
        model.processAction(Action.REDO);
        model.updateGrid();
        model.isFinish();
    }
}

