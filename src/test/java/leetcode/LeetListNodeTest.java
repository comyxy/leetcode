package leetcode;

import leetcode.node.ListNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author comyxy
 * @date 2020/6/7
 */
class LeetListNodeTest {

    ListNode start;

    @BeforeEach
    void setUp() {
        start = new ListNode(4);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(1);
        ListNode n5 = new ListNode(5);
        start.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
    }

    @Test
    public void testSortList(){


        LeetListNode leetListNode = new LeetListNode();
        ListNode result = leetListNode.sortList(start);
        ListNode curr = result;
        while(curr != null){
            System.out.print(curr.val);
            if(curr.next != null){
                System.out.print("->");
            }
            curr = curr.next;
        }
    }

    @Test
    void testReorderList() {
        LeetListNode leetListNode = new LeetListNode();
        leetListNode.reorderList(start);

    }

    private void printListNode(ListNode node){
        ListNode curr = node;
        while(curr != null){
            System.out.print(curr.val);
            if(curr.next != null){
                System.out.print("->");
            }
            curr = curr.next;
        }
        System.out.println();
    }
}