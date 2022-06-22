package g56080.same.view.layer;

import g56080.same.view.layer.Layer.LinkedLayer;

import javafx.scene.layout.StackPane;

/**
 * Concrete implementation of the composable pane using a stack pane as root and linked layer elements.
 */
public class StackLayer extends ComposablePane<StackPane, LinkedLayer>{
    
    /**
     * Constructs a new empty stack layer.
     */
    public StackLayer(){
        super(new StackPane());
    }
    
    @Override
    public void compose(String start){
        LinkedLayer current = map.get(start);
        if(current != null)
            root.getChildren().clear();

        while(current != null){
            root.getChildren().add(current.getLayer().getRoot());
            current = map.get(current.getNext());
        }
    }
}

