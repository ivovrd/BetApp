package com.ivovrd.BetApp.service;

import com.ivovrd.BetApp.model.Ticket;
import com.ivovrd.BetApp.model.Transaction;
import com.ivovrd.BetApp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public void persistTicketToDb (Ticket ticket){
        ticketRepository.save(ticket);
    }

    public Ticket getTicketByTransaction (Transaction transaction){
        return ticketRepository.findTicketByTransaction(transaction);
    }

    public Ticket getTicketById (Long id){
        return ticketRepository.findTicketById(id);
    }
}
