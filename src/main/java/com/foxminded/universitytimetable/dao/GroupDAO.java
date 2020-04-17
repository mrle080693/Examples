package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.dao.models.Group;
import com.foxminded.universitytimetable.dao.models.Lesson;
import com.foxminded.universitytimetable.dao.models.Timetable;

import java.util.Date;
import java.util.List;

public interface GroupDAO {
    void add(Group group);

    List<Group> getAll();

    Group getById(int id);

    Group getByName(String name);

    void update(Group group);

    void remove(Group group);

    Timetable getTimetable(int groupId, Date from, Date till);

    void addLesson(Lesson lesson);
}
