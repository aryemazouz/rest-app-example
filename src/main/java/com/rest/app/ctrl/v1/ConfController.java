package com.rest.app.ctrl.v1;


import com.rest.app.AttackService;
import com.rest.app.ctrl.ControllersConst;
import com.rest.app.model.Conf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllersConst.BASE_V1_URL + "/conf")
public class ConfController {

    @Autowired
    private AttackService attackService;

    @PutMapping
    public void set(Conf conf){
        attackService.reset(conf);
    }
}
