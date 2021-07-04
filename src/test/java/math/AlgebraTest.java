package math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgebraTest {
    Algebra algebra = new Algebra();

    @Test
    public void testJudgeSquareSum() {
        assertTrue(algebra.judgeSquareSum(5));
    }

}