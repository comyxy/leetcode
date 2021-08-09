package sort;

/**
 * @since 2021/8/8 17:13
 */
public class Main {
    public static void main(String[] args) {

        int[] nums = new int[]{10, 2, 42, 52, 21, 43, 1, 32, 464, 215, 434, 2124};
        final int n = nums.length;
        Heap heap = new Heap(11);
        for (int num : nums) {
            heap.offer(num);
            if (heap.size > 10) {
                heap.poll();
            }
        }
        while (heap.size > 0) {
            int val = heap.poll();
            System.out.println(val);
        }

    }

    static class Heap {
        int size;
        int[] data;

        Heap(int cap) {
            data = new int[cap];
        }

        void offer(int x) {
            int i = size;
            size++;
            if (i == 0) {
                data[0] = x;
            } else {
                siftUp(i, x);
            }
        }

        int poll() {
            size--;
            int i = size;
            int x = data[0];
            int c = data[i];
            if (i > 0) {
                // sift down from root 0
                siftDown(0, c, i);
            }
            return x;
        }

        void siftDown(int k, int x, int i) {
            int half = i >> 1;
            while (k < half) {
                int child = (k << 1) + 1;
                int c = data[child];
                int right = child + 1;
                if (right < i && data[right] < c) {
                    c = data[right];
                    child = right;
                }
                if (x <= c) {
                    break;
                }
                data[k] = c;
                k = child;
            }
            data[k] = x;
        }

        void siftUp(int i, int x) {
            while (i > 0) {
                int parent = (i - 1) >> 1;
                int c = data[parent];
                if (x >= c) {
                    break;
                }
                data[i] = c;
                i = parent;
            }
            data[i] = x;
        }
    }
}
