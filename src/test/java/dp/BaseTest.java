package dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/8/15 16:32
 */
class BaseTest {

    Base dpBase = new Base();

    @Test
    void findPaths() {
        int res = dpBase.findPaths(2, 2, 2, 0, 0);
        System.out.println(res);
    }
}