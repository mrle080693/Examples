package com.foxminded.universitytimetable.dao.impl.hibernate;

import com.foxminded.universitytimetable.dao.TimetableDAO;
import com.foxminded.universitytimetable.models.Lesson;

import java.util.Date;
import java.util.List;

public class TimetableImplHibernate implements TimetableDAO {
    @Override
    public List<Lesson> getGroupTimetable(int groupId, Date from, Date till) {
        return null;
    }

    @Override
    public List<Lesson> getProfessorTimetable(int professorId, Date from, Date till) {
        return null;
    }
}
