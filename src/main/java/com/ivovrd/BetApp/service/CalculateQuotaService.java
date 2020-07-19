package com.ivovrd.BetApp.service;

import com.ivovrd.BetApp.model.BetEvent;
import com.ivovrd.BetApp.model.PlayedEvent;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for calculating total quota of bet ticket.
 */
@Service
public class CalculateQuotaService {
    private Double quotaSum = 1.0;
    public static Double BONUS5 = 5.0;
    public static Double BONUS10 = 10.0;
    /**
     * Set of all available sports
     */
    private Set<String> allSports = new HashSet<>();
    /**
     * HashMap containing sports and their occurrence in the ticket
     */
    private final HashMap<String, Integer> sportsMap = new HashMap<>();
    /**
     * Set containing PlayedEvents objects for BetEvents added to Ticket
     */
    private final Set<PlayedEvent> playedEvents = new HashSet<>();

    public Set<String> getAllSports() {
        return allSports;
    }

    public void setAllSports(Set<String> allSports) {
        this.allSports = allSports;
    }

    public Double getQuotaSum() {
        return quotaSum;
    }

    /**
     * Gets quota based on the type selected in BetEvent
     * @param type Character containing type selected
     * @param betEvent BetEvent object selected to be added to bet ticket
     * @return A Double quota
     */
    public Double getQuota(Character type, BetEvent betEvent){
        if (type.equals('1')){
            return betEvent.getQuota1();
        } else if (type.equals('2')) {
            return betEvent.getQuota2();
        } else
            return betEvent.getQuotaX();
    }

    /**
     * Increase quota and call methods for updating sportsMap and new playedEvents
     * @param type Character containing type selected
     * @param betEvent BetEvent object selected to be added to bet ticket
     * @return A Double representing value of current quota
     */
    public Double increaseSumQuota(Character type, BetEvent betEvent) {
        addSportsMap(betEvent.getSport());
        addPlayedEvent(new PlayedEvent(betEvent, type));
        return this.quotaSum *= getQuota(type, betEvent);
    }

    /**
     * Decrease quota and call methods for updating sportsMap and new playedEvents
     * @param type Character containing type selected
     * @param betEvent BetEvent object selected to be added to bet ticket
     * @return A Double representing value of current quota
     */
    public Double decreaseSumQuota(Character type, BetEvent betEvent) {
        removeSportsMap(betEvent.getSport());
        removePlayedEvent(betEvent);
        return this.quotaSum /= getQuota(type, betEvent);
    }

    /**
     * Update sportsMap HashMap with new sport or increase occurrence
     * @param sport A String representing sport
     */
    public void addSportsMap(String sport){
        if (this.sportsMap.containsKey(sport)){
            this.sportsMap.put(sport, sportsMap.get(sport) + 1);
        } else {
            this.sportsMap.put(sport, 1);
        }
    }

    /**
     * Update sportsMap HashMap
     * Remove sport or decrease occurrence
     * @param sport A String representing sport
     */
    public void removeSportsMap(String sport){
        if (this.sportsMap.containsKey(sport)){
            if (this.sportsMap.get(sport) > 1){
                this.sportsMap.put(sport, sportsMap.get(sport) - 1);
            } else {
                this.sportsMap.remove(sport);
            }
        }
    }

    /**
     * Adds new item in playedEvents Set
     * @param playedEvent PlayedEvent object
     */
    public void addPlayedEvent(PlayedEvent playedEvent){
        this.playedEvents.add(playedEvent);
    }

    /**
     * Finds PlayedEvent item based on BetEvent sent and removes it from playedEvents
     * @param betEvent BetEvent object
     */
    public void removePlayedEvent(BetEvent betEvent) {
        this.playedEvents.removeIf((PlayedEvent event) -> event.getEvent().equals(betEvent));
    }

    /**
     * Gets all bet events and selected types currently added to bet ticket
     * @return Set containing all PlayedEvent objects for bet ticket
     */
    public Set<PlayedEvent> getPlayedEvents (){
        return this.playedEvents;
    }

    /**
     * Add bonus to total quota in case three bet events from the same sport are added to ticket
     */
    public void checkThreeFromSameSportBonus(){
        this.sportsMap.forEach((key, value) -> {
            if (value >= 3){
                this.quotaSum += BONUS5;
            }
        });
    }

    /**
     * Add bonus to total quota in case at least one bet event from each sport available is added to ticket
     */
    public void checkAllSportsBonus(){
        for (String sport: this.allSports) {
            if (!this.sportsMap.containsKey(sport)){
                return;
            }
        }
        this.quotaSum += BONUS10;
    }

    /**
     * Call methods for checking and adding bonuses
     */
    public void checkAndAddBonuses(){
        checkThreeFromSameSportBonus();
        checkAllSportsBonus();
    }

    /**
     * Reset all cache data once the ticket is submitted to be ready for new one
     */
    public void resetCacheOnSubmit(){
        this.quotaSum = 1.0;
        this.sportsMap.clear();
        this.playedEvents.clear();
    }
}
