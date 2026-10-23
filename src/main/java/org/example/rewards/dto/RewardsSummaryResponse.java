package org.example.rewards.dto;

import java.util.List;

public class RewardsSummaryResponse {

    private final List<CustomerRewardSummary> customerRewardSummaries;

    public RewardsSummaryResponse(List<CustomerRewardSummary> summaries) {
        this.customerRewardSummaries = summaries;
    }

    public List<CustomerRewardSummary> getCustomerRewardSummaries() {
        return customerRewardSummaries;
    }
}
