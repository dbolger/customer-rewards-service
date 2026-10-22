package org.example.rewards.dto;

import org.example.customer.Customer;
import org.example.transaction.Transaction;

import java.util.List;

public class CustomerRewardSummary {
    private final Customer customer;
    private final List<Transaction> transactions;
    private final long points;

    public CustomerRewardSummary(Customer customer, List<Transaction> transactions, long points) {
        this.customer = customer;
        this.transactions = transactions;
        this.points = points;
    }
}
