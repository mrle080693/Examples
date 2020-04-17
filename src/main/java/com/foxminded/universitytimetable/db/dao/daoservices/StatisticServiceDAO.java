package com.foxminded.universitytimetable.db.dao.daoservices;

import com.foxminded.universitytimetable.db.models.Group;
import com.foxminded.universitytimetable.db.models.Professor;

import java.util.Date;

public interface StatisticServiceDAO {
    int getProfessorEmployment(Professor professor, Date from, Date till);

    int getGroupEmployment(Group group, Date from, Date till);
}
