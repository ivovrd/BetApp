package com.ivovrd.BetApp.controller;

import com.alibaba.fastjson.JSON;
import com.ivovrd.BetApp.exceptions.MissingFundsException;
import com.ivovrd.BetApp.model.*;
import com.ivovrd.BetApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(path = "/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private BetEventService betEventService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PlayedEventService playedEventService;
    @Autowired
    private CalculateQuotaService calculateQuotaService;
    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public @ResponseBody Double addBetEventOnTicket(@RequestBody String json) {
        Long id = Long.parseLong(JSON.parseObject(json).get("id").toString());
        Character type = JSON.parseObject(json).get("type").toString().charAt(0);
        BetEvent betEvent = betEventService.getBetEventById(id);
        return calculateQuotaService.increaseSumQuota(type, betEvent);
    }

    @PostMapping(path = "/remove", consumes = "application/json", produces = "application/json")
    public @ResponseBody Double removeBetEventOnTicket(@RequestBody String json) {
        Long id = Long.parseLong(JSON.parseObject(json).get("id").toString());
        Character type = JSON.parseObject(json).get("type").toString().charAt(0);
        BetEvent betEvent = betEventService.getBetEventById(id);
        return calculateQuotaService.decreaseSumQuota(type, betEvent);
    }

    @PostMapping(path = "/submit", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void submitTicket(@RequestBody String json){
        Double bet = Double.parseDouble(JSON.parseObject(json).get("bet").toString());
        String username = JSON.parseObject(json).get("username").toString();
        Account account = accountService.getAccount(username);
        if (!account.decreaseBalance(bet)){
            throw new MissingFundsException();
        }
        accountService.persistAccountToDb(account);
        Transaction transaction = new Transaction(account, "OUT");
        calculateQuotaService.setAllSports(betEventService.getAvailableSports());
        calculateQuotaService.checkAndAddBonuses();
        Ticket ticket = new Ticket(transaction, bet, calculateQuotaService.getQuotaSum());
        ticketService.persistTicketToDb(ticket);
        calculateQuotaService.getPlayedEvents().forEach((PlayedEvent event) -> {
            event.setTicket(ticket);
        });
        //playedEventService.persistPlayedEventsToDb(playedEvents);
        playedEventService.persistPlayedEventsToDb(calculateQuotaService.getPlayedEvents());
        calculateQuotaService.resetCacheOnSubmit();
    }

    @GetMapping(path = "/player")
    public @ResponseBody Iterable<Ticket> getTicketsForPlayer(@RequestParam(value = "username")String username) {
        Account account = accountService.getAccount(username);
        Iterable<Transaction> transactions = transactionService.getTransactionByAccount(account);
        Set<Ticket> tickets = new HashSet<>();
        for (Transaction transaction: transactions) {
            tickets.add(ticketService.getTicketByTransaction(transaction));
        }
        return tickets;
    }

    @GetMapping(path = "/pairs")
    public @ResponseBody Iterable<TicketPlayedEvents> getPairsOfTicket(@RequestParam(value = "ticketId") Long id){
        Ticket ticket = ticketService.getTicketById(id);
        Iterable<PlayedEvent> playedEvents = playedEventService.getPlayedEventsByTicket(ticket);
        Set<TicketPlayedEvents> ticketPlayedEvents = new HashSet<>();
        for (PlayedEvent playedEvent:playedEvents) {
            ticketPlayedEvents.add(new TicketPlayedEvents(playedEvent.getEvent().getText(), playedEvent.getTypePlayed(), calculateQuotaService.getQuota(playedEvent.getTypePlayed(), playedEvent.getEvent())));
        }
        return ticketPlayedEvents;
    }
}
