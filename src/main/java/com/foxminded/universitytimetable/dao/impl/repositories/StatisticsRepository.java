package com.foxminded.universitytimetable.dao.impl.repositories;

import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface StatisticsRepository {
    @Query(JPQLQueries.GET_GROUP_EMPLOYMENT)
    Integer getGroupEmployment(int groupId, Date from, Date till);

    @Query(JPQLQueries.GET_PROFESSOR_EMPLOYMENT)
    Integer getProfessorEmployment(int professorId, Date from, Date till);
}
