package leetcode.dp;

import leetcode.math.Geometry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @date 2020/6/17
 */
class DPTest {
    @Test
    void testEggDrop() {
        EggDrop eggDrop = new EggDrop();
        assertEquals(3, eggDrop.superEggDrop(2, 6));
        assertEquals(4, eggDrop.superEggDrop(3, 14));

        assertEquals(3, eggDrop.superEggDrop2(2, 6));
        assertEquals(4, eggDrop.superEggDrop2(3, 14));

        assertEquals(3, eggDrop.superEggDrop3(2, 6));
        assertEquals(4, eggDrop.superEggDrop3(3, 14));
    }

    @Test
    void testBag() {
        Bag bag = new Bag();
        int[] wt = {2, 1, 3};
        int[] val = {4, 2, 3};
        int result = bag.knapsack(4, 3, wt, val);
        assertEquals(6, result);
    }

    @Test
    void testCanPartition() {
        Base base = new Base();
        int[] nums = {1,5,11,5};
        boolean can = base.canPartition(nums);
        assertTrue(can);
    }

    @Test
    void testMinDistance() {
        Base base = new Base();
        int result = base.minDistance("horse", "ros");
        assertEquals(3, result);
        int result2 = base.minDistanceWithTrace("horse", "ros");
        assertEquals(3, result2);
    }
}