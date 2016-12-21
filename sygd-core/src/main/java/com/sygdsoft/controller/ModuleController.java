package com.sygdsoft.controller;

import com.sygdsoft.model.Module;
import com.sygdsoft.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 舒展 on 2016-05-18.
 */
@RestController
public class ModuleController {
    @Autowired
    ModuleService moduleService;
    /**
     * 获取全部可用模块
     */
    @RequestMapping(value = "moduleGet")
    public Module[] getAllModule() {
        return moduleService.getAllModule();
    }
}
