package com.foxminded.universitytimetable.dao.impl.repositories;

import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query(JPQLQueries.DELETE_LESSON)
    int remove(int id);

    @Query(JPQLQueries.GET_GROUP_EMPLOYMENT)
    Integer getGroupEmployment(int groupId, Date from, Date till);

    @Query(JPQLQueries.GET_PROFESSOR_EMPLOYMENT)
    Integer getProfessorEmployment(int professorId, Date from, Date till);

    @Query(JPQLQueries.GET_GROUP_TIMETABLE)
    List<Lesson> getGroupTimetable(int groupId, Date from, Date till);

    @Query(JPQLQueries.GET_PROFESSOR_TIMETABLE)
    List<Lesson> getProfessorTimetable(int professorId, Date from, Date till);

}
