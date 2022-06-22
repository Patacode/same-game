package g56080.same.view.layer;

/**
 * An object able to be composed of elements identified by
 * a given id key. A composable can be altered by adding or
 * removing elements from it and is updated by calling the
 * compose method.
 *
 * @param <K> the key type
 * @param <C> the component type
 * */
public interface Composable<K, C>{

    /**
     * Updates this composable starting from the element identified by the given key.
     *
     * @param root the starting node
     */
    void compose(K root);

    /**
     * Adds to this composable all the elements previously dropped.
     */
    void addDroppedElement();

    /**
     * Adds the given elements identified by the given key to this composable. If the given
     * key already exists, the element pointed by this key is updated.
     *
     * @param key the element's id
     * @param elem the element to add
     */
    void addElement(K key, C elem);

    /**
     * Gets the element identified by the given key.
     *
     * @param key the element's id
     *
     * @return the element identified by the given key or null if no element is associated to this key.
     */
    C getElement(K key);

    /**
     * Drops the element identified by the given key from this composable and
     * saves it if it exists. Dropping an element doesn't remove it permanently from this composable,
     * but it can be retrieved through the {@link g56080.same.view.layer.Composable#addDroppedElement()}
     * method.
     *
     * @param key the element's id
     */
    void dropElement(K key);

    /**
     * Removes the element identified by the given key from this composable permanently if it
     * exists.
     *
     * @param key the element's id
     */
    void removeElement(K key);
}

