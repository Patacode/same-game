package g56080.same.controller;

import g56080.same.model.Model;
import g56080.same.view.ConsoleView;

/**
 * Application controller for the console.
 */
public class ControllerConsole extends Controller{
    
    /**
     * Constructs a new controller for the console using the given model.
     *
     * @param model the application model
     */
    public ControllerConsole(Model model){
        this.model = model;
        this.view = new ConsoleView(this, model);
    } 

    @Override
    public void start(){
        while(!view.isExhausted()){
            view.update();
        }
    }
}

