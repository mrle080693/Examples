package com.foxminded.universitytimetable.database.impl;

import com.foxminded.universitytimetable.database.ProfessorDAO;
import com.foxminded.universitytimetable.database.impl.repositories.ProfessorRepository;
import com.foxminded.universitytimetable.models.Professor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("professorImplBean")
public class ProfessorImpl implements ProfessorDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorImpl.class);
    private ProfessorRepository professorRepository;

    @Autowired
    public ProfessorImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public Professor add(Professor professor) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add professor: " + professor);
        }

        Professor returnedProfessor = professorRepository.save(professor);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add professor with id = " + returnedProfessor);
        }

        return returnedProfessor;
    }

    @Override
    public List<Professor> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table professors");
        }

        List<Professor> professors = professorRepository.findAll();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professors);
        }

        return professors;
    }

    @Override
    public Professor getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor by id = " + id);
        }

        Professor professor = professorRepository.getOne(id);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professor);
        }

        return professor;
    }

    @Override
    public List<Professor> getBySurname(String surname) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor by surname = " + surname);
        }

        List<Professor> professors = professorRepository.getBySurname(surname);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professors);
        }

        return professors;
    }

    @Override
    public Professor update(Professor professor) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update professor: " + professor);
        }

        Professor returnedProfessor = professorRepository.save(professor);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update professor with id = " + returnedProfessor);
        }

        return returnedProfessor;
    }

    @Override
    public Professor remove(int professorId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove professor with id = " + professorId);
        }

        Professor status = professorRepository.remove(professorId);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove professor with id: " + professorId);
        }

        return status;
    }
}
