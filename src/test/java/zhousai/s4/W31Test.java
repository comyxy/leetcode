package zhousai.s4;

import org.junit.jupiter.api.Test;
import zhousai.s1.W3;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/9/5 10:39
 */
class W31Test {
    W31 w = new W31();

    @Test
    void countQuadruplets() {
        int[] nums = {28,8,49,85,37,90,20,8};
        int result = w.countQuadruplets(nums);
        assertEquals(1, result);
    }

    @Test
    void numberOfWeakCharacters() {
        int[][] p = {{10,1},{5,1},{7,10},{4,1},{5,9},{6,9},{7,2},{1,10}};
        int result = w.numberOfWeakCharacters(p);
        assertEquals(4, result);
    }

    @Test
    void gcdSort() {
        int[] nums = {5, 2, 6, 2};
        boolean res = w.gcdSort(nums);
        assertFalse(res);
    }
}