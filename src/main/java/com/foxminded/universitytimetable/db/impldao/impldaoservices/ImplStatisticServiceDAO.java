package com.foxminded.universitytimetable.db.impldao.impldaoservices;

import com.foxminded.universitytimetable.db.dao.daoservices.StatisticServiceDAO;
import com.foxminded.universitytimetable.db.models.Group;
import com.foxminded.universitytimetable.db.models.Professor;

import java.util.Date;

public class ImplStatisticServiceDAO implements StatisticServiceDAO {
    public int getProfessorEmployment(Professor professor, Date from, Date till) {
        return 0;
    }

    public int getGroupEmployment(Group group, Date from, Date till) {
        return 0;
    }
}
