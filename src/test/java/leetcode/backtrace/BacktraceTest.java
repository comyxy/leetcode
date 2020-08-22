package leetcode.backtrace;

import leetcode.node.TreeNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** 2020/8/22 */
public class BacktraceTest {
  static Base backtrace;

  @BeforeAll
  static void init() {
    backtrace = new Base();
  }

  @Test
  void testJudgePoint24(){
    int[] nums = {4, 1, 8, 7};
    assertTrue(backtrace.judgePoint24(nums));
    assertFalse(backtrace.judgePoint24(new int[]{1,2,1,2}));
    assertTrue(backtrace.judgePoint24(new int[]{1,9,1,2}));
  }

  @Test
  void testRestoreIpAddresses(){
    List<String> possibleIP = backtrace.restoreIpAddresses("19216833131");
    System.out.println(possibleIP);
    assertEquals("192.168.33.131", possibleIP.get(0));
  }

  @Test
  void testGenerateTrees(){
    List<TreeNode> generateTrees = backtrace.generateTrees(3);
    assertEquals(5, generateTrees.size());
  }
}
