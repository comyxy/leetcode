package leetcode.backtrace;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @date 2020/6/12
 */
class NQueueTest {

    @Test
    void testNQueue() {
        NQueue nQueue = new NQueue();
        System.out.println(nQueue.solveNQueens(4));
    }
}