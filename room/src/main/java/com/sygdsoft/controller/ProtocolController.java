package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.ProtocolMapper;
import com.sygdsoft.model.Protocol;
import com.sygdsoft.service.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
@RestController
public class ProtocolController {
    @Autowired
    ProtocolService protocolService;

    @RequestMapping(value = "protocolAdd")
    public void protocolAdd(@RequestBody Protocol protocol) throws Exception {
        protocolService.add(protocol);
    }

    @RequestMapping(value = "protocolDelete")
    @Transactional(rollbackFor = Exception.class)
    public void protocolDelete(@RequestBody List<Protocol> protocolList) throws Exception {
        protocolService.delete(protocolList);
    }

    @RequestMapping(value = "protocolUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void protocolUpdate(@RequestBody List<Protocol> protocolList) throws Exception {
        if (protocolList.size() > 1) {
            if (protocolList.get(0).getId().equals(protocolList.get(protocolList.size() / 2).getId())) {
                protocolService.update(protocolList.subList(0, protocolList.size() / 2));
                return;
            }
        }
        protocolService.update(protocolList);
    }

    @RequestMapping(value = "protocolGet")
    public List<Protocol> protocolGet(@RequestBody Query query) throws Exception {
        return protocolService.get(query);
    }
}
