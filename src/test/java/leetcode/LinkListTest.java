package leetcode;

import struct.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author comyxy
 * @date 2020/6/7
 */
class LinkListTest {

    ListNode start;

    LinkList linkList = new LinkList();

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
        ListNode result = linkList.sortList(start);
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
        linkList.reorderList(start);

    }

    @Test
    void reverseK() {
        ListNode res = linkList.reverseK(start, 3);
        printListNode(res);
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