package dp.strStr;

/**
 * 算法4 KMP
 *
 * <p>2020/7/3
 */
public class KMP {

  private final String pat;
  private final int[][] dfa;

  public KMP(String pat) {
    this.pat = pat;
    int M = pat.length();
    int R = 256;
    dfa = new int[R][M];

    dfa[pat.charAt(0)][0] = 1;
    for (int X = 0, j = 1; j < M; j++) {
      for (int c = 0; c < R; c++) {
        dfa[c][j] = dfa[c][X]; // 匹配失败时复制重置位置的值
      }
      dfa[pat.charAt(j)][j] = j + 1; // 匹配成功时设置下一状态值
      X = dfa[pat.charAt(j)][X]; // 更新重置位置
    }
  }

  public int search(String txt) {
    int N = txt.length(), M = pat.length();
    int i, j;
    for (i = 0, j = 0; i < N && j < M; i++) {
      j = dfa[txt.charAt(i)][j];
    }
    if (j == M) {
      return i - M;
    } else {
      return N;
    }
  }

  public static void main(String[] args) {
    KMP kmp = new KMP("AACAA");
    System.out.println(kmp.search("AACABAACAA"));
  }
}
