package com.ivovrd.BetApp.model;

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
}
