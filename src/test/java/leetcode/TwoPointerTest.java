package leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/8/19 9:27
 */
class TwoPointerTest {

    TwoPointer tp = new TwoPointer();

    @Test
    void reverseVowels() {
        String reversed = tp.reverseVowels("hello");
        assertEquals("holle", reversed);
    }
}