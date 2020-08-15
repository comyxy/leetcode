package leetcode.dp.strStr;

import java.util.Arrays;

/**
 * 算法4 Boyer-Moore
 *
 * <p>2020/7/3
 */
public class BM {
  private final int[] right;
  private final String pat;

  public BM(String pat) {
    this.pat = pat;
    int M = pat.length();
    int R = 256;
    right = new int[R];
    Arrays.fill(right, -1);
    for (int j = 0; j < M; j++) {
      right[pat.charAt(j)] = j;
      //            System.out.println(pat.charAt(j) + ":" + j);
    }
  }

  public int search(String text) {
    int M = pat.length(), N = text.length();
    int skip;
    for (int i = 0; i <= N - M; i += skip) {
      skip = 0;
      for (int j = M - 1; j >= 0; j--) {
        if (pat.charAt(j) != text.charAt(i + j)) {
          skip = j - right[text.charAt(i + j)];
          if (skip < 1) {
            skip = 1;
          }
          break;
        }
      }
      if (skip == 0) {
        return i;
      }
    }
    return N;
  }

  public static void main(String[] args) {
    BM bm = new BM("ll");
    System.out.println(bm.search("hello"));
  }
}
