package com.foxminded.universitytimetable.db.dao.daoservices;

import com.foxminded.universitytimetable.db.models.Group;
import com.foxminded.universitytimetable.db.models.Lesson;
import com.foxminded.universitytimetable.db.models.Professor;
import com.foxminded.universitytimetable.db.models.Timetable;

import java.util.Date;

public interface TimetableServiceDAO {
    Timetable getGroupTimetable(Group group, Date from, Date till);

    Timetable getProfessorTimetable(Professor professor, Date from, Date till);

    void addLesson(Lesson lesson);
}
