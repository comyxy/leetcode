package leetcode.backtrace;


import leetcode.node.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @date 2020/7/8
 */
public class Base {

    /**
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树
     *
     * @param n
     * @return 所有二叉搜索树根节点组成的列表
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> res = new LinkedList<>();
        if (start > end) {
            res.add(null);
            return res;
        }
        for (int i = start; i <= end; i++) {
            // [start, i-1]范围内节点组成的二叉搜索树
            List<TreeNode> leftChild = generateTrees(start, i - 1);
            // [i+1, end]范围内节点组成的二叉搜索树
            List<TreeNode> rightChild = generateTrees(i + 1, end);
            for (TreeNode leftNode : leftChild) {
                for (TreeNode rightNode : rightChild) {
                    TreeNode newNode = new TreeNode(i);
                    newNode.left = leftNode;
                    newNode.right = rightNode;
                    res.add(newNode);
                }
            }
        }
        return res;
    }

    /**
     * LeetCode93 复原IP地址
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        segments = new int[4];
        ipRes = new ArrayList<>();
        restoreIpAddressesDfs(s, 0, 0);
        return ipRes;
    }

    private int[] segments;
    private List<String> ipRes;

    /**
     *
     * @param s 字符串
     * @param segId ip地址分段id 0 1 2 3
     * @param segStart ip地址字符位置
     */
    private void restoreIpAddressesDfs(String s, int segId, int segStart) {
        // 4段ip地址同时遍历完字符串
        if(segId == 4){
            if(segStart == s.length()){
                StringBuilder ipAddr = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    ipAddr.append(segments[i]);
                    if(i != 4 - 1){
                        ipAddr.append(".");
                    }
                }
                ipRes.add(ipAddr.toString());
            }
            return;
        }

        // 没有找到4段ip地址就已经遍历完字符串
        if(segStart == s.length()){
            return;
        }

        // 某一段第一个字符为0
        if(s.charAt(segStart) == '0'){
            segments[segId] = 0;
            restoreIpAddressesDfs(s, segId + 1, segStart + 1);
        }

        // 一般情况下
        int addr = 0;
        for(int segEnd = segStart;segEnd < s.length();segEnd++){
            addr = addr * 10 + (s.charAt(segEnd) - '0');
            if(addr > 0 && addr <= 255){
                segments[segId] = addr;
                restoreIpAddressesDfs(s, segId + 1, segEnd + 1);
            }else{
                break;
            }
        }
    }
}
