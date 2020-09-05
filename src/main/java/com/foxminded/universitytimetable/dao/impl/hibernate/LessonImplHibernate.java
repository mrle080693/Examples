package com.foxminded.universitytimetable.dao.impl.hibernate;

import com.foxminded.universitytimetable.dao.LessonDAO;
import com.foxminded.universitytimetable.models.Lesson;

import java.util.List;

public class LessonImplHibernate implements LessonDAO {
    @Override
    public int add(Lesson lesson) {
        return 0;
    }

    @Override
    public List<Lesson> getAll() {
        return null;
    }

    @Override
    public Lesson getById(int id) {
        return null;
    }

    @Override
    public int update(Lesson lesson) {
        return 0;
    }

    @Override
    public int remove(int lessonId) {
        return 0;
    }
}
