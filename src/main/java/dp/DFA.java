package dp;

/**
 * 有限状态机
 *
 * <p>2020/7/8
 */
public class DFA {

  /**
   * LeetCode65
   */
  public boolean isNumber(String s) {
    char[] cs = s.toCharArray();
    int[][] transfer = getTransfer();
    // 初始状态0
    int state = 0;
    for (int i = 0; i < cs.length; i++) {
      char c = cs[i];
      int choice = getChoice(c);
      if (choice < 0) {
        return false;
      }
      state = transfer[state][choice];
      if (state < 0) {
        return false;
      }
    }
    // 最后state为3,6,5,8返回true
    return state == 3 || state == 6 || state == 5 || state == 8;
  }

  /**
   * 根据字符确定choice other字符返回为-1
   *
   * @param c 字符
   * @return 根据字符返回选择
   */
  private int getChoice(char c) {
    switch (c) {
      case ' ':
        return 0;
      case '+':
      case '-':
        return 1;
      case '.':
        return 3;
      case 'e':
        return 4;
      default:
        if (Character.isDigit(c)) {
          return 2;
        }
    }
    return -1;
  }

  /**
   * 得到有限状态机dfa数组

   */
  private int[][] getTransfer() {
    // int[state][choice]
    // blank  +/-   0-9   .   e
    return new int[][] {
      {0, 1, 6, 2, -1},
      {-1, -1, 6, 2, -1},
      {-1, -1, 3, -1, 4},
      {8, -1, 3, -1, 4},
      {-1, 7, 5, -1, -1},
      {8, -1, 5, -1, -1},
      {8, -1, 6, 3, 4},
      {-1, -1, 5, -1, -1},
      {8, -1, -1, -1, -1}
    };
  }

  /**
   * LeetCode8
   *
   * @param str 数字的字符表示
   * @return 数字
   */
  public int myAtoi(String str) {
    char[] cs = str.toCharArray();
    // 0-9  +/-  blank  other
    int[][] transfer =
        new int[][] {
          {2, 1, 0, -1},
          {2, -1, -1, -1},
          {2, 3, 3, 3},
        };
    // 初始状态0
    int state = 0;
    int sum = 0;
    boolean positive = true;
    for (char c : cs) {
      int choice = myAtoiGetChoice(c);
      state = transfer[state][choice];
      if (choice == 0) {
        int add = c - '0';
        boolean outRange = isOutRangeInt32(sum, add, positive);
        if (outRange) {
          return positive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        sum = sum * 10 + add;
      }

      if (choice == 1 && state == 1) {
        if (c == '-') positive = false;
        else if (c == '+') positive = true;
      }

      if (state < 0) {
        return 0;
      } else if (state == 3) {
        break;
      }
    }
    return positive ? sum : -sum;
  }

  /**
   * 32位int 是否溢出
   *
   * @param sum 基数
   * @param add 要加上的数
   * @param positive 正负
   * @return 是否溢出
   */
  private boolean isOutRangeInt32(int sum, int add, boolean positive) {
    if (positive && sum > (Integer.MAX_VALUE - add) / 10) {
      return true;
    }
    return !positive
            && (sum > (Integer.MAX_VALUE - add) / 10
            || (add > 0 && sum > (Integer.MAX_VALUE - add + 1) / 10));
  }

  private int myAtoiGetChoice(char c) {
    switch (c) {
      case ' ':
        return 2;
      case '+':
      case '-':
        return 1;
      default:
        if (Character.isDigit(c)) {
          return 0;
        }
    }
    return 3;
  }
}
