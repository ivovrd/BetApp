package com.ivovrd.BetApp.controller;

import com.ivovrd.BetApp.model.BetEvent;
import com.ivovrd.BetApp.service.BetEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/events")
public class BetEventController {
    @Autowired
    private BetEventService betEventService;

    /**
     * Gets all betting events available from specified sport
     * @param sport A string containing sport
     * @return All betting events from sport
     */
    @GetMapping(path = "/sport")
    public @ResponseBody Iterable<BetEvent> getEventsBySport(@RequestParam(value = "name")String sport) {
        return betEventService.getBetEventsBySport(sport);
    }
}