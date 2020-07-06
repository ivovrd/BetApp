package com.ivovrd.BetApp.controller;

import com.ivovrd.BetApp.model.Account;
import com.ivovrd.BetApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Account> getAllEvents() {
        return accountService.getAllAccounts();
    }
}
