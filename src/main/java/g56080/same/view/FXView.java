package g56080.same.view;

import g56080.same.Config;
import g56080.same.controller.Controller;
import g56080.same.model.GraphState.State;
import g56080.same.model.Model;
import g56080.same.view.layer.*;
import g56080.same.view.layer.Layer.LinkedLayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

/**
 * The GUI view used by the application to interact with the user. 
 */
public class FXView extends Scene implements View{
    
    private final Controller controller;
    private final Model model;
    private final StackLayer stackLayer;
    private Theme theme;
    private boolean isFirst;

    /**
     * Constructs a new GUI view using the given controller and model.
     *
     * @param controller the application controller used to notify the model that a certain
     * action occured.
     * @param model the application model used to retrieve informations about the game.
     */
    public FXView(Controller controller, Model model){
        super(new Pane(), Config.DEFAULT_SCENE_WIDTH.getValue(), Config.DEFAULT_SCENE_HEIGHT.getValue());
        this.controller = controller;
        this.model = model;
        this.theme = Theme.DEFAULT;
        stackLayer = new StackLayer();

        model.addObserver(this);
    }

    @Override
    public void start(Stage mainStage){
        constructScene();

        mainStage.setTitle("Same game");
        mainStage.setMinWidth(this.getWidth());
        mainStage.setMinHeight(this.getHeight());
        mainStage.setScene(this);
        mainStage.sizeToScene();
        mainStage.show();
    }

    @Override
    public void update(){
        State state = model.getState(); 
        switch(state){
            case GAME_STARTED:
                isFirst = true;
                stackLayer.dropElement("startLayer");
                controller.shuffle();
                break;
            case PLAYER_TURN:
                if(isFirst){
                    createBoardLayer();
                    isFirst = false;
                } else updateBoardLayer();
                break;
            case GAME_TURN:
                controller.updateGrid();
                break;
            case ERROR_CLICK:
                createDialogLayer("You cannot click here, no valid zone detected");
                controller.errorProcessed();
                break;
            case ERROR_CMD:
                createDialogLayer("Cannot undo");
                controller.errorProcessed();
                break;
            case ERROR_NO_CLICK:
                createDialogLayer("You haven't clicked yet");
                controller.errorProcessed();
                break;
            case GAME_CHECK:
                controller.isFinish();
                break;
            case GAME_OVER:
                updateBoardLayer();
                createEndGameLayer();
        }
    }

    /**
     * Sets the theme of this view.
     *
     * @param theme the new theme to assign to this view
     */
    public void setTheme(Theme theme){
        this.theme = theme;
        for(LinkedLayer ll : stackLayer){
            if(ll.getLayer() instanceof Themable){
                Themable themable = (Themable) ll.getLayer(); 
                themable.updateTheme(theme);
            }
        }
    }

    /**
     * Creates a new transparent layer to disallow the user to click on the layer's nodes
     * below.
     */
    public void createTransparentLayer(){
        stackLayer.getElement("boardLayer").setNext("transparentLayer");
        stackLayer.addElement("transparentLayer", new LinkedLayer(new Layer(), null));
        stackLayer.compose("bgLayer");
    }

    /**
     * Removes the transparent layer previously created.
     */
    public void removeTransparentLayer(){
        stackLayer.removeElement("transparentLayer");
        stackLayer.getElement("boardLayer").setNext(null);
        stackLayer.compose("bgLayer");
    }



    private final void constructScene(){
        Layer mainLayer = createMainLayer();
        Layer bgLayer = new RectangleLayer(theme.getPrimaryColor(), this, new Insets(0));
        Layer startLayer = new StartLayer(this, controller);

        stackLayer.addElement("bgLayer", new LinkedLayer(bgLayer, "startLayer"));
        stackLayer.addElement("startLayer", new LinkedLayer(startLayer, null));
        stackLayer.compose("bgLayer");

        setRoot(mainLayer.getRoot());
    }

    private final Layer createMainLayer(){
        Layer mainLayer = new Layer();
        VBox box = new VBox();
        MenuBar menuBar = new FXMenu(this);

        box.getChildren().addAll(menuBar, stackLayer.getRoot());
        mainLayer.addElement("container", box);
        mainLayer.addElement("menu", menuBar);
        mainLayer.setRoot(box);

        return mainLayer;
    }

    private final void createBoardLayer(){
        double nw = 50 + (getWidth() - Config.DEFAULT_SCENE_WIDTH.getValue()) / 2.;
        double nh = 50 + (getHeight() - Config.DEFAULT_SCENE_HEIGHT.getValue()) / 2.;
        Layer boardLayer = new BoardLayer(theme, this, model, controller);
        stackLayer.getElement("bgLayer").setNext("boardLayer");
        stackLayer.addElement("boardLayer", new LinkedLayer(boardLayer, null));
        stackLayer.compose("bgLayer");
        stackLayer.getRoot().setMargin(boardLayer.getRoot(), new Insets(nh, nw, nh, nw));

        widthProperty().addListener((obs, o, n) -> {
            Insets currentInsets = StackPane.getMargin(boardLayer.getRoot());
            stackLayer.getRoot().setMargin(boardLayer.getRoot(), new Insets(
                currentInsets.getTop(), 
                50 + (n.doubleValue() - Config.DEFAULT_SCENE_WIDTH.getValue()) / 2., 
                currentInsets.getBottom(), 
                50 + (n.doubleValue() - Config.DEFAULT_SCENE_WIDTH.getValue()) / 2.));
        });

        heightProperty().addListener((obs, o, n) -> {
            Insets currentInsets = StackPane.getMargin(boardLayer.getRoot());
            stackLayer.getRoot().setMargin(boardLayer.getRoot(), new Insets(
                50 + (n.doubleValue() - Config.DEFAULT_SCENE_HEIGHT.getValue()) / 2., 
                currentInsets.getRight(),
                50 + (n.doubleValue() - Config.DEFAULT_SCENE_HEIGHT.getValue()) / 2.,
                currentInsets.getLeft()));
        });
    }

    private final void updateBoardLayer(){
        BoardLayer boardLayer = (BoardLayer) stackLayer.getElement("boardLayer").getLayer();

        boardLayer.updateBoard();
        boardLayer.updateScore();
    }


    private final void createDialogLayer(String msg){
        Layer dialogLayer = new DialogLayer(msg, this);
        stackLayer.getElement("boardLayer").setNext("dialogLayer");
        stackLayer.addElement("dialogLayer", new LinkedLayer(dialogLayer, null));
        stackLayer.compose("bgLayer");

        dialogLayer.getRoot().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            setCursor(Cursor.DEFAULT);
            stackLayer.removeElement("dialogLayer");
            stackLayer.getElement("boardLayer").setNext(null);
            stackLayer.compose("bgLayer");
        });
    }

    private final void createEndGameLayer(){
        Layer endGameLayer = new EndGameLayer(this, model, controller);
        stackLayer.getElement("boardLayer").setNext("endGameLayer");
        stackLayer.addElement("endGameLayer", new LinkedLayer(endGameLayer, null));
        stackLayer.compose("bgLayer");

        endGameLayer.getRoot().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            setCursor(Cursor.DEFAULT);
            stackLayer.removeElement("endGameLayer");
            stackLayer.removeElement("boardLayer");
            stackLayer.getElement("bgLayer").setNext("startLayer");
            stackLayer.addDroppedElement();
            stackLayer.compose("bgLayer");
            controller.replay();
        });
    }

}

