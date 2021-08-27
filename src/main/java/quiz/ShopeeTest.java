package quiz;

import struct.ListNode;

import java.util.*;

/**
 * @since 2021/8/23 12:06
 */
public class ShopeeTest {
    public static void main(String[] args) {
//        q3();
        q4();
    }
    public ListNode reverseLinkedList (ListNode head, int n) {
        // write code here
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        while (head != null) {
            ListNode tail = pre;
            for (int i = 0; i < n; i++) {
                if (tail.next == null) {
                    break;
                }
                tail = tail.next;
            }
            ListNode nextNode = tail.next;
            tail.next = null;
            ListNode[] reversed = reverse(head, tail);

            head = reversed[0];
            tail = reversed[1];

            pre.next = head;
            tail.next = nextNode;

            head = nextNode;
            pre = tail;
        }
        return dummy.next;
    }

    private ListNode[] reverse(ListNode head, ListNode tail) {
        ListNode pre = null, t = null, cur = head;
        while (cur != null) {
            t = cur.next;
            cur.next = pre;
            pre = cur;
            cur = t;
        }
        return new ListNode[]{tail, head};
    }


    public boolean checkValidString (String s) {
        // write code here
        int ln = 0, rn = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '*') {
                ln++;
            } else {
                ln--;
            }
            if (s.charAt(s.length()-i-1) == '(') {
                rn--;
            } else {
                rn++;
            }
            if (ln < 0 || rn < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给一个二叉查找树（Binary Search Tree）的前序遍历结果数组，打印出所有的叶子节点。
     */
    private static void q4() {
        Scanner in = new Scanner(System.in);
        String[] s = in.nextLine().split(" ");
        int[] nums = new int[s.length];
        for(int i=0;i<nums.length;i++) {
            nums[i] = Integer.parseInt(s[i]);
        }
        List<Integer> res = leaf(nums);
        for (Integer re : res) {
            System.out.print(re + " ");
        }
    }

    private static List<Integer> leaf(int[] nums) {
        Set<Integer> set = new HashSet<>();
        // 单调递减栈
        Deque<Integer> st = new LinkedList<>();
        st.push(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            int cur = nums[i];
            if (!st.isEmpty() && cur < st.peek()) {
                // cur是st.peek()左子节点
                set.add(st.peek());
            } else {
                int tmp = 0;
                while (!st.isEmpty() && cur > st.peek()){
                    tmp = st.pop();
                }
                // cur是tmp的右字节点
                set.add(tmp);
            }
            st.push(cur);
        }
        List<Integer> res = new ArrayList<>();
        for (int num : nums) {
            if(!set.contains(num)) {
                res.add(num);
            }
        }
        return res;
    }


    private static void q3() {
        ShopeeTest shopeeTest = new ShopeeTest();
        String s = shopeeTest.computeString("3*[a2*[c]]");
        System.out.println(s);
    }

    public String computeString(String str) {
        // write code here
        Deque<String> cst = new LinkedList<>();
        Deque<Integer> nst = new LinkedList<>();
        char[] cs = str.toCharArray();
        int n = cs.length;
        int num = 0;
        String res = "";
        for (int i = 0; i < n; i++) {
            char c = cs[i];
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                nst.push(num);
                num = 0;
                cst.push(res);
                res = "";
            } else if (c == ']') {
                int r = nst.pop();
                String ss = cst.pop();
                for (int j = 0; j < r; j++) {
                    ss = ss + res;
                }
                res = ss;
            } else if (c != '*') {
                res = res + c;
            }
        }
        return res;
    }

    public int romanToInt(String s) {
        // write code here
        char[] cs = s.toCharArray();
        int sum = 0;
        int pre = romanToInt(cs[0]);
        int n = cs.length;
        for (int i = 1; i < n; i++) {
            int cur = romanToInt(cs[i]);
            if (pre >= cur) {
                sum += pre;
            } else {
                // IV情况
                sum -= pre;
            }
            pre = cur;
        }
        // last char
        sum += pre;
        return sum;
    }

    private int romanToInt(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
}
