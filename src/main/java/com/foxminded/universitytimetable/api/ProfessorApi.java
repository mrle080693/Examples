package com.foxminded.universitytimetable.api;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.services.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api_professors")
public class ProfessorApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorApi.class);

    private final ProfessorService professorService;

    @Autowired
    public ProfessorApi(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int add(@RequestParam Professor professor) {
        LOGGER.debug("Try to add professor: " + professor.toString());
        int id = 0;

        try {
            id = professorService.add(professor);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully add professor with id = " + id);

        return id;
    }

    @RequestMapping("/get_all")
    public List<Professor> getAll() {
        LOGGER.debug("Try to get all professors");

        List<Professor> professors = null;

        try {
            professors = professorService.getAll();
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        if (professors != null) {
            LOGGER.debug("Successfully got with professors: " + professors.size());
        } else {
            LOGGER.debug("Successfully got without professors");
        }

        return professors;
    }

    @RequestMapping("/get_by_id")
    public Professor getById(@RequestParam int id) {
        LOGGER.debug("Try to get professor with id = " + id);
        Professor professor = null;

        try {
            professor = professorService.getById(id);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully got with professor: " + professor.toString());

        return professor;
    }

    @RequestMapping("/get_by_surname")
    public List<Professor> getBySurname(@RequestParam String surname) {
        LOGGER.debug("Try to get all professors with surname = " + surname);
        List<Professor> professors = null;

        try {
            professors = professorService.getBySurname(surname);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        if (professors != null) {
            LOGGER.debug("Successfully got with professors: " + professors.size());
        }

        LOGGER.debug("Successfully got without professors");

        return professors;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestParam Professor professor) {
        LOGGER.debug("Try to update professor: " + professor.toString());
        int status = 0;

        try {
            status = professorService.update(professor);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully update professor with status:" + status);

        return status;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int remove(@RequestParam int professorId) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to remove professor with id = " + professorId);
        int status = 0;

        try {
            status = professorService.remove(professorId);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully remove professor with id: " + professorId);

        return status;
    }
}
