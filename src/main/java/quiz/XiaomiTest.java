package quiz;

import java.util.Scanner;

import static utils.EasyUtil.MOD;

/**
 * @since 2021/9/1 9:49
 */
public class XiaomiTest {
    public static void main(String[] args) {
//        q1();
        q2();
    }

    private static void q2() {
        char[][] board = {{'X', 'Y', 'Z', 'E'}, {'S', 'F', 'Z', 'S'}, {'X', 'D', 'E', 'E'}};
        String word = "XYZZED";
        XiaomiTest xiaomi = new XiaomiTest();
        boolean exist = xiaomi.exist(board, word);
        System.out.println(exist);
    }

    private static void q1() {
        Scanner in = new Scanner(System.in);
        String[] cs = in.nextLine().split(" ");
        int n = Integer.parseInt(cs[0]);
        int m = Integer.parseInt(cs[1]);
        int[] nums = new int[n];
        for (int i = 0; i < m; i++) {
            cs = in.nextLine().split(" ");
            int l = Integer.parseInt(cs[0]);
            int r = Integer.parseInt(cs[1]);
            for (int j = l; j <= r; j++) {
                nums[j] = i + 1;
            }
        }
    }

    private static final int[][] DIRS = {{-1,0},{1,0},{0,-1},{0,1}};

    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        boolean[][] used = new boolean[m][n];
        char[] cs = word.toCharArray();
        int sc = word.charAt(0);
        boolean res = false;
        for(int i=0;i<m;i++) {
            for(int j=0;j<n;j++) {
                if (board[i][j] == sc) {
                    used[i][j] = true;
                    res = dfs(i, j, 1, board, cs, used);
                    used[i][j] = false;
                }
                if (res) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean dfs(int i, int j, int depth, char[][] board, char[] cs, boolean[][] used) {
        if (depth == cs.length) {
            return true;
        }
        int m = board.length, n = board[0].length;
        boolean res = false;
        for(int k=0;k<4;k++) {
            int ni = i + DIRS[k][0], nj = j + DIRS[k][1];
            if (ni >= 0 && ni < m && nj >= 0 && nj < n && !used[ni][nj]) {
                if (board[ni][nj] == cs[depth]) {
                    used[ni][nj] = true;
                    res = dfs(ni, nj, depth + 1, board, cs, used);
                    used[ni][nj] = false;
                }
                if (res) {
                    return true;
                }
            }
        }
        return false;
    }
}
