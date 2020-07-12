package com.ivovrd.BetApp.service;

import com.ivovrd.BetApp.model.PlayedEvent;
import com.ivovrd.BetApp.model.Ticket;
import com.ivovrd.BetApp.repository.PlayedEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class PlayedEventService {
    @Autowired
    private PlayedEventsRepository playedEventsRepository;

    public void persistPlayedEventsToDb(Set<PlayedEvent> playedEvents) {
        playedEventsRepository.saveAll(playedEvents);
    }

    public Iterable<PlayedEvent> getPlayedEventsByTicket(Ticket ticket){
        return playedEventsRepository.findPlayedEventsByTicket(ticket);
    }
}
