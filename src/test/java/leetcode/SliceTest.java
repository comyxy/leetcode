package leetcode;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SliceTest {

    Slice slice = new Slice();

    @Test
    public void testLeastBricks() {
        List<Integer> l1 = Arrays.asList(100000000);
        List<Integer> l2 = Arrays.asList(100000000);
        List<Integer> l3 = Arrays.asList(100000000);
        List<List<Integer>> ll = new ArrayList<>();
        ll.add(l1);
        ll.add(l2);
        ll.add(l3);

        assertEquals(3, slice.leastBricks(ll));
    }

}