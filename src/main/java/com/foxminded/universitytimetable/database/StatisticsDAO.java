package com.foxminded.universitytimetable.database;

import java.util.Date;

public interface StatisticsDAO {
    long getProfessorEmployment(int professorId, Date from, Date till);

    long getGroupEmployment(int groupId, Date from, Date till);
}
