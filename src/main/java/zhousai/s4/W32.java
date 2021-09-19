package zhousai.s4;

import struct.Pair;

import java.util.*;

/**
 * @since 2021/9/11 14:50
 */
public class W32 {

    public int minimumSwitchingTimes(int[][] source, int[][] target) {
        Map<Integer, Integer> mp = new HashMap<>();
        for (int[] row : source) {
            for (int ele : row) {
                Integer val = mp.getOrDefault(ele, 0);
                mp.put(ele, val + 1);
            }
        }
        int res = 0;
        for (int[] row : target) {
            for (int ele : row) {
                if (!mp.containsKey(ele)) {
                    res++;
                } else {
                    Integer val = mp.get(ele);
                    val--;
                    if (val == 0) {
                        mp.remove(ele);
                    } else {
                        mp.put(ele, val);
                    }
                }
            }
        }
        return res;
    }

    public int maxmiumScore(int[] cards, int cnt) {
        List<Integer> odd = new ArrayList<>();
        List<Integer> even = new ArrayList<>();
        for (int card : cards) {
            if (card % 2 == 0) {
                even.add(card);
            } else {
                odd.add(card);
            }
        }
        odd.sort((o1, o2) -> o2 - o1);
        even.sort((o1, o2) -> o2 - o1);
        int res = 0;
        int i = 0, j = 0;
        while (cnt > 0) {
            if (cnt % 2 == 1) {
                if (even.size() == 0) {
                    break;
                }
                res += even.get(j);
                j += 1;
                cnt -= 1;
            } else {
                int t1 = 0, t2 = 0;
                if (i + 1 < odd.size()) {
                    t1 = odd.get(i) + odd.get(i + 1);
                }
                if (j + 1 < even.size()) {
                    t2 = even.get(j) + even.get(j + 1);
                }
                if (t1 == 0 && t2 == 0) {
                    return 0;
                }
                if (t1 > t2) {
                    res += t1;
                    i += 2;
                } else {
                    res += t2;
                    j += 2;
                }
                cnt -= 2;
            }
        }
        return res;
    }

    public int flipChess(String[] chessboard) {
        final int m = chessboard.length, n = chessboard[0].length();
        int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (chessboard[i].charAt(j) == 'O') {
                    for (int k = 0; k < 8; k++) {
                        int ni = dirs[k][0] + i, nj = dirs[k][1] + j;
                        if (ni >= 0 && ni < m && nj >= 0 && nj < n && chessboard[ni].charAt(nj) == '.') {
                            char[][] cp = new char[m][n];
                            for (int l = 0; l < m; l++) {
                                for (int o = 0; o < n; o++) {
                                    cp[l][o] = chessboard[l].charAt(o);
                                }
                            }
                            int ans = 0;
                            Queue<int[]> queue = new LinkedList<>();
                            queue.offer(new int[]{ni, nj});
                            while (!queue.isEmpty()) {
                                int[] p = queue.poll();
                                for (int l = 0; l < 8; l++) {
                                    int tmp = 0;
                                    int mi = p[0] + dirs[l][0], mj = p[1] + dirs[l][1];
                                    while (mi >= 0 && mi < m && mj >= 0 && mj < n) {
                                        if (cp[mi][mj] == 'O') {
                                            tmp++;
                                        } else if (cp[mi][mj] == 'X') {
                                            ans += tmp;
                                            for (int o = 0; o < tmp; o++) {
                                                queue.offer(new int[]{mi - (o + 1) * dirs[l][0], mj - (o + 1) * dirs[l][1]});
                                                cp[mi - (o + 1) * dirs[l][0]][mj - (o + 1) * dirs[l][1]] = 'X';
                                            }
                                            break;
                                        } else {
                                            break;
                                        }
                                        mi += dirs[l][0];
                                        mj += dirs[l][1];
                                    }
                                }
                            }
                            res = Math.max(res, ans);
                        }
                    }
                }
            }
        }
        return res;
    }

    public int circleGame(int[][] toys, int[][] circles, int r) {
        Map<Pair<Integer, Integer>, Integer> mp = new HashMap<>();
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        for (int[] toy : toys) {
            set.add(Pair.of(toy[0], toy[1]));
        }
        for (int[] circle : circles) {
            int x = circle[0], y = circle[1];
            for (int i = x - r; i <= x; i++) {
                for (int j = y; j >= y - r; j--) {
                    double d = r - Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
                    if (d < 0) {
                        break;
                    }
                    int ans = (int) d;
                    add(mp, set, i, j, ans);
                    add(mp, set, 2 * x - i, j, ans);
                    add(mp, set, i, 2 * y - j, ans);
                    add(mp, set, 2 * x - i, 2 * y - j, ans);
                }
            }

        }
        int res = 0;
        for (int[] toy : toys) {
            if (mp.getOrDefault(Pair.of(toy[0], toy[1]), 0) >= toy[2]) {
                res++;
            }
        }
        return res;
    }

    private void add(Map<Pair<Integer, Integer>, Integer> mp, Set<Pair<Integer, Integer>> set, int i, int j, int ans) {
        Pair<Integer, Integer> a = Pair.of(i, j);
        if (set.contains(a)) {
            if (mp.containsKey(a)) {
                mp.put(a, Math.max(mp.get(a), ans));
            } else {
                mp.put(a, ans);
            }
        }
    }
}
