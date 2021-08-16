package struct;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @since 2021/8/16 16:44
 */
class SegmentTreeTest {

    @Test
    void testQuery() {
        int[] nums = {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1};
        final int n = nums.length;
        SegmentTree segmentTree = new SegmentTree(nums);
        int result = segmentTree.query(1, n);
        assertEquals(31, result);
    }

    @Test
    void testAdd() {
        int[] nums = {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1};
        final int n = nums.length;
        SegmentTree segmentTree = new SegmentTree(nums);
        int result = segmentTree.query(1, n);
        assertEquals(31, result);
        segmentTree.add(1, 1);
        int result2 = segmentTree.query(1, 1);
        assertEquals(2, result2);
    }

    @Test
    void testBatchAdd() {
        int[] nums = {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1};
        final int n = nums.length;
        SegmentTree segmentTree = new SegmentTree(nums);
        int result = segmentTree.query(1, n);
        assertEquals(31, result);
        segmentTree.batchAdd(3, 4, 2);
        int result2 = segmentTree.query(1, 3);
        assertEquals(8, result2);
    }
}