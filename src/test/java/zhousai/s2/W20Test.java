package zhousai.s2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class W20Test {
    W20 w = new W20();

    @Test
    void numberOfRounds() {
        assertEquals(95, w.numberOfRounds("00:00", "23:59"));
    }

    @Test
    void minDifference() {
        int[] nums = {4,5,2,2,7,10};
        int[][] queries = {{2,3},{0,2},{0,5},{3,5}};
        w.minDifference(nums, queries);
    }
}