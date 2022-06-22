package g56080.same.view.layer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import g56080.same.Config;
import g56080.same.controller.Controller;
import g56080.same.model.Action;
import g56080.same.model.Board;
import g56080.same.model.GridCell.GridCellColor;
import g56080.same.model.GridCell;
import g56080.same.model.Model;
import g56080.same.model.Point;
import g56080.same.model.Zone;
import g56080.same.view.FXView;
import g56080.same.view.Theme;
import g56080.same.view.Themable;
import g56080.same.view.AreaHoverHandler;
import g56080.same.view.ClickCellHandler;

import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

/**
 * Themable layer for the game board. This layer contains the game board and informations
 * about it, such as scores, remaining cells and action buttons for example.
 */
public class BoardLayer extends Layer implements Themable{
    
    private final Theme theme;
    private final FXView scene;
    private final Model model;
    private final Controller controller;

    /**
     * Constructs a new board layer using the given parameters.
     *
     * @param theme the theme to be used by this layer
     * @param scene the scene view to be used by this layer
     * @param model the application to be used
     * @param controller the application controller to be used
     */
    public BoardLayer(Theme theme, FXView scene, Model model, Controller controller){
        this.theme = theme;
        this.scene = scene;
        this.model = model;
        this.controller = controller;

        createLayer();
    }

    @Override
    public void updateTheme(Theme theme){
        HBox boardBox  = getElement("boardBox", HBox.class);
        Button btnRedo = getElement("redo", Button.class);
        Button btnUndo = getElement("undo", Button.class);
        Button btnStop = getElement("stop", Button.class);
        Label title = getElement("title", Label.class);

        boardBox.setBackground(new Background(new BackgroundFill(theme.getSecondaryColor(), null, null)));
        btnRedo.setBackground(new Background(new BackgroundFill(theme.getPrimaryColor(), null, null))); 
        btnUndo.setBackground(new Background(new BackgroundFill(theme.getPrimaryColor(), null, null))); 
        btnStop.setBackground(new Background(new BackgroundFill(theme.getPrimaryColor(), null, null))); 
        title.setTextFill(new LinearGradient(0, 0, 10, 80, false, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(1, theme.getSecondaryColor())));

        btnUndo.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            btnUndo.setBackground(new Background(new BackgroundFill(theme.getSecondaryColor(), null, null)));
            scene.setCursor(Cursor.HAND);
        });
        btnUndo.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            btnUndo.setBackground(new Background(new BackgroundFill(theme.getPrimaryColor(), null, null)));
            scene.setCursor(Cursor.DEFAULT);
        });
        btnRedo.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            btnRedo.setBackground(new Background(new BackgroundFill(theme.getSecondaryColor(), null, null)));
            scene.setCursor(Cursor.HAND);
        });
        btnRedo.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            btnRedo.setBackground(new Background(new BackgroundFill(theme.getPrimaryColor(), null, null)));
            scene.setCursor(Cursor.DEFAULT);
        });
        btnStop.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            btnStop.setBackground(new Background(new BackgroundFill(theme.getSecondaryColor(), null, null)));
            scene.setCursor(Cursor.HAND);
        });
        btnStop.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            btnStop.setBackground(new Background(new BackgroundFill(theme.getPrimaryColor(), null, null)));
            scene.setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * Updates the board of this layer.
     */
    public void updateBoard(){
        GridPane grid = getElement("board", GridPane.class);
        Board board = model.getBoard(); 
        int bdw = Config.GRIDCELL_WIDTH.getValue() * board.getWidth(); // fx board width
        int bdh = Config.GRIDCELL_HEIGHT.getValue() * board.getHeight(); // fx board height

        // button size
        double btnw = bdw != Config.DEFAULT_FXGRID_WIDTH.getValue() ? Config.DEFAULT_FXGRID_WIDTH.getValue() / (board.getWidth() + 0.) : Config.GRIDCELL_WIDTH.getValue();
        double btnh = bdh != Config.DEFAULT_FXGRID_HEIGHT.getValue() ? Config.DEFAULT_FXGRID_HEIGHT.getValue() / (board.getHeight() + 0.) : Config.GRIDCELL_HEIGHT.getValue();

        grid.getChildren().clear();
        for(int i = 0; i < board.getHeight(); i++){
            for(int j = 0; j < board.getWidth(); j++){
                GridCell cell = board.getCell(j, i);
                Rectangle rect = new Rectangle();
                Point p = new Point(j, i);
                if(cell.isFilled()){
                    rect.setStyle("-fx-stroke: rgba(255, 255, 255, 0.3); -fx-stroke-width:5;");
                    rect.setFill(cell.getColorFX().getColor());
                    rect.addEventHandler(MouseEvent.MOUSE_ENTERED, new AreaHoverHandler(board, this, p, scene, true));
                    rect.addEventHandler(MouseEvent.MOUSE_EXITED, new AreaHoverHandler(board, this, p, scene, false));
                    rect.addEventHandler(MouseEvent.MOUSE_CLICKED, new ClickCellHandler(board, this, p, controller, scene));
                } else{
                    rect.setStyle("-fx-stroke: rgba(255,255,255,0.1); -fx-stroke-width:5;");
                }
                rect.setWidth(btnw);
                rect.setHeight(btnh);
                grid.add(rect, j, i);
                addElement(String.format("cell%dx%dy", j, i), rect);
            }
        }
    }

    /**
     * Updates the score of this layer.
     */
    public void updateScore(){
        getElement("score", Label.class).setText(model.getScore() + "");
        getElement("clickScore", Label.class).setText(model.getCurrentScore() + "");
        getElement("cells", Label.class).setText(model.getBoard().countCells() + "");
    }

    private final void createLayer(){
        VBox vbox = new VBox();
        Label title = createTitle();
        HBox boardBox = createBoardBox();

        vbox.getChildren().addAll(title, boardBox);
        vbox.setAlignment(Pos.CENTER);

        updateTheme(theme);
        setRoot(vbox);
    }

    private final Label createTitle(){
        Label lbl = new Label("The Same Game");
        lbl.setFont(Font.font("Verdana", FontWeight.BOLD, 80));
        lbl.setTextFill(new LinearGradient(0, 0, 10, 80, false, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(1, theme.getSecondaryColor())));
        lbl.setEffect(new DropShadow(10.0, 10.0, 10., new Color(0., 0., 0., 0.3)));
        addElement("title", lbl);
        return lbl;
    }

    private final HBox createBoardBox(){
        HBox box = new HBox();
        GridPane board = createBoard();
        VBox boardScores = createBoardScores();
        VBox boardActions = createBoardActions(); 

        box.getChildren().addAll(boardActions, board, boardScores);
        box.setSpacing(20);
        box.setPadding(new Insets(30, 15, 30, 15));
        box.setAlignment(Pos.CENTER);
        box.setEffect(new DropShadow(10.0, 10.0, 10., new Color(0., 0., 0., 0.3)));
        try{
            box.setBorder(new Border(new BorderImage(new Image(new FileInputStream("ressources/mountains.jpg")), BorderStroke.THIN, null, BorderStroke.MEDIUM, false, null, null)));
        } catch(FileNotFoundException exc){
            box.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, BorderStroke.THIN))); 
        }

        addElement("boardBox", box);
        addElement("board", board);
        addElement("boardActions", boardActions);
        addElement("boardScores", boardScores);
        return box;
    }

    private final GridPane createBoard(){
        GridPane grid = new GridPane();
        Board board = model.getBoard(); 
        int bdw = Config.GRIDCELL_WIDTH.getValue() * board.getWidth(); // fx board width
        int bdh = Config.GRIDCELL_HEIGHT.getValue() * board.getHeight(); // fx board height

        // button size
        double btnw = bdw != Config.DEFAULT_FXGRID_WIDTH.getValue() ? Config.DEFAULT_FXGRID_WIDTH.getValue() / (board.getWidth() + 0.) : Config.GRIDCELL_WIDTH.getValue();
        double btnh = bdh != Config.DEFAULT_FXGRID_HEIGHT.getValue() ? Config.DEFAULT_FXGRID_HEIGHT.getValue() / (board.getHeight() + 0.) : Config.GRIDCELL_HEIGHT.getValue();


        for(int i = 0; i < board.getHeight(); i++){
            for(int j = 0; j < board.getWidth(); j++){
                GridCell cell = board.getCell(j, i);
                Rectangle rect = new Rectangle();
                Point p = new Point(j, i);
                if(cell.isFilled()){
                    rect.setStyle("-fx-stroke: rgba(255, 255, 255, 0.3); -fx-stroke-width:5;");
                    rect.setFill(cell.getColorFX().getColor());
                    rect.addEventHandler(MouseEvent.MOUSE_ENTERED, new AreaHoverHandler(board, this, p, scene, true));
                    rect.addEventHandler(MouseEvent.MOUSE_EXITED, new AreaHoverHandler(board, this, p, scene, false));
                    rect.addEventHandler(MouseEvent.MOUSE_CLICKED, new ClickCellHandler(board, this, p, controller, scene));
                }
                rect.setWidth(btnw);
                rect.setHeight(btnh);
                grid.add(rect, j, i);
                addElement(String.format("cell%dx%dy", j, i), rect);
            }
        }

        try{
            grid.setBorder(new Border(new BorderImage(new Image(new FileInputStream("ressources/multicolor.jpg")), BorderStroke.MEDIUM, null, BorderStroke.MEDIUM, false, null, null)));
        } catch(FileNotFoundException exc){
            grid.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderStroke.THIN)));
            grid.setPadding(new Insets(5));
        }

        return grid;
    }

    private final VBox createBoardScores(){
        VBox box = new VBox();
        Label scoreLabel = new Label("Score"), cScoreLabel = new Label("Click Score"), hScoreLabel = new Label("High Score");
        Label cellsLabel = new Label("Remaining cells");
        Label score = new Label(model.getScore() + ""), cScore = new Label(model.getCurrentScore() + ""), hScore = new Label(model.getHighScore() + "");
        Label cellsScore = new Label(model.getBoard().countCells() + "");

        Label[] labels = {scoreLabel, cScoreLabel, hScoreLabel, cellsLabel};
        Label[] scores = {score, cScore, hScore, cellsScore};
        for(Label label : labels){
            label.setFont(Font.font("Verdana", 20));
            label.setPadding(new Insets(2, 8, 2, 8));
        }

        for(Label label : labels){
            try{
                label.setBorder(new Border(new BorderImage(new Image(new FileInputStream("ressources/multicolor.jpg")), BorderStroke.MEDIUM, null, BorderStroke.MEDIUM, false, null, null)));
                label.setTextFill(new ImagePattern(new Image(new FileInputStream("ressources/multicolor.jpg"))));
            } catch(FileNotFoundException exc){
                label.setTextFill(Color.WHITE);
            }
        }

        for(Label label : scores){
            label.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
            label.setTextFill(Color.WHITE);
        }

        addElement("score", score);
        addElement("clickScore", cScore);
        addElement("highScore", hScore);
        addElement("cells", cellsScore);

        box.getChildren().addAll(scoreLabel, score, cScoreLabel, cScore, hScoreLabel, hScore, cellsLabel, cellsScore);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private final VBox createBoardActions(){
        VBox box = new VBox();
        Button btnUndo = new Button("Undo");
        Button btnRedo = new Button("Redo");
        Button btnStop = new Button("Abandon");

        try{
            btnUndo.setBorder(new Border(new BorderImage(new Image(new FileInputStream("ressources/multicolor.jpg")), BorderStroke.THIN, null, BorderStroke.MEDIUM, false, null, null)));
            btnRedo.setBorder(new Border(new BorderImage(new Image(new FileInputStream("ressources/multicolor.jpg")), BorderStroke.THIN, null, BorderStroke.MEDIUM, false, null, null)));
            btnStop.setBorder(new Border(new BorderImage(new Image(new FileInputStream("ressources/multicolor.jpg")), BorderStroke.THIN, null, BorderStroke.MEDIUM, false, null, null)));
        } catch(FileNotFoundException exc){
            btnUndo.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, BorderStroke.THIN)));
            btnRedo.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, BorderStroke.THIN)));
            btnStop.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, BorderStroke.THIN)));
        }

        btnUndo.setPadding(new Insets(10));
        btnRedo.setPadding(new Insets(10));
        btnStop.setPadding(new Insets(10));
        btnUndo.setTextFill(Color.WHITE);
        btnRedo.setTextFill(Color.WHITE);
        btnStop.setTextFill(Color.WHITE);

        btnUndo.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> controller.processAction(Action.UNDO));
        btnRedo.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> controller.processAction(Action.REDO));
        btnStop.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> controller.processAction(Action.STOP));

        addElement("undo", btnUndo);
        addElement("redo", btnRedo);
        addElement("stop", btnStop);

        box.getChildren().addAll(btnUndo, btnRedo, btnStop);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER_RIGHT);
        return box;
    }
}

