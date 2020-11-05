package com.foxminded.universitytimetable.database;

import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;

import java.util.List;

public interface LessonDAO {
    Lesson add(Lesson lesson);

    List<Lesson> getAll();

    Lesson getById(int id);

    Lesson update(Lesson lesson);

    Integer remove(int lessonId);
}
