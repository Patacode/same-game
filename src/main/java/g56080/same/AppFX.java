package g56080.same;

import g56080.same.controller.Controller;
import g56080.same.controller.ControllerFX;
import g56080.same.model.GameModel;
import g56080.same.model.Model;

import javafx.application.Application;

import javafx.stage.Stage;

/**
 * Main class used to run the application using a GUI.
 */
public class AppFX extends Application{
    
    /**
     * Main entry-point launching the application.
     *
     * @param args the command-line arguments (not used here)
     */
    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage mainStage){
        Model model = new GameModel();
        Controller controller = new ControllerFX(model, mainStage);

        controller.start();
    }

    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }
}
