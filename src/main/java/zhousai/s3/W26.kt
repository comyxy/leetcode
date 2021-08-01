package zhousai.s3

import kotlin.math.min

/**
 * @since 2021/8/1 18:20
 * https://leetcode-cn.com/contest/weekly-contest-252/
 */
class W26 {
    fun isThree(n: Int): Boolean {
        var count = 0
        for (i in 1..n) {
            if (i * i > n) {
                break
            }
            if (n % i == 0) {
                count += if (n / i != i) 2 else 1
            }
            if (count == 3) {
                return true
            }
        }
        return false
    }

    fun numberOfWeeks(milestones: IntArray): Long {
        var maxValue: Long = 0
        var sumValue: Long = 0
        for (milestone in milestones) {
            if (milestone > maxValue) {
                maxValue = milestone.toLong()
            }
            sumValue += milestone
        }
        val remain = sumValue - maxValue
        return min(sumValue, 2 * remain + 1)
    }

    fun minimumPerimeter(neededApples: Long): Long {
        var lo = 1L;
        var hi = 100000L
        while (lo <= hi) {
            val mid = (lo + hi) / 2
            val total = 2 * mid * (mid + 1) * (2 * mid + 1)
            if (total >= neededApples) {
                hi = mid - 1
            } else {
                lo = mid + 1
            }
        }
        return lo * 8
    }

    companion object Static {
        const val MOD = 1000000007
    }

    fun countSpecialSubsequences(nums: IntArray): Int {
        var zero = 0L; var one = 0L; var two = 0L
        for (num in nums) {
            when (num) {
                0 -> {zero += zero + 1; zero %= MOD}
                1 -> {one += one + zero; one %= MOD}
                2 -> {two += two + one; two %= MOD}
            }
        }
        return two.toInt()
    }
}