package str;

/**
 * Rabin-Karp 算法4
 *
 * 2020/7/3
 * @author comyxy
 */
public class RK {
  private final String pat;
  private final long patHash;
  private final int M;

  /**
   * 很大的随机质数
   */
  private final long Q;

  /**
   * 字母表大小 进制
   */
  private final int R = 256;

  /**
   * R^(M-1)%Q
   */
  private long RM;

  public RK(String pat) {
    this.pat = pat;
    M = pat.length();
    Q = longRandomPrime();
    RM = 1;
    for (int i = 1; i <= M - 1; i++) {
      RM = (R * RM) % Q;
    }
    patHash = hash(pat, M);
  }

  public int search(String text) {
    int N = text.length();
    long textHash = hash(text, M);
    if (patHash == textHash && check(0)) {
      return 0;
    }
    for (int i = M; i < N; i++) {
      // + Q 避免负数
      textHash = (textHash + Q - (text.charAt(i - M) * RM) % Q) % Q;
      textHash = (textHash * R + text.charAt(i)) % Q;
      if (patHash == textHash) {
        if (check(i - M + 1)) {
          return i - M + 1;
        }
      }
    }
    return N;
  }

  /**
   * 检查散列值相同的具体字符是否确实相同
   *
   * @param i text中开始索引
   * @return 是否相同
   */
  private boolean check(int i) {
    return true;
  }

  private long longRandomPrime() {
    return 997;
  }

  private long hash(String pat, int M) {
    long h = 0;
    for (int j = 0; j < M; j++) {
      h = (R * h + pat.charAt(j)) % Q;
    }
    return h;
  }

  public static void main(String[] args) {
    RK rk = new RK("ll");
    System.out.println(rk.search("hello"));
  }
}
