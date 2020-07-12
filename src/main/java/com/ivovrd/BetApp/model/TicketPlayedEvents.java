package com.ivovrd.BetApp.model;

import java.util.Objects;

/**
 * This class represents bet event placed in ticket
 */
public class TicketPlayedEvents {
    private String text;
    private Character type;
    private Double quota;

    public TicketPlayedEvents(String text, Character type, Double quota) {
        this.text = text;
        this.type = type;
        this.quota = quota;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
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
        TicketPlayedEvents that = (TicketPlayedEvents) o;
        return text.equals(that.text) &&
                type.equals(that.type) &&
                quota.equals(that.quota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, type, quota);
    }

    @Override
    public String toString() {
        return "TicketPlayedEvents{" +
                "text='" + text + '\'' +
                ", type=" + type +
                ", quota=" + quota +
                '}';
    }
}
