package g56080.same.util;

/**
 * Runtime exception used to express a potentially unsafe use of a varargs argument.
 **/
public class UnsafeVarargsException extends RuntimeException{
    
    private final static String DEFAULT_MSG = "Unsafe varargs exception";

    /**
     * Creates a default UnsafeVarargsException using a default error message.
     **/
    public UnsafeVarargsException(){
        super(DEFAULT_MSG);
    }

    /**
     * Creates an UnsafeVarargsException using the given description.
     * @param desc the description of this exception
     **/
    public UnsafeVarargsException(String desc){
        super(desc);
    }

    /**
     * Creates an UnsafeVarargsException using the given description and cause.
     * @param desc the description of this exception
     * @param cause the cause of this exception
     **/
    public UnsafeVarargsException(String desc, Throwable cause){
        super(desc, cause);
    }

    /**
     * Creates an UnsafeVarargsException using the given cause. The default error message will be used.
     * @param cause the cause of this exception
     **/
    public UnsafeVarargsException(Throwable cause){
        super(DEFAULT_MSG, cause);
    }
}
