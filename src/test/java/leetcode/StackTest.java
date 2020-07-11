package leetcode;

import leetcode.datastructure.UseStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @date 2020/7/10
 */
class StackTest {

    @Test
    void testRemoveOuterParentheses() {
        UseStack useStack = new UseStack();
        String result = useStack.removeOuterParentheses("((()()))");
        assertEquals("(()())", result);
    }

    @Test
    void testTrap() {
        UseStack useStack = new UseStack();
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        int trap = useStack.trap(height);
        assertEquals(6, trap);
    }
}