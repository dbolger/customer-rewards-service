package com.example.rewards

import org.example.rewards.RewardCalculationUtil
import org.example.rewards.dto.CustomerMonthSummary
import org.example.transaction.Transaction
import spock.lang.Specification

class RewardCalculationUtilSpec extends Specification {

    def "Can calculate for all conditions of tier one rewards"() {
        when:
        def result = RewardCalculationUtil.calculateTierOneRewardPoints(total)

        then:
        result == expected

        where:
        total   | expected
        120.00  | 40
        90.00   | 0
    }

    def "Can calculate for all conditions of tier one rewards"() {
        when:
        def result = RewardCalculationUtil.calculateTierTwoRewardPoints(total)

        then:
        result == expected

        where:
        total   | expected
        1.00    | 0
        100.00  | 50
        51.00   | 1
    }

    def "Can calculate total transaction reward as expected"() {
        when:
        def result = RewardCalculationUtil.calculateTransactionReward(total)

        then:
        result == expected

        where:
        total   | expected
        120.00  | 90
        55.00   | 5
        0.01    | 0
        999     | 1848
        3.54    | 0
        51.42   | 1
    }

    def "Can calculate monthly reward sum"() {
        given:
        def t1 = new Transaction(transactionTotal: 120.00)
        def t2 = new Transaction(transactionTotal: 120.00)
        def transactions = [t1, t2]

        when:
        def result = RewardCalculationUtil.determineMonthlyRewardsSum(transactions)

        then:
        // 90 per each expected
        result == 180
    }

    def "Can determine total earned via summaries"() {
        given:
        def s1 = new CustomerMonthSummary(totalPointsEarned: 90)
        def s2 = new CustomerMonthSummary(totalPointsEarned: 1)
        def summaries = [s1, s2]

        when:
        def result = RewardCalculationUtil.determineTotalEarned(summaries)

        then:
        result == 91
    }

    def "Can determine total spend from list of transactions"() {
        given:
        def t1 = new Transaction(transactionTotal: 111)
        def t2 = new Transaction(transactionTotal: 222)
        def transactions = [t1, t2]

        when:
        def result = RewardCalculationUtil.determineTotalSpend(transactions)

        then:
        result == 333.00d
    }
}
