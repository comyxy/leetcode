package struct;

import java.util.HashMap;
import java.util.Map;

/**
 * @author comyxy
 * @date 2020/6/6
 */
public class LRUCache<K, V> {
    /**
     * 双向链表节点
     */
    static class DoubleLinkedNode<K, V> {
        K key;
        V value;
        DoubleLinkedNode<K, V> prev;
        DoubleLinkedNode<K, V> next;
    }

    /**
     * 最大容量
     */
    private final int cap;

    /**
     * 当前大小
     */
    private int size;

    /**
     * 双向链表缓存
     */
    private final Map<K, DoubleLinkedNode<K, V>> cache;

    /**
     * 双向链表头节点
     */
    private final DoubleLinkedNode<K, V> head;

    /**
     * 双向链表尾节点
     */
    private final DoubleLinkedNode<K, V> tail;

    /**
     * 构建LRU
     * @param cap 最大容量
     */
    public LRUCache(int cap) {
        this.cap = cap;
        this.size = 0;
        this.cache = new HashMap<>(cap + 1);
        this.head = new DoubleLinkedNode<>();
        this.tail = new DoubleLinkedNode<>();
        this.head.next = tail;
        this.tail.prev = head;
    }

    /**
     * 从LRU中获取value
     * @param key 键
     * @return key对应的value
     */
    public V get(K key) {
        DoubleLinkedNode<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        moveToHead(node);
        return node.value;
    }

    /**
     * 往LRU中对应键值对放(key, value)
     * @param key 键
     * @param val 更新或者插入的值
     */
    public void put(K key, V val) {
        DoubleLinkedNode<K, V> node = cache.get(key);
        if (node == null) {
            // insert
            node = new DoubleLinkedNode<>();
            node.key = key;
            node.value = val;
            cache.put(key, node);
            addToHead(node);
            this.size++;
            if (this.size > this.cap) {
                 node = removeTail();
                 cache.remove(node.key);
                 this.size--;
            }
        } else {
            node.value = val;
            moveToHead(node);
        }
    }

    /*--- 移动链表元素的私有方法 ---*/

    private void moveToHead(DoubleLinkedNode<K, V> node) {
        remove(node);
        addToHead(node);
    }

    private void addToHead(DoubleLinkedNode<K, V> node) {
        node.next = this.head.next;
        node.prev = this.head;
        this.head.next.prev = node;
        this.head.next = node;
    }

    private void remove(DoubleLinkedNode<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    private DoubleLinkedNode<K, V> removeTail() {
        DoubleLinkedNode<K, V> node = this.tail.prev;
        remove(node);
        return node;
    }
}
