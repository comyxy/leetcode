package leetcode;

/**
 * @since 2021/8/15 17:19
 */
public class DivideConquer {

    /**
     * 区间[l,r]
     */
    static class Status {
        /**
         * [l,r]中以l为左端点的最大子序和
         */
        int lSum;

        /**
         * [l,r]中以r为右端点的最大子序和
         */
        int rSum;

        /**
         * [l,r]中的最大子序和
         */
        int mSum;

        /**
         * [l,r]的区间和
         */
        int iSum;

        public Status(int lSum, int rSum, int mSum, int iSum) {
            this.lSum = lSum;
            this.rSum = rSum;
            this.mSum = mSum;
            this.iSum = iSum;
        }
    }

    public int maxSubArray(int[] nums) {
        return getInfo(nums, 0, nums.length - 1).mSum;
    }

    private Status getInfo(int[] nums, int l, int r) {
        if (l == r) {
            return new Status(nums[l], nums[l], nums[l], nums[l]);
        }
        int mid = (l + r) >> 1;
        Status lSub = getInfo(nums, l, mid);
        Status rSub = getInfo(nums, mid + 1, r);
        return pushUp(lSub, rSub);
    }

    private Status pushUp(Status l, Status r) {
        int iSum = l.iSum + r.iSum;
        int lSum = Math.max(l.lSum, l.iSum + r.lSum);
        int rSum = Math.max(r.rSum, r.iSum + l.rSum);
        int mSum = Math.max(Math.max(l.mSum, r.mSum), l.rSum + r.lSum);
        return new Status(lSum, rSum, mSum, iSum);
    }
}
