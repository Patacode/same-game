package g56080.same.view;

import g56080.same.controller.Controller;
import g56080.same.model.Board;
import g56080.same.model.Point;
import g56080.same.model.Zone;
import g56080.same.view.layer.Layer;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;

import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.util.Duration;

/**
 * Handler for the click on a board's cell.
 */
public class ClickCellHandler implements EventHandler<MouseEvent>{
    
    private final Board board;
    private final Layer layer;
    private final Point point;
    private final Controller controller;
    private final FXView view;

    /**
     * Constructs a new click cell handler using the given parameters.
     *
     * @param board the board on which to click
     * @param layer the layer of the board 
     * @param point the click point
     * @param controller the application controller to notify the model that a click occured
     * @param view the view to interact with the user interface while performing the click action.
     */
    public ClickCellHandler(Board board, Layer layer, Point point, Controller controller, FXView view){
        this.board = board;
        this.layer = layer;
        this.point = point;
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void handle(MouseEvent ev){
        if(board.canClick(point)){
            view.createTransparentLayer();
            Zone zone = board.getLastClickableZone();
            int counter = 0;
            for(Point p : zone){
                Rectangle target = layer.getElement(String.format("cell%dx%dy", p.getX(), p.getY()), Rectangle.class);

                StrokeTransition st = new StrokeTransition(Duration.seconds(0.3));
                FadeTransition ft = new FadeTransition(Duration.seconds(0.2));
                SequentialTransition sqt = new SequentialTransition(target, st, ft);

                st.setFromValue(Color.WHITE);
                st.setToValue((Color)target.getFill());
                st.setShape(target);
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setByValue(0.1);
                sqt.play();

                if(counter == 0)
                    sqt.setOnFinished(e -> {
                        view.removeTransparentLayer();
                        controller.click(point);
                    });
                counter++;
            }
        } else{
            controller.click(point);
        }
    }
}

