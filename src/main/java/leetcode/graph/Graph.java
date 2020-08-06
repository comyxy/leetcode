package leetcode.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 2020/8/4
 */
public class Graph {

    /**
     * LeetCode207
     *
     * @param numCourses    课程数
     * @param prerequisites 限制矩阵 [[0,1]] 代表 想要学习课程 0 需要先完成课程 1
     * @return 能否学完课程
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        final int m = prerequisites.length;
        if (m == 0) {
            return true;
        }
        // 每一个节点的出度 
        int[] out = new int[numCourses];
        for (int i = 0; i < m; i++) {
            // 对于每一个限制条件 被限制的节点出度加1
            int limited = prerequisites[i][0];
            out[limited]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (out[i] == 0) {
                queue.offer(i);
                out[i]--;
            }
        }

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (int i = 0; i < m; i++) {
                int limited = prerequisites[i][0];
                int pre = prerequisites[i][1];
                // 先修课程已经修过
                if (pre == poll) {
                    out[limited]--;
                }
                if (out[limited] == 0) {
                    queue.offer(limited);
                    out[limited]--;
                }
            }
        }

        // 检查出度
        for (int i = 0; i < numCourses; i++) {
            if (out[i] > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 有向图的变
     */
    private List<List<Integer>> edges;
    /**
     * visited
     * 0 尚未探索
     * 1 正在探索 尚未回溯
     * 2 已经探索 回溯完成
     */
    private int[] visited;
    /**
     * 能否学完
     */
    private boolean valid = true;

    /**
     * LeetCode207 dfs
     *
     * @param numCourses    课程数
     * @param prerequisites 限制矩阵 [[0,1]] 代表 想要学习课程 0 需要先完成课程 1
     * @return 能否学完课程
     */
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        visited = new int[numCourses];
        for (int[] info : prerequisites) {
            // edge 1:2,3 代表 1->2 1->3 即1是2,3的先修课程
            edges.get(info[1])
                    .add(info[0]);
        }
        for (int i = 0; i < numCourses && valid; i++) {
            if (visited[i] == 0) {
                canFinish2dfs(i);
            }
        }
        return valid;
    }

    private void canFinish2dfs(int id) {
        // 探索中
        visited[id] = 1;
        List<Integer> nextIds = edges.get(id);
        for (int next : nextIds) {
            if(visited[next] == 0){
                // 探索下一个
                canFinish2dfs(next);
                if(!valid){
                    // 搜索过程中到了尚未回溯的节点 说明有环
                    return;
                }
            }else if(visited[next] == 1){
                // 搜索过程中到了尚未回溯的节点 说明有环
                valid = false;
                return;
            }
        }
        // 回溯
        visited[id] = 2;
    }

    /**
     * LeetCode207 bfs
     *
     * @param numCourses    课程数
     * @param prerequisites 限制矩阵 [[0,1]] 代表 想要学习课程 0 需要先完成课程 1
     * @return 能否学完课程
     */
    public boolean canFinish3(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        int[] indeg = new int[numCourses];
        for (int[] info : prerequisites) {
            // edge 1:2,3 代表 1->2 1->3 即1是2,3的先修课程
            edges.get(info[1])
                    .add(info[0]);
            indeg[info[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if(indeg[i] == 0){
                queue.offer(i);
            }
        }
        // 记录访问过的节点
        int visited = 0;
        while(!queue.isEmpty()){
            visited++;
            int u = queue.poll();
            for(int next : edges.get(u)){
                indeg[next]--;
                if(indeg[next] == 0){
                    queue.offer(next);
                }
            }
        }
        return visited == numCourses;
    }
}
