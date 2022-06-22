package g56080.same.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import g56080.same.Config;
import g56080.same.model.GridCell.GridCellColor;
import g56080.same.util.Recorder;
import g56080.same.util.Util;

/**
 * The game board.
 */
public class Board{
    
    private final GridCell[][] grid;
    private final Level level;
    private final ScoreCounter scoreCounter;
    private int score, currentClickScore;
    private Zone zone; // to record the last clickable zone

    /**
     * Constructs a new board using the given level and additional size. The level 
     * specifies the number of different colors present on the board (which increments 
     * the difficulty to obtain a high score and to empty the board). The additional
     * size is peformed on the default value provided by the {@link g56080.same.Config} 
     * enumeration by the literal fields {@link g56080.same.Config#DEFAULT_GRID_HEIGHT} 
     * and {@link g56080.same.Config#DEFAULT_GRID_WIDTH}.
     *
     * @param level the board level
     * @param adSize the additional size
     */
    public Board(int level, int adSize){
        if(!isValidAdSize(adSize))
            throw new IllegalArgumentException("Invalid additional size");

        this.level = new Level(level);
        scoreCounter = new ScoreCounter(v -> v * (v - 1));
        grid = createGrid(adSize);
    }

    /**
     * Constructs a new board using the informations extracted from the given board.
     *
     * @param board the board to copy
     */
    public Board(Board board){
        score = board.score;
        currentClickScore = board.currentClickScore;
        level = new Level(board.level);
        zone = new Zone(board.zone);
        scoreCounter = new ScoreCounter(board.scoreCounter);
        grid = Util.copyOf2D(board.grid, board.grid[0][0].getClass());
    }

    // for unit tests only
    Board(GridCell[][] grid){
        this.grid = grid;
        scoreCounter = new ScoreCounter(v -> v * (v - 1));
        level = new Level();
    }

    /**
     * Gets the width of this board.
     *
     * @return the integer width of this board.
     */
    public int getWidth(){
        return grid[0].length;
    }

    /**
     * Gets the height of this board.
     *
     * @return the integer height of this board.
     */
    public int getHeight(){
        return grid.length;
    }

    /**
     * Gets the grid cell found at the given x,y position.
     *
     * @param x the x position (the column's index)
     * @param y the y position (the row's index)
     *
     * @return the grid cell found at the given position.
     */
    public GridCell getCell(int x, int y){
        return grid[y][x];
    }

    /**
     * Gets the level of this board.
     *
     * @return the integer level of this board.
     */
    public int getLevel(){
        return level.getLevel();
    }

    /**
     * Gets the current total score of this board.
     *
     * @return the integer total score of this board.
     */
    public int getScore(){
        return score;
    }

    /**
     * Gets the score of the current click (the last click) performed on this
     * board.
     *
     * @return the integer current click score.
     */
    public int getCurrentClickScore(){
        return currentClickScore;
    }

    /**
     * Gets the zone associated to the last successful click on this board. 
     *
     * @return the last clickable zone.
     */
    public Zone getLastClickableZone(){
        return zone;
    }

    /**
     * Shuffles this board by randomly changing the position of its cells.
     */
    public void shuffle(){
        do{
            for(int i = 0; i < grid.length; i++){
                for(int j = grid[0].length - 1; j > 0; j--){
                    int ri = Util.genRandom(0, j);
                    Util.swap(grid, new Point(j, i), new Point(ri, i));
                }
            }
        } while(isExhausted());
    }

    /**
     * Clicks on the board at the given position and removes the cells associated
     * to the produced clickable zone. This method should only be invoked after having
     * checked for the given point to be valid using the {@link g56080.same.model.Board#canClick(Point)}. 
     *
     * @param p the click point
     */
    public void click(Point p){
        if(!canClick(p))
            throw new IllegalArgumentException("Invalid click point");

        int n = zone.getPoints().size();
        currentClickScore = scoreCounter.compute(n);
        scoreCounter.add(n);
        score = scoreCounter.asInt();
        eraseZone(grid, zone);
    }

    /**
     * Checks if it possible to click on the board at the given position. It is possible
     * to click at a given position if the position is inside the board and if the adjacent
     * cells to the one clicked form a zone of cells with a size equal or higher to the
     * {@link g56080.same.Config#ZONE_MIN_SIZE} enum literal.
     *
     * @param p the position to be checked
     *
     * @return true it it possible to click at given position on this board, false otherwise.
     */
    public boolean canClick(Point p){
        if(!isValidPoint(p))
            return false;

        zone = createZone(p);
        return zone.getPoints().size() >= Config.ZONE_MIN_SIZE.getValue();
    }

    /**
     * Updates this board by shifting all the points to the bottom left.
     */
    public void update(){
        if(zone != null){
            Recorder<Integer> recorder = new Recorder<>(0); // to save the min column index
            Map<Integer, Queue<Point>> points = zone.toMap();
            for(int i = 0; i < grid[0].length; i++){
                if(points.containsKey(i)){
                    shiftPoints(grid, zone.getMaxPoint(i), recorder);
                }
            }

            shiftLeft(grid, recorder.getValue());
        }
    }

    /**
     * Checks if this board is empty. The board is considered to be empty
     * if its lowest cell on the left is not filled.
     *
     * @return true if this board is empty, false otherwise.
     */
    public boolean isEmpty(){
        return !grid[grid.length - 1][0].isFilled();
    }

    /**
     * Checks if this board is exhausted. The board is considered to be exhausted if it it
     * is empty or if there are no more clickable points.
     *
     * @return true if this board is exhausted, false otherwise.
     */
    public boolean isExhausted(){
        if(isEmpty())
            return true;

        Zone zone = new Zone();
        GridCell[][] grid = Util.copyOf2D(this.grid, this.grid[0][0].getClass());
        Point startPoint = new Point(0, grid.length - 1);
        Point nfp = getNextFilledPoint(grid, startPoint);

        if(nfp != null){
            do{
                zone = createZone(nfp);
                eraseZone(grid, zone);
                startPoint = nfp;
            } while((nfp = getNextFilledPoint(grid, startPoint)) != null && zone.getPoints().size() < Config.ZONE_MIN_SIZE.getValue());
        }

        return zone.getPoints().size() < Config.ZONE_MIN_SIZE.getValue();
    }

    /**
     * Gets the number of remaining filled cells present in this board.
     *
     * @return the number of remaining filled cells.
     */
    public int countCells(){
        int counter = 0;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j].isFilled()) counter++;
            }
        }

        return counter;
    }

    // for unit tests
    GridCell[][] getGrid(){
        return grid;
    }

    ScoreCounter getScoreCounter(){
        return scoreCounter;
    }





    // private
    private boolean isValidAdSize(int adSize){
        return adSize >= -Config.MIN_ADSIZE.getValue() && adSize <= Config.MAX_ADSIZE.getValue();
    }

    private final GridCell[][] createGrid(int adSize){
        GridCell[][] grid = new GridCell[Config.DEFAULT_GRID_HEIGHT.getValue() + adSize][Config.DEFAULT_GRID_WIDTH.getValue() + adSize];
        fillinGrid(grid);
        return grid;
    }

    private final void fillinGrid(GridCell[][] grid){
        GridCellColor[] colors = GridCellColor.values();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new GridCell(colors[Util.genRandom(0, level.getNumberColors())]);
            }
        }
    }

    private final Zone createZone(Point p){
        Zone zone = new Zone();
        fillinZone(zone, grid[p.getY()][p.getX()], p);
        return zone;
    }

    private final void fillinZone(Zone zone, GridCell color, Point p){
        if(isValidPoint(p) && !zone.contains(p) && grid[p.getY()][p.getX()].equals(color)){
            zone.add(p);
            fillinZone(zone, color, new Point(p.getX(), p.getY() - 1)); // top
            fillinZone(zone, color, new Point(p.getX(), p.getY() + 1)); // bottom
            fillinZone(zone, color, new Point(p.getX() - 1, p.getY())); // left
            fillinZone(zone, color, new Point(p.getX() + 1, p.getY())); // right
        }
    }

    private final boolean isValidPoint(Point p){
        return p.getX() >= 0 && p.getX() < grid[0].length 
            && p.getY() >= 0 && p.getY() < grid.length 
            && grid[p.getY()][p.getX()].isFilled();
    }   
    
    private final void eraseZone(GridCell[][] grid, Zone zone){
        for(Point pt : zone){
            grid[pt.getY()][pt.getX()].setState(false);
        }
    }

    // describe the structure of a shift operation
    private final int shiftPoints(GridCell[][] grid, int startInclusive, Predicate<Integer> hasNext, UnaryOperator<Integer> next, Predicate<Integer> canSwap, BiConsumer<Integer, Integer> action){
        int shift = 0;
        for(int i = startInclusive; hasNext.test(i); i = next.apply(i)){
            if(canSwap.test(i)){
                action.accept(i, shift);
            } else shift++;
        }

        return shift;
    }

    private final void shiftPoints(GridCell[][] grid, Point startPoint, Recorder<Integer> recorder){
        int stx = startPoint.getX(), sty = startPoint.getY();
        int shft = shiftPoints(grid, sty, i -> i >= 0, i -> i - 1, i -> grid[i][stx].isFilled(), (i, shift) -> {
            Util.swap(grid, new Point(stx, i), new Point(stx, i + shift));
        });

        if(shft == grid.length && stx < recorder.getValue()){ // empty column
            recorder.record(stx);
        }
    }

    private final void shiftLeft(GridCell[][] grid, int startColumn){
        int lastColumn = getLastColumn(grid);
        shiftPoints(grid, startColumn, i -> i <= lastColumn, i -> i + 1, i -> !isEmptyColumn(grid, i), (i, shift) -> {
            for(int k = 0; k < grid.length; k++){
                Util.swap(grid, new Point(i, k), new Point(i - shift, k));
            }
        });
    }

    private final int getLastColumn(GridCell[][] grid){
        for(int i = grid[0].length - 1; i >= 0; i--){
            if(grid[grid.length - 1][i].isFilled())
                return i;
        }

        return -1;
    }

    private final boolean isEmptyColumn(GridCell[][] grid, int columnIndex){
        return !grid[grid.length - 1][columnIndex].isFilled();
    }

    private final Point getNextFilledPoint(GridCell[][] grid, Point startPoint){
        for(int i = startPoint.getX(); i < grid[0].length; i++){
            if(grid[startPoint.getY()][i].isFilled())
                return new Point(i, startPoint.getY());
        }

        for(int i = startPoint.getY() - 1; i >= 0; i--){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j].isFilled())
                    return new Point(j, i);
            }
        }

        return null;
    }
}

