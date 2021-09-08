package quiz;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @since 2021/8/22 9:48
 */
public class Meituan {
    private static final int MOD = 1000000007;

    public static void main(String[] args) throws InterruptedException {
        Meituan meituan = new Meituan();
        Thread a = new Thread(() -> {
            try {
                meituan.a();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread b = new Thread(() -> {
            try {
                meituan.b();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ;
        });
        a.start();
        b.start();
        a.join();
        b.join();
    }

    public static synchronized void a() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            System.out.println("a" + i);
        }
    }

    public void b() throws InterruptedException {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(100);
                System.out.println("b" + i);
            }
        }
    }

    private static void q3() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String replace = s.replace("()", "2");
        System.out.println(replace);
        Deque<Character> ops = new LinkedList<>();
        Deque<long[]> nums = new LinkedList<>();
        long l = 0;
        for (int i = 0; i < replace.length(); i++) {
            char cur = replace.charAt(i);
            if (cur == '(') {
                ops.push(cur);
                l++;
            } else if (cur == ')') {
                ops.pop();
                long res = 1L;
                if (nums.peek() != null) {
                    long level = nums.peek()[1];
                    while (nums.peek() != null && nums.peek()[1] == level) {
                        long p = nums.pop()[0];
                        res = (res * p) % MOD;
                    }
                    res = (res + 1) % MOD;
                    nums.push(new long[]{res, level - 1});
                }
                l--;
            } else {
                nums.push(new long[]{2L, l});
            }
        }
        long res = 1L;
        while (!nums.isEmpty()) {
            res = (res * nums.pop()[0]) % MOD;
        }
        System.out.println(res);
    }

    private static void q2() {
        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine();
        int[] idxes = new int[26];
        Arrays.fill(idxes, -1);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int ld = idxes[c - 'a'];
            if (ld != -1) {
                Integer o = map.getOrDefault(ld, Integer.MAX_VALUE);
                if (i - ld < o) {
                    map.put(ld, i - ld);
                }
                map.put(i, i - ld);
            }
            idxes[c - 'a'] = i;
        }
        int n = Integer.parseInt(scanner.nextLine());
        int j = word.length();
        for (int i = 0; i < n; i++) {
            String[] cs = scanner.nextLine().split(" ");
            int opt = Integer.parseInt(cs[0]);
            if (opt == 1) {
                char c = cs[1].charAt(0);
                int ld = idxes[c - 'a'];
                if (ld != -1) {
                    Integer o = map.getOrDefault(ld, Integer.MAX_VALUE);
                    if (j - ld < o) {
                        map.put(ld, j - ld);
                    }
                    map.put(j, j - ld);
                }
                idxes[c - 'a'] = j;
                j++;
            } else {
                int c = Integer.parseInt(cs[1]) - 1;
                System.out.println(map.getOrDefault(c, -1));
            }
        }
    }

    private static void hepler(List<List<Integer>> res, int[] nums, int i) {
        if (i >= nums.length) {
            List<Integer> tmp = new ArrayList<>();
            for (int num : nums) {
                tmp.add(num);
            }
            res.add(tmp);
        }
        for (int j = i; j < nums.length; j++) {
            swap(nums, j, i);
            hepler(res, nums, i + 1);
            swap(nums, j, i);
        }
    }

    private static void swap(int[] nums, int j, int i) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
