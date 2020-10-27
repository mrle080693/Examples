package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.services.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProfessorRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorRestController.class);
    private final ProfessorService professorService;
    private final ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "                        SORRY :( \n" +
                    "We know about this trouble and will correct it soon");

    @Autowired
    public ProfessorRestController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @RequestMapping(value = Urls.API_REST_POST_PROFESSOR_JSON, method = RequestMethod.POST)
    public Professor add(@RequestParam String name, @RequestParam String surname,
                         @RequestParam String patronymic, @RequestParam String subject) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Try to add professor with: " + " name = " + name + " surname = " + surname + " patronymic = " +
                    patronymic + " subject = " + subject);

        Professor professor = new Professor(name, surname, patronymic, subject);

        try {
            professor = professorService.add(professor);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully add professor: " + professor);

        return professor;
    }

    @RequestMapping(Urls.API_REST_GET_PROFESSORS_JSON)
    public List<Professor> getAll() {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to get all professors");

        List<Professor> professors = new ArrayList<>();

        try {
            professors = professorService.getAll();
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully got with professors: " + professors.size());

        return professors;
    }

    @RequestMapping(Urls.API_REST_GET_PROFESSOR_JSON_BY_ID)
    public Professor getById(@PathVariable int id) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to get professor with id = " + id);
        Professor professor = new Professor();

        try {
            professor = professorService.getById(id);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully got with professor: " + professor);

        return professor;
    }

    @RequestMapping(Urls.API_REST_GET_PROFESSOR_JSON_BY_SURNAME)
    public List<Professor> getBySurname(@PathVariable String surname) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to get all professors with surname = " + surname);
        List<Professor> professors = new ArrayList<>();

        try {
            professors = professorService.getBySurname(surname);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully got with professors: " + professors.size());

        return professors;
    }

    @RequestMapping(value = Urls.API_REST_PUT_PROFESSOR_JSON, method = RequestMethod.PUT)
    public Professor update(@RequestParam int id, @RequestParam String name, @RequestParam String surname,
                            @RequestParam String patronymic, @RequestParam String subject) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Try to update professor with: id = " + id + " name = " + name + " surname = " + surname + " patronymic = " +
                    patronymic + " subject = " + subject);

        Professor professor = new Professor(name, surname, patronymic, subject);

        try {
            professor.setId(id);

            professor = professorService.update(professor);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully update professor with status:" + professor);

        return professor;
    }

    @RequestMapping(value = Urls.API_REST_DELETE_PROFESSOR_JSON, method = RequestMethod.DELETE)
    public Professor remove(@RequestParam int professorId) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to remove professor with id = " + professorId);
        Professor returnedProfessor = new Professor();

        try {
            returnedProfessor = professorService.remove(professorId);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw responseStatusException;
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully remove professor with id: " + professorId);

        return returnedProfessor;
    }
}
