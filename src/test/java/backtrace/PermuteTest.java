package backtrace;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @date 2020/6/12
 */
class PermuteTest {

    Permute permute = new Permute();

    @Test
    void testPermuteUnique() {
        List<List<Integer>> result1 = permute.permuteUnique(new int[]{1, 2, 3});
        System.out.println(result1);
        assertEquals(6, result1.size());

        List<List<Integer>> result2 = permute.permuteUnique(new int[]{1, 1, 2});
        System.out.println(result2);
        assertEquals(3, result2.size());
    }

    @Test
    void testPermute() {
        List<List<Integer>> result1 = permute.permute(new int[]{1, 2, 3});
        System.out.println(result1);
        assertEquals(6, result1.size());

        List<List<Integer>> result2 = permute.permute(new int[]{1, 1, 2});
        System.out.println(result2);
        assertEquals(6, result2.size());
    }

    @Test
    void testPermuteV2() {
        List<List<Integer>> result1 = permute.permuteV2(new int[]{1, 2, 3});
        System.out.println(result1);
        assertEquals(6, result1.size());

        List<List<Integer>> result2 = permute.permuteV2(new int[]{1, 1, 2});
        System.out.println(result2);
        assertEquals(6, result2.size());
    }
}