package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Group;

import java.util.List;

public interface GroupDAO {
    int add(Group group);

    List<Group> getAll();

    Group getById(int id);

    List<Group> getByName(String name);

    int update(Group group);

    int remove(int groupId);
}
