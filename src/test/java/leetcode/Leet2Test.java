package leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 2021/2/27
 */
class Leet2Test {

    private final Leet2 leet2 = new Leet2();

    @Test
    void longestSubstring() {
        assertEquals(3, leet2.longestSubstring("aaabb", 3));
    }

    @Test
    void minOperations() {
        assertArrayEquals(new int[]{11,8,5,4,3,4}, leet2.minOperations("001011"));
    }

    @Test
    void longestPalindrome() {
        assertEquals(5, leet2.longestPalindrome("cacb", "cbba"));
    }

    @Test
    void calculate() {
        assertEquals(23, leet2.calculate("(1+(4+5+2)-3)+(6+8)"));
    }
}