package com.foxminded.universitytimetable.db.dao.daomodels;

import com.foxminded.universitytimetable.db.models.Lesson;

import java.util.List;

public interface LessonDAO {
    void add(Lesson lesson);

    List<Lesson> getAll();

    Lesson getByLessonNumber(int lessonNumber);

    void update(Lesson lesson);

    void remove(Lesson lesson);
}
