package g56080.same.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import g56080.same.util.Util;

public class ZoneTest{
    
    private Zone zone;

    @Before
    public void init(){
        zone = new Zone();
    }

    @Test
    public void zoneAddPointTest_differentX_sameY(){
        List<Point> points = List.of(new Point(0, 0), 
                new Point(1, 0), 
                new Point(2, 0), 
                new Point(3, 0), 
                new Point(4, 0));
        for(int i = 0; i < 5; i++){
            zone.add(new Point(i, 0));
        }

        assertTrue(points.containsAll(zone.getPoints()));
    }

    @Test
    public void zoneAddPointTest_differentY_sameX(){
        List<Point> points = List.of(new Point(0, 0), 
                new Point(0, 1), 
                new Point(0, 2), 
                new Point(0, 3), 
                new Point(0, 4));
        for(int i = 0; i < 5; i++){
            zone.add(new Point(0, i));
        }

        assertTrue(points.containsAll(zone.getPoints()));
    }

    @Test
    public void zoneAddPointTest_samePoints(){
        List<Point> points = List.of(new Point());

        for(int i = 0; i < 5; i++){
            zone.add(new Point());
        }

        assertTrue(points.containsAll(zone.getPoints()));
    }

    @Test
    public void zoneAddPointTest_sameX_sorted(){
        for(int i = 0; i < 5; i++){
            zone.add(new Point(0, i));
        }

        assertEquals(new Point(0, 4), zone.getMaxPoint(0));
    }

    @Test
    public void zoneAddAllPointsTest(){
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Point p = new Point(Util.genRandom(0, 10), Util.genRandom(0, 10));
            if(!points.contains(p))
                points.add(p);
        }

        zone.addAll(points.toArray(size -> new Point[size]));
        assertTrue(points.containsAll(zone.getPoints()));
    }

    @Test
    public void zoneContainsTest_existingPoint(){
        zone.add(new Point());
        assertTrue(zone.contains(new Point()));
    }

    @Test
    public void zoneContainsTest_inexistentPoint(){
        zone.add(new Point());
        assertTrue(!zone.contains(new Point(1, 4)));
    }

    @Test
    public void zoneGetPointsTest_existingX(){
        zone.addAll(new Point(0, 4), new Point(10, 6));
        assertTrue(List.of(new Point(0, 4)).containsAll(zone.getPoints(0)));
    }

    @Test
    public void zoneGetPointsTest_inexistentX(){
        zone.addAll(new Point(0, 4), new Point(10, 6));
        assertTrue(List.of().containsAll(zone.getPoints(1)));
    }
}
