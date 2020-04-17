package com.foxminded.universitytimetable.db.dao.daomodels;

import com.foxminded.universitytimetable.db.models.Group;

import java.util.List;

public interface GroupDAO {
    void add(Group group);

    List<Group> getAll();

    Group getByName(String name);

    void update(Group group);

    void remove(Group group);
}
