package org.example.rewards.dto;

import org.example.customer.Customer;

import java.time.Month;
import java.util.Map;

public class CustomerRewardSummary {
    private final Customer customer;
    private final Map<Month, CustomerMonthSummary> rewardsEarnedByMonth;
    private final long totalPointsEarned;

    public CustomerRewardSummary(Customer customer, Map<Month, CustomerMonthSummary> rewardsEarnedByMonth, long totalPointsEarned) {
        this.customer = customer;
        this.rewardsEarnedByMonth = rewardsEarnedByMonth;
        this.totalPointsEarned = totalPointsEarned;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Month, CustomerMonthSummary> getRewardsEarnedByMonth() {
        return rewardsEarnedByMonth;
    }

    public long getTotalPointsEarned() {
        return totalPointsEarned;
    }
}
