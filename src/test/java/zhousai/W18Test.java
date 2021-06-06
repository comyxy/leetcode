package zhousai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class W18Test {
    W18 w = new W18();

    @Test
    void findRotation() {
        int[][] mat = new int[][]{{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        int[][] target = new int[][]{{1, 1, 1}, {0, 1, 0}, {0, 0, 0}};
        assertTrue(w.findRotation(mat, target));
    }

    @Test
    void reductionOperations() {
        int[] nums = {5,1,3};
        assertEquals(3, w.reductionOperations(nums));
    }

    @Test
    void minFlips() {
        String s = "111000";
        assertEquals(2, w.minFlips(s));
    }

    @Test
    void minWastedSpace() {
        int[] packages = new int[]{2,3,5};
        int[][] boxes = new int[][]{{4,8},{2,8}};
        assertEquals(6, w.minWastedSpace(packages, boxes));
    }
}