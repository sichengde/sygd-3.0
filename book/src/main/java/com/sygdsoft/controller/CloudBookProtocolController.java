package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.CloudBookProtocol;
import com.sygdsoft.service.CloudBookProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@RestController
public class CloudBookProtocolController {
    @Autowired
    CloudBookProtocolService cloudBookProtocolService;

    @RequestMapping(value = "cloudBookProtocolAdd")
    public void cloudBookProtocolAdd(@RequestBody CloudBookProtocol cloudBookProtocol) throws Exception {
        cloudBookProtocolService.add(cloudBookProtocol);
    }

    @RequestMapping(value = "cloudBookProtocolDelete")
    @Transactional(rollbackFor = Exception.class)
    public void cloudBookProtocolDelete(@RequestBody List<CloudBookProtocol> cloudBookProtocolList) throws Exception {
        cloudBookProtocolService.delete(cloudBookProtocolList);
    }

    @RequestMapping(value = "cloudBookProtocolUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void cloudBookProtocolUpdate(@RequestBody List<CloudBookProtocol> cloudBookProtocolList) throws Exception {
        if (cloudBookProtocolList.size() > 1) {
            if (cloudBookProtocolList.get(0).getId().equals(cloudBookProtocolList.get(cloudBookProtocolList.size() / 2).getId())) {
                cloudBookProtocolService.update(cloudBookProtocolList.subList(0, cloudBookProtocolList.size() / 2));
                return;
            }
        }
        cloudBookProtocolService.update(cloudBookProtocolList);
    }

    @RequestMapping(value = "cloudBookProtocolGet")
    public List<CloudBookProtocol> cloudBookProtocolGet(@RequestBody Query query) throws Exception {
        return cloudBookProtocolService.get(query);
    }
}
