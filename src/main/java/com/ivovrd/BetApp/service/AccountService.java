package com.ivovrd.BetApp.service;

import com.ivovrd.BetApp.model.Account;
import com.ivovrd.BetApp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account getAccount(String username){
        return accountRepository.findAccountByUsername(username);
    }

    public void persistAccountToDb (Account account) {
        accountRepository.save(account);
    }

    public Iterable<Account> getAllAccounts(){
        return accountRepository.findAll();
    }
}
