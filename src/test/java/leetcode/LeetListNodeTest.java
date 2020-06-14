package leetcode;

import leetcode.node.ListNode;
import org.junit.jupiter.api.Test;

/**
 * @author comyxy
 * @date 2020/6/7
 */
class LeetListNodeTest {

    @Test
    public void testSortList(){
        ListNode n1 = new ListNode(4);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(1);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        ListNode result = LeetListNode.sortList(n1);
        ListNode curr = result;
        while(curr != null){
            System.out.print(curr.val);
            if(curr.next != null){
                System.out.print("->");
            }
            curr = curr.next;
        }
    }

}