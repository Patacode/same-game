package g56080.same.model;

import g56080.same.Config;

import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest{
    
    @Test
    public void levelConstructorTest_default(){
        Level level = new Level();
        assertEquals(Config.MIN_LEVEL.getValue(), level.getLevel());
    }

    @Test
    public void levelConstructorTest_customizable(){
        for(int i = Config.MIN_LEVEL.getValue(); i <= Config.MAX_LEVEL.getValue(); i++){
            Level level = new Level(i);
            assertEquals(i, level.getLevel());
        }
    }

    @Test
    public void levelConstructorTest_invalidLevel(){
        assertThrows(IllegalArgumentException.class, () -> new Level(Config.MIN_LEVEL.getValue() - 1));
        assertThrows(IllegalArgumentException.class, () -> new Level(Config.MAX_LEVEL.getValue() + 1));
    }

    @Test
    public void levelSetterTest_validLevel(){
        Level level = new Level();
        for(int i = Config.MIN_LEVEL.getValue(); i <= Config.MAX_LEVEL.getValue(); i++){
            level.setLevel(i);
            assertEquals(i, level.getLevel());
        }
    }

    @Test
    public void levelSetterTest_invalidLevel(){
        Level level = new Level();
        assertThrows(IllegalArgumentException.class, () -> level.setLevel(Config.MIN_LEVEL.getValue() - 1));
        assertThrows(IllegalArgumentException.class, () -> level.setLevel(Config.MAX_LEVEL.getValue() + 1));
    }

    @Test
    public void levelNumberColorsTest(){
        Level level = new Level();
        for(int i = Config.MIN_LEVEL.getValue(); i <= Config.MAX_LEVEL.getValue(); i++){
            level.setLevel(i);
            assertEquals(Config.DEFAULT_NUMBER_COLORS.getValue() + level.getLevel() - 1, level.getNumberColors());
        }
    }

    @Test
    public void levelUpgradeTest_validUpgrade(){
        Level level = new Level();
        int i = 1;
        while(level.getLevel() < Config.MAX_LEVEL.getValue()){
            assertEquals(i, level.getLevel());
            level.upgrade();
            i++;
        }

        assertEquals(i, level.getLevel());
    }

    @Test
    public void levelUpgradeTest_invalidUpgrade(){
        Level level = new Level(Config.MAX_LEVEL.getValue());
        assertThrows(IllegalStateException.class, () -> level.upgrade());
    }

    @Test
    public void levelDowngradeTest_validUpgrade(){
        Level level = new Level(Config.MAX_LEVEL.getValue());
        int i = level.getLevel();
        while(level.getLevel() > Config.MIN_LEVEL.getValue()){
            assertEquals(i, level.getLevel());
            level.downgrade();
            i--;
        }

        assertEquals(i, level.getLevel());
    }

    @Test
    public void levelDowngradeTest_invalidUpgrade(){
        Level level = new Level(Config.MIN_LEVEL.getValue());
        assertThrows(IllegalStateException.class, () -> level.downgrade());
    }
}

