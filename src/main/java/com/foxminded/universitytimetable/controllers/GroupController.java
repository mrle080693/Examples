package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
I want to see SRD ))
Not CRUD!!!
 */
@Controller
public class GroupController {
    @Autowired
    GroupService groupService;
    private Gson gson = new Gson();

    @GetMapping("/groups")
    public String getGroupsPage() {
        return "groups";
    }

    @GetMapping("/groups/add")
    @ResponseBody
    public String add(JsonObject jsonObject) {
        Group group = gson.fromJson(jsonObject, Group.class);
        int newGroupId = groupService.add(group);
        String result = String.valueOf(newGroupId);

        return result;
    }

    @GetMapping("/groups/getall")
    @ResponseBody
    public String getAll() {
        List<Group> list = null;
        String result = null;

        try {
            System.out.println("TRY ))))))))))))))))))))))))))))))))))");
            list = groupService.getAll();
            //list = new ArrayList<>();
            System.out.println("DONE ))))))))))))))))))))))))))))))))))");

            result = gson.toJson(list);
        } catch (Exception e) {
            result = e.getMessage();
        }

        if (result.trim().isEmpty()) {
            result = "No groups :(";
        }

        return result;
    }

    @GetMapping("/groups/getByName")
    @ResponseBody
    public String getByName(String inputGroupName) {
        List<Group> groupsWithInputName = groupService.getByName(inputGroupName);
        String result = gson.toJson(groupsWithInputName);

        return result;
    }
}
