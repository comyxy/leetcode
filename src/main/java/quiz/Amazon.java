package quiz;

import java.util.Scanner;

/**
 * @since 2021/8/28 15:44
 */
public class Amazon {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        String word = sc.nextLine();
        int res = q1(text, word);
        System.out.println(res);
    }

    private static int q1(String text, String word) {
        int res = 0;
        for (int i = 0, j = 0; i < text.length(); i++) {
            if (text.charAt(i) == word.charAt(j)) {
                j++;
                if (j == word.length()) {
                    res++;
                    j = 0;
                }
            } else {
                j = 0;
            }
        }
        return res;
    }
}
