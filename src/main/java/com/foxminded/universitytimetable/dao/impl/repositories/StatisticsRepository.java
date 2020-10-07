package com.foxminded.universitytimetable.dao.impl.repositories;

import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface StatisticsRepository extends JpaRepository<Lesson, Integer> {
    @Query(JPQLQueries.GET_GROUP_EMPLOYMENT)
    Integer getGroupEmployment(int groupId, Date from, Date till);

    @Query(JPQLQueries.GET_PROFESSOR_EMPLOYMENT)
    Integer getProfessorEmployment(int professorId, Date from, Date till);

}
