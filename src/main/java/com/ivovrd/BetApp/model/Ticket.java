package com.ivovrd.BetApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents bet ticket entity
 */
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference(value="ticket")
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;
    private Double bet;
    private Double quota;
    @OneToMany(mappedBy = "ticket", targetEntity = PlayedEvent.class, cascade = CascadeType.PERSIST)
    @JsonManagedReference(value="ticket")
    Set<PlayedEvent> playedEvents = new HashSet<>();

    // default constructor to prevent Jackson JsonMappingException
    public Ticket(){ }

    public Ticket(Transaction transaction, Double bet, Double quota) {
        this.transaction = transaction;
        this.bet = bet;
        this.quota = quota;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Set<PlayedEvent> getPlayedEvents() {
        return playedEvents;
    }

    public void setPlayedEvents(Set<PlayedEvent> playedEvents) {
        this.playedEvents = playedEvents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransactionId(Transaction transaction) {
        this.transaction = transaction;
    }

    public Double getBet() {
        return bet;
    }

    public void setBet(Double bet) {
        this.bet = bet;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id) &&
                Objects.equals(bet, ticket.bet) &&
                Objects.equals(quota, ticket.quota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bet, quota);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", bet=" + bet +
                ", quota=" + quota +
                '}';
    }
}
