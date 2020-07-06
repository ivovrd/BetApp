package com.ivovrd.BetApp.repository;

import com.ivovrd.BetApp.model.Ticket;
import com.ivovrd.BetApp.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Ticket findTicketByTransaction (Transaction transaction);
    Ticket findTicketById (Long id);
}
