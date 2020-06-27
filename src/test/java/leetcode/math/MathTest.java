package leetcode.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @date 2020/6/27
 */
class MathTest {

    @Test
    void testIsRobotBounded() {
        Geometry geometry = new Geometry();
        String ins = "GL";
        boolean result = geometry.isRobotBounded("GG");
        assertFalse(result);
    }

    @Test
    void testJosephus() {
        Geometry geometry = new Geometry();
        int result = geometry.josephus(5, 3);
        assertEquals(3, result);
    }
}