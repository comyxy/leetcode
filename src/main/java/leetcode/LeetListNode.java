package leetcode;

import leetcode.node.ListNode;

/**
 * @author comyxy
 * @date 2020/6/7
 */
public class LeetListNode {
    /**
     * 对链表进行排序
     *
     * @param head 链表头节点
     * @return 排序后头节点
     */
    public ListNode sortList(ListNode head) {
        // 空或者只有一个节点
        if (head == null || head.next == null) {
            return head;
        }
        // 找到中间节点mid
        ListNode mid = splitListNode(head);
        // 分
        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        // 并
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (left != null && right != null) {
            if (left.val < right.val) {
                curr.next = left;
                left = left.next;
            } else {
                curr.next = right;
                right = right.next;
            }
            curr = curr.next;
        }
        curr.next = left != null ? left : right;
        return dummy.next;
    }

    /**
     * 对链表按照规则进行进行重新排序
     * 1->2->3->4->5 变为
     * 1->5->2->4->3
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode cur = splitListNode(head);

        ListNode left = head, right = reverseListNode(cur), temp = new ListNode(0);
        printListNode(left);
        printListNode(right);
        int i = 0;
        while (left != null && right != null) {
            if ((i & 1) == 0) {
                temp.next = left;
                left = left.next;
            } else {
                temp.next = right;
                right = right.next;
            }
            temp = temp.next;
            i++;
        }
        temp.next = left == null ? right : left;
        printListNode(head);
    }

    /**
     * 反转链表
     * @param cur 链表头节点
     * @return 反转后链表头节点
     */
    private ListNode reverseListNode(ListNode cur) {
        ListNode prev = null, t;
        while (cur != null) {
            t = cur.next;
            cur.next = prev;
            prev = cur;
            cur = t;
        }
        return prev;
    }

    /**
     * 从链表中间拆分链表
     * 1->2->3->4->5 拆分为 1->2->3 与 4->5
     * head指向拆分后第一个链表 return的拆分后第二个链表头节点
     *
     * @param head 原来带拆分链表
     * @return 拆分后第二个链表头节点
     * @throws IllegalArgumentException head参数不能为空
     */
    private ListNode splitListNode(ListNode head) {
        if (head == null) {
            throw new IllegalArgumentException("链表不能为null");
        }
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode cur = slow.next;
        slow.next = null;
        return cur;
    }

    private void printListNode(ListNode node) {
        ListNode curr = node;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null) {
                System.out.print("->");
            }
            curr = curr.next;
        }
        System.out.println();
    }
}
