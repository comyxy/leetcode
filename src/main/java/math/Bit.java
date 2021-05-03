package math;

import java.util.HashMap;
import java.util.Map;

/**
 * @author comyxy
 * @date 2020/7/9
 */
public class Bit {
    /**
     * 反转32位Int
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

    /**
     * 137
     */
    public int singleNumber(int[] nums) {
        int a = 0, b = 0;
        for (int num : nums) {
            int na = a & (~b) & (~num) | (~a) & b & num;
            int nb = (~a) & (b ^ num);
            a = na;
            b = nb;
        }
        return b;
    }

    /**
     * 1720
     */
    public int[] decode(int[] encoded, int first) {
        int n = encoded.length + 1;
        int[] decoded = new int[n];
        decoded[0] = first;
        for (int i = 1; i < n; i++) {
            decoded[i] = first ^ encoded[i-1];
            first = decoded[i];
        }
        return decoded;
    }

    /**
     * 1486
     */
    public int xorOperation(int n, int start) {
        int hStart = start / 2;
        int hEnd = hStart + n - 1;
        int hResult = xorFromNToM(hStart, hEnd);
        // 当start为奇数且n为奇数时 最后一位结果为1
        int lResult = (start & 1) == 1 && (n & 1) == 1 ? 1 : 0;
        return (hResult << 1) + lResult;
    }

    private int xorFrom1ToN(int n) {
        int t = n % 4;
        if(n < 0){
            return 0;
        }

        if(t == 1) {
            return 1;
        }else if(t == 2) {
            return n+1;
        }else if(t == 3){
            return 0;
        }
        return n;
    }

    /**
     * [n, m]
     */
    private int xorFromNToM(int n, int m) {
        return xorFrom1ToN(n-1) ^ xorFrom1ToN(m);
    }
}
