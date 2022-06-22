package g56080.same.util;

import g56080.same.model.Point;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Util class wrapping useful static methods.
 */
public class Util{
    
    /**
     * Swap two value at the given start and end point in the given 2D array.
     *
     * @param <T> the array type
     * @param arr the 2D array
     * @param start the starting swap point
     * @param end the ending swap point
     */
    public static <T> void swap(T[][] arr, Point start, Point end){
        T tmp = arr[start.getY()][start.getX()];
        arr[start.getY()][start.getX()] = arr[end.getY()][end.getX()];
        arr[end.getY()][end.getX()] = tmp;
    }

    /**
     * Generates a random number between the given start (inclusive) and end (exclusive).
     *
     * @param start the inclusive start
     * @param end the exclusive end
     *
     * @return a random number between the given bounds.
     */
    public static int genRandom(int start, int end){
        return (int) (Math.random() * (end - start) + start);
    }

    /**
     * Gets the number of digits present in the given number.
     *
     * @param nb the integer number
     *
     * @return the number of digits present in the given number.
     */
    public static int digits(int nb){
        int acc = 1, digits = 0;
        while(nb > 0){
            nb /= acc;
            acc *= 10;
            digits++;
        }

        return digits;
    }

    /**
     * Copies the given 2D array deeply and returns a new reference to it. It the type of the elements
     * of the 2D array are not immutable, please refer to the second version of this method.
     *
     * @param <T> the type of the element's array
     * @param original the original 2D array to copy
     *
     * @return a new reference of the given 2D array. 
     */
    @SuppressWarnings("unchecked")
    public static <T> T[][] copyOf2D(T[][] original){
        T[][] newArr = (T[][]) Array.newInstance(original[0][0].getClass(), original.length, original[0].length);
        for(int i = 0; i < newArr.length; i++){
            for(int j = 0; j < newArr[i].length; j++){
                newArr[i][j] = original[i][j];
            }
        }

        return newArr;
    }

    /**
     * Copies the given 2D array deeply as well as its elements and returns a new reference to it. To work
     * properly, the elements of the array have to dispose of a constructor taking an object of the same class.
     *
     * @param <T> the type of the element's array
     * @param original the original 2D array to copy
     * @param targetClass the class of the array's element
     *
     * @return a new reference of the given 2D array or null if an error occurs while copying the array.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[][] copyOf2D(T[][] original, Class<?> targetClass){
        T[][] newArr = (T[][]) Array.newInstance(original[0][0].getClass(), original.length, original[0].length);
        try{
            Constructor<?> constructor = targetClass.getConstructor(targetClass);
            for(int i = 0; i < newArr.length; i++){
                for(int j = 0; j < newArr[i].length; j++){
                    newArr[i][j] = (T) constructor.newInstance(original[i][j]);
                }
            }
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exc){
            return null;
        }

        return newArr;
    }

    /**
     * Checks if the given 2D arrays are equals. This algorithm assumes that both arrays are squared.
     *
     * @param <T> the type of the element's array
     * @param expected the expected 2D array
     * @param source the source 2D array
     *
     * @return true if both array are equals, false otherwise.
     */
    public static <T> boolean array2DEquals(T[][] expected, T[][] source){
        if(expected.length != source.length || expected[0].length != source[0].length)
            return false;

        for(int i = 0; i < expected.length; i++){
            for(int j = 0; j < expected[i].length; j++){
                if(!expected[i][j].equals(source[i][j]))
                    return false;
            }
        }

        return true;
    }
}

