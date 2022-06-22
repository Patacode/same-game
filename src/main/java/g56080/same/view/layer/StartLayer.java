package g56080.same.view.layer;

import java.util.function.Supplier;

import g56080.same.Config;
import g56080.same.controller.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.util.Duration;

/**
 * Layer for the start screen containing the modifiable board configurations and a start button to
 * initiate the game.
 */
public class StartLayer extends Layer{
    
    private final Controller controller;
    private final Scene scene;
    
    /**
     * Constructs a new start layer using the given scene and controller.
     *
     * @param scene the application scene to be used
     * @param controller the application controller to be used
     */
    public StartLayer(Scene scene, Controller controller){
        this.scene = scene;
        this.controller = controller;

        fillinLayer();
    }

    private final void fillinLayer(){
        VBox container = new VBox();
        HBox sliderContainer = new HBox();
        VBox sliderSizeContainer = new VBox();
        VBox sliderLevelContainer = new VBox();
        Label start = new Label("Start");
        Label size = new Label("Size");
        Label level = new Label("Level");
        Label sizeInfo = new Label(String.format("Board size (%dh;%dw)", Config.DEFAULT_GRID_HEIGHT.getValue(), Config.DEFAULT_GRID_WIDTH.getValue()));
        Label levelInfo = new Label(String.format("Level (%d)", Config.MIN_LEVEL.getValue()));
        Slider sliderSize = new Slider(-Config.MIN_ADSIZE.getValue(), Config.MAX_ADSIZE.getValue(), 0);
        Slider sliderLevel = new Slider(Config.MIN_LEVEL.getValue(), Config.MAX_LEVEL.getValue(), 1);

        setRoot(container);
        sliderSizeContainer.getChildren().addAll(size, sliderSize, sizeInfo);
        sliderLevelContainer.getChildren().addAll(level, sliderLevel, levelInfo);
        sliderContainer.getChildren().addAll(sliderSizeContainer, sliderLevelContainer);
        container.getChildren().addAll(start, sliderContainer);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(15);
        container.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0.3), null, null)));
        sliderSizeContainer.setAlignment(Pos.CENTER);
        sliderLevelContainer.setAlignment(Pos.CENTER);
        sliderContainer.setAlignment(Pos.CENTER);

        start.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        start.setPadding(new Insets(5, 10, 5, 10));
        start.setFont(Font.font("Verdana", FontWeight.BOLD, 50.));
        size.setTextFill(Color.WHITE);
        level.setTextFill(Color.WHITE);
        sizeInfo.setTextFill(Color.WHITE);
        levelInfo.setTextFill(Color.WHITE);

        addEventHandlers(start);
        Supplier<String> sizeSupplier = () -> String.format("Board size (%.0fh;%.0fw)", Config.DEFAULT_GRID_HEIGHT.getValue() + sliderSize.getValue(), Config.DEFAULT_GRID_WIDTH.getValue() + sliderSize.getValue());
        Supplier<String> levelSupplier = () -> String.format("Level (%.0f)", sliderLevel.getValue());
        sliderSize.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> sizeInfo.setText(sizeSupplier.get()));
        sliderLevel.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> levelInfo.setText(levelSupplier.get()));
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> controller.init((int) Math.round(sliderLevel.getValue()), (int) Math.round(sliderSize.getValue())));
    }

    private final void addEventHandlers(Region node){
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            scene.setCursor(Cursor.HAND);
            node.setEffect(new DropShadow(50., 0, 0, new Color(1, 1, 1, 0.5)));
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            scene.setCursor(Cursor.DEFAULT);
            node.setEffect(null);
        });

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            node.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 0.5), null, null)));
        });
        
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            node.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        });
    }
}
