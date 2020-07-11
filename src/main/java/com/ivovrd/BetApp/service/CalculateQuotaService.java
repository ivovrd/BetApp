package com.ivovrd.BetApp.service;

import com.ivovrd.BetApp.model.BetEvent;
import com.ivovrd.BetApp.model.PlayedEvent;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class CalculateQuotaService {
    private Double quotaSum = 1.0;
    public static Double BONUS5 = 5.0;
    public static Double BONUS10 = 10.0;
    private Set<String> allSports = new HashSet<>();
    private final HashMap<String, Integer> sportsMap = new HashMap<>();
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

    public Double getQuota(Character type, BetEvent betEvent){
        if (type.equals('1')){
            return betEvent.getQuota1();
        } else if (type.equals('2')) {
            return betEvent.getQuota2();
        } else
            return betEvent.getQuotaX();
    }

    public Double increaseSumQuota(Character type, BetEvent betEvent) {
        addSportsMap(betEvent.getSport());
        addPlayedEvent(new PlayedEvent(betEvent, type));
        return this.quotaSum *= getQuota(type, betEvent);
    }

    public Double decreaseSumQuota(Character type, BetEvent betEvent) {
        removeSportsMap(betEvent.getSport());
        removePlayedEvent(betEvent);
        return this.quotaSum /= getQuota(type, betEvent);
    }

    public void addSportsMap(String sport){
        if (this.sportsMap.containsKey(sport)){
            this.sportsMap.put(sport, sportsMap.get(sport) + 1);
        } else {
            this.sportsMap.put(sport, 1);
        }
    }

    public void removeSportsMap(String sport){
        if (this.sportsMap.containsKey(sport)){
            if (this.sportsMap.get(sport) > 1){
                this.sportsMap.put(sport, sportsMap.get(sport) - 1);
            } else {
                this.sportsMap.remove(sport);
            }
        }
    }

    public void addPlayedEvent(PlayedEvent playedEvent){
        this.playedEvents.add(playedEvent);
    }

    public void removePlayedEvent(BetEvent betEvent) {
        this.playedEvents.removeIf((PlayedEvent event) -> event.getEvent().equals(betEvent));
    }

    public Set<PlayedEvent> getPlayedEvents (){
        return this.playedEvents;
    }

    public void checkThreeFromSameSportBonus(){
        this.sportsMap.forEach((key, value) -> {
            if (value >= 3){
                this.quotaSum += BONUS5;
            }
        });
    }

    public void checkAllSportsBonus(){
        for (String sport: this.allSports) {
            if (!this.sportsMap.containsKey(sport)){
                return;
            }
        }
        this.quotaSum += BONUS10;
    }

    public void checkAndAddBonuses(){
        checkThreeFromSameSportBonus();
        checkAllSportsBonus();
    }

    public void resetCacheOnSubmit(){
        this.quotaSum = 1.0;
        this.sportsMap.clear();
        this.playedEvents.clear();
    }
}
