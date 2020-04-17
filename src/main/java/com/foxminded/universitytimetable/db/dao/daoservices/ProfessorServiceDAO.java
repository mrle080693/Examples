package com.foxminded.universitytimetable.db.dao.daoservices;

import com.foxminded.universitytimetable.db.models.Professor;

public interface ProfessorServiceDAO {
    void add(Professor professor);

    void remove(Professor professor);
}
