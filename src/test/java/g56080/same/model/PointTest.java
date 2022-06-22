package g56080.same.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest{
    
    @Test
    public void pointConstructorTest_default(){
        Point p = new Point();
        assertTrue(p.getX() == 0 && p.getY() == 0);
    }

    @Test
    public void pointConstructorTest_customizable(){
        Point p = new Point(5, 6);
        assertTrue(p.getX() == 5 && p.getY() == 6);
    }

    @Test
    public void pointSetterTest_newX(){
        Point p = new Point();
        p.setX(-10);
        assertTrue(p.getX() == -10 && p.getY() == 0);
    }

    @Test
    public void pointSetterTest_newY(){
        Point p = new Point();
        p.setY(-10);
        assertTrue(p.getX() == 0 && p.getY() == -10);
    }

    @Test
    public void pointComparisonTest_sameX_differentY(){
        Point p1 = new Point();
        Point p2 = new Point(0, 12);

        assertNotEquals(p1, p2);
    }

    @Test
    public void pointComparisonTest_sameY_differentX(){
        Point p1 = new Point();
        Point p2 = new Point(12, 0);

        assertNotEquals(p1, p2);
    }

    @Test
    public void pointComparisonTest_differentCoordinates(){
        Point p1 = new Point();
        Point p2 = new Point(66, -67);

        assertNotEquals(p1, p2);
    }

    @Test
    public void pointComparisonTest_samePoint(){
        Point p1 = new Point(66, -67);
        Point p2 = new Point(66, -67);

        assertEquals(p1, p2);
    }
}
