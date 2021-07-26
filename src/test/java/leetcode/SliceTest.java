package leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SliceTest {

    Slice slice = new Slice();

    @Test
    public void testLeastBricks() {
        List<Integer> l1 = Arrays.asList(100000000);
        List<Integer> l2 = Arrays.asList(100000000);
        List<Integer> l3 = Arrays.asList(100000000);
        List<List<Integer>> ll = new ArrayList<>();
        ll.add(l1);
        ll.add(l2);
        ll.add(l3);

        assertEquals(3, slice.leastBricks(ll));
    }

    @Test
    void longestCommonSubsequence() {
        String t1 = "abcde";
        String t2 = "ace";

        assertEquals(3, slice.longestCommonSubsequence(t1, t2));
    }

    @Test
    void lengthOfLIS() {
        int[] nums = new int[]{10,9,2,5,3,7,101,18};
        assertEquals(4, slice.lengthOfLIS(nums));
    }

    @Test
    void lengthOfLISV2() {
        int[] nums = new int[]{0,1,0,3,2,3};
        assertEquals(4, slice.lengthOfLISV2(nums));
    }

    @Test
    void minOperations() {
        int[] target = new int[]{6,4,8,1,3,2};
        int[] arr = new int[]{4,7,6,2,3,8,6,1};
        assertEquals(3, slice.minOperations(target, arr));
    }
}