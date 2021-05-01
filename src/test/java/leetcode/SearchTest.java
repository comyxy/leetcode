package leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    Search search = new Search();

    @Test
    public void testShipWithinDays() {
        int[] weights = new int[]{1,2,3,4,5,6,7,8,9,10};
        int D = 5;
        assertEquals(15, search.shipWithinDays(weights, D));
    }
}