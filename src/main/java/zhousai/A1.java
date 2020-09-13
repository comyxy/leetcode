package zhousai;

import java.util.Arrays;

/** 2020/9/12 */
public class A1 {
  public int calculate(String s) {
    int x = 1, y = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == 'A') {
        x = 2 * x + y;
      } else {
        y = 2 * y + x;
      }
    }
    return x + y;
  }

  public int breakfastNumber(int[] staple, int[] drinks, int x) {
    long ret = 0;
    Arrays.sort(staple);
    Arrays.sort(drinks);

    int left = drinks.length - 1;
    for (int i = 0; i < staple.length; i++) {
      while(left >= 0 && staple[i] + drinks[left] > x){
        left--;
      }
      ret += left + 1;
    }
    return (int) (ret % (1000000007));
  }

  public int minimumOperations(String leaves) {
    return 0;
  }

  public static void main(String[] args) {
    A1 a1 = new A1();
    //    int ab = a1.calculate("AB");
    //    System.out.println(ab);

        int[] staple = {10, 20, 5};
        int[] drinks = {5, 5, 2};
        int x = 15;
        int breakfastNumber = a1.breakfastNumber(staple, drinks, x);
        System.out.println(breakfastNumber);

//    int minimumOperations = a1.minimumOperations("ryyrryyrryyrryyr");
//    System.out.println(minimumOperations);
  }
}
