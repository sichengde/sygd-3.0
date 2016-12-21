package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Approver;
import com.sygdsoft.service.ApproverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
@RestController
public class ApproverController {
    @Autowired
    ApproverService approverService;

    @RequestMapping(value = "approverAdd")
    public void approverAdd(@RequestBody Approver approver) throws Exception {
        approverService.add(approver);
    }

    @RequestMapping(value = "approverDelete")
    @Transactional(rollbackFor = Exception.class)
    public void approverDelete(@RequestBody List<Approver> approverList) throws Exception {
        approverService.delete(approverList);
    }

    @RequestMapping(value = "approverUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void approverUpdate(@RequestBody List<Approver> approverList) throws Exception {
        if (approverList.size() > 1) {
            if (approverList.get(0).getId().equals(approverList.get(approverList.size() / 2).getId())) {
                approverService.update(approverList.subList(0, approverList.size() / 2));
                return;
            }
        }
        approverService.update(approverList);
    }

    @RequestMapping(value = "approverGet")
    public List<Approver> approverGet(@RequestBody Query query) throws Exception {
        return approverService.get(query);
    }
}
