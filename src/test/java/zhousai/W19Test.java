package zhousai;

import org.junit.jupiter.api.Test;
import zhousai.s2.W19;

import static org.junit.jupiter.api.Assertions.*;

class W19Test {
    W19 w = new W19();

    @Test
    void maximumRemovals() {
        String s = "abcacb";
        String p = "ab";
        int[] removable = new int[]{3,1,0};
        assertEquals(2, w.maximumRemovals(s, p, removable));
    }
}