package struct;

/**
 * @since 2021/8/15 19:16
 * 线段树 索引从1开始
 */
public class SegmentTree {
    /**
     * 和
     */
    private final int[] sums;

    /**
     * 加法懒惰标志
     */
    private final int[] adds;

    /**
     * 原始数组右移1位
     */
    private final int[] nums;

    public SegmentTree(int[] nums) {
        final int n = nums.length;
        // 4 * n
        this.sums = new int[n << 2];
        this.adds = new int[n << 2];
        this.nums = new int[n + 1];
        System.arraycopy(nums, 0, this.nums, 1, n);
        buildSegmentTree(1, n, 1);
    }

    /**
     * 构建线段树 [left, right] 当前节点区间
     *
     * @param left  左侧 闭区间
     * @param right 右侧 闭区间
     * @param idx   线段树节点编号 从1开始
     */
    private void buildSegmentTree(int left, int right, int idx) {
        if (left == right) {
            sums[idx] = nums[left];
            return;
        }
        int mid = (left + right) >> 1;
        buildSegmentTree(left, mid, idx << 1);
        buildSegmentTree(mid + 1, right, (idx << 1) + 1);
        pushUp(idx);
    }

    public void add(int k, int v) {
        add(k, v, 1, nums.length - 1, 1);
    }

    /**
     * 单点增加
     *
     * @param k     nums[k] 原始数组索引
     * @param v     增加值
     * @param left  线段树当前节点区间 左侧 闭区间
     * @param right 线段树当前节点区间 右侧 闭区间
     * @param idx   线段树节点编号 从1开始
     */
    private void add(int k, int v, int left, int right, int idx) {
        if (left == right) {
            sums[idx] += v;
            return;
        }
        int mid = (left + right) >> 1;
        if (k <= mid) {
            add(k, v, left, mid, idx << 1);
        } else {
            add(k, v, mid + 1, right, (idx << 1) + 1);
        }
        // 左右子节点更新 父节点也更新
        pushUp(idx);
    }

    public void batchAdd(int l, int r, int v) {
        batchAdd(l, r, v, 1, nums.length - 1, 1);
    }

    /**
     * 区间增加 [l, r]
     *
     * @param l     操作区间左侧 闭区间
     * @param r     操作区间右侧 闭区间
     * @param v     增加值
     * @param left  线段树当前节点区间 左侧 闭区间
     * @param right 线段树当前节点区间 右侧 闭区间
     * @param idx   线段树节点编号 从1开始
     */
    private void batchAdd(int l, int r, int v, int left, int right, int idx) {
        if (l <= left && right <= r) {
            // [left, right]完全在[l, r]中
            sums[idx] += v * (right - left + 1);
            // 懒惰标记
            adds[idx] += v;
            return;
        }
        int mid = (left + right) >> 1;
        pushDown(idx, mid - left + 1, right - mid);
        if (l <= mid) {
            // 线段树左子节点区间[left, mid]与操作区间有交集
            batchAdd(l, r, v, left, mid, idx << 1);
        }
        if (r > mid) {
            batchAdd(l, r, v, mid + 1, right, (idx << 1) + 1);
        }
        pushUp(idx);
    }

    /**
     * 下移标记
     *
     * @param idx 线段树节点编号 从1开始
     * @param ln  线段树左子节点区间长度
     * @param rn  线段树右子节点区间长度
     */
    private void pushDown(int idx, int ln, int rn) {
        if (adds[idx] != 0) {
            // 下推懒惰标记
            adds[idx << 1] += adds[idx];
            adds[(idx << 1) + 1] += adds[idx];
            // 修改子节点的值
            sums[idx << 1] += ln * adds[idx];
            sums[(idx << 1) + 1] = rn * adds[idx];
            // 清除该节点懒惰标记
            adds[idx] = 0;
        }
    }

    private void pushUp(int idx) {
        sums[idx] = sums[idx << 1] + sums[(idx << 1) + 1];
    }

    public int query(int l, int r) {
        return query(l, r, 1, nums.length - 1, 1);
    }

    /**
     * 区间查询
     *
     * @param l     操作区间左侧 闭区间
     * @param r     操作区间右侧 闭区间
     * @param left  线段树当前节点区间 左侧 闭区间
     * @param right 线段树当前节点区间 右侧 闭区间
     * @param idx   线段树节点编号 从1开始
     * @return 区间和
     */
    private int query(int l, int r, int left, int right, int idx) {
        if (l <= left && right <= r) {
            // 操作区间完全包括在线段树当前节点区间内
            return sums[idx];
        }
        int mid = (left + right) >> 1;
        pushDown(idx, mid - left + 1, right - mid);

        int res = 0;
        if (l <= mid) {
            res += query(l, r, left, mid, idx << 1);
        }
        if (r > mid) {
            res += query(l, r, mid + 1, right, (idx << 1) + 1);
        }
        return res;
    }
}
