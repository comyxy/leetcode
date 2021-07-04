package dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobTest {
    Rob rob = new Rob();

    @Test
    void deleteAndEarn() {
        int[] nums = new int[]{3,4,2};
        assertEquals(6, rob.deleteAndEarn(nums));
    }
}