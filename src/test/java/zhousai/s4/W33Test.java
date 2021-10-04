package zhousai.s4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @since 2021/9/25 15:42
 */
class W33Test {

    W33 w = new W33();

    @Test
    void volunteerDeployment() {
        int[] f = {1, 16};
        int t = 21;
        int[][] edges = {{0, 1}, {1, 2}};
        int[][] plans = {{2, 1}, {1, 0}, {3, 0}};
        int[] result = w.volunteerDeployment(f, t, edges, plans);
        for (int r : result) {
            System.out.println(r);
        }
    }

    @Test
    void gobang() {
//        int[][] pieces = {{1, 2, 1}, {1, 4, 1}, {1, 5, 1}, {2, 1, 0}, {2, 3, 0}, {2, 4, 0}, {3, 2, 1}, {3, 4, 0}, {4, 2, 1}, {5, 2, 1}};
        int[][] pieces = {{0,0,1},{0,1,0},{0,3,0},{0,4,0},{0,7,0},{0,8,1}};
        String gobang = w.gobang(pieces);
        assertEquals("Black", gobang);
    }
}