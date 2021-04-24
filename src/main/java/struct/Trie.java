package struct;

/**
 * 前缀树
 * @author comyxy
 */
public class Trie {
    static class TrieNode {
        TrieNode[] next;
        boolean isEnd;

        public TrieNode() {
            next = new TrieNode[26];
            isEnd = false;
        }
    }

    public TrieNode root = new TrieNode();

    public void put(String key) {
        TrieNode node = this.root;
        for (int i = 0; i < key.length(); i++) {
            int idx = key.charAt(i) - 'a';
            if(node.next[idx] == null) {
                node.next[idx] = new TrieNode();
            }
            node = node.next[idx];
        }
        node.isEnd = true;
    }
}
