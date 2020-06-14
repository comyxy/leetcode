package leetcode;

import leetcode.node.ListNode;

/**
 * @author comyxy
 * @date 2020/6/7
 */
public class LeetListNode {
    /**
     * 对链表进行排序
     * @param head 链表头节点
     * @return
     */
    public static ListNode sortList(ListNode head) {
        // 空或者只有一个节点
        if(head == null || head.next == null){
            return head;
        }
        // 找到中间节点mid
        ListNode slow = head, fast = head.next;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode mid = slow.next;
        slow.next = null;
        // 分
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        // 并
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while(left != null && right != null){
            if(left.val < right.val){
                curr.next = left;
                left = left.next;
            }else {
                curr.next = right;
                right = right.next;
            }
            curr = curr.next;
        }
        curr.next = left != null ? left : right;
        return dummy.next;
    }
}
