package zhousai.s4;

import struct.Pair;
import struct.TreeNode;
import struct.Triple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @since 2021/9/25 14:59
 */
public class W33 {

    public int numColor(TreeNode root) {
        Set<Integer> s = new HashSet<>();
        numColorHelper(root, s);
        return s.size();
    }

    private void numColorHelper(TreeNode node, Set<Integer> s) {
        if (node == null) {
            return;
        }
        s.add(node.val);
        numColorHelper(node.left, s);
        numColorHelper(node.right, s);
    }

    public int[] volunteerDeployment(int[] finalCnt, long totalNum, int[][] edges, int[][] plans) {
        int n = finalCnt.length + 1;
        Map<Integer, List<Integer>> adjs = new HashMap<>();
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            List<Integer> u2v = adjs.computeIfAbsent(u, o -> new ArrayList<>());
            u2v.add(v);
            List<Integer> v2u = adjs.computeIfAbsent(v, o -> new ArrayList<>());
            v2u.add(u);
        }
        int[][] es = new int[n][2];
        es[0] = new int[]{1, 0};
        for (int i = 1; i < n; i++) {
            es[i] = new int[]{0, finalCnt[i - 1]};
        }
        for (int i = plans.length - 1; i >= 0; i--) {
            int[] plan = plans[i];
            int num = plan[0], idx = plan[1];
            if (num == 1) {
                es[idx][0] *= 2;
                es[idx][1] *= 2;
            } else if (num == 2) {
                List<Integer> vs = adjs.getOrDefault(idx, new ArrayList<>());
                for (Integer v : vs) {
                    es[v][0] -= es[idx][0];
                    es[v][1] -= es[idx][1];
                }
            } else {
                List<Integer> vs = adjs.getOrDefault(idx, new ArrayList<>());
                for (Integer v : vs) {
                    es[v][0] += es[idx][0];
                    es[v][1] += es[idx][1];
                }
            }
        }
        long a = 0, b = 0;
        for (int[] e : es) {
            a += e[0];
            b += e[1];
        }
        int x = (int) ((totalNum - b) / a);
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = es[i][0] * x + es[i][1];
        }
        return res;
    }

    /**
     * 1、若黑棋存在一步致胜的方案，则黑胜，否则2
     * 2a、若白棋存在多个一步制胜的落子位，则白胜
     * 2b、若白棋存在一个一步制胜的落子位，则黑先手必须堵这个位置
     * __3b、黑棋堵位后，黑棋存在多个一步制胜的落子位，则黑剩，否则平
     * 2c、若白棋不存在一步制胜的落子位
     * __3c、黑能落一子创造多个一步制胜的落子位，则黑胜，否则平
     */
    public String gobang(int[][] pieces) {
        final int[][] D = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        Map<Pair<Integer, Integer>, Integer> board = new HashMap<>();
        for (int[] piece : pieces) {
            board.put(Pair.of(piece[0], piece[1]), piece[2]);
        }

        List<List<List<Pair<Integer, Integer>>>> blacks = findLines(0, board, D);
        // 1.黑棋一步胜
        if (blacks.get(1).size() > 0) {
            return "Black";
        }

        List<List<List<Pair<Integer, Integer>>>> white = findLines(1, board, D);
        // 2a. 白棋一步胜
        Set<Pair<Integer, Integer>> positions = white.get(1).stream().map(o -> o.get(0)).collect(Collectors.toSet());
        if (positions.size() > 1) {
            return "White";
        }

        // 2b. 白棋只有一个落子可以胜 黑棋先手堵住
        if (positions.size() == 1) {
            for (Pair<Integer, Integer> p : positions) {
                board.put(p, 0);
            }
            blacks = findLines(0, board, D);
            // __3b. 黑棋堵位后 黑棋存在多个一步制胜的落子位则黑剩
            positions = blacks.get(1).stream().map(o -> o.get(0)).collect(Collectors.toSet());
            if (positions.size() > 1) {
                return "Black";
            } else {
                return "None";
            }
        }

        // 2c. 白棋不存在一步制胜的落子位
        // __3c. 黑能落一子创造多个一步制胜的落子则黑胜
        Set<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> pairs = blacks.get(2).stream()
                .map(o -> Pair.of(o.get(0), o.get(1))).collect(Collectors.toSet());
        positions.clear();
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> pair : pairs) {
            Pair<Integer, Integer> first = pair.getFirst(), second = pair.getSecond();
            if (positions.contains(first) || positions.contains(second)) {
                return "Black";
            }
            positions.add(first);
            positions.add(second);
        }
        return "None";
    }

    private List<List<List<Pair<Integer, Integer>>>> findLines(int color, Map<Pair<Integer, Integer>, Integer> board, int[][] D) {
        // start point and direction --> list of points need to put down (a line)
        // (1,1,right) -> [(1,2), (1,3)]
        Map<Triple<Integer, Integer, Integer>, List<Pair<Integer, Integer>>> lines = new HashMap<>();
        for (Map.Entry<Pair<Integer, Integer>, Integer> kvs : board.entrySet()) {
            // every (x,y)
            Pair<Integer, Integer> point = kvs.getKey();
            Integer c = kvs.getValue();
            if (c != color) {
                continue;
            }
            int x = point.getFirst(), y = point.getSecond();
            for (int i = 0; i < D.length; i++) {
                // 4 directions/lines
                int dx = D[i][0], dy = D[i][1];
                for (int j = 0; j < 3; j++) {
                    // consider (x,y), (x-dx,y-dy), (x-2dx,y-2dy)
                    int nx = x - dx * j, ny = y - dy * j;
                    //  key: start point and direction
                    Triple<Integer, Integer, Integer> xyp = new Triple<>(nx, ny, i);
                    if (lines.containsKey(xyp)) {
                        continue;
                    }
                    // from start point, need 5 points
                    for (int k = 0; k < 5; k++) {
                        c = board.getOrDefault(Pair.of(nx, ny), -1);
                        if (c != color) {
                            if (c >= 0 || (lines.containsKey(xyp) && lines.get(xyp).size() >= 2)) {
                                // contradictory color or
                                // blank position more than 2
                                lines.remove(xyp);
                                break;
                            }
                            List<Pair<Integer, Integer>> ps = lines.computeIfAbsent(xyp, o -> new ArrayList<>());
                            // add the position
                            ps.add(Pair.of(nx, ny));
                        }
                        nx += dx;
                        ny += dy;
                    }
                }
            }
        }
        List<List<List<Pair<Integer, Integer>>>> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            res.add(new ArrayList<>());
        }
        // category into 1, 2 points (list of lines)
        for (List<Pair<Integer, Integer>> vs : lines.values()) {
            if (vs.size() > 0) {
                res.get(vs.size()).add(vs);
            }
        }
        return res;
    }

}
