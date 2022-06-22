package g56080.same.view;

/**
* Defines several colors that make use of the ANSI standard.
*
* <p>These colors can be directly used in front of and behind a given string
* of text (by calling the <code>get()</code> method associated with it) to color it or 
* several other methods allow you to directly color a whole given string without 
* having to surround it with the adequate colors.</p>
*
* <p>If for any reason, the ANSI colors want to be used directly by refering to their
* enum litteral or by using the extended set of colors, the RESET field must always be
* put behind the string to stop the colorization.</p>
*
* @author Maximilien Ballesteros
*/
public enum AnsiColor{

	/**
	* The reset color used to stop the colorization.
	*/
	RESET(0),	

	// basic color
	/**
	* The black color.
	*/
	BLACK(30),

	/**
	* The red color.
	*/
	RED(31),

	/**
	* The green color.
	*/
	GREEN(32),

	/**
	* The yellow color.
	*/
	YELLOW(33),

	/**
	* The blue color.
	*/
	BLUE(34),

	/**
	* The magenta color.
	*/
	MAGENTA(35),

	/**
	* the cyan color.
	*/
	CYAN(36),

	/**
	* The white color.
	*/
	WHITE(37),

	// basic bg
	/**
	* The black background.
	*/
	BLACK_BACKGROUND(40),

	/**
	* The red background.
	*/
	RED_BACKGROUND(41),

	/**
	* The green background.
	*/
	GREEN_BACKGROUND(42),
	
	/**
	* The yellow background.
	*/
	YELLOW_BACKGROUND(43),

	/**
	* The blue background.
	*/
	BLUE_BACKGROUND(44),

	/**
	* The magenta background.
	*/
	MAGENTA_BACKGROUND(45),

	/**
	* The cyan background.
	*/
	CYAN_BACKGROUND(46),

	/**
	* The white background.
	*/
	WHITE_BACKGROUND(47),
	
	// basic deco
	/**
	* The bold text decoration.
	*/
	BOLD(1),

	/**
	* The underline text decoration.
	*/
	UNDERLINE(4),

	/**
	* The reversed text decoration, inversing the currently used color.
	*/
	REVERSED(7);

    private final int codePoint;
	private final String ansiColor;
	private final static String ansiEscapeSequence = "\u001b[";
	
	private AnsiColor(int codePoint){
		ansiColor = ansiEscapeSequence + codePoint;
        this.codePoint = codePoint;
	}
	
	/**
	* Returns the ANSI color sequence associated with this enum litteral as a string.
	*
	* <p>Be default, this method will returns the non-bold version of
	* the color. If the bold version want to be obtained, the {@link #get(boolean)} can
	* be called instead.</p>
	*
	* @return the ANSI color sequence associated with this enum litteral.
	*/
	public String get(){
		return get(false);
	}

	/**
	* Returns the ANSI color sequence associated with this enum litteral as a string.
	*
	* <p>Using this method, you can specify wether the resulting color sequence will represent
	* the bold version of the color or not by setting the <code>bold</code> flag to true or false
	* respectively.</p>
	*
	* @param bold the bold flag
	*
	* @return the ANSI color sequence associated with this enum litteral.
	*/
	public String get(boolean bold){
		switch(this){
			case BOLD:
			case UNDERLINE:
			case REVERSED:
			case RESET: return ansiColor + "m";
		}

		return ansiColor + (bold ? ";1m" : "m");
	}

    /**
     * Returns the AnsiColor litteral that match the given code point. A value of <strong>null</strong>
     * will be returned if no litteral matched the given code point.
     *
     * @param codePoint the integer code point
     *
     * @return the AnsiColor litteral that match the given code point or null if no litteral was found.
     */
    public static AnsiColor valueOf(int codePoint){
        AnsiColor[] colors = values();
        for(AnsiColor color : colors){
            if(color.codePoint == codePoint){
                return color;
            }
        }

        return null;
    }
	
	/**
	* Returns the ANSI color sequence from the extended set of 256 colors based on a given code point.
	*
	* <p>The code point must be included between 0 and 255, otherwise an <code>IllegalArgumentException</code> will be thrown.</p>
	*
	* @param codepoint the code point associated with the desired color
	*
	* @throws IllegalArgumentException if the given code point value is not included between 0 and 255.
	*
	* @return the ANSI color sequence associated with the given code point.
	*/
	public static String getExtendedColor(int codepoint){
		return getExtendedColor(codepoint, false);
	}
	
	/**
	* Returns the ANSI color sequence from the extended set of 256 colors based on a given code point.
	*
	* <p>Using this version you can also specify wether the resulting color will be considered as a background or not by setting
	* the <code>background</code> flag to true or false respectively.</p>
	*
	* <p>Note that the code point must be included between 0 and 255, otherwise an <code>IllegalArgumentException</code> will be thrown.</p>
	*
	* @param codepoint the code point associated with the desired color
	* @param background the background flag.
	*
	* @throws IllegalArgumentException if the given code point value is not included between 0 and 255.
	*
	* @return the ANSI color sequence associated with the given code point.
	*/
	public static String getExtendedColor(int codepoint, boolean background){
		if(codepoint < 0 || codepoint > 255)
			throw new IllegalArgumentException(String.format("Code point : %d  is not valid. Valid code points are located in the inclusive interval [1..255].", codepoint));

		int nature = background ? 48 : 38;
		return String.format(ansiEscapeSequence + "%1$d;5;%2$dm", nature, codepoint);
	}

	/**
	* Colors a given string using the extended set of 256 colors.
	*
	* <p>Note that the code point must be included between 0 and 255, otherwise an <code>IllegalArgumentException</code> will be thrown.</p>
	*
	* @param text the string text to color
	* @param codepoint the code point associated with the desired color
	*
	* @throws IllegalArgumentException if the given code point value is not included between 0 and 255.
	*
	* @return the given string of text colored.
	*/
	public static String colorText(String text, int codepoint){
		return colorText(text, codepoint, false);
	}

	/**
	* Colors a given string using the extended set of 256 colors.
	*
	* <p>Using this version you can also specify wether the resulting color will be considered as a background or not by setting
	* the <code>background</code> flag to true or false respectively.</p>
	*
	* <p>Note that the code point must be included between 0 and 255, otherwise an <code>IllegalArgumentException</code> will be thrown.</p>
	*
	* @param text the string text to color
	* @param codepoint the code point associated with the desired color
	* @param background the background flag.
	*
	* @throws IllegalArgumentException if the given code point value is not included between 0 and 255.
	*
	* @return the given string of text colored.
	*/
	public static String colorText(String text, int codepoint, boolean background){
		return getExtendedColor(codepoint, background) + text + AnsiColor.RESET.get();
	}

	/**
	* Colors a given string using the provided ANSI colors.
	*
	* @param text the string text to color
	* @param color the ANSI color with which to color the given text
	*
	* @return the given string of text colored.
	*/
	public static String colorText(String text, AnsiColor color){
		return colorText(text, color, false);
	}

	/**
	* Colors a given string using the provided ANSI colors.
	*
	* <p>Using this version you can also specify wether the resulting color will be considered as a bold or not by
	* setting the <code>bold</code> flag to true or false respectively.</p> 
	*
	* @param text the string text to color
	* @param color the ANSI color with which to color the given text
	* @param bold the bold flag.
	*
	* @return the given string of text colored.
	*/
	public static String colorText(String text, AnsiColor color, boolean bold){
		return color.get(bold) + text + AnsiColor.RESET.get();
	}
}
