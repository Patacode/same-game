package g56080.same.dp.observer;

/**
 * Observable interface implemented by application models. An observable
 * is a mutable object designed to update its recorded observers.
 */
public interface Observable{

    /**
     * Adds an observer dedicated to observe this object for change.
     *
     * @param obs the observer to add
     */
    void addObserver(Observer obs);

    /**
     * Removes the given observer from the list of added observers if it exists.
     *
     * @param obs the observer to remove
     */
    void removeObserver(Observer obs);

    /**
     * Notifies the added observers by invoking the {@link g56080.same.dp.observer.Observer#update()} 
     * on them.
     */
    void notifyObservers();
}
