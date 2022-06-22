package g56080.same.view.layer;

import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import g56080.same.view.Theme;
import g56080.same.view.Themable;

/**
 * Themable responsive rectangle layer.
 */
public class RectangleLayer extends Layer implements Themable{
    
    private final Color color;
    private final Scene scene;
    private final Insets padding;

    /**
     * Creates a new rectangle layer using the given color, scene and padding.
     *
     * @param color the color this layer
     * @param scene the application scene to be used
     * @param padding the padding around this layer
     */
    public RectangleLayer(Color color, Scene scene, Insets padding){
        this.color = color;
        this.scene = scene;
        this.padding = padding;

        createLayer();
    }

    @Override
    public void updateTheme(Theme theme){
        getElement("rectangle", Rectangle.class).setFill(theme.getPrimaryColor());
    }

    private final void createLayer(){
        Rectangle rect = new Rectangle(scene.getWidth(), scene.getHeight());
        rect.setFill(color);

        scene.widthProperty().addListener((obs, o, n) -> rect.setWidth(n.doubleValue() - padding.getLeft() - padding.getRight()));
        scene.heightProperty().addListener((obs, o, n) -> rect.setHeight(n.doubleValue() - padding.getTop() - padding.getBottom()));

        addElement("rectangle", rect);
        compose("rectangle");
    }
}
