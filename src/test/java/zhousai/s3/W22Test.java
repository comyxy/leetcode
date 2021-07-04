package zhousai.s3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @date 2021/7/4 11:38
 */
class W22Test {

    W22 w = new W22();

    @Test
    void longestCommonSubpath() {
        int n = 5;
        int[][] paths = new int[][]{
                {4,3,2,1,0},
                {0,1,2,3,4}
        };
        int ret = w.longestCommonSubpath(n, paths);
        assertEquals(1, ret);
    }
}