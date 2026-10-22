package org.example.transaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.customer.Customer;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Transaction> getAllTransactions() {
        String jpql = "SELECT t FROM Transaction t JOIN FETCH t.customer";

        TypedQuery<Transaction> query = entityManager.createQuery(jpql, Transaction.class);
        return query.getResultList();
    }

    public Map<Customer, Map<Month, List<Transaction>>> getAllCustomerMonthlyTransactions() {
        return getAllTransactions().stream().collect(
                Collectors.groupingBy(
                        Transaction::getCustomer,
                        Collectors.groupingBy(transaction ->
                                transaction.getCreatedTs().getMonth()
                        )
                ));
    }
}
