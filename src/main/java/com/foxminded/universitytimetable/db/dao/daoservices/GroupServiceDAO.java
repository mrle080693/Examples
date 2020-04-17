package com.foxminded.universitytimetable.db.dao.daoservices;

import com.foxminded.universitytimetable.db.models.Group;

public interface GroupServiceDAO {
    void add(Group group);

    void remove(Group group);
}
