package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.TimetableDAO;
import com.foxminded.universitytimetable.dao.impl.queries.Queries;
import com.foxminded.universitytimetable.dao.impl.rowmappers.GroupMapper;
import com.foxminded.universitytimetable.dao.impl.rowmappers.LessonMapper;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("timetableImplBean")
public class TimetableImpl implements TimetableDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Lesson> getGroupTimetable(int groupId, Date from, Date till) {
        List<Lesson> lessons;

        try {
            // For check if group with such id exists
            // Maybe this checking will be in service
            jdbcTemplate.queryForObject(Queries.GET_GROUP_BY_ID_QUERY, new Object[]{groupId},
                    new GroupMapper());

            lessons = jdbcTemplate.query(Queries.GET_GROUP_TIMETABLE_QUERY, new Object[]{groupId, from, till},
                    new LessonMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Row with input id doesnt exist", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get group timetable", ex);
        }

        return lessons;
    }

    @Override
    public List<Lesson> getProfessorTimetable(int professorId, Date from, Date till) {
        List<Lesson> lessons;

        try {
            // For check if professor with such id exists
            // Maybe this checking will be in service
            jdbcTemplate.queryForObject(Queries.GET_PROFESSOR_BY_ID_QUERY, new Object[]{professorId},
                    new GroupMapper());


            lessons = jdbcTemplate.query(Queries.GET_PROFESSOR_TIMETABLE_QUERY, new Object[]{professorId, from, till},
                    new LessonMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Row with input id doesnt exist", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get professor timetable", ex);
        }

        return lessons;
    }
}
