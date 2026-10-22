package org.example.transaction;

import org.example.customer.Customer;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public Map<Customer, Map<Month, List<Transaction>>> getAllCustomerMonthlyTransactions() {
        return transactionRepository.getAllCustomerMonthlyTransactions();
    }
}
