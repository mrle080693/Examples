package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class GroupImpl implements GroupDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Group group) {

    }

    public List<Group> getAll() {
        return null;
    }

    public Group getById(int id) {
        return null;
    }

    public Group getByName(String name) {
        return null;
    }

    public void update(Group group) {

    }

    public void remove(Group group) {

    }

    public Timetable getTimetable(int groupId, Date from, Date till) {
        return null;
    }

    public void addLesson(Lesson lesson) {

    }
}
