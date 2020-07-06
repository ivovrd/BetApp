package com.ivovrd.BetApp.service;

import com.ivovrd.BetApp.model.BetEvent;
import com.ivovrd.BetApp.repository.BetEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BetEventService {
    @Autowired
    private BetEventRepository betEventRepository;

    public Iterable<BetEvent> getAllBetEvents(){
        return betEventRepository.findAll();
    }

    public Iterable<BetEvent> getBetEventsBySport(String sport){
        return betEventRepository.findEventsBySport(sport);
    }

    public BetEvent getBetEventById(Long id){
        return betEventRepository.findEventById(id);
    }

    public Set<String> getAvailableSports() { return betEventRepository.findAllAvailableSports(); }
}
