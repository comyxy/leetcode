package zhousai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class W17Test {

    W17 w = new W17();

    @Test
    void assignTasks() {
        int[] servers = new int[]{3,3,2};
        int[] tasks = new int[]{1,2,3,2,1,2};
        assertArrayEquals(new int[]{2,2,0,2,1,2}, w.assignTasks(servers, tasks));
    }

    @Test
    void minSkips() {
        int[] dist = new int[]{1,3,2};
        int speed = 4, hoursBefore = 2;
        assertEquals(1, w.minSkips(dist, speed, hoursBefore));
    }
}