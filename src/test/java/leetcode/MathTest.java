package leetcode;

import math.Bit;
import math.Geometry;
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


    @Test
    void testBits() {
        Bit bit = new Bit();

        int reversed = bit.reverseBits(0b11111111111111111111111111111101);
        assertEquals("10111111111111111111111111111111", Integer.toBinaryString(reversed));

        int hammingWeight = bit.hammingWeight(0b11111111111111111111111111111101);
        assertEquals(31, hammingWeight);
        hammingWeight = bit.hammingWeight(0b11111111111111111111111111111101);
        assertEquals(31, hammingWeight);
    }
}