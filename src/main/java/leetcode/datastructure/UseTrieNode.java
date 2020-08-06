package leetcode.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @date 2020/7/17
 */
public class UseTrieNode {

    /*----------------------------------------*/

    /**
     * 字典树（仅小写字母）
     */
    static class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        /**
         * 加入字典树节点
         *
         * @param key 单词
         */
        public void put(String key) {
            final int len = key.length();
            TrieNode node = root;
            for (int i = 0; i < len; i++) {
                int j = key.charAt(i) - 'a';
                if (node.next[j] == null) {
                    node.next[j] = new TrieNode();
                }
                node = node.next[j];
            }
            node.isEnd = true;
        }

    }

    /**
     * 字典树节点
     */
    static class TrieNode {
        /**
         * 指向下一个节点
         */
        TrieNode[] next;
        /**
         * 是否由单词以该节点结尾
         */
        boolean isEnd;

        TrieNode() {
            next = new TrieNode[26];
            isEnd = false;
        }
    }

    /*------------------------------------------*/

    /**
     * LeetCode17.13 回复空格
     *
     * @param dictionary 字典
     * @param sentence   删除空格后的句子
     * @return 不能在字典中匹配的字符数量
     */
    public int respace(String[] dictionary, String sentence) {
        Trie trie = new Trie();
        for (String word : dictionary) {
            trie.put(word);
        }
        final int n = sentence.length();
        // dp[i] 表示 第i个字符到末尾不能匹配的字符数量（i=0表示整个句子 i=n表示空）
        int[] dp = new int[n + 1];
        dp[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            // 初始假设都不匹配 则不匹配的字符数为 n-i
            dp[i] = n - i;
            TrieNode node = trie.root;
            for (int j = i; j < n; j++) {
                // 句子中索引从i到末尾的子句子
                int c = sentence.charAt(j) - 'a';
                if (node.next[c] == null) {
                    // 字典树中不存在该后续节点 计算不匹配字符后直接退出本次循环
                    dp[i] = Math.min(dp[i], dp[j + 1] + (j - i + 1));
                    break;
                }
                if (node.next[c].isEnd) {
                    // 字典树中存在对应单词 但为了求最小 继续循环
                    dp[i] = Math.min(dp[i], dp[j + 1]);
                } else {
                    // 字典树中有对应单词路径 但是不能确定是否对应 计算不匹配字符并继续循环
                    dp[i] = Math.min(dp[i], dp[j + 1] + (j - i + 1));
                }
                node = node.next[c];
            }
        }
        return dp[0];
    }


    /**
     * LeetCode139
     *
     * @param s        非空字符串
     * @param wordDict 字典
     * @return s能否被拆分成字典中的单词
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Trie trie = new Trie();
        for (String word : wordDict) {
            trie.put(word);
        }
        final int n = s.length();
        // dp[i] 代表第i个字符到结尾组成的字符串能否被拆分为字典中的单词（i=0表示整个句子 i=n表示空）
        boolean[] dp = new boolean[n + 1];
        dp[n] = true;
        for (int i = n - 1; i >= 0; i--) {
            TrieNode node = trie.root;
            for (int j = i; j < n; j++) {
                // 从第i个字符到末尾的子句子
                int c = s.charAt(j) - 'a';
                if (node.next[c] == null) {
                    // 字典树中不存在该后续节点 直接退出本次循环
                    // dp[i] = dp[i];
                    break;
                }
                if (node.next[c].isEnd) {
                    // 字典树中存在对应单词 则dp[i]的值由dp[j+1]决定
                    dp[i] = dp[j + 1];
                    if (dp[i]) {
                        // dp[i] 为true 表示i后面的字符串可以拆分成字典中的单词 直接退出本次循环
                        break;
                    }
                } else {
                    // 字典树中有对应单词路径 但是不能确定是否对应 继续循环
                    // dp[i] = dp[i];
                }
                node = node.next[c];
            }
        }
        return dp[0];
    }

    /*------------------------------*/

    /**
     * 字典树（仅小写字母）
     */
    static class TrieWithIndex {
        TrieNodeWithIndex root;

        TrieWithIndex() {
            root = new TrieNodeWithIndex();
        }

        /**
         * 加入字典树节点
         *
         * @param key   单词
         * @param index 单词索引
         */
        public void put(String key, int index) {
            final int len = key.length();
            TrieNodeWithIndex node = root;
            for (int i = 0; i < len; i++) {
                int j = key.charAt(i) - 'a';
                if (node.next[j] == null) {
                    node.next[j] = new TrieNodeWithIndex();
                }
                node = node.next[j];
            }
            node.index = index;
        }

    }

    /**
     * 字典树节点
     */
    static class TrieNodeWithIndex {
        /**
         * 指向下一个节点
         */
        TrieNodeWithIndex[] next;
        /**
         * 节点标记
         * -1 不是单词结束节点
         * 0 1 2 代表单词索引
         */
        int index;

        TrieNodeWithIndex() {
            next = new TrieNodeWithIndex[26];
            index = -1;
        }
    }

    /**
     * LeetCode336 回文对
     *
     * @param words 一组唯一的单词
     * @return 索引对 (i,j) 其中 words[i]+words[j] 可以组成回文串
     */
    public List<List<Integer>> palindromePairs(String[] words) {
        TrieWithIndex trieWithIndex = new TrieWithIndex();
        for (int i = 0; i < words.length; i++) {
            trieWithIndex.put(words[i], i);
        }

        List<List<Integer>> ret = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            int m = words[i].length();
            for (int j = 0; j <= m; j++) {
                // 单词分为 t1 t2 两个部分
                // j可以取[0,m] j=0 整个单词归为右串 j=m 整个单词归为左串
                if (isPalindrome(words[i], j, m - 1)) {
                    // j == m 时 t2为空 也是回文
                    // 右部分t2是回文 则判断字典是否存在左部分t1的逆序排列
                    int leftIndex = findWord(trieWithIndex, words[i], 0, j - 1);
                    if(leftIndex != -1 && leftIndex != i){
                        // 找到并且不是自己
                        ret.add(Arrays.asList(i, leftIndex));
                    }
                }
                if(j != 0 && isPalindrome(words[i], 0, j - 1)){
                    // j == 0 时 t1为空 也是回文 （不加限制则会出现重复 与上面if语句中的j==m重复）
                    // t1是回文 则判断字典是否存在左部分t2的逆序排列
                    int rightIndex = findWord(trieWithIndex, words[i], j, m - 1);
                    if(rightIndex != -1 && rightIndex != i){
                        // 找到并且不是自己
                        ret.add(Arrays.asList(rightIndex, i));
                    }
                }
            }
        }
        return ret;
    }

    /**
     * @param trieWithIndex 字典树
     * @param word          单词
     * @param left          左边索引
     * @param right         右边索引
     * @return 返回找到单词索引 无则-1
     */
    private int findWord(TrieWithIndex trieWithIndex, String word, int left, int right) {
        TrieNodeWithIndex node = trieWithIndex.root;
        for (int i = right; i >= left; i--) {
            int c = word.charAt(i) - 'a';
            if(node.next[c] == null){
                // 字典树上不存在
                return -1;
            }
            node = node.next[c];
        }
        return node.index;
    }

    /**
     * 判断是否为回文
     *
     * @param word  单词
     * @param left  左边索引
     * @param right 右边索引
     * @return 是否为回文
     */
    private boolean isPalindrome(String word, int left, int right) {
        int len = right - left + 1;
        for (int i = 0; i < len / 2; i++) {
            if (word.charAt(left + i) != word.charAt(right - i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        UseTrieNode useTrieNode = new UseTrieNode();
        String[] words = {"abcd","dcba","lls","s","sssll"};
        List<List<Integer>> ret = useTrieNode.palindromePairs(words);
        System.out.println(ret);


    }
}
