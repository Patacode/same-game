package g56080.same.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ScoreCounterTest{

    private ScoreCounter sc;

    @Before
    public void init(){
        sc = new ScoreCounter(v -> v * (v - 1));
    }

    @Test
    public void scoreCounterComputeTest(){
        assertEquals(20, sc.compute(5));
        assertEquals(110, sc.compute(11));
    }

    @Test
    public void scoreCounterAddTest(){
        sc.add(5);
        sc.add(11);

        assertEquals(130, sc.asInt());
    }
}
