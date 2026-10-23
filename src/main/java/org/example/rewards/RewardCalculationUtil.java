package org.example.rewards;

import org.example.rewards.dto.CustomerMonthSummary;
import org.example.transaction.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

public class RewardCalculationUtil {

    public static double determineTotalSpend(List<Transaction> transactions) {
        double totalSpent = transactions.stream().mapToDouble(Transaction::getTransactionTotal).sum();
        return new BigDecimal(Double.toString(totalSpent)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static long determineTotalEarned(Collection<CustomerMonthSummary> monthSummaries) {
        return monthSummaries.stream().map(CustomerMonthSummary::getTotalPointsEarned).mapToLong(Long::longValue).sum();
    }

    public static long determineMonthlyRewardsSum(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::getTransactionTotal).mapToLong(RewardCalculationUtil::calculateTransactionReward).sum();
    }

    public static long calculateTransactionReward(double transactionTotal) {
        // Tier 1: 2 points for every dollar over $100 spent
        long tierOnePoints = calculateTierOneRewardPoints(transactionTotal);
        // Tier 2: 1 point for every dollar between $50-$100 spent
        long tierTwoPoints = calculateTierTwoRewardPoints(transactionTotal);
        return tierOnePoints + tierTwoPoints;
    }

    public static long calculateTierTwoRewardPoints(double transactionTotal) {
        // If the customer spent over $100, we know for a fact they spent $50 between $50-$100.
        // They automatically get 50 points
        // Otherwise, calculate how many dollars between the $50-$100 they spent.
        if (transactionTotal >= 100) {
            return 50;
        } else if (transactionTotal > 50) {
            return Math.round(transactionTotal - 50);
        }
        return 0L;
    }

    public static long calculateTierOneRewardPoints(double transactionTotal) {
        return transactionTotal > 100 ? Math.round((transactionTotal - 100) * 2) : 0;
    }
}
