package leetcode.datastructure;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2020/7/10
 */
public class UseStack {

    /**
     * LeetCode1021
     * @param S
     * @return
     */
    public String removeOuterParentheses(String S) {
        char[] cs = S.toCharArray();
        final int n = cs.length;
        if(n == 0) {
            throw new IllegalArgumentException();
        }
        Deque<Character> stack = new LinkedList<>();
        List<String> stringList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : cs) {
            if(c == '('){
                stack.push(c);
            }else if(c == ')'){
                stack.pop();
            }
            sb.append(c);
            if(stack.isEmpty()){
                sb.deleteCharAt(sb.length() - 1);
                sb.deleteCharAt(0);
                stringList.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        return String.join("", stringList);
    }

    /**
     * LeetCode42
     * 单调栈
     * @param height
     * @return
     */
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (int i = 0; i < height.length; i++) {
            while(!stack.isEmpty() && height[i] > height[stack.peek()]){
                Integer index = stack.pop();
                if(stack.isEmpty()){
                    break;
                }
                int left = stack.peek();
                int right = i;
                res += (right - left - 1) *
                        (Math.min(height[left], height[right]) - height[index]);
            }
            stack.push(i);
        }
        return res;
    }
}
