package com.ivovrd.BetApp.repository;

import com.ivovrd.BetApp.model.Account;
import com.ivovrd.BetApp.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Iterable<Transaction> findTransactionByAccount(Account account);
}
