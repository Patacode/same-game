package g56080.same.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import g56080.same.dp.observer.Observer;

import javafx.stage.Stage;

/**
 * Observer view interface implemented by object dealing with the user interaction.
 */
public interface View extends Observer{
    
    /**
     * Displays the given component using the class arguments and object dependencies related
     * to this component.
     *
     * @param componentName the name of the component to display
     * @param args classes of the parameters needed by the constructor of the component 
     * @param deps instances of the parameters needed by the constructor of the component
     *
     * @return true if the component has been successfully displayed, false otherwise.
     */
    default boolean display(String componentName, Class<?>[] args, Object... deps){
        try{
            Class<?>[] nestedClasses = Component.class.getDeclaredClasses();
            for(Class<?> cls : nestedClasses){
                if(cls.getSimpleName().equalsIgnoreCase(componentName)){
                    Method display = cls.getMethod("display");
                    display.invoke(cls.getDeclaredConstructor(args).newInstance(deps));
                    return true;
                }
            }

        } catch(InstantiationException | InvocationTargetException | IllegalAccessException exc){
            System.out.println(exc.getMessage());
        } catch(NoSuchMethodException exc){
            System.out.println("Impossible to find an adequate constructor to display the component !");
        }

        return false;
    }

    /**
     * Checks it this view is exhausted.
     *
     * @return true if this view is exhausted, false otherwise.
     */
    default boolean isExhausted(){
        throw new UnsupportedOperationException("Invalid operation");
    }

    /**
     * Starts this view using the given JavaFX stage container.
     *
     * @param stage the JavaFX stage container to be used
     */
    default void start(Stage stage){
        throw new UnsupportedOperationException("Invalid operation");
    }
}

