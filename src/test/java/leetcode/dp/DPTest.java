package leetcode.dp;

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
}