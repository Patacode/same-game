package g56080.same.dp.observer;

/**
 * Observer interface implemented by the application views. A view is an object
 * able to interact with the user and to display informations based on the state of the
 * game model it uses.
 */
public interface Observer{

    /**
     * Updates this observer.
     */
    void update();
}
