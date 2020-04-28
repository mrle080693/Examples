package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.TimetableDAO;
import com.foxminded.universitytimetable.dao.impl.rowmappers.LessonMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static com.foxminded.universitytimetable.dao.impl.queries.Queries.GET_PROFESSOR_TIMETABLE_QUERY;
import static com.foxminded.universitytimetable.dao.impl.queries.Queries.GET_GROUP_TIMETABLE_QUERY;

@Repository
public class TimetableImpl implements TimetableDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Lesson> getGroupTimetable(int groupId, Date from, Date till) {
        return getTimetable(groupId, from, till, false);
    }

    @Override
    public List<Lesson> getProfessorTimetable(int professorId, Date from, Date till) {
        return getTimetable(professorId, from, till, true);
    }

    private List<Lesson> getTimetable(int professorOrGroupId, Date from, Date till, boolean isProfessor) {
        List<Lesson> lessons = null;

        try {
            if (isProfessor) {
                lessons = jdbcTemplate.query(GET_PROFESSOR_TIMETABLE_QUERY, new Object[]{professorOrGroupId, from, till},
                        new LessonMapper());
            } else {
                lessons = jdbcTemplate.query(GET_GROUP_TIMETABLE_QUERY, new Object[]{professorOrGroupId, from, till},
                        new LessonMapper());
            }
        } catch (DataAccessException dae) {
            throw new DAOException("Cant get timetable", dae);
        }

        return lessons;
    }
}
