package zhousai.s4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/9/11 15:17
 */
class W32Test {

    W32 w = new W32();

    @Test
    void maxmiumScore() {
        int[] nums = {1, 2, 8, 9};
        int k = 3;
        int result = w.maxmiumScore(nums, k);
        System.out.println(result);
    }

    @Test
    void flipChess() {
        String[] cb = {".......", ".......", ".......", "X......", ".O.....", "..O....", "....OOX"};
        int res = w.flipChess(cb);
        System.out.println(res);
    }
}