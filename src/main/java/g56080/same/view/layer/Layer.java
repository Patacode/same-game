package g56080.same.view.layer;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Concrete implementation of the composable pane using a pane root and node elements.
 */
public class Layer extends ComposablePane<Pane, Node>{

    /**
     * Layer linked to an element identified by its string key. 
     */
    public static class LinkedLayer{
        
        private Layer layer;
        private String next;

        /**
         * Creates a new linked layer using the given layer and string id for its following element.
         *
         * @param layer the layer to be linked
         * @param next the string id of the following layer (null if no layer follow this one).
         */
        public LinkedLayer(Layer layer, String next){
            this.layer = layer;
            this.next = next;
        }

        /**
         * Gets the layer of this linked layer.
         *
         * @return the layer of this linked layer.
         */
        public Layer getLayer(){
            return layer;
        }

        /**
         * Gets the string id of the element following the layer contained in this linked layer.
         *
         * @return the string id of the next element.
         */
        public String getNext(){
            return next;
        }

        /**
         * Sets the layer of this linked layer.
         *
         * @throws NullPointerException if the given layer is null.
         *
         * @param layer the new layer to assign to this linked layer
         */
        public void setLayer(Layer layer){
            if(layer == null)
                throw new NullPointerException("Null layer is not valid");

            this.layer = layer;
        }

        /**
         * Sets the string id of the next element.
         *
         * @param next the string id of the next element
         */
        public void setNext(String next){
            this.next = next;
        }
    }

    /**
     * Constructs a new empty layer.
     */
    public Layer(){
        super(new Pane());
    }
    
    @Override
    public void compose(String start){
        Node node = map.get(start);

        if(node != null){
            root.getChildren().clear();
            root.getChildren().add(node);
        }
    }
}

