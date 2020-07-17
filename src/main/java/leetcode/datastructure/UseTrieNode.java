package leetcode.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @date 2020/7/17
 */
public class UseTrieNode {
    public static void main(String[] args) {
        UseTrieNode useTrieNode = new UseTrieNode();
        String[] dictionary = {"a", "abc", "b", "cd"};
        List<String> wordDict = new ArrayList<>();
        for (String s : dictionary) {
            wordDict.add(s);
        }
        boolean result = useTrieNode.wordBreak("abcd", wordDict);
        System.out.println(result);
    }

    public int respace(String[] dictionary, String sentence) {
        Trie trie = new Trie();
        for (String word : dictionary) {
            trie.put(word);
        }
        final int n = sentence.length();
        // dp[i] 第i-1个字符到末尾不能匹配的字符
        int[] dp = new int[n + 1];
        dp[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            // 初始假设都不匹配 n-i
            dp[i] = n - i;
            TrieNode node = trie.root;
            for (int j = i; j < n; j++) {
                int c = sentence.charAt(j) - 'a';
                if (node.next[c] == null) {
                    dp[i] = Math.min(dp[i], dp[j + 1] + j - i + 1);
                    break;
                }
                if (node.next[c].isEnd) {
                    dp[i] = Math.min(dp[i], dp[j + 1]);
                } else {
                    dp[i] = Math.min(dp[i], dp[j + 1] + j - i + 1);
                }
                node = node.next[c];
            }
        }
        return dp[0];
    }


    /**
     * LeetCode139
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Trie trie = new Trie();
        for(String word:wordDict){
            trie.put(word);
        }
        final int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[n] = true;
        for(int i=n-1;i>=0;i--){
            TrieNode node = trie.root;
            for(int j=i;j<n;j++){
                int c = s.charAt(j)-'a';
                /////////////////////////
                if(node.next[c] == null){
                    dp[i] = dp[i];
                    break;
                }
                if(node.next[c].isEnd){
                    dp[i] = dp[j+1];
                    if(dp[i]){
                        break;
                    }
                }else{
                    dp[i] = dp[i];
                }
                node = node.next[c];
                /////////////////////////
            }
        }
        return dp[0];
    }

    class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        public void put(String key) {
            final int len = key.length();
            TrieNode node = root;
            for (int i = 0; i < len; i++) {
                int j = key.charAt(i)-'a';
                if(node.next[j] == null){
                    node.next[j] = new TrieNode();
                }
                node = node.next[j];
            }
            node.isEnd = true;
        }

    }

    class TrieNode {
        TrieNode[] next;
        boolean isEnd;

        TrieNode() {
            next = new TrieNode[26];
            isEnd = false;
        }
    }
}
