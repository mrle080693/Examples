package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Group;

import java.util.List;

public interface GroupDAO {
    void add(Group group);

    List<Group> getAll();

    Group getById(int id);

    Group getByName(String name);

    void update(Group group);

    void remove(Group group);
}
