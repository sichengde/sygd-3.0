package com.sygdsoft.controller.especially;

import com.sygdsoft.model.ReportJson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HuaYuanRoomParseController {
    @RequestMapping(value = "huayuanRoomParseReport")
    public List<HuaYuanRoomParseReturn> huayuanRoomParseReport(@RequestBody ReportJson reportJson){
        List<HuaYuanRoomParseReturn> huaYuanRoomParseReturnList=new ArrayList<>();
        return huaYuanRoomParseReturnList;
    }

    static class HuaYuanRoomParseReturn{
        private String project;
        private String subProject;
        private Double day;
        private Double month;
        private Double year;

        public HuaYuanRoomParseReturn() {
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getSubProject() {
            return subProject;
        }

        public void setSubProject(String subProject) {
            this.subProject = subProject;
        }

        public Double getDay() {
            return day;
        }

        public void setDay(Double day) {
            this.day = day;
        }

        public Double getMonth() {
            return month;
        }

        public void setMonth(Double month) {
            this.month = month;
        }

        public Double getYear() {
            return year;
        }

        public void setYear(Double year) {
            this.year = year;
        }
    }
}
