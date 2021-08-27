package backtrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * N皇后问题
 *
 * @date 2020/6/12
 */
public class NQueen {

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        char[][] boards = new char[n][n];
        for (char[] board : boards) {
            Arrays.fill(board, '.');
        }
        backtrace(res, boards, 0);
        return res;
    }

    private void backtrace(List<List<String>> res, char[][] boards, int row) {
        if (row == boards.length) {
            List<String> list = new ArrayList<>();
            for (char[] board : boards) {
                list.add(new String(board));
            }
            res.add(list);
            return;
        }

        for (int col = 0; col < boards[row].length; col++) {
            if (!isValid(boards, row, col)) {
                continue;
            }

            boards[row][col] = 'Q';
            backtrace(res, boards, row + 1);
            boards[row][col] = '.';
        }
    }

    private boolean isValid(char[][] boards, int row, int col) {
        final int n = boards.length;
//        for (char[] board : boards) {
//            if (board[col] == 'Q') {
//                return false;
//            }
//        }
        for(int i = row - 1; i >= 0; i--){
            if(boards[i][col] == 'Q'){
                return false;
            }
        }

        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (boards[i][j] == 'Q') {
                return false;
            }
        }

        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if(boards[i][j] == 'Q'){
                return false;
            }
        }

        return true;
    }


}
