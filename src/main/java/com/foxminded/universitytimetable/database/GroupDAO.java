package com.foxminded.universitytimetable.database;

import com.foxminded.universitytimetable.models.Group;

import java.util.List;

public interface GroupDAO {
    Group add(Group group);

    List<Group> getAll();

    Group getById(int id);

    List<Group> getByName(String name);

    Group update(Group group);

    int remove(int groupId);
}
