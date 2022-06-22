package g56080.same.util;

/**
 * Mutable object wrapper.
 *
 * @param <T> the type of the wrapped object
 */
public class Recorder<T>{
    
    private T value;

    /**
     * Constructs a new recorder. The wrapped object will be
     * initialized to null.
     */
    public Recorder(){}

    /**
     * Constructs a new recorder using the given value.
     *
     * @param value the wrapped value
     */
    public Recorder(T value){
        this.value = value;
    }

    /**
     * Gets the object wrapped in this recorder.
     *
     * @return the object wrapped in this recorder.
     */
    public T getValue(){
        return value;
    }

    /**
     * Sets the value of the wrapped object using the given value.
     *
     * @param value the new value to assign to the wrapped object
     */
    public void record(T value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
