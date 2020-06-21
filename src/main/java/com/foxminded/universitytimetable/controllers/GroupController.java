package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("/groups")
    public String getGroupsPage() {
        return "groups";
    }

    @GetMapping("/groups/getall")
    @ResponseBody
    public String getAllGroups() {
        Gson gson = null;
        List<Group> list = null;
        String result = null;

        try {
            gson = new Gson();
            System.out.println("TRY ))))))))))))))))))))))))))))))))))");

            list = groupService.getAll();
            //list = new ArrayList<>();
            System.out.println("DONE ))))))))))))))))))))))))))))))))))");

            result = gson.toJson(list);
        } catch (NullPointerException e) {
            result = e.getMessage();
        }

        if (result.trim().isEmpty()) {
            result = "No groups :(";
        }

        return result;
    }
}
