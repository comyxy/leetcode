package math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitTest {
    Bit bit = new Bit();

    @Test
    void decode() {
        int[] encoded = new int[]{1,2,3};
        int first = 1;
        assertArrayEquals(new int[]{1,0,2,1}, bit.decode(encoded, first));
    }

    @Test
    void xorOperation() {
        int n = 5, start = 0;
        assertEquals(8, bit.xorOperation(n, start));

        n = 1;
        start = 7;
        assertEquals(7, bit.xorOperation(n, start));
    }
}