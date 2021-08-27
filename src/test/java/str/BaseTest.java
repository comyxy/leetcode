package str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/8/20 21:35
 */
class BaseTest {

    Base str = new Base();

    @Test
    void longestWord() {
        String[] words = {"wo","wor","worl","world"};
        String result = str.longestWord(words);
        assertEquals("", result);
    }

    @Test
    void multiply() {
        String result = str.multiply("999", "999");
        assertEquals("998001", result);
    }
}