package zhousai;

import java.util.*;

/** 2020/11/10 */
public class W11 {
  public int getMaximumGenerated(int n) {
    if (n < 1) {
      return 0;
    }
    int max = 1;
    int[] nums = new int[n + 1];
    nums[0] = 0;
    nums[1] = 1;
    for (int i = 2; i <= n; i++) {
      if ((i & 1) == 0) {
        nums[i] = nums[i / 2];
      } else {
        nums[i] = nums[i / 2] + nums[i / 2 + 1];
      }
      max = Math.max(max, nums[i]);
    }
    return max;
  }

  public int minDeletions(String s) {
    Map<Character, Integer> catToTimesMap = new HashMap<>();
    int maxTime = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      catToTimesMap.put(c, catToTimesMap.getOrDefault(c, 0) + 1);
      maxTime = Math.max(maxTime, catToTimesMap.get(c));
    }
    int[] times = new int[maxTime + 1];
    for (Integer time : catToTimesMap.values()) {
      times[time]++;
    }

    int ans = 0;
    for (int i = maxTime; i >= 1; i--) {
      int t = times[i];
      if (t > 1) {
        ans += t - 1;
        times[i - 1] += t - 1;
      }
    }
    return ans;
  }

  private static final int MOD = 1000000007;

  private long rangeSum(int x, int y) {
    return (long) (x + y) * (y - x + 1) / 2;
  }

  public int maxProfit(int[] inventory, int orders) {
    int l = 0, r = Arrays.stream(inventory).max().getAsInt();
    int T = -1;
    while (l <= r) {
      int mid = (l + r) / 2;
      long total = Arrays.stream(inventory).mapToLong(ai -> Math.max(ai - mid, 0)).sum();
      if (total <= orders) {
        T = mid;
        r = mid - 1;
      } else {
        l = mid + 1;
      }
    }
    int finalT = T;
    // 大于等于T的值变为T-1的个数=总次数-大于等于T的值全部变为T时的次数
    int rest = orders - Arrays.stream(inventory).map(ai -> Math.max(ai - finalT, 0)).sum();
    long ans = 0;
    for (int ai : inventory) {
      if (ai >= T) {
        if (rest > 0) {
          ans += rangeSum(T, ai);
          rest--;
        } else {
          ans += rangeSum(T + 1, ai);
        }
      }
    }
    return (int) (ans % MOD);
  }

  public int createSortedArray(int[] instructions) {
    // int max = Arrays.stream(instructions).max().getAsInt();

    // 压缩空间
    int n = instructions.length;
    int[] tmp = new int[n];
    System.arraycopy(instructions, 0, tmp, 0, n);
    Arrays.sort(tmp);
    for (int i = 0; i < n; i++) {
      instructions[i] = Arrays.binarySearch(tmp, instructions[i]) + 1;
    }

    BIT bit = new BIT(n);
    long ans = 0;
    for (int i = 0; i < instructions.length; i++) {
      int v = instructions[i];
      // 比v小的数有多少个
      int num1 = bit.query(v - 1);
      // 比v大的数有多少个 = 总个数 - 小于等于v的个数
      int num2 = i - bit.query(v);
      ans += Math.min(num1, num2);
      bit.update(v, 1);
    }
    return (int) (ans % MOD);
  }

  static class BIT {
    int[] tree;
    int n;

    public BIT(int n) {
      this.n = n;
      this.tree = new int[n + 1];
    }

    public static int lowbit(int x) {
      return (x) & (-x);
    }

    public int query(int i) {
      int ans = 0;
      while (i > 0) {
        ans += tree[i];
        i -= lowbit(i);
      }
      return ans;
    }

    public void update(int i, int x) {
      while (i <= n) {
        tree[i] += x;
        i += lowbit(i);
      }
    }
  }

  public int[] sortByBits(int[] arr) {
    int[] cmp = Arrays.stream(arr).map(Integer::bitCount).toArray();
    sort(arr, 0, arr.length - 1, cmp);
    return arr;
  }

  private void sort(int[] arr, int lo, int hi, int[] cmp) {
    if (hi <= lo) return;
    int j = partition(arr, lo, hi, cmp);
    sort(arr, lo, j - 1, cmp);
    sort(arr, j + 1, hi, cmp);
  }

  private int partition(int[] arr, int lo, int hi, int[] cmp) {
    int i = lo, j = hi + 1;
    int v = cmp[lo], x = arr[lo];
    while (true) {
      while (less(cmp[++i], arr[i], v, x)) if (i == hi) break;
      while (less(v, x, cmp[--j], arr[j])) if (j == lo) break;
      if (i >= j) break;
      swap(cmp, i, j);
      swap(arr, i, j);
    }
    swap(cmp, lo, j);
    swap(arr, lo, j);
    return j;
  }

  private boolean less(int cv, int cx, int v, int x) {
    if (cv < v) {
      return true;
    } else if (cv > v) {
      return false;
    } else {
      return cx < x;
    }
  }

  private void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  /** LeetCode327 通过前缀和以及归并求 */
  public int countRangeSum(int[] nums, int lower, int upper) {
    // 前序和
    long s = 0;
    long[] sum = new long[nums.length + 1];
    for (int i = 0; i < nums.length; ++i) {
      s += nums[i];
      sum[i + 1] = s;
    }
    return mergeSortToCountRangeSum(sum, lower, upper, 0, sum.length - 1);
  }

  private int mergeSortToCountRangeSum(long[] sum, int lower, int upper, int lo, int hi) {
    if (hi <= lo) {
      return 0;
    }
    int mid = (lo + hi) / 2;
    int n1 = mergeSortToCountRangeSum(sum, lower, upper, lo, mid);
    int n2 = mergeSortToCountRangeSum(sum, lower, upper, mid + 1, hi);
    int ret = n1 + n2;

    // 统计满足条件的
    int i = lo, l = mid + 1, r = mid + 1;
    while (i <= mid) {
      while (l <= hi && sum[l] - sum[i] < lower) {
        l++;
      }
      while (r <= hi && sum[r] - sum[i] <= upper) {
        r++;
      }
      // [l, r)
      ret += r - l;
      i += 1;
    }

    // 并
    long[] tmp = new long[hi - lo + 1];
    int p1 = lo, p2 = mid + 1;
    int k = 0;
    while (p1 <= mid || p2 <= hi) {
      if (p1 > mid) {
        tmp[k++] = sum[p2++];
      } else if (p2 > hi) {
        tmp[k++] = sum[p1++];
      } else if (sum[p1] < sum[p2]) {
        tmp[k++] = sum[p1++];
      } else {
        tmp[k++] = sum[p2++];
      }
    }
    if (hi + 1 - lo >= 0) System.arraycopy(tmp, 0, sum, lo, hi + 1 - lo);

    return ret;
  }

  /** LeetCode327 通过前缀和以及树状数组求 */
  public int countRangeSum2(int[] nums, int lower, int upper) {
    // 前序和
    long s = 0;
    long[] sum = new long[nums.length + 1];
    for (int i = 0; i < nums.length; ++i) {
      s += nums[i];
      sum[i + 1] = s;
    }
    Set<Long> set = new TreeSet<>();
    for (long l : sum) {
      set.add(l);
      set.add(l - lower);
      set.add(l - upper);
    }
    Map<Long, Integer> sims = new HashMap<>();
    int idx = 1;
    for (Long l : set) {
      sims.put(l, idx);
      idx++;
    }

    BIT bit = new BIT(sims.size());
    int ans = 0;
    for (int i = 0; i < sum.length; i++) {
      int left = sims.get(sum[i] - upper), right = sims.get(sum[i] - lower);
      ans += bit.query(right) - bit.query(left - 1);
      bit.update(sims.get(sum[i]),1);
    }
    return ans;
  }

  public static void main(String[] args) {
    W11 w = new W11();

    int countRangeSum = w.countRangeSum2(new int[]{-2, 5, -1}, -2, 2);
    System.out.println(countRangeSum);
  }
}
