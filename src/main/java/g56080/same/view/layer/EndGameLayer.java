package g56080.same.view.layer;

import g56080.same.controller.Controller;
import g56080.same.model.Model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Layer for the end of the game containing informations about the final score among others.
 */
public class EndGameLayer extends Layer{
    
    private final Scene scene;
    private final Model model;
    private final Controller controller;

    /**
     * Constructs a new end game layer using the given scene, model and controller.
     *
     * @param scene the application scene to be used
     * @param model the application model to be used
     * @param controller the application controller to be used
     */
    public EndGameLayer(Scene scene, Model model, Controller controller){
        this.scene = scene;
        this.model = model;
        this.controller = controller;

        fillinLayer();
    }

    private final void fillinLayer(){
        VBox box = new VBox();
        Label endTxt = new Label("The game is over");
        Label successTxt = new Label("Congrats, you emptied the board !");
        Label scoreTxt = new Label("Final score: " + model.getScore());

        endTxt.setFont(Font.font(25));
        successTxt.setFont(Font.font(25));
        scoreTxt.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        endTxt.setTextFill(Color.WHITE);
        scoreTxt.setTextFill(Color.WHITE);
        successTxt.setTextFill(Color.GREEN);
        box.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0.6), null, null)));
        box.setPadding(new Insets(5, 8, 5, 8));
        box.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> scene.setCursor(Cursor.HAND));

        box.getChildren().add(endTxt);
        if(model.isSuccess()) 
            box.getChildren().add(successTxt);
        box.getChildren().add(scoreTxt);
        box.setAlignment(Pos.CENTER);

        setRoot(box);
    }
}

