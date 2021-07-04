package zhousai.random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RW1Test {

    RW1 rw = new RW1();

    @AfterEach
    void tearDown() {
    }

    @Test
    void isAlienSorted() {
        String[] words = new String[]{"fxasxpc", "dfbdrifhp", "nwzgs", "cmwqriv", "ebulyfyve", "miracx", "sxckdwzv", "dtijzluhts", "wwbmnge", "qmjwymmyox"};
        String order = "zkgwaverfimqxbnctdplsjyohu";
        assertFalse(rw.isAlienSorted(words, order));
    }

    @Test
    void canReorderDoubled() {
        int[] arr = new int[]{1,2,4,16,8,4};
        assertFalse(rw.canReorderDoubled(arr));
    }
}