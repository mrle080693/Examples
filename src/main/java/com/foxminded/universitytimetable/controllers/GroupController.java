package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GroupController {
    @Autowired
    GroupService groupService;
    private Gson gson = new Gson();

    @GetMapping("/groups")
    public String getGroupsPage() {
        return "groups";
    }

    /*
    @RequestMapping(value = "/groups/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestParam String groupInJson) {
        Group group = gson.fromJson(groupInJson, Group.class);
        int newGroupId = groupService.add(group);
        String result = String.valueOf(newGroupId);

        return result;
    }
    */

    @RequestMapping(value = "/groups/add", method = RequestMethod.GET)
    public @ResponseBody
    int add(@RequestBody @RequestParam Group group) {
        int newGroupId = groupService.add(group);
        return newGroupId;
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

    @RequestMapping(value = "/groups/getByName", method = RequestMethod.GET)
    @ResponseBody
    public String getByName(@RequestParam String name) {
        List<Group> groupsWithInputName = groupService.getByName(name);
        String result = gson.toJson(groupsWithInputName);

        return result;
    }

    @GetMapping("/groups/getById")
    @ResponseBody
    public String getById(@RequestParam String inputId) {
        int parsedInputId = Integer.valueOf(inputId);
        Group group = groupService.getById(parsedInputId);
        String result = gson.toJson(group);

        return result;
    }


}
