package com.ivovrd.BetApp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlayedEventId implements Serializable {
    @Column(name = "ticket_id")
    private Long ticketId;
    @Column(name = "event_id")
    private Long eventId;

    public PlayedEventId() {
    }

    public PlayedEventId(Long ticketId, Long eventId) {
        this.ticketId = ticketId;
        this.eventId = eventId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayedEventId that = (PlayedEventId) o;
        return ticketId.equals(that.ticketId) &&
                eventId.equals(that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, eventId);
    }
}
