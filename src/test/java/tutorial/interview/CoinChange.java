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
                {1},
                {2, 5, 10, 1},
                {3, 7}
        };

        int[] amounts = {
                11,   // Expected: 3
                3,    // Expected: -1
                0,    // Expected: 0
                27,   // Expected: 4 (10+10+5+2? actually 10+10+5+1+1 = 5, best is 10+10+5+1+1=5; better check)
                5     // Expected: -1
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
        if (amount == 0) {
            return 0;
        }
        Integer minNumberOfCoins = findMinimumCoins(coins, amount, 0);
        return (minNumberOfCoins == null) ? -1 : minNumberOfCoins;
    }

    private Integer findMinimumCoins(int[] coins, int amount, int coinIndex) {
        if (amount == 0) {
            return 0;
        }

        if (coinIndex >= coins.length) {
            return null;
        }

        int currentCoin = coins[coinIndex];
        Integer minimumCoins = null;

        for (int numberOfCurrentCoin = 0; numberOfCurrentCoin <= amount / currentCoin; numberOfCurrentCoin++) {
            int remainingAmount = amount - numberOfCurrentCoin * currentCoin;

            if (remainingAmount == 0) {
                // Found a combination using current coin.

                int totalCoins = numberOfCurrentCoin;
                if (minimumCoins == null || totalCoins < minimumCoins) {
                    minimumCoins = totalCoins;
                }
            } else {
                // Try combinations with the next coin denomination.

                Integer subProblemCoins = findMinimumCoins(coins, remainingAmount, coinIndex + 1);

                if (subProblemCoins != null) {
                    // Valid solution, see if number of coins smaller.

                    int totalCoins = numberOfCurrentCoin + subProblemCoins;

                    if (minimumCoins == null || totalCoins < minimumCoins) {
                        minimumCoins = totalCoins;
                    }
                }
            }
        }

        return minimumCoins;
    }
}
