package g56080.same;

import g56080.same.controller.Controller;
import g56080.same.controller.ControllerConsole;
import g56080.same.model.GameModel;
import g56080.same.model.Model;

/**
 * Main class used to run the console application.
 */
public class AppConsole{
    
    /**
     * Main entry-point starting the console application.
     *
     * @param args the command-line arguments (not used here)
     */
    public static void main(String[] args){
        Model model = new GameModel();
        Controller controller = new ControllerConsole(model);

        controller.start();
    }
}

