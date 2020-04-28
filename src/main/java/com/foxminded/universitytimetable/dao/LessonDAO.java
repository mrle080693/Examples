package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Lesson;

import java.util.List;

public interface LessonDAO {
    void add(Lesson lesson);

    List<Lesson> getAll();

    Lesson getById(int id);

    void update(Lesson lesson);

    void remove(Lesson lesson);
}
