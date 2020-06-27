package leetcode.math;

/**
 * @date 2020/6/27
 */
public class Geometry {

    /**
     * LeetCode 1041 困于环中的机器人
     * @param instructions 指令 L左转90 R右转90 G前进1
     * @return
     */
    public boolean isRobotBounded(String instructions) {
        char[] ins = instructions.toCharArray();
        Robot robot = new Robot(0, 0, Heading.NORTH);
        for (char in : ins) {
            if(in == 'G'){
                robot.go();
            }else if(in == 'L'){
                robot.turnLeft();
            }else if(in == 'R'){
                robot.turnRight();
            }
        }
        if(robot.x == 0 && robot.y == 0){
            return true;
        }
        if(!robot.heading.equals(Heading.NORTH)){
            return true;
        }
        return false;
    }

    private static class Robot{
        private int x;
        private int y;
        private Heading heading;

        public Robot(int x, int y, Heading heading) {
            this.x = x;
            this.y = y;
            this.heading = heading;
        }

        public void go(){
            switch (heading){
                case NORTH: y++;break;
                case WEST: x--;break;
                case SOUTH: y--;break;
                case EAST: x++;break;
            }
        }

        public void turnLeft(){
            switch (heading){
                case NORTH: heading = Heading.WEST;break;
                case WEST: heading = Heading.SOUTH;break;
                case SOUTH: heading = Heading.EAST;break;
                case EAST: heading = Heading.NORTH;break;
            }
        }

        public void  turnRight(){
            switch (heading){
                case NORTH: heading = Heading.EAST;break;
                case EAST: heading = Heading.SOUTH;break;
                case SOUTH: heading = Heading.WEST;break;
                case WEST: heading = Heading.NORTH;break;
            }
        }
    }

    enum Heading{
        NORTH, WEST, SOUTH, EAST;
    }


    /**
     * 约瑟夫环问题
     * 递推公式 pn = (pn-1 + k ) % 当前人数i
     * @param n 总人数
     * @param k 第k个人出列
     * @return 安全位置
     */
    public int josephus(int n, int k){
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = (result + k) % i;
        }
        return result;
    }
}
