package com.rest.app.ctrl.v1;

import com.rest.app.AttackService;
import com.rest.app.StatsExecutionTime;
import com.rest.app.ctrl.ControllersConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(ControllersConst.BASE_V1_URL + "/attack")
public class AttackController {

    @Autowired
    private AttackService attackService;

    @GetMapping
    @StatsExecutionTime
    public Set<String> attack(@RequestParam(name = "vm_id") String vmId) throws Exception{
        if(vmId == null){
            return Collections.EMPTY_SET;
        }

        return attackService.attack(vmId);
    }
}
