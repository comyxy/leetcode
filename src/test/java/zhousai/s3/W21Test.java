package zhousai.s3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class W21Test {

    W21 w = new W21();

    @Test
    void rotateGrid() {
        int[][] grid = new int[][]{{1,2,3,4}, {5,6,7,8}, {9,10,11,12},{13,14,15,16}};
        int[][] rotateGrid = w.rotateGrid(grid, 2);
    }

    @Test
    void wonderfulSubstrings() {
        long aabb = w.wonderfulSubstrings("aabb");
        assertEquals(9, aabb);
    }
}