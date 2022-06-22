package g56080.same.view;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import g56080.same.command.*;
import g56080.same.Config;
import g56080.same.command.ClickCommand;
import g56080.same.controller.Controller;
import g56080.same.dp.command.Command;
import g56080.same.model.Action;
import g56080.same.model.GameModel;
import g56080.same.model.GraphState.State;
import g56080.same.model.Level;
import g56080.same.model.Model;
import g56080.same.model.Point;
import g56080.same.util.Terminal;

/**
 * The console view used by the application to interact with the user.
 */
public class ConsoleView implements View{

    private final Controller controller;
    private final Model model;
    private boolean isExhausted, isValidStateMutation;
    private String boardShape;

    /**
     * Constructs a new console view using the given controller and model.
     *
     * @param controller the application controller used to notify the model that a certain
     * action occured.
     * @param model the application model used to retrieve informations about the game.
     */
    public ConsoleView(Controller controller, Model model){
        this.controller = controller;
        this.model = model;
        isValidStateMutation = true;
        boardShape = "board";

        model.addObserver(this);
    }

    @Override
    public void update(){
        State state = model.getState();
        switch(state){
            case NOT_STARTED:
                displayIntro();
                controller.init(askLevel(), askAdSize());
                break;
            case GAME_STARTED:
                controller.shuffle();
                break;
            case PLAYER_TURN:
                if(isValidStateMutation){
                    displayBoard(boardShape);
                    displayScore();
                    isValidStateMutation = false;
                }
                String input = askInput();
                processInput(input);
                break;
            case ERROR_CLICK:
                System.out.println("You cannot click here, no valid zone detected");
                controller.errorProcessed();
                break;
            case ERROR_CMD:
                System.out.println("Cannot undo");
                controller.errorProcessed();
                break;
            case ERROR_NO_CLICK:
                System.out.println("You haven't clicked yet");
                controller.errorProcessed();
                break;
            case GAME_TURN:
                isValidStateMutation = true;
                controller.updateGrid();
                break;
            case GAME_CHECK:
                controller.isFinish();
                break;
            case GAME_OVER:
                displayBoard(boardShape);
                displayScore();
                setExhaustion(true);
                if(model.isSuccess())
                    System.out.println("Congrats ! You emptied the board :p");
                displayEnd();
        }
    }

    @Override
    public boolean isExhausted(){
        return isExhausted;
    }

    /**
     * Displays the introduction message.
     */
    public void displayIntro(){
        display("intro", null);
    }
    
    /**
     * Displays the board usinf the given shape and clears the terminal if it is possible.
     *
     * @param shape the board shape to display
     */
    public void displayBoard(String shape){
        if(!System.getProperty("os.name").toLowerCase().contains("windows") && Terminal.canExec("clear"))
            System.out.println(Terminal.exec("clear"));

        display(shape, new Class<?>[]{Model.class}, model);
    }

    /**
     * Displays the ending message.
     */
    public void displayEnd(){
        display("end", null);
    }

    /**
     * Displays the score of the game.
     */
    public void displayScore(){
        display("score", new Class<?>[]{Model.class}, model);
    }

    /**
     * Checks if the given name is a valid board shape.
     *
     * @param name the board shape name to check
     *
     * @return true if the given board shape name is valid, false otherwise.
     */
    public boolean isValidBoardShape(String name){
        Class<?>[] nestedClasses = Component.class.getDeclaredClasses();
        for(Class<?> cls : nestedClasses){
            if(hasAnnotation(cls, Displayable.class) && cls.getSimpleName().equalsIgnoreCase(name)){
                return true;
            }
        }

        return false;
    }

    /**
     * Displays the available actions that can be performed on this view.
     */
    public void displayActions(){
        Action[] actions = Action.values();
        for(Action action : actions){
            System.out.printf("\t%s - %s\n", action.toString(), action.getDescription());
        }
    }
    
    /**
     * Displays the available board shapes.
     */
    public void displayBoardShapes(){
        Class<?>[] nestedClasses = Component.class.getDeclaredClasses();
        try{
            for(Class<?> cls : nestedClasses){
                if(hasConstructor(cls, Model.class) && hasAnnotation(cls, Displayable.class)){
                    System.out.print(cls.getSimpleName());
                    System.out.println(" - " + cls.getMethod("getDescription").invoke(cls.getDeclaredConstructor(Model.class).newInstance(model)));
                }
            }
        } catch(NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException exc){
            System.out.println("An error occurs");
        }
    }

    /**
     * Sets the exhaustion state of this view.
     *
     * @param state the exhaustion state to assign to this view
     */
    public void setExhaustion(boolean state){
        isExhausted = state;
    }

    /**
     * Sets the board shape of this view using the given name.
     *
     * @param name the board shape name
     */
    public void setBoardShape(String name){
        boardShape = name;
    }








    // // // // // //
    private final String askInput(){
        String input = null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a command:\n-> ");
        while(scanner.hasNextLine()){
            input = scanner.nextLine();
            if(isValidAction(input)) break;
            else{ 
                System.out.println("Invalid action. Try the 'help' command to see available actions !");
                System.out.print("Enter a command:\n-> ");
            }
        }

        return input;
    }

    private final void processInput(String input){
        input = input.strip();

        Action action = getAction(input);
        if(isValidActionPattern(action, input)){
            Matcher matcher = action.getPattern().matcher(input); matcher.matches();
            Command command = null;
            switch(action){
                case CLICK:
                    int x = Integer.valueOf(matcher.group("x")) - 1;
                    int y = Integer.valueOf(matcher.group("y")) - 1;
                    controller.click(new Point(x, y));
                    break;
                case HELP:
                    command = new HelpCommand(this);
                    break;
                case REDO:
                case UNDO:
                    controller.processAction(action);
                    break;
                case DECORATE:
                    String name = matcher.group("name");
                    command = new DecorateCommand(this, name);
                    break;
                case SHAPES:
                    command = new ShapesCommand(this);
                    break;
                case STOP:
                    command = new StopCommand(this);

            }

            if(command != null)
                command.execute();
        } else System.out.println("Invalid action pattern. Please retry !");

    }

    private final boolean hasConstructor(Class<?> cls, Class<?>... args){
        try{
            cls.getConstructor(args);
        } catch(NoSuchMethodException exc){
            return false;
        }

        return true;
    }

    private final <T extends Annotation> boolean hasAnnotation(AnnotatedElement target, Class<T> annotationClass){
        return target.getAnnotation(annotationClass) != null;
    }

    private int askLevel(){
        return askInteger("Enter a level", "Invalid level. Please retry !", ConsoleView::isValidLevel);
    }

    private int askAdSize(){
        return askInteger("Additional board size (can be negative) ?", "Invalid additional board size. Please retry !", ConsoleView::isValidAdSize);
    }

    private boolean askReplay(){
        return askBoolean("Do you want to replay ? (Y/N)", "Invalid choice. Please retry !");
    }

    private static boolean isValidLevel(int lvl){
        try{
            Level level = new Level(lvl);
        } catch(IllegalArgumentException exc){
            return false;
        }
        return true;
    }

    private static boolean isValidAdSize(int adSize){
        return adSize >= -Config.MIN_ADSIZE.getValue() && adSize <= Config.MAX_ADSIZE.getValue();
    }

    private int askInteger(String msg, String errMsg, Predicate<Integer> predicate){
        Scanner scanner = new Scanner(System.in);
        int value = 0;
        
        System.out.println(msg);
        System.out.print("-> ");
        while(scanner.hasNext()){
            if(scanner.hasNextInt()){
                value = scanner.nextInt();
                if(predicate.test(value)) break;
                else{
                    System.out.println(errMsg);
                    System.out.print("-> ");
                }
            } else{
                System.out.println(errMsg);
                System.out.print("-> ");
                scanner.next();
            }
        }

        return value;
    }

    private boolean askBoolean(String msg, String errMsg){
        Scanner scanner = new Scanner(System.in);
        String value = null;

        System.out.println(msg);
        System.out.print("-> ");
        while(scanner.hasNextLine()){
            value = scanner.nextLine();
            if(scanner.hasNext("(?)y|n")){ 
                value = scanner.next();
                break;
            } else{
                System.out.println(errMsg);
                System.out.print("-> ");
            }
        }

        return Character.toLowerCase(value.charAt(0)) == 'y';
    }


    private final boolean isValidAction(String input){
        try{
            String cmd = input.split(" ")[0];
            Action.valueOf(cmd.toUpperCase());
        } catch(IllegalArgumentException exc){
            return false;
        }

        return true;
    }

    private final boolean isValidActionPattern(Action action, String input){
        return action.getPattern().matcher(input).matches();
    }

    private final Action getAction(String input){
        return Action.valueOf(input.split(" ")[0].toUpperCase());
    }
}

