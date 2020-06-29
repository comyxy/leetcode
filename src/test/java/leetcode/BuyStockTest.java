package leetcode;

import leetcode.dp.BuyStock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author comyxy
 * @date 2020/6/9
 */
class BuyStockTest {
    @Test
    void testBuyStockLeet121() {
        BuyStock buyStock = new BuyStock();
        int[] prices = {7, 1, 5, 3, 6, 4};
        assertEquals(5, buyStock.maxProfit1(prices));
    }

    @Test
    void testBuyStockLeet122() {
        BuyStock buyStock = new BuyStock();
        int[] prices = {7, 1, 5, 3, 6, 4};
        assertEquals(7, buyStock.maxProfit2(prices));
    }

    @Test
    void testBuyStockLeet123() {
        BuyStock buyStock = new BuyStock();
        int[] prices = {3, 3, 5, 0, 0, 3, 1, 4};
        assertEquals(6, buyStock.maxProfit3(prices));
    }

    @Test
    void testBuyStockLeet188() {
        BuyStock buyStock = new BuyStock();
        int[] prices = {3, 2, 6, 5, 0, 3};
        int k = 2;
        assertEquals(7, buyStock.maxProfit4(k, prices));
    }

    @Test
    void testBuyStockLeet309() {
        BuyStock buyStock = new BuyStock();
        int[] prices = {1, 2, 3, 0, 2};
        int coolDown = 1;
        assertEquals(3, buyStock.maxProfit5(coolDown, prices));
        assertEquals(2, buyStock.maxProfit5(2, prices));
    }

    @Test
    void testBuyStockLeet714() {
        BuyStock buyStock = new BuyStock();
        int[] prices = {1, 3, 2, 8, 4, 9};
        int fee = 2;
        assertEquals(8, buyStock.maxProfit6(fee, prices));
    }
}