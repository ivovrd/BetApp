package com.ivovrd.BetApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "played_events")
public class PlayedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonBackReference(value="ticket")
    Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference(value="event")
    BetEvent event;

    @Column(name = "type_played")
    Character typePlayed;

    public PlayedEvent() {
    }

    public PlayedEvent(BetEvent event, Character typePlayed) {
        this.event = event;
        this.typePlayed = typePlayed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public BetEvent getEvent() {
        return event;
    }

    public void setEvent(BetEvent betEvent) {
        this.event = betEvent;
    }

    public Character getTypePlayed() {
        return typePlayed;
    }

    public void setTypePlayed(Character typePlayed) {
        this.typePlayed = typePlayed;
    }

}
