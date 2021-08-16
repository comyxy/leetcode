package zhousai.s3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/8/15 10:52
 */
class W28Test {

    W28 w = new W28();

    @Test
    void rearrangeArray() {
        int[] nums = new int[]{1,2,3,4,5};
        int[] res = w.rearrangeArray(nums);
        for (int re : res) {
            System.out.println(re);
        }
    }

    @Test
    void minNonZeroProduct() {
        int res = w.minNonZeroProduct(2);
        System.out.println(res);
    }
}