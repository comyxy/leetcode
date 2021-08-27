package backtrace;

import org.junit.jupiter.api.Test;

/**
 * @date 2020/6/12
 */
class NQueenTest {

    @Test
    void testNQueue() {
        NQueen nQueen = new NQueen();
        System.out.println(nQueen.solveNQueens(4));
    }
}