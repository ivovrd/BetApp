package com.ivovrd.BetApp.repository;

import com.ivovrd.BetApp.model.PlayedEvent;
import com.ivovrd.BetApp.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface PlayedEventsRepository extends CrudRepository<PlayedEvent, Long> {
    Iterable<PlayedEvent> findPlayedEventsByTicket(Ticket ticket);
}
