package org.example.rewards;

import org.example.customer.Customer;
import org.example.rewards.dto.RewardsSummaryResponse;
import org.example.transaction.Transaction;
import org.example.transaction.TransactionService;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;

@Service
public class RewardsService {

    private final TransactionService transactionService;

    public RewardsService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public RewardsSummaryResponse getRewardsSummary() {
        Map<Customer, Map<Month, List<Transaction>>> allCustomerMonthlyTransactions = transactionService.getAllCustomerMonthlyTransactions();

        return null; // TODO: Map
    }
}
