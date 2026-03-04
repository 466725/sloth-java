package tutorial.interview;
// https://www.jointaro.com/interviews/questions/coin-change/?src=taro75

import java.util.Arrays;

/**
 *
 * You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
 * <p>
 * Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
 * <p>
 * You may assume that you have an infinite number of each kind of coin.
 * <p>
 * Example 1:
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 * <p>
 * Example 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 * <p>
 * Example 3:
 * Input: coins = [1], amount = 0
 * Output: 0
 *
 */
public class CoinChange {
    // ==================================
    // ✅ Test Harness
    // ==================================
    public static void main(String[] args) {
        CoinChange solver = new CoinChange();
        int[][] coinSets = {
                {1, 2, 5},
                {2},
                {7,13,17},
                {1},
                {2, 5, 10, 1},
                {3, 7},
                {5, 1}
        };
        int[] amounts = {
                11,   // Expected: 3
                3,    // Expected: -1
                3,    // Expected: -1
                0,    // Expected: 0
                27,   // Expected: 4 (10+10+5+2? actually 10+10+5+1+1 = 5, best is 10+10+5+1+1=5; better check)
                5,    // Expected: -1
                16    // Expected: 4 (5+5+5+1) but actually 5+5+5+1 = 4, better check
        };
        System.out.println("==== Coin Change Tests (Recursive) ====");
        for (int i = 0; i < coinSets.length; i++) {
            int[] coins = coinSets[i];
            int amount = amounts[i];
            System.out.println("---------------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Coins:  " + Arrays.toString(coins));
            System.out.println("Amount: " + amount);
            int result = solver.coinChange(coins, amount);
            System.out.println("Result: " + result);
        }
        System.out.println("=======================================");
    }

    public int coinChange(int[] coins, int amount) {
        if (amount == 0)
            return 0;
        Integer minNumberOfCoins = findMinCoin(coins, amount, 0);
        return (minNumberOfCoins == null) ? -1 : minNumberOfCoins;
    }

    private Integer findMinCoin(int[] coins, int amount, int coinIndex) {
        if (coinIndex >= coins.length)
            return null; // Cannot be
        Integer minCoin = null;
        for (int i = 0; i <= amount / coins[coinIndex]; i++) {
            if ((amount - i * coins[coinIndex]) == 0) {
                // Found a combination
                if (minCoin == null || i < minCoin)
                    minCoin = i;
            } else {
                // Try next coin
                Integer nextCoin = findMinCoin(coins, (amount - i * coins[coinIndex]), coinIndex + 1);
                if (nextCoin != null) { // Found a combination
                    if (minCoin == null || (i + nextCoin) < minCoin)
                        minCoin = (i + nextCoin);
                }
            }
        }
        return minCoin;
    }
}
