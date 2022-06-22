package g56080.same.view;

import java.util.Arrays;

import g56080.same.Config;
import g56080.same.model.Model;
import g56080.same.util.Util;

import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The menu bar used by the application using a GUI.
 */
public class FXMenu extends MenuBar{
    
    private final FXView view;

    /**
     * Constructs a new menu bar using the given view. The view is needed in order
     * to update certain of its configurations as a result of the interaction with 
     * the fields present in this menu bar.
     *
     * @param view the view to be used by the menu bar
     */
    public FXMenu(FXView view){
        this.view = view;

        fillinMenu();
    }

    private final void fillinMenu(){
        Menu themeMenu = createThemeMenu();
        getMenus().add(themeMenu);
    }

    private final Menu createThemeMenu(){
        Menu themeMenu = new Menu("Theme");
        Theme[] themes = Theme.values();
        Arrays.stream(Theme.values()).forEach(theme -> {
            MenuItem item = new MenuItem(theme.toString());
            item.setOnAction(e -> view.setTheme(theme));
            themeMenu.getItems().add(item);

        });

        return themeMenu;
    }
}

