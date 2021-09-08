package zhousai.s4;

import java.util.*;

import static utils.EasyUtil.MOD;

/**
 * @since 2021/9/5 10:31
 */
public class W31 {

    public int countQuadruplets(int[] nums) {
        int res = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (nums[i] + nums[j] + nums[k] == nums[l]) {
                            res++;
                        }
                    }
                }
            }
        }
        return res;
    }

    public int numberOfWeakCharacters(int[][] properties) {
        final int n = properties.length;
        Arrays.sort(properties, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            }
            return o2[1] - o1[1];
        });
        int maxDefense = 0;
        int res = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (maxDefense != 0 && maxDefense > properties[i][1]) {
                res++;
            }
            if (i > 0 && properties[i][0] == properties[i - 1][0]) {
                continue;
            }
            if (maxDefense < properties[i][1]) {
                maxDefense = properties[i][1];
            }
        }
        return res;
    }

    public int firstDayBeenInAllRooms(int[] nextVisit) {
        int n = nextVisit.length;
        int[] dp = new int[n - 1];
        // dp[i] 表示第一次访问i后 再次访问i所需要的天数 i --> nextVisit[i] (j) --> i
        // dp[i] = i-j+1 + sum(dp[k) from j -> i-1
        int[] sum = new int[n];
        // 前缀和 sum[i+1] = sum[i]+dp[i] sum[0] = 0
        for (int i = 0; i < n - 1; i++) {
            // j <= i
            int j = nextVisit[i];
            dp[i] = (dp[i] + (i - j + 1)) % MOD;
            dp[i] = (dp[i] + sum[i] - sum[j] + MOD) % MOD;
            sum[i + 1] = (sum[i] + dp[i]) % MOD;
        }
        return (sum[n - 1] + (n - 2 + 1)) % MOD;
    }

    public boolean gcdSort(int[] nums) {
        int n = nums.length;
        // 约数 -> 能够除以这个约数的数的索引的列表
        Map<Integer, List<Integer>> eles = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j * j <= nums[i]; j++) {
                List<Integer> idxes;
                if (nums[i] % j == 0) {
                    idxes = eles.computeIfAbsent(j, (dummy) -> new ArrayList<>());
                    idxes.add(i);
                    if (nums[i] / j != j) {
                        idxes = eles.computeIfAbsent(nums[i] / j, (dummy) -> new ArrayList<>());
                        idxes.add(i);
                    }
                }
            }
        }
        UF uf = new UF(n);
        for (Map.Entry<Integer, List<Integer>> kvs : eles.entrySet()) {
            Integer k = kvs.getKey();
            if (k <= 1) {
                continue;
            }
            List<Integer> idxes = kvs.getValue();
            for (int i = 0; i < idxes.size() - 1; i++) {
                uf.union(idxes.get(i), idxes.get(i+1));
            }
        }
        // 索引parent -> 属于同一组的索引列表
        Map<Integer, List<Integer>> components = uf.components();
        int[] nn = new int[n];
        for (Map.Entry<Integer, List<Integer>> kvs : components.entrySet()) {
            List<Integer> idxes = kvs.getValue();
            List<Integer> vv = new ArrayList<>(idxes);
            // 排序索引 根据索引对应值的大小顺序
            vv.sort(Comparator.comparingInt(o -> nums[o]));
            for (int i = 0; i < vv.size(); i++) {
                nn[idxes.get(i)] = nums[vv.get(i)];
            }
        }
        for (int i = 0; i < n - 1; i++) {
            if (nn[i + 1] < nn[i]) {
                return false;
            }
        }
        return true;
    }

    static class UF {
        int[] parents;
        int[] ranks;
        int size;

        UF(int n) {
            this.size = n;
            this.parents = new int[n];
            this.ranks = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
                ranks[i] = 1;
            }
        }

        int find(int x) {
            if (x == parents[x]) {
                return parents[x];
            }
            parents[x] = find(parents[x]);
            return parents[x];
        }

        void union(int x, int y) {
            int px = find(x), py = find(y);
            if(px == py) {
                return;
            }
            if (ranks[px] > ranks[py]) {
                ranks[px] += ranks[py];
                parents[py] = px;
            } else {
                ranks[py] += ranks[px];
                parents[px] = py;
            }
        }

        Map<Integer, List<Integer>> components() {
            Map<Integer, List<Integer>> res = new HashMap<>();
            for (int i = 0; i < size; i++) {
                List<Integer> idxes = res.computeIfAbsent(find(i), (dummy) -> new ArrayList<>());
                idxes.add(i);
            }
            return res;
        }
    }
}
