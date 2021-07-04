package dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaintHouseTest {
    PaintHouse ph = new PaintHouse();

    @Test
    public void testMinCost(){
        int[] houses = new int[]{0,2,1,2,0};
        int[][] cost = new int[][]{{1,10},{10,1},{10,1},{1,10},{5,1}};
        int m = 5, n = 2, target = 3;
        assertEquals(11, ph.minCost(houses, cost, m, n, target));
    }
}