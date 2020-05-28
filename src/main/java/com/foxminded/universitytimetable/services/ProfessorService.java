package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.dao.ProfessorDAO;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.EntityValidationException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("professorServiceBean")
public class ProfessorService {
    private final ProfessorDAO professorDAO;

    @Autowired
    public ProfessorService(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    public int add(Professor professor) {
        int professorIdInTable;

        try {
            checkProfessor(professor);

            if (professor.getId() != 0) {
                throw new EntityValidationException("New professor id must be 0. \n" +
                        "If you want update professor you have to use update method");
            }

            professorIdInTable = professorDAO.add(professor);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant add professor", ex);
        }

        return professorIdInTable;
    }

    public List<Professor> getAll() {
        List<Professor> professors;

        try {
            professors = professorDAO.getAll();

            if (professors.isEmpty()) {
                throw new NotFoundEntityException("Table professors is empty");
            }
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get all from table professors", ex);
        }

        return professors;
    }

    public Professor getById(int id) {
        Professor professor;

        try {
            if (id == 0) {
                throw new EntityValidationException("Professor id cant be 0");
            }

            professor = professorDAO.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Table professors have not rows with input id", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get by id from table professors", ex);
        }

        return professor;
    }

    public List<Professor> getBySurname(String surname) {
        List<Professor> professors;

        try {
            if (surname == null) {
                throw new EntityValidationException("Professor surname have not be null");
            }

            if (surname.trim().isEmpty()) {
                throw new EntityValidationException("Professor surname have not be empty");
            }

            professors = professorDAO.getBySurname(surname);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundEntityException("Table professors have not rows with input surname", ex);
        } catch (DataAccessException ex) {
            throw new DAOException("Cant get by surname from table professors", ex);
        }

        return professors;
    }

    public int update(Professor professor) {
        int status;

        try {
            checkProfessor(professor);

            if (professor.getId() == 0) {
                throw new EntityValidationException("New professor id must not be 0. \n" +
                        "If you want add new professor you have to use add method");
            }

            status = professorDAO.update(professor);

            if (status != 1) {
                throw new NotFoundEntityException("Professor with input id doesnt exist");
            }
        } catch (DataAccessException ex) {
            throw new DAOException("Cant update table professors", ex);
        }

        return status;
    }

    public int remove(int professorId) {
        int status;

        try {
            status = professorDAO.remove(professorId);

            if (status != 1) {
                throw new NotFoundEntityException("Professor with input id doesnt exist");
            }
        } catch (DataAccessException ex) {
            throw new DAOException("Cant remove element of table professors", ex);
        }

        return status;
    }

    private void checkProfessor(Professor professor) {
        if (professor == null) {
            throw new IllegalArgumentException("Professor for add or update cant be null");
        }

        String name = professor.getName();
        String surname = professor.getSurname();
        String patronymic = professor.getPatronymic();
        String subject = professor.getSubject();

        if (name == null || surname == null || patronymic == null || subject == null) {
            throw new EntityValidationException("Professor must have name, surname, patronymic and subject not null");
        }

        if (name.trim().isEmpty() || surname.trim().isEmpty() || patronymic.trim().isEmpty() || subject.trim().isEmpty()) {
            throw new EntityValidationException("Professor name, surname, patronymic and subject must not be empty");
        }
    }
}
