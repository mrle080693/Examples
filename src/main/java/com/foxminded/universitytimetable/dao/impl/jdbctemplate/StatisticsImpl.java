package com.foxminded.universitytimetable.dao.impl.jdbctemplate;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import com.foxminded.universitytimetable.dao.queries.SQLQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("statisticsImplBean")
public class StatisticsImpl implements StatisticsDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatisticsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long getGroupEmployment(int groupId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group employment. Group id: " + groupId + "from: " + from + "till: " + till);
        }

        int lessonsQuantity = jdbcTemplate.queryForObject(SQLQueries.GET_GROUP_EMPLOYMENT,
                new Object[]{groupId, from, till}, Integer.class);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get group employment. Group id = " + groupId + "from: " + from + "till: " + till);
        }

        return lessonsQuantity;
    }

    @Override
    public long getProfessorEmployment(int professorId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor employment. Professor id: " + professorId + " from: " + from + "till: "
                    + till);
        }

        int lessonsQuantity = jdbcTemplate.queryForObject(SQLQueries.GET_PROFESSOR_EMPLOYMENT,
                new Object[]{professorId, from, till}, Integer.class);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get professor employment. Professor id = " + professorId + "from: " + from +
                    "till: " + till);
        }

        return lessonsQuantity;
    }
}
