package com.foxminded.universitytimetable.dao.interfaces;

import java.util.Date;

public interface StatisticsDAO {
    int getProfessorEmployment(int professorId, Date from, Date till);

    int getGroupEmployment(int groupId, Date from, Date till);
}
