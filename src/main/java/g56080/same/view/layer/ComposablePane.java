package g56080.same.view.layer;

import javafx.scene.layout.Pane;

/**
 * Composable adapter using a root pane in addition to the elements it contains. 
 *
 * @param <P> the type of the root used by this composable
 * @param <C> the type of the elements contained in this composable
 */
public abstract class ComposablePane<P extends Pane, C> extends ComposableAdapter<C>{
    
    protected P root;
    
    /**
     * Constructs a new composable pane using the given element as root.
     *
     * @param root the root of this composable
     */
    public ComposablePane(P root){
        this.root = root;
    }

    /**
     * Gets the root of this composable.
     *
     * @return the root of this composable.
     */
    public P getRoot(){
        return root;
    }

    /**
     * Sets the root of this composable.
     *
     * @param root the new pane root to assign to this composable
     */
    public void setRoot(P root){
        this.root = root;
    }
}

