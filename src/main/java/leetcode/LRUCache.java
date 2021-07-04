package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author comyxy
 * @date 2020/6/6
 */
public class LRUCache {
    /**
     * 双向链表节点
     */
    static class DoubleLinkedNode{
        private int key;
        private int value;
        private DoubleLinkedNode prev;
        private DoubleLinkedNode next;
        public DoubleLinkedNode(){}
    }

    /**
     * 规定容量
      */
    private int capacity;
    /**
     * 实际存放了多少数据
     */
    private int size;

    private Map<Integer, DoubleLinkedNode> cache;
    /**
     * dummy head
     */
    private DoubleLinkedNode head;
    /**
     * dummy tail
     */
    private DoubleLinkedNode tail;


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
            return -1;
        }

        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DoubleLinkedNode node = cache.get(key);
        if(node == null){
            // 新增
            DoubleLinkedNode newNode = new DoubleLinkedNode();
            newNode.key = key;
            newNode.value = value;
            //放入cache
            cache.put(key, newNode);
            // 添加到链表头部
            addToHead(newNode);
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

    private void remove(DoubleLinkedNode node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
        node.prev = null;
    }

    private void moveToHead(DoubleLinkedNode node){
        remove(node);
        addToHead(node);
    }

    private DoubleLinkedNode removeFromTail(){
        DoubleLinkedNode removedNode = this.tail.prev;
        remove(removedNode);
        return removedNode;
    }
}
