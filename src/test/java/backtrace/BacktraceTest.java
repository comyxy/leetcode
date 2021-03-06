package backtrace;

import struct.TreeNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** 2020/8/22 */
public class BacktraceTest {
  static BackTrace backtrace;

  @BeforeAll
  static void init() {
    backtrace = new BackTrace();
  }

  @Test
  void testJudgePoint24(){
    int[] nums = {4, 1, 8, 7};
    assertTrue(backtrace.judgePoint24(nums));
    assertFalse(backtrace.judgePoint24(new int[]{1,2,1,2}));
    assertTrue(backtrace.judgePoint24(new int[]{1,9,1,2}));
    assertTrue(backtrace.judgePoint24(new int[]{4,9,6,7}));
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

  @Test
  void testFindSubSeq(){
    int[] nums = new int[]{4, 6, 7, 7};
    assertEquals(8, backtrace.findSubsequences(nums).size());
  }
}
