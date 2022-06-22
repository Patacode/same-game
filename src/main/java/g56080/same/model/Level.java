package g56080.same.model;

import g56080.same.Config;

/**
 * A game level.
 */
public class Level{
    
    private int level;

    /**
     * Constructs a new level initialized with minimum allowed level.
     *
     * @see g56080.same.Config#MIN_LEVEL
     */
    public Level(){
        level = Config.MIN_LEVEL.getValue();;
    }

    /**
     * Constructs a new level using the given integer level.
     *
     * @param level the integer level to be used
     *
     * @throws IllegalArgumentException if the given integer level is not valid. To be valid
     * the given integer level must be included in the domain defined by the {@link g56080.same.Config#MIN_LEVEL}
     * and the {@link g56080.same.Config#MAX_LEVEL} litterals.
     */
    public Level(int level){
        if(!isValidLevel(level))
            throw new IllegalArgumentException("Invalid level");

        this.level = level;
    }

    /**
     * Constructs a copy of the given level.
     *
     * @param lvl the level to copy
     */
    public Level(Level lvl){
        level = lvl.level;
    }

    /**
     * Sets the level of this object.
     *
     * @param level the integer level to assign
     *
     * @throws IllegalArgumentException if the given integer level is not valid. To be valid
     * the given integer level must be included in the domain defined by the {@link g56080.same.Config#MIN_LEVEL}
     * and the {@link g56080.same.Config#MAX_LEVEL} litterals.
     */
    public void setLevel(int level){
        if(!isValidLevel(level))
            throw new IllegalArgumentException("Invalid level");

        this.level = level;
    }

    /**
     * Gets the integer level associated to this object.
     *
     * @return the intege level of this object.
     */
    public int getLevel(){
        return level;
    }

    /**
     * Gets the number of different colors used by the game depending
     * on the integer level of this object.
     *
     * @return the number of different colors used by the game.
     */
    public int getNumberColors(){
        return Config.DEFAULT_NUMBER_COLORS.getValue() + level - 1;
    }

    /**
     * Upgrades the integer level of this object by incrementing it by one.
     *
     * @throws IllegalStateException if the current integer cannot be upgraded.
     */
    public void upgrade(){
        if(level >= Config.MAX_LEVEL.getValue())
            throw new IllegalStateException("Max level reached");

        level++;
    }

    /**
     * Downgrades the integer level of this object by decrementing it by one.
     *
     * @throws IllegalStateException if the current integer cannot be downgraded.
     */
    public void downgrade(){
        if(level <= Config.MIN_LEVEL.getValue())
            throw new IllegalStateException("Min level reached");

        level--;
    }

    private boolean isValidLevel(int level){
        return level >= Config.MIN_LEVEL.getValue() && level <= Config.MAX_LEVEL.getValue();
    }

    @Override
    public String toString(){
        return level + "";
    }
}

