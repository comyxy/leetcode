package struct;


import java.util.Arrays;

import static utils.EasyUtil.*;

/**
 * @since 2021/8/5 21:01
 * min heap
 */
public class Heap {

    private int size;

    private final int[] data;

    public Heap(int cap) {
        this.data = new int[cap];
    }

    public Heap(int[] nums) {
        this.data = Arrays.copyOf(nums, nums.length);
        this.size = this.data.length;
        heapify();
    }

    private void heapify() {
        int n = size, i = (size >> 1) - 1;
        for (; i >= 0; i--) {
            siftDown(i, data[i], n);
        }
    }

    public int[] sort() {
        Heap h = new Heap(this.data);
        for (int i = h.size() - 1; i > 0; i--) {
            swapInt(h.data(), 0, i);
            h.siftDown(0, h.data()[0], i);
        }
        return h.data();
    }

    public void offer(int x) {
        int k = size;
        size++;
        if (k == 0) {
            data[0] = x;
        } else {
            siftUp(k, x);
        }
    }

    public int poll() {
        size--;
        int n = size;
        int result = data[0];
        int x = data[n];
        if (n != 0) {
            siftDown(0, x, n);
        }
        return result;
    }

    private void siftDown(int k, int x, int n) {
        int half = n >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            int c = data[child];
            int right = child + 1;
            if (right < n && data[right] < c) {
                child = right;
                c = data[right];
            }
            if (x <= c) {
                break;
            }
            data[k] = c;
            k = child;
        }
        data[k] = x;
    }

    private void siftUp(int k, int x) {
        while (k > 0) {
            int parent = (k - 1) >> 1;
            int c = data[parent];
            if (x >= c) {
                break;
            }
            data[k] = c;
            k = parent;
        }
        data[k] = x;
    }

    public int size() {
        return size;
    }

    public int[] data() {
        return data;
    }
}
