package com.foxminded.universitytimetable.dao.impl.repositories;

import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TimetableRepository {
    @Query(JPQLQueries.GET_GROUP_TIMETABLE)
    List<Lesson> getGroupTimetable(int groupId, Date from, Date till);

    @Query(JPQLQueries.GET_PROFESSOR_TIMETABLE)
    List<Lesson> getProfessorTimetable(int professorId, Date from, Date till);
}
