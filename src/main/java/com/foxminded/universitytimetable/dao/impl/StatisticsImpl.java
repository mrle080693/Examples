package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import com.foxminded.universitytimetable.dao.impl.repositories.LessonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("statisticsImplBean")
public class StatisticsImpl implements StatisticsDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsImpl.class);
    private LessonRepository lessonRepository;

    @Autowired
    public StatisticsImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public long getProfessorEmployment(int professorId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor employment. Professor id: " + professorId + " from: " + from + "till: "
                    + till);
        }

        int professorEmployment = lessonRepository.getProfessorEmployment(professorId, from, till);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get professor employment. Professor id = " + professorId + "from: " + from +
                    "till: " + till);
        }

        return professorEmployment;
    }

    @Override
    public long getGroupEmployment(int groupId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group employment. Group id: " + groupId + "from: " + from + "till: " + till);
        }

        int groupEmployment = lessonRepository.getGroupEmployment(groupId, from, till);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get group employment. Group id = " + groupId + "from: " + from + "till: " + till);
        }

        return groupEmployment;
    }
}
