package com.ivovrd.BetApp.controller;

import com.alibaba.fastjson.JSON;
import com.ivovrd.BetApp.exceptions.InvalidParameterException;
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
    private static final String transactionType = "OUT";

    /**
     * Adds selected BetEvent to current bet ticket and returns updated value of total quota
     * @param json Json file containing BetEvent id and selected type
     * @return Updated value of current quota
     */
    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public @ResponseBody Double addBetEventOnTicket(@RequestBody String json) {
        long id;
        char type;
        try {
            id = Long.parseLong(JSON.parseObject(json).get("id").toString());
            type = JSON.parseObject(json).get("type").toString().charAt(0);
        } catch (Exception exception) {
            throw new InvalidParameterException();
        }
        BetEvent betEvent = betEventService.getBetEventById(id);
        return calculateQuotaService.increaseSumQuota(type, betEvent);
    }

    /**
     * Removes selected BetEvent from current bet ticket and returns updated value of total quota
     * @param json Json file containing BetEvent id and selected type
     * @return Updated value of current quota
     */
    @PostMapping(path = "/remove", consumes = "application/json", produces = "application/json")
    public @ResponseBody Double removeBetEventOnTicket(@RequestBody String json) {
        long id;
        char type;
        try {
            id = Long.parseLong(JSON.parseObject(json).get("id").toString());
            type = JSON.parseObject(json).get("type").toString().charAt(0);
        } catch (Exception exception) {
            throw new InvalidParameterException();
        }
        BetEvent betEvent = betEventService.getBetEventById(id);
        return calculateQuotaService.decreaseSumQuota(type, betEvent);
    }

    /**
     * Saves bet ticket to database, creates new transaction and updates account information accordingly
     * @param json Json file containing stake and username of the player
     */
    @PostMapping(path = "/submit", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void submitTicket(@RequestBody String json){
        Double bet;
        String username;
        try {
            bet = Double.parseDouble(JSON.parseObject(json).get("bet").toString());
            username = JSON.parseObject(json).get("username").toString();
        } catch (Exception exception) {
            throw new InvalidParameterException();
        }
        Account account = accountService.getAccount(username);
        if (!account.decreaseBalance(bet)){
            throw new MissingFundsException();
        }
        accountService.persistAccountToDb(account);
        Transaction transaction = new Transaction(account, transactionType);
        calculateQuotaService.setAllSports(betEventService.getAvailableSports());
        calculateQuotaService.checkAndAddBonuses();
        Ticket ticket = new Ticket(transaction, bet, calculateQuotaService.getQuotaSum());
        ticketService.persistTicketToDb(ticket);
        calculateQuotaService.getPlayedEvents().forEach((PlayedEvent event) -> event.setTicket(ticket));
        playedEventService.persistPlayedEventsToDb(calculateQuotaService.getPlayedEvents());
        calculateQuotaService.resetCacheOnSubmit();
    }

    /**
     * Gets all tickets of specified player
     * @param username A string containing username of the player
     * @return All tickets of the player
     */
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

    /**
     * Gets all the pairs played in the bet ticket
     * @param id A Long containing ticket id
     * @return All the bet events played in the ticket
     */
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
