package com.rest.app.ctrl.v1;

import com.rest.app.StatsService;
import com.rest.app.ctrl.ControllersConst;
import com.rest.app.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllersConst.BASE_V1_URL + "/stats")
public class StatsController {
    @Autowired
    private StatsService statsService;


    @GetMapping
    public Stats stats() throws Exception{
        return statsService.getCurrentStats();
    }
}
