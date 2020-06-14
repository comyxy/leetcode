package leetcode;

import org.junit.jupiter.api.Test;

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
}