package g56080.same.view;

import g56080.same.model.Board;
import g56080.same.model.Point;
import g56080.same.model.Zone;
import g56080.same.view.layer.Layer;

import javafx.event.EventHandler;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Handler to hover an area of cells on mouse move.
 */
public class AreaHoverHandler implements EventHandler<MouseEvent>{
    
    private final Board board;
    private final Layer layer;
    private final Point point;
    private final Scene scene;
    private final boolean hovering;

    /**
     * Creates a new area hover handler using the given parameters.
     *
     * @param board the board on which to hover the area
     * @param layer the layer of the board
     * @param point the hovered point
     * @param scene the application scene graph
     * @param hovering the state of the hovering
     */
    public AreaHoverHandler(Board board, Layer layer, Point point, Scene scene, boolean hovering){
        this.board = board;
        this.layer = layer;
        this.point = point;
        this.scene = scene;
        this.hovering = hovering;
    }

    @Override
    public void handle(MouseEvent event){
        scene.setCursor(hovering ? Cursor.HAND : Cursor.DEFAULT);
        if(board.canClick(point)){
            Zone zone = board.getLastClickableZone();
            for(Point p : zone){
                Rectangle rect = layer.getElement(String.format("cell%dx%dy", p.getX(), p.getY()), Rectangle.class);
                Color fill = (Color) rect.getFill();
                if(fill != null){
                    rect.setFill(hovering ? fill.darker() : fill.brighter());
                }
            }
        }
    }
}
