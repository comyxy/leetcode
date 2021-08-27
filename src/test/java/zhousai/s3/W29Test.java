package zhousai.s3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/8/21 22:37
 */
class W29Test {

    W29 w = new W29();

    @Test
    void minTimeToType() {
        int zjpc = w.minTimeToType("zjpc");
        assertEquals(34, zjpc);
    }

    @Test
    void maxMatrixSum() {
        int[][] matrix = {{2, 9, 3}, {5, 4, -4}, {1, 7, 1}};
        long l = w.maxMatrixSum(matrix);
        assertEquals(34, l);
    }
}