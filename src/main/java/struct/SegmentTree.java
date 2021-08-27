package struct;

/**
 * @since 2021/8/15 19:16
 * 线段树 索引从1开始
 */
public class SegmentTree {

    /**
     * 节点
     */
    private final Node[] tr;

    /**
     * 原始数组右移1位
     */
    private final int[] nums;

    public SegmentTree(int[] nums) {
        final int n = nums.length;
        // 4 * n
        this.tr = new Node[n << 2];
        for (int i = 0; i < (n << 2); i++) {
            tr[i] = new Node();
        }
        this.nums = new int[n + 1];
        System.arraycopy(nums, 0, this.nums, 1, n);
        buildSegmentTree(1, 1, n);
    }

    /**
     * 构建线段树 [l, r] 当前节点区间
     *
     * @param u 线段树节点编号 从1开始
     * @param l 左侧 闭区间
     * @param r 右侧 闭区间
     */
    private void buildSegmentTree(int u, int l, int r) {
        tr[u].left = l;
        tr[u].right = r;
        if (l == r) {
            tr[u].sum = nums[l];
            return;
        }
        int mid = (l + r) >> 1;
        buildSegmentTree(u << 1, l, mid);
        buildSegmentTree((u << 1) + 1, mid + 1, r);
        // 从底向上递归构建线段树
        pushUp(u);
    }

    public void add(int k, int v) {
        add(1, k, v);
    }

    /**
     * 单点增加
     *
     * @param u 线段树节点编号 从1开始
     * @param k nums[k] 原始数组索引
     * @param v 增加值
     */
    private void add(int u, int k, int v) {
        if (tr[u].left == tr[u].right) {
            tr[u].sum += v;
            return;
        }
        int mid = (tr[u].left + tr[u].right) >> 1;
        if (k <= mid) {
            add(u << 1, k, v);
        } else {
            add((u << 1) + 1, k, v);
        }
        // 左右子节点更新 父节点也更新
        pushUp(u);
    }

    public void batchAdd(int l, int r, int v) {
        batchAdd(1, l, r, v);
    }

    /**
     * 区间增加 [l, r]
     *
     * @param u 线段树节点编号 从1开始
     * @param l 操作区间左侧 闭区间
     * @param r 操作区间右侧 闭区间
     * @param v 增加值
     */
    private void batchAdd(int u, int l, int r, int v) {
        if (l <= tr[u].left && tr[u].right <= r) {
            // [left, right]完全在[l, r]中
            tr[u].sum += v * (tr[u].right - tr[u].left + 1);
            // 懒惰标记
            tr[u].add += v;
            return;
        }
        int mid = (tr[u].left + tr[u].right) >> 1;
        pushDown(u, mid - tr[u].left + 1, tr[u].right - mid);
        if (l <= mid) {
            // 线段树左子节点区间[left, mid]与操作区间有交集
            batchAdd(u << 1, l, r, v);
        }
        if (r > mid) {
            batchAdd((u << 1) + 1, l, r, v);
        }
        pushUp(u);
    }

    /**
     * 下移标记
     *
     * @param u  线段树节点编号 从1开始
     * @param ln 线段树左子节点区间长度
     * @param rn 线段树右子节点区间长度
     */
    private void pushDown(int u, int ln, int rn) {
        if (tr[u].add != 0) {
            // 下推懒惰标记
            tr[u << 1].add += tr[u].add;
            tr[(u << 1) + 1].add += tr[u].add;
            // 修改子节点的值
            tr[u << 1].sum += ln * tr[u].add;
            tr[(u << 1) + 1].sum = rn * tr[u].add;
            // 清除该节点懒惰标记
            tr[u].add = 0;
        }
    }

    private void pushUp(int u) {
        tr[u].sum = tr[u << 1].sum + tr[(u << 1) + 1].sum;
    }

    public int query(int l, int r) {
        return query(1, l, r);
    }

    /**
     * 区间查询
     *
     * @param u 线段树节点编号 从1开始
     * @param l 操作区间左侧 闭区间
     * @param r 操作区间右侧 闭区间
     * @return 区间和
     */
    private int query(int u, int l, int r) {
        if (l <= tr[u].left && tr[u].right <= r) {
            // 操作区间完全包括在线段树当前节点区间内
            return tr[u].sum;
        }
        int mid = (tr[u].left + tr[u].right) >> 1;
        pushDown(u, mid - tr[u].left + 1, tr[u].right - mid);

        int res = 0;
        if (l <= mid) {
            res += query(u << 1, l, r);
        }
        if (r > mid) {
            res += query((u << 1) + 1, l, r);
        }
        return res;
    }

    static class Node {
        int left;
        int right;
        int sum;
        int add;
    }
}
