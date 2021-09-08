package zhousai.s3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/8/29 10:38
 */
class W30Test {

    W30 w = new W30();

    @Test
    void minimumDifference() {
        int[] nums = {9, 4, 1, 7};
        int k = 2;
        int result = w.minimumDifference(nums, k);
        assertEquals(2, result);
    }

    @Test
    void kthLargestNumber() {
        String[] nums = {"2", "21", "12", "1"};
        int k = 3;
        String result = w.kthLargestNumber(nums, k);
        assertEquals("2", result);
    }

    @Test
    void minSessions() {
        int[] tasks = {2,3,3,4,4,4,5,6,7,10};
        int st = 12;
        int result = w.minSessions(tasks, st);
        assertEquals(4, result);
    }
}