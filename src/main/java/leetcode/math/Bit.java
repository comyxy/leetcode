package leetcode.math;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2020/7/9
 */
public class Bit {
    /**
     * 反转32位Int
     * @param n
     * @return
     */
    public int reverseBits(int n) {
        int res = 0, leftMove = 24;
        cache = new HashMap<>();
        while(n != 0){
            res += reverseByte(n & 0xff) << leftMove;
            n = n >>> 8;
            leftMove -= 8;
        }
        return res;
    }

    private Map<Integer, Integer> cache;

    private int reverseByte(int b){
        if(cache.containsKey(b)){
            return cache.get(b);
        }
        cache.put(b, (int) (((b * 0x0202020202L & 0x010884422010L)) % 1023));
        return cache.get(b);
    }

    /**
     * 2进制中1的个数
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        int count = 0;
        while(n != 0){
            count += n & 1;
            n = n >>> 1;
        }
        return count;
    }

    public int hammingWeight2(int n) {
        int count;
        for(count = 0; n != 0; count++){
            // clear the least significant bit set
            n = n & (n - 1);
        }
        return count;
    }
}
