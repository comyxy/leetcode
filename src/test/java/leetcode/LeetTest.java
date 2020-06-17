package leetcode;

import leetcode.dp.DPMinCostTickets;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author comyxy
 * @date 2020/5/4
 */
class LeetTest {

    @Test
    public void testLengthOfLongestSubstring(){
        String s = "pwwkew";
        assertEquals(Leet.lengthOfLongestSubstring(s), 3);
        assertEquals(Leet.lengthOfLongestSubstring("abcdefghi"), 9);

        DoublePointer doublePointer = new DoublePointer();
        int result = doublePointer.lengthOfLongestSubstring(s);
        assertEquals(3, result);
    }

    @Test
    public void testMaxArea(){
        int[] height = {1,8,6,2,5,4,8,3,7};
        assertEquals(Leet.maxArea(height), 49);
    }

    @Test
    public void testGenerateParenthesis(){
        System.out.println(Leet.generateParenthesis(3));
    }

    @Test
    public void testMincostTickets(){
        int[] days = {1,2,3,4,5,6,7,8,9,10,30,31};
        int[] costs = {2,7,15};
        assertEquals(new DPMinCostTickets().mincostTickets(days, costs), 17);
    }

    @Test
    public void testAtoi(){
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
    void minWindow() {
        DoublePointer doublePointer = new DoublePointer();
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String result = doublePointer.minWindow(s, t);
//        System.out.println(result);
        assertEquals("BANC", result);
        s = "a";
        t = "a";
        result = doublePointer.minWindow(s, t);
//        System.out.println(result);
        assertEquals("a", result);
    }

    @Test
    void testCheckInclusion() {
        DoublePointer doublePointer = new DoublePointer();
        String s = "ADOBECODEBANC";
        String t = "DOC";
        assertTrue(doublePointer.checkInclusion(s, t));
        s = "abcdeabcdx";
        t = "abcdxabcde";
        assertTrue(doublePointer.checkInclusion(s, t));
    }

    @Test
    void testFindAnagrams() {
        DoublePointer doublePointer = new DoublePointer();
        String s = "cbaebabacd";
        String t = "abc";
        List<Integer> result = doublePointer.findAnagrams(s, t);
        System.out.println(result);
        assertEquals(0, result.get(0));
        assertEquals(6, result.get(1));
    }
}