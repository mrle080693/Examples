package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Lesson;

import java.util.List;

public interface ILessonDAO {
    // Create
    void add(Lesson lesson);

    // Read
    List<Lesson> getAll();

    Lesson getByLessonNumber(int lessonNumber);

    // Update
    void update(Lesson lesson);

    // Delete
    void remove(Lesson lesson);
}
