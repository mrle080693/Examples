package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("/groups")
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("/groups")
    public String getGroupsPage(Model model) {
        List<Group> groups = groupService.getAll();

        model.addAttribute("groups", groups);

        return "groups";
    }

    @PostMapping("/groups/add")
    public String add(@RequestParam String groupName) {
        Group group = new Group(groupName);
        groupService.add(group);

        return "redirect:/groups";
    }

    @PostMapping("/groups/update")
    public String update(@RequestParam int id, @RequestParam String newName) {
        Group group = new Group(newName);
        group.setId(id);

        groupService.update(group);

        return "redirect:/groups";
    }

    @PostMapping("/groups/remove")
    public String remove(@RequestParam int id) {
        groupService.remove(id);
        return "redirect:/groups";
    }

    // lessons.html have to return
}
