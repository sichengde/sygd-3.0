package com.sygdsoft.controller;

import com.sygdsoft.mapper.SqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-08-22.
 */
@RestController
public class SqlController {
    @Autowired
    SqlMapper sqlMapper;

    @RequestMapping(value = "sql")
    public List<String> sql(@RequestBody String sql){
        return sqlMapper.getStringList(sql);
    }
}
