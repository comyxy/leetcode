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

    @Test
    void distinctSubseqII() {
        String s = "zchmliaqdgvwncfatcfivphddpzjkgyygueikthqzyeeiebczqbqhdytkoawkehkbizdmcnilcjjlpoeoqqoqpswtqdpvszfaksn";
        int result = str.distinctSubseqII(s);
        assertEquals(97915677, result);
    }

    @Test
    void smallestSubsequence() {
        String s = "mmmxmxymmm";
        int k = 8;
        char letter = 'm';
        int repetition = 4;
        String result = str.smallestSubsequence(s, k, letter, repetition);
        assertEquals("mmmmxmmm", result);
    }
}