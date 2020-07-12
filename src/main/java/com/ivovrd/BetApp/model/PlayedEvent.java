package com.ivovrd.BetApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.Objects;

/**
 * This class represents joint table for ManyToMany relationship between Ticket and BetEvent entities
 */
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

    // default constructor to prevent Jackson JsonMappingException
    public PlayedEvent() { }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayedEvent that = (PlayedEvent) o;
        return Objects.equals(event, that.event) &&
                Objects.equals(typePlayed, that.typePlayed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, typePlayed);
    }
}
