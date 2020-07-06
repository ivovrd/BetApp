package com.ivovrd.BetApp.repository;

import com.ivovrd.BetApp.model.BetEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BetEventRepository extends CrudRepository<BetEvent, Long> {
    Iterable<BetEvent> findEventsBySport(String sport);
    BetEvent findEventById(Long id);
    @Query("SELECT DISTINCT sport FROM BetEvent")
    Set<String> findAllAvailableSports();
}
