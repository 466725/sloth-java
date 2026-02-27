package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/best-time-to-buy-and-sell-stock/?src=taro75

import java.util.Arrays;

/**
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * <p>
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
 * <p>
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 * <p>
 * Example 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 * <p>
 * Example 2:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 *
 */
public class MaximumProfitBuySellStock {
    public static void main(String[] args) {
        runCase(new int[]{7, 1, 5, 3, 6, 4}, 5);
        runCase(new int[]{7, 6, 4, 3, 1}, 0);
        runCase(new int[]{2, 4, 1}, 2);
        runCase(new int[]{1}, 0);
        runCase(new int[]{}, 0);
        runCase(null, 0);
    }

    public static int maximumProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int minPrice = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            int todayPrice = prices[i];
            if (todayPrice < minPrice) {
                minPrice = todayPrice;
            } else {
                maxProfit = Math.max(maxProfit, todayPrice - minPrice);
            }
        }

        return maxProfit;
    }

    private static void runCase(int[] prices, int expected) {
        int actual = maximumProfit(prices);
        System.out.println(
                "prices=" + Arrays.toString(prices)
                        + ", expected=" + expected
                        + ", actual=" + actual
        );
    }
}
