package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Group;

import java.util.List;

public interface IGroupDAO {
    // Create
    void add(Group group);

    // Read
    List<Group> getAll();

    Group getByName(String name);

    // Update
    void update(Group group);

    // Delete
    void remove(Group group);
}
