package dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/9/4 18:49
 */
class BagTest {
    Bag bag = new Bag();

    @Test
    void zeroOnePack() {
        int[] c = {1, 2, 4, 8};
        int[] w = {4, 2, 3 ,10};
        int V = 8;
        int N = 4;
        int result = bag.zeroOnePack(N, V, c, w);
        assertEquals(10, result);
    }

    @Test
    void completePack() {
        int[] c = {1, 2, 4, 8};
        int[] w = {4, 2, 3 ,10};
        int V = 8;
        int N = 4;
        int result = bag.completePack(N, V, c, w);
        assertEquals(32, result);
    }

    @Test
    void multiplePack() {
        int[] c = {1, 2, 4, 8};
        int[] w = {4, 2, 3 ,10};
        int[] a = {3, 2, 4, 4};
        int V = 8;
        int N = 4;
        int result = bag.multiplePack(N, V, c, w, a);
        assertEquals(16, result);
    }

    @Test
    void strsSubset() {
        String[] strs = {"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;
        int result = bag.findMaxForm(strs, m, n);
        assertEquals(4, result);
    }
}