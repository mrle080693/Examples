package com.foxminded.universitytimetable.database.impl.repositories;

import com.foxminded.universitytimetable.database.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Lesson, Integer> {
    @Query(JPQLQueries.GET_GROUP_TIMETABLE)
    List<Lesson> getGroupTimetable(int groupId, Date from, Date till);

    @Query(JPQLQueries.GET_PROFESSOR_TIMETABLE)
    List<Lesson> getProfessorTimetable(int professorId, Date from, Date till);

}
