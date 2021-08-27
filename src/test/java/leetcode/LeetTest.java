package leetcode;

import dp.DPMinCostTickets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeetTest {

  static Leet leet;

  @BeforeAll
  static void init() {
    leet = new Leet();
  }


  @Test
  public void testMincostTickets() {
    int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31};
    int[] costs = {2, 7, 15};
    assertEquals(new DPMinCostTickets().mincostTickets(days, costs), 17);
  }

  @Test
  public void testAtoi() {
    String[] test = {"42", "  -42", "4193 with words", "words and 231", "-91283472332"};
    assertEquals(Leet.atoi("42"), 42);
    assertEquals(Leet.atoi("  -42"), -42);
    assertEquals(Leet.atoi("4193 with words"), 4193);
    assertEquals(Leet.atoi("words with 231"), 0);
    assertEquals(Leet.atoi("-91283472332"), -2147483648);
    assertEquals(Leet.atoi("2147483646"), 2147483646);
  }

  @Test
  void testSearchRange() {
    Search search = new Search();
    int[] nums = {1, 2, 3, 3, 3, 4, 6};
    int[] results = search.searchRange(nums, 3);
    assertEquals(2, results[0]);
    assertEquals(4, results[1]);
  }

  @Test
  void testCheckInclusion() {
    TwoPointer TwoPointer = new TwoPointer();
    String s = "ADOBECODEBANC";
    String t = "DOC";
    assertTrue(TwoPointer.checkInclusion(s, t));
    s = "abcdeabcdx";
    t = "abcdxabcde";
    assertTrue(TwoPointer.checkInclusion(s, t));
  }

  @Test
  void testFindAnagrams() {
    TwoPointer TwoPointer = new TwoPointer();
    String s = "cbaebabacd";
    String t = "abc";
    List<Integer> result = TwoPointer.findAnagrams(s, t);
    System.out.println(result);
    assertEquals(0, result.get(0));
    assertEquals(6, result.get(1));
  }

  @Test
  void testIntersect() {
    int[] nums1 = {1, 2, 3, 4, 5, 7, 7};
    int[] nums2 = {4, 6, 7, 6, 7};
    int[] result = leet.intersect(nums1, nums2);
    for (int r : result) {
      System.out.println(r);
    }
  }


  @Test
  void testFourSum() {
    int[] nums = {0, 0, 0, 0};
    List<List<Integer>> lists = leet.fourSum(nums, 0);
    System.out.println(lists);
  }

  @Test
  void testSplitArray() {
    int[] nums = {7, 2, 5, 10, 8};
    int result = leet.splitArray(nums, 2);
    System.out.println(result);
  }

  @Test
  void testSmallestRange() {
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    List<Integer> list3 = new ArrayList<>();
    list1.add(4);
    list1.add(10);
    list1.add(15);
    list1.add(24);
    list1.add(26);

    list2.add(0);
    list2.add(9);
    list2.add(12);
    list2.add(20);

    list3.add(5);
    list3.add(18);
    list3.add(22);
    list3.add(30);

    List<List<Integer>> l = new ArrayList<>();
    l.add(list1);
    l.add(list2);
    l.add(list3);

    int[] ints = leet.smallestRange(l);
    for (int anInt : ints) {
      System.out.println(anInt);
    }
  }

  @Test
  void testCountBinarySubstrings() {
    int result = leet.countBinarySubstrings("00110011");
    assertEquals(6, result);
  }

  @Test
  void testFindSubsequences() {
    int[] nums = new int[] {4, 6, 7, 7};
    System.out.println(leet.findSubsequences(nums));
    assertEquals(8, leet.findSubsequences(nums).size());
  }
}
