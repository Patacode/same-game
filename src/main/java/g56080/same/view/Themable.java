package g56080.same.view;

/**
 * Themable interface implemented by layers supporting the theme system.
 */
@FunctionalInterface
public interface Themable{
    
    /**
     * Updates the themable by change the colors of its node using the given theme.
     *
     * @param theme the new theme to be used
     */
    void updateTheme(Theme theme);
}
