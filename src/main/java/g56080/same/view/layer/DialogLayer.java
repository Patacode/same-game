package g56080.same.view.layer;

import javafx.geometry.Insets;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Layer containing a string message. 
 */
public class DialogLayer extends Layer{
    
    private final String msg;
    private final Scene scene;

    /**
     * Constructs a new dialog layer using the given message and scene.
     *
     * @param msg the string message to display
     * @param scene the application scene to be used
     */
    public DialogLayer(String msg, Scene scene){
        this.msg = msg;
        this.scene = scene;

        fillinLayer();
    }

    private final void fillinLayer(){
        BorderPane txtPane = new BorderPane();
        Label txt = new Label(msg);

        txt.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        txt.setTextFill(Color.RED);
        txt.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 0.6), null, null)));
        txt.setPadding(new Insets(5, 8, 5, 8));
        txtPane.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0.3), null, null)));
        txtPane.setCenter(txt);
        txtPane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> scene.setCursor(Cursor.HAND));

        setRoot(txtPane);
    }
}
