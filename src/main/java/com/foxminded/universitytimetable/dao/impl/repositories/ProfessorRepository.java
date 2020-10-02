package com.foxminded.universitytimetable.dao.impl.repositories;

import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    @Query(JPQLQueries.GET_PROFESSORS_BY_SURNAME)
    List<Professor> getBySurname(String surname);

    @Query(JPQLQueries.DELETE_PROFESSOR)
    int remove(int id);
}
