package str;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @since 2021/8/20 21:10
 */
public class Base {

    /**
     * 字符串乘法
     */
    public String multiply(String s, String t) {
        final int ls = s.length(), lt = t.length();
        String zero = "0";
        if (zero.equals(s) || zero.equals(t)) {
            return zero;
        }
        // 位数最多ls+lt
        int[] res = new int[ls + lt];
        for (int i = ls - 1; i >= 0; i--) {
            int vi = s.charAt(i) - '0';
            for (int j = lt - 1; j >= 0; j--) {
                int vj = t.charAt(j) - '0';
                // 还要加上该位上原来的值
                int vij = vi * vj + res[i + j + 1];
                res[i + j + 1] = vij % 10;
                res[i + j] += vij / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        for (int i = 0; i < ls + lt; i++) {
            if (leadingZero && res[i] == 0) {
                continue;
            }
            leadingZero = false;
            sb.append(res[i]);
        }
        return sb.toString();
    }

    /**
     * 前缀递增
     * a -> ap -> app -> appl -> apply
     */
    public String longestWord(String[] words) {
        Trie trie = new Trie();
        trie.insert(words);
        return trie.dfs();
    }

    static class Trie {
        TrieNode root;

        String[] words;

        Trie() {
            this.root = new TrieNode('0');
        }

        void insert(String[] words) {
            this.words = words;
            for (int i = 0; i < words.length; i++) {
                insert(words[i], i + 1);
            }
        }

        void insert(String word, int index) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                cur.children.putIfAbsent(c, new TrieNode(c));
                cur = cur.children.get(c);
            }
            cur.type = index;
        }

        String dfs() {
            String res = "";
            Deque<TrieNode> st = new LinkedList<>();
            st.push(root);
            while (!st.isEmpty()) {
                TrieNode node = st.pop();
                if (node == root || node.type > 0) {
                    if (node != root) {
                        // find longest
                        String word = words[node.type - 1];
                        if (word.length() > res.length() || (word.length() == res.length() && word.compareTo(res) < 0)) {
                            res = word;
                        }
                    }
                    for (TrieNode child : node.children.values()) {
                        st.push(child);
                    }
                }
            }
            return res;
        }
    }

    static class TrieNode {
        char c;
        Map<Character, TrieNode> children = new HashMap<>();
        int type;

        TrieNode(char c) {
            this.c = c;
        }
    }
}
