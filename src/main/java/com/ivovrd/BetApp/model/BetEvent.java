package com.ivovrd.BetApp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents betting event entity
 */
@Entity
@Table(name = "sporting_event")
public class BetEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Double quota1;
    private Double quotaX;
    private Double quota2;
    private String sport;
    @OneToMany(mappedBy = "event", targetEntity = PlayedEvent.class, cascade = CascadeType.PERSIST)
    @JsonManagedReference(value="event")
    Set<PlayedEvent> playedEvents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getQuota1() {
        return quota1;
    }

    public void setQuota1(Double quota1) {
        this.quota1 = quota1;
    }

    public Double getQuotaX() {
        return quotaX;
    }

    public void setQuotaX(Double quotaX) {
        this.quotaX = quotaX;
    }

    public Double getQuota2() {
        return quota2;
    }

    public void setQuota2(Double quota2) {
        this.quota2 = quota2;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Set<PlayedEvent> getPlayedEvents() {
        return playedEvents;
    }

    public void setPlayedEvents(Set<PlayedEvent> playedEvents) {
        this.playedEvents = playedEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetEvent betEvent = (BetEvent) o;
        return id.equals(betEvent.id) &&
                text.equals(betEvent.text) &&
                sport.equals(betEvent.sport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, sport);
    }

    @Override
    public String toString() {
        return "BetEvent{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", quota1=" + quota1 +
                ", quotaX=" + quotaX +
                ", quota2=" + quota2 +
                ", sport='" + sport + '\'' +
                '}';
    }
}
