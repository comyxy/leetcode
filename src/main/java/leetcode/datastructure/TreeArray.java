package leetcode.datastructure;

/**
 * 树状数组 前缀和
 *
 * @date 2020/7/11
 */
public class TreeArray {
    private final int[] c;

    public TreeArray(int size) {
        c = new int[size + 1];
    }

    public void update(int i, int v){
        while(i < c.length){
            c[i] += v;
            i += lowBit(i);
        }
    }

    public int query(int i){
        int res = 0;
        while(i > 0){
            res += c[i];
            i -= lowBit(i);
        }
        return res;
    }

    private int lowBit(int x) {
        return x & (-x);
    }

}
