package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author comyxy
 * @date 2020/6/6
 */
public class LRUCache {
    static class DoubleLinkedNode{
        private int key;
        private int value;
        private DoubleLinkedNode prev;
        private DoubleLinkedNode next;
        public DoubleLinkedNode(){}
    }

    private int capacity; // 规定容量
    private int size; // 实际存放了多少数据
    private Map<Integer, DoubleLinkedNode> cache;
    private DoubleLinkedNode head; // dummy head
    private DoubleLinkedNode tail; // dummy tail

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<Integer, DoubleLinkedNode>(capacity + 1);
        this.head = new DoubleLinkedNode();
        this.tail = new DoubleLinkedNode();
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public int get(int key) {
        DoubleLinkedNode node = cache.get(key);
        if(node == null){
            return -1; // 未找到
        }

        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DoubleLinkedNode node = cache.get(key);
        if(node == null){
            DoubleLinkedNode newNode = new DoubleLinkedNode();
            newNode.key = key;
            newNode.value = value;
            cache.put(key, newNode); //放入cache
            addToHead(newNode); // 添加到链表头部
            size++; // 大小加1
            if(size > capacity){
                // 大小超过容量 移除链表尾节点
                DoubleLinkedNode removedNode = removeFromTail();
                cache.remove(removedNode.key);
                size--;
            }
        }else{
            node.value = value;
            moveToHead(node);
        }
    }

    /*----------------------------*/

    private void addToHead(DoubleLinkedNode node){
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void moveToHead(DoubleLinkedNode node){
        remove(node);
        addToHead(node);
    }

    private void remove(DoubleLinkedNode node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
        node.prev = null;
    }

    private DoubleLinkedNode removeFromTail(){
        DoubleLinkedNode removedNode = this.tail.prev;
        // this.tail.prev = removedNode.prev;
        // removedNode.prev.next = this.tail;
        // removedNode.next = null;
        // removedNode.prev = null;
        remove(removedNode);
        return removedNode;
    }
}
