package org.example.rewards.dto;

public class CustomerMonthSummary {

    private long totalPointsEarned;
    private double totalDollarsSpent;

    public CustomerMonthSummary(long totalPointsEarned, double totalDollarsSpent) {
        this.totalDollarsSpent = totalDollarsSpent;
        this.totalPointsEarned = totalPointsEarned;
    }

    public CustomerMonthSummary() {}

    public long getTotalPointsEarned() {
        return totalPointsEarned;
    }

    public void setTotalPointsEarned(long totalPointsEarned) {
        this.totalPointsEarned = totalPointsEarned;
    }

    public double getTotalDollarsSpent() {
        return totalDollarsSpent;
    }

    public void setTotalDollarsSpent(double totalDollarsSpent) {
        this.totalDollarsSpent = totalDollarsSpent;
    }
}
