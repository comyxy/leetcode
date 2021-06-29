package zhousai.s2;

/**
 * @author comyxy
 */
public class W15 {
    public int getMinDistance(int[] nums, int target, int start) {
        int dis = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num != target) {
                continue;
            }
            if (Math.abs(i - start) < dis) {
                dis = Math.abs(i - start);
            }
        }
        return dis;
    }

    public boolean splitString(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            String sub = s.substring(0, i + 1);
            if (canSplit(s.substring(i + 1), sub, false)) {
                return true;
            }
        }
        return false;
    }

    private boolean canSplit(String s, String val, boolean can) {
        int len = s.length();
        if (len == 0 && can) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            String sub = s.substring(0, i + 1);
            // 比较两个string大小 val与sub
            int vl = val.length(), vs = sub.length();
            long d = 0, p = 1;
            int j = vl - 1, k = vs - 1;
            for (; j >= 0 || k >= 0; j--, k--) {
                int k1 = j >= 0 ? val.charAt(j) - '0' : 0;
                int k2 = k >= 0 ? s.charAt(k) - '0' : 0;
                int key = k1 - k2;
                d += p * key;
                p *= 10;
                if (d != 1 && d != 1 - p && d != 0) {
                    break;
                }
            }
            if (d != 1) {
                continue;
            }
            if (canSplit(s.substring(i + 1), sub, true)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        W15 w = new W15();

        String s = "200100";
        System.out.println(w.splitString(s));
    }
}
