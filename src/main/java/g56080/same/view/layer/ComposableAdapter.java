package g56080.same.view.layer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Iterable adapter class of the composable interface using a string key to identify each one of its element. 
 *
 * @param <C> the type of the elements contained in this composable
 */
public abstract class ComposableAdapter<C> implements Composable<String, C>, Iterable<C>{
    
    protected final Map<String, C> map;
    protected final Map<String, C> buffer;
    
    /**
     * Constructs a new empty composable.
     */
    public ComposableAdapter(){
        map = new HashMap<>();
        buffer = new HashMap<>();
    }

    @Override
    public void addDroppedElement(){
        for(Map.Entry<String, C> entry : buffer.entrySet()){
            map.put(entry.getKey(), entry.getValue());
        }

        buffer.clear();
    }

    @Override
    public void addElement(String id, C elem){
        map.put(id, elem);
    }

    @Override
    public C getElement(String id){
        return map.get(id);
    }

    /**
     * Gets the element identified by the given string id and cast this element using 
     * the given target class.
     *
     * @param <T> the type of the element to be retrieved.
     * @param id the string id of the element
     * @param target the class of the element to be retrieved
     *
     * @return the element identified by the given string id properly casted into the given target class.
     */
    public <T> T getElement(String id, Class<T> target){
        return target.cast(map.get(id));
    }

    @Override
    public void dropElement(String id){
        C elem = map.remove(id);
        if(elem != null)
            buffer.put(id, elem);
    }

    @Override
    public void removeElement(String id){
        map.remove(id);
    }

    @Override
    public Iterator<C> iterator(){
        return new ComposableIterator();
    }

    private class ComposableIterator implements Iterator<C>{
        
        private int pointer;
        private final Object[] entries;

        public ComposableIterator(){
            entries = map.entrySet().toArray();
        }

        @Override
        public boolean hasNext(){
            return pointer < entries.length;
        }

        @Override
        @SuppressWarnings("unchecked")
        public C next(){
            if(!hasNext())
                throw new NoSuchElementException("No more elements");

            Map.Entry<String, C> current = (Map.Entry<String, C>) entries[pointer++];
            return current.getValue();
        }
    }
}
