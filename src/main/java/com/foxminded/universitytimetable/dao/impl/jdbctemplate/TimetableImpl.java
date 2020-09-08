package com.foxminded.universitytimetable.dao.impl.jdbctemplate;

import com.foxminded.universitytimetable.dao.TimetableDAO;
import com.foxminded.universitytimetable.dao.queries.SQLQueries;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.rowmappers.LessonMapper;
import com.foxminded.universitytimetable.models.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("timetableImplBean")
public class TimetableImpl implements TimetableDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimetableImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimetableImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Lesson> getGroupTimetable(int groupId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group timetable. Group id: " + groupId + "from: " + from + "till: " + till);
        }

        List<Lesson> lessons = jdbcTemplate.query(SQLQueries.GET_GROUP_TIMETABLE, new Object[]{groupId, from, till},
                new LessonMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get group timetable. Group id = " + groupId + "from: " + from + "till: " + till);
        }

        return lessons;
    }

    @Override
    public List<Lesson> getProfessorTimetable(int professorId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor timetable. Professor id: " + professorId + " from: " + from + "till: "
                    + till);
        }

        List<Lesson> lessons = jdbcTemplate.query(SQLQueries.GET_PROFESSOR_TIMETABLE, new Object[]{professorId, from, till},
                new LessonMapper());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get professor timetable. Professor id = " + professorId + "from: " + from +
                    "till: " + till);
        }

        return lessons;
    }
}
