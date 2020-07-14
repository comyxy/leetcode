package leetcode;

import leetcode.dp.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

        assertEquals(3, eggDrop.superEggDrop4(2, 6));
        assertEquals(4, eggDrop.superEggDrop4(3, 14));

        assertEquals(3, eggDrop.superEggDrop5(2, 6));
        assertEquals(4, eggDrop.superEggDrop5(3, 14));

    }

    @Test
    void testBurstBalloon() {
        BurstBalloon burstBalloon = new BurstBalloon();
        int[] nums = {3, 1, 5, 8};
        assertEquals(167, burstBalloon.maxCoins(nums));
        assertEquals(167, burstBalloon.maxCoins2(nums));
    }

    @Test
    void testBag() {
        Bag bag = new Bag();
        int[] wt = {2, 1, 3};
        int[] val = {4, 2, 3};
        int result = bag.knapsack(4, 3, wt, val);
        assertEquals(6, result);
    }

    /*------------------Greedy-------------------------*/

    @Test
    void testEraseOverlapIntervals() {
        Greedy greedy = new Greedy();
        int[][] intervals = new int[4][2];
        intervals[0] = new int[]{1, 2};
        intervals[1] = new int[]{2, 3};
        intervals[2] = new int[]{3, 4};
        intervals[3] = new int[]{1, 3};
        int result = greedy.eraseOverlapIntervals(intervals);
        assertEquals(1, result);
        int result2 = greedy.eraseOverlapIntervals2(intervals);
        assertEquals(1, result2);
    }

    @Test
    void testFindMinArrowShots() {
        Greedy greedy = new Greedy();
        int[][] points = {{10,16}, {2,8}, {1,6}, {7,12}};
        int result = greedy.findMinArrowShots(points);
        assertEquals(2, result);
    }

    /*----------Robot--------------------------------*/

    @Test
    void testRobot() {
        Robot robot = new Robot();
        int[][] grid = new int[3][3];
        grid[0] = new int[]{0,0,0};
        grid[1] = new int[]{0,1,0};
        grid[2] = new int[]{0,0,0};
        assertEquals(2, robot.uniquePathsWithObstacles(grid));
        int[][] grid2 = new int[3][3];
        grid2[0] = new int[]{1,3,1};
        grid2[1] = new int[]{1,5,1};
        grid2[2] = new int[]{4,2,1};
        assertEquals(7, robot.minPathSum(grid2));
    }

    /*------------------dfa----------------------*/

    @Test
    void testIsNumber() {
        DFA dfa = new DFA();
        boolean isNumber = dfa.isNumber(" .2");
        assertTrue(isNumber);
    }

    @Test
    void testMyAtoi() {
        DFA dfa = new DFA();
        int result = dfa.myAtoi("words and 987");
        System.out.println(result);
    }

    /*-----------------------------------------------*/

    @Test
    void testCanPartition() {
        Base base = new Base();
        int[] nums = {1, 5, 11, 5};
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

    @Test
    void testLongestCommonSubsequence() {
        Base base = new Base();
        int result = base.longestCommonSubsequence("babcde", "acze");
        assertEquals(3, result);
        int result2 = base.longestCommonSubsequence2("aaaa", "aaab");
        assertEquals(3, result2);
    }

    @Test
    void testLongestPalindromeSubseq() {
        Base base = new Base();
        int result = base.longestPalindromeSubseq("bbbab");
        assertEquals(4, result);
    }

    @Test
    void testStoneGame() {
        Base base = new Base();
        int[] piles = {5, 3, 5, 4};
        assertTrue(base.stoneGame(piles));
        int[] piles2 = {1, 100, 1, 3, 5};
        assertFalse(base.stoneGame(piles2));
    }

    @Test
    void testIsMatch() {
        Base base = new Base();
        boolean result = base.isMatch("aaab", "a.*b");
        assertTrue(result);
        boolean result2 = base.isMatch("mississippi", "mis*is*p*.");
        assertFalse(result2);
        boolean result3 = base.isMatch2("mississippi", "mis*is*p*.");
        assertFalse(result3);
    }

    @Test
    void testMaxOf4KeyBoard() {
        Base base = new Base();
        assertEquals(3,base.maxOf4KeyBoard(3));
        assertEquals(9,base.maxOf4KeyBoard(7));

        assertEquals(9,base.maxOf4KeyBoard2(7));
    }

    @Test
    void testNim() {
        Base base = new Base();
        boolean nim = base.canWinNim(6);
        System.out.println(nim);
    }
}