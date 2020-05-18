package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.dao.impl.TimetableImpl;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.EntityValidationException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("timetableServiceBean")
public class TimetableService {
    private final TimetableImpl timetableImpl;
    private final GroupImpl groupImpl;
    private final ProfessorImpl professorImpl;

    @Autowired
    public TimetableService(TimetableImpl timetableImpl, GroupImpl groupImpl, ProfessorImpl professorImpl) {
        this.timetableImpl = timetableImpl;
        this.groupImpl = groupImpl;
        this.professorImpl = professorImpl;
    }

    public List<Lesson> getGroupTimetable(int groupId, Date from, Date till) {
        List<Lesson> lessons;

        try {
            groupImpl.getById(groupId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Group is not exists", ex);
        }

        try {
            lessons = timetableImpl.getGroupTimetable(groupId, from, till);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get group timetable", ex);
        }

        if (from == null || till == null) {
            throw new EntityValidationException("Date cant be null");
        }

        return lessons;
    }

    public List<Lesson> getProfessorTimetable(int professorId, Date from, Date till) {
        List<Lesson> lessons;

        try {
            professorImpl.getById(professorId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Professor is not exist", ex);
        }

        try {
            lessons = timetableImpl.getProfessorTimetable(professorId, from, till);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get professor timetable", ex);
        }

        if (from == null || till == null) {
            throw new EntityValidationException("Date cant be null");
        }

        return lessons;
    }
}
