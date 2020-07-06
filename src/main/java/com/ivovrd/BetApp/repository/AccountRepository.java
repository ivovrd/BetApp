package com.ivovrd.BetApp.repository;

import com.ivovrd.BetApp.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findAccountByUsername (String username);
}
