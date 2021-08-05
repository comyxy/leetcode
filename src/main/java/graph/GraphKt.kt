package graph

import java.util.*
import kotlin.math.max

/**
 * @since 2021/8/2 20:05
 */
class GraphKt {
    /**
     * https://leetcode-cn.com/problems/network-delay-time/
     */
    fun networkDelayTime(times: Array<IntArray>, n: Int, k: Int): Int {
        val dis = IntArray(n + 1) { _ -> -1 }
        val pq = PriorityQueue<Pair<Int, Int>> { a, b -> a.first - b.first }
        dis[k] = 0
        pq.offer(Pair(0, k))
        while (!pq.isEmpty()) {
            val p = pq.poll()
            val u = p.second; val t = p.first
            for (time in times) {
                if (time[0] == u) {
                    val v = time[1]; val w = time[2]
                    if (dis[v] == -1 || t + w < dis[v]) {
                        dis[v] = t + w
                        pq.offer(Pair(t + w, v))
                    }
                }
            }
        }
        var res = 0
        for(i in 1 until n + 1) {
            if (dis[i] == -1) {
                return -1
            }
            res = max(res, dis[i])
        }
        return res
    }

}

fun main() {
    val g = GraphKt()
    val times = arrayOf(intArrayOf(2,1,1), intArrayOf(2,3,1), intArrayOf(3,4,1))
    val k = 2; val n = 4
    val ret = g.networkDelayTime(times, n, k)
    println(ret)
}