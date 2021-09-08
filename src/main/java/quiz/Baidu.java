package quiz;

import java.util.Scanner;

/**
 * @since 2021/9/7 18:57
 */
public class Baidu {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        for (int i = 0; i < n; i++) {
            String res = solve(in.nextLine());
            System.out.println(res);
        }
    }

    static String solve(String s) {
        //System.out.println("s:" + s);
        char[] cs = s.toCharArray();
        int n = cs.length;
        for (int i = 0; i < n; i++) {
            char c = cs[i];
            if (c == '1' || c == '2' || c == '3') {

            } else if (c == '0') {
                for (int j = i; j < n; j++) {
                    cs[j] = '3';
                }
                for(int j = i - 1; j >= 0;j--) {
                    if (cs[j] != '1') {
                        cs[j] = (char)(cs[j] - 1);
                        break;
                    }
                    cs[j] = (char)(cs[j] - 1);
                }
                break;
            } else {
                for (int j = i; j < n; j++) {
                    cs[j] = '3';
                }
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        for (char c : cs) {
            if (leadingZero && c == '0') {
                leadingZero = false;
                continue;
            }
            leadingZero = false;
            if (c == '0') {
                sb.append('3');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
