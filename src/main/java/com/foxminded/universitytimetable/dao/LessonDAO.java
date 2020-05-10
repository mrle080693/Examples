package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Lesson;

import java.util.List;

/*
Можно сделать save() вместо add() и update()
if id = 0 - addQuery
else - updateQuery
 */

public interface LessonDAO {
    int add(Lesson lesson);

    List<Lesson> getAll();

    Lesson getById(int id);

    int update(Lesson lesson);

    int remove(int lessonId);
}
