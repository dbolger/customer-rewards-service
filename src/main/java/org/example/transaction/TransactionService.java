package org.example.transaction;

import org.example.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Period;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public List<Transaction> getAllTransactionsWithinPeriod(Period period) {
        log.info("Finding all transactions within the period {}", period);
        List<Transaction> allTransactionsWithinPeriod = transactionRepository.getAllTransactionsWithinPeriod(period);
        log.info("Found {} transactions within period", allTransactionsWithinPeriod.size());
        return allTransactionsWithinPeriod;
    }

    public Map<Customer, Map<Month, List<Transaction>>> getAllCustomerMonthlyTransactions() {
        return transactionRepository.getAllCustomerMonthlyTransactions();
    }
}
