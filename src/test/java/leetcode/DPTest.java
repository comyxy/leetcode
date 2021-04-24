package leetcode;

import dp.*;
import leetcode.dp.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** @date 2020/6/17 */
class DPTest {

  /*--------------base test-----------------*/

  static Base base;

  @BeforeAll
  static void init() {
    base = new Base();
  }

  @Test
  void testLengthOfLIS() {
    int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
    assertEquals(4, base.lengthOfLIS(nums));
  }

  @Test
  void testMaxSubArray() {
    int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    assertEquals(6, base.maxSubArray(nums));
  }

  @Test
  void testMinDistance() {
    int result = base.minDistance("horse", "ros");
    assertEquals(3, result);
    int result2 = base.minDistanceWithTrace("horse", "ros");
    assertEquals(3, result2);
  }

  @Test
  void testLongestCommonSubsequence() {
    int result = base.longestCommonSubsequence("babcde", "acze");
    assertEquals(3, result);
    int result2 = base.longestCommonSubsequence2("aaaa", "aaab");
    assertEquals(3, result2);
  }

  @Test
  void testLongestPalindromeSubseq() {
    int result = base.longestPalindromeSubseq("bbbab");
    assertEquals(4, result);
  }

  @Test
  void testStoneGame() {
    int[] piles = {5, 3, 5, 4};
    assertTrue(base.stoneGame(piles));
    int[] piles2 = {1, 100, 1, 3, 5};
    assertFalse(base.stoneGame(piles2));
  }

  @Test
  void testIsMatch() {
    boolean result = base.isMatch("aaab", "a.*b");
    assertTrue(result);
    boolean result2 = base.isMatch("mississippi", "mis*is*p*.");
    assertFalse(result2);
    boolean result3 = base.isMatch2("mississippi", "mis*is*p*.");
    assertFalse(result3);
  }

  @Test
  void testMaxOf4KeyBoard() {
    assertEquals(3, base.maxOf4KeyBoard(3));
    assertEquals(9, base.maxOf4KeyBoard(7));

    assertEquals(9, base.maxOf4KeyBoard2(7));
  }

  @Test
  void testNim() {
    boolean nim = base.canWinNim(6);
    System.out.println(nim);
  }

  @Test
  void testMinimumTotal(){
      List<List<Integer>> tri = new ArrayList<>();
      tri.add(Collections.singletonList(2));
      tri.add(Arrays.asList(3,4));
      tri.add(Arrays.asList(6,5,7));
      tri.add(Arrays.asList(4,1,8,3));
      assertEquals(11, base.minimumTotal(tri));
  }

  @Test
  void testSubSequence(){
      assertTrue(base.isSubsequence("abc", "ahbxgc"));
  }

  @Test
  void testSplitArray() {
    int[] nums = {7, 2, 5, 10, 8};
    int result = base.splitArray(nums, 2);
    System.out.println(result);
  }

  @Test
  void testIntegerBreak(){
      assertEquals(36, base.integerBreak(10));
      assertEquals(36, base.integerBreak2(10));
  }

  @Test
  void testRemoveBoxes() {
    int[] boxes = {1, 3, 2, 2, 2, 3, 4, 3, 1};
    int result = base.removeBoxes(boxes);
    assertEquals(23, result);
  }

  /*---------扔鸡蛋问题-----------*/

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

  /*---------戳气球问题-----------*/

  @Test
  void testBurstBalloon() {
    BurstBalloon burstBalloon = new BurstBalloon();
    int[] nums = {3, 1, 5, 8};
    assertEquals(167, burstBalloon.maxCoins(nums));
    assertEquals(167, burstBalloon.maxCoins2(nums));
  }

  /*---------背包问题-----------*/

  @Test
  void testBag() {
    Bag bag = new Bag();
    int[] wt = {2, 1, 3};
    int[] val = {4, 2, 3};
    int result = bag.knapsack(4, 3, wt, val);
    assertEquals(6, result);
  }

  @Test
  void testCanPartition() {
    Bag bag = new Bag();
    int[] nums = {1, 5, 11, 5};
    boolean can = bag.canPartition(nums);
    assertTrue(can);
  }

  @Test
  void testChange2() {
    Bag bag = new Bag();
    int[] coins = {1, 2, 5};
    assertEquals(4, bag.change(5, coins));
  }

  /*---------贪婪算法---------*/

  @Test
  void testEraseOverlapIntervals() {
    Greedy greedy = new Greedy();
    int[][] intervals = new int[4][2];
    intervals[0] = new int[] {1, 2};
    intervals[1] = new int[] {2, 3};
    intervals[2] = new int[] {3, 4};
    intervals[3] = new int[] {1, 3};
    int result = greedy.eraseOverlapIntervals(intervals);
    assertEquals(1, result);
    int result2 = greedy.eraseOverlapIntervals2(intervals);
    assertEquals(1, result2);
  }

  @Test
  void testFindMinArrowShots() {
    Greedy greedy = new Greedy();
    int[][] points = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
    int result = greedy.findMinArrowShots(points);
    assertEquals(2, result);
  }

  /*----------路径问题-------------*/

  @Test
  void testRobot() {
    Robot robot = new Robot();
    int[][] grid = new int[3][3];
    grid[0] = new int[] {0, 0, 0};
    grid[1] = new int[] {0, 1, 0};
    grid[2] = new int[] {0, 0, 0};
    assertEquals(2, robot.uniquePathsWithObstacles(grid));
    int[][] grid2 = new int[3][3];
    grid2[0] = new int[] {1, 3, 1};
    grid2[1] = new int[] {1, 5, 1};
    grid2[2] = new int[] {4, 2, 1};
    assertEquals(7, robot.minPathSum(grid2));
  }

  /*--------dfa有限状态机-------------*/

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


}
