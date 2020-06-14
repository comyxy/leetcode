package leetcode.backtrace;

import leetcode.backtrace.PermuteUnique;
import org.junit.jupiter.api.Test;

/**
 * @date 2020/6/12
 */
class PermuteUniqueTest {
    @Test
    void testPermuteUnique() {
        PermuteUnique permuteUnique = new PermuteUnique();
        int[] nums = {1, 2, 3};
        System.out.println(permuteUnique.permuteUnique(nums));
    }
}