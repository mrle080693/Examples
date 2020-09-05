package com.foxminded.universitytimetable.dao.impl.hibernate;

import com.foxminded.universitytimetable.dao.StatisticsDAO;

import java.util.Date;

public class StatisticsImplHibernate implements StatisticsDAO {
    @Override
    public int getProfessorEmployment(int professorId, Date from, Date till) {
        return 0;
    }

    @Override
    public int getGroupEmployment(int groupId, Date from, Date till) {
        return 0;
    }
}
