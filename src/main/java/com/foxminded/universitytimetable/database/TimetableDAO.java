package com.foxminded.universitytimetable.database;

import com.foxminded.universitytimetable.models.Lesson;

import java.util.Date;
import java.util.List;

public interface TimetableDAO {
    List<Lesson> getGroupTimetable(int groupId, Date from, Date till);

    List<Lesson> getProfessorTimetable(int professorId, Date from, Date till);
}
