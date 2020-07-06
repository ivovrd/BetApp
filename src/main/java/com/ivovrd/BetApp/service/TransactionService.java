package com.ivovrd.BetApp.service;

import com.ivovrd.BetApp.model.Account;
import com.ivovrd.BetApp.model.Transaction;
import com.ivovrd.BetApp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getTransactionByAccount(Account account){
        return transactionRepository.findTransactionByAccount(account);
    }
}
