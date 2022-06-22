package g56080.same.controller;

import g56080.same.model.Model;
import g56080.same.view.FXView;
import g56080.same.view.Theme;

import javafx.stage.Stage;

/**
 * Application controller for the GUI.
 */
public class ControllerFX extends Controller{

    private final Stage stage;
    
    /**
     * Constructs a new controller for the GUI using the given model and stage for the view.
     *
     * @param model the application model
     * @param stage the JavaFX stage container used by the view
     */
    public ControllerFX(Model model, Stage stage){
        this.model = model;
        view = new FXView(this, model);
        this.stage = stage;
    }

    @Override
    public void start(){
        view.start(stage);
    }
}
