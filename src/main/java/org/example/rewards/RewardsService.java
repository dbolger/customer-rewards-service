package org.example.rewards;

import org.example.customer.Customer;
import org.example.rewards.dto.CustomerMonthSummary;
import org.example.rewards.dto.CustomerRewardSummary;
import org.example.rewards.dto.RewardsSummaryResponse;
import org.example.transaction.Transaction;
import org.example.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RewardsService {

    private static final Logger log = LoggerFactory.getLogger(RewardsService.class);
    private final TransactionService transactionService;

    private static final Period REWARD_SUMMARY_WINDOW = Period.ofMonths(3);

    public RewardsService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public RewardsSummaryResponse getRewardsSummary() {
        log.info("Getting all transactions within reward summary window, window: {}", REWARD_SUMMARY_WINDOW);
        List<Transaction> allTransactionsWithinWindow = transactionService.getAllTransactionsWithinPeriod(REWARD_SUMMARY_WINDOW);
        log.info("Found {} transactions within window", allTransactionsWithinWindow.size());
        Map<Customer, Map<Month, List<Transaction>>> customerMonthlyTransactions = mapTransactionsToCustomersMonthlyTransactions(allTransactionsWithinWindow);
        List<CustomerRewardSummary> summaries = new ArrayList<>();
        log.info("Producing summaries for {} customers", customerMonthlyTransactions.size());
        customerMonthlyTransactions.forEach((customer, monthlyTransactions) -> {
            // Calculate rewards earned each month
            Map<Month, CustomerMonthSummary> monthlyRewards = calculateMonthlyRewards(monthlyTransactions);
            long allRewardsEarned = RewardCalculationUtil.determineTotalEarned(monthlyRewards.values());
            CustomerRewardSummary customerRewardSummary = new CustomerRewardSummary(customer, monthlyRewards, allRewardsEarned);
            summaries.add(customerRewardSummary);
        });
        log.info("Produced {} summaries, returning it in a response object", summaries.size());
        return new RewardsSummaryResponse(summaries);
    }

    private Map<Month, CustomerMonthSummary> calculateMonthlyRewards(Map<Month, List<Transaction>> monthlyTransactions) {
        Map<Month, CustomerMonthSummary> monthlyRewards = new HashMap<>();
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minus(REWARD_SUMMARY_WINDOW.minusMonths(1)); // We subtract 1 to prevent the month of July from being picked up.
        Stream.iterate(start, dt -> !dt.isAfter(end), dt -> dt.plusMonths(1))
                .map(LocalDateTime::getMonth).forEach(m -> monthlyRewards.putIfAbsent(m, new CustomerMonthSummary(0, 0)));
        log.info("Creating summaries for the months of {}", monthlyRewards.keySet());
        monthlyTransactions.forEach((month, transactions) -> {
            double totalSpent = RewardCalculationUtil.determineTotalSpend(transactions);
            long monthlyRewardSum = RewardCalculationUtil.determineMonthlyRewardsSum(transactions);
            long existing = monthlyRewards.get(month).getTotalPointsEarned();
            long newMonthTotal = existing + monthlyRewardSum;
            log.debug("month: {}, totalSpent: {}, monthlyRewardsSum: {}, existing: {}, new: {}", month, totalSpent, monthlyRewardSum, existing, newMonthTotal);
            CustomerMonthSummary summary = new CustomerMonthSummary(newMonthTotal, totalSpent);
            monthlyRewards.put(month, summary);
        });
        return monthlyRewards;
    }



    private Map<Customer, Map<Month, List<Transaction>>> mapTransactionsToCustomersMonthlyTransactions(List<Transaction> transactions) {
        // Convert the list of transactions to a Customer and their transactions organized by Month
        return transactions.stream().collect(
                Collectors.groupingBy(Transaction::getCustomer,
                        Collectors.groupingBy(t -> t.getCreatedTs().getMonth())));
    }
}
