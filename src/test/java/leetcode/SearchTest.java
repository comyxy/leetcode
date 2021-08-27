package leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    Search search = new Search();

    @Test
    public void testShipWithinDays() {
        int[] weights = new int[]{1,2,3,4,5,6,7,8,9,10};
        int D = 5;
        assertEquals(15, search.shipWithinDays(weights, D));
    }

    @Test
    void upperBound() {
        int[] nums = new int[]{1, 2, 4, 6, 8, 9};
        int target = 8;
        assertEquals(5, search.upperBound(nums, 0, nums.length, target));
    }

    @Test
    void upperBoundRe() {
        int[] nums = new int[]{1, 2, 4, 6, 8, 9};
        int target = 8;
        assertEquals(3, upperBoundRe(nums, 0, nums.length, target));
    }

    int upperBoundRe(int[] nums, int left, int right, int target) {
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        return right;
    }

    @Test
    void lowerBound() {
        int[] nums = new int[]{1, 2, 4, 6, 8, 9};
        int target = 8;
        assertEquals(4, search.lowerBound(nums, 0, nums.length, target));
    }

    @Test
    void lowerBoundRe() {
        int[] nums = new int[]{1, 2, 4, 6, 8, 9};
        int target = 8;
        assertEquals(4, lowerBoundRe(nums, 0, nums.length, target));
    }

    int lowerBoundRe(int[] nums, int left, int right, int target) {
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] <= target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }

    @Test
    void findCheapestPrice() {
        int n = 4;
        int[][] edges = {{0,1,1}, {0,2,5}, {1,2,1}, {2,3,1}};
        int src = 0;
        int dst = 3;
        int k = 1;
        int cheapestPrice = search.findCheapestPrice(n, edges, src, dst, k);
        assertEquals(6, cheapestPrice);
    }
}