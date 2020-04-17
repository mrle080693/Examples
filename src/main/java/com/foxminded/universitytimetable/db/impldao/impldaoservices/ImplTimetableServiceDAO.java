package com.foxminded.universitytimetable.db.impldao.impldaoservices;

import com.foxminded.universitytimetable.db.dao.daoservices.TimetableServiceDAO;
import com.foxminded.universitytimetable.db.models.Group;
import com.foxminded.universitytimetable.db.models.Lesson;
import com.foxminded.universitytimetable.db.models.Professor;
import com.foxminded.universitytimetable.db.models.Timetable;

import java.util.Date;

public class ImplTimetableServiceDAO implements TimetableServiceDAO {
    public Timetable getGroupTimetable(Group group, Date from, Date till) {
        return null;
    }

    public Timetable getProfessorTimetable(Professor professor, Date from, Date till) {
        return null;
    }

    public void addLesson(Lesson lesson) {

    }
}
