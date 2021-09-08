package quiz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/9/3 18:58
 */
class TencentTestTest {

    TencentTest tt = new TencentTest();

    @Test
    void q1() {
        String[] strs = {"add", "for 43", "end", "for 10", "for 15", "add", "end", "add", "end"};
        int result = tt.q1(strs);
        assertEquals(161, result);
        strs = new String[]{"add", "for 43", "end", "for 10", "for 15", "add", "add", "end", "add", "end"};
        assertEquals(311, tt.q1(strs));
        int x = Integer.MAX_VALUE / 5;
        strs = new String[]{"add", "for 43", "end", "for " + x, "for 15", "add", "end", "add", "end"};
        assertEquals(Integer.MAX_VALUE, tt.q1(strs));
    }


    @Test
    void q2() {
        int[] nums = {2, 4, 5, 8};
        int k = 2;
        int result = tt.q2(nums, k);
        assertEquals(2, result);
        nums = new int[]{2,3,4,8};
        k = 2;
        result = tt.q2(nums, k);
        assertEquals(2, result);
        nums = new int[]{2,3,4,5,8};
        k = 2;
        assertEquals(2, result);
    }
}