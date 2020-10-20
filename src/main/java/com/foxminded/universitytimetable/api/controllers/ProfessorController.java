package com.foxminded.universitytimetable.api.controllers;

import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.services.ProfessorService;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/professors")
public class ProfessorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorController.class);

    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping("/add")
    public String save(@RequestParam String newName, @RequestParam String newSurname,
                       @RequestParam String newPatronymic, @RequestParam String newSubject) {
        LOGGER.debug("Try to add professor with: " + " name = " + newName);

        Professor professor;

        try {
            professor = new Professor(newName, newSurname, newPatronymic, newSubject);
            professorService.add(professor);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }

        LOGGER.debug("Successfully add professor with id = " + professor);

        return "redirect:/professors";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getProfessorsViewAndAllProfessors() {
        LOGGER.debug("Try get professors.html with all professors");

        ModelAndView modelAndView = new ModelAndView("professors");
        List<Professor> professors = new ArrayList<>();

        try {
            professors = professorService.getAll();
            modelAndView.addObject("professors", professors);

            LOGGER.debug("professors.html successfully got with professors: " + professors.size());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

        LOGGER.debug("Successfully got with groups quantity: " + professors.size());

        return modelAndView;
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable("id") int id) {
        LOGGER.debug("Try get professors.html with professor with id = " + id);

        ModelAndView modelAndView = new ModelAndView("professors");

        try {
            Professor professor = professorService.getById(id);
            modelAndView.addObject("id", professor.getId());
            modelAndView.addObject("name", professor.getName());
            modelAndView.addObject("surname", professor.getSurname());
            modelAndView.addObject("patronymic", professor.getPatronymic());
            modelAndView.addObject("subject", professor.getSubject());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

        LOGGER.debug("professors.html successfully got with professor with id = : " + id);

        return modelAndView;
    }

    @PostMapping("/update")
    public String update(@RequestParam(value = "id", defaultValue = "0") int id, @RequestParam String newName,
                         @RequestParam String newSurname, @RequestParam String newPatronymic, @RequestParam String newSubject) {
        LOGGER.debug("Try to update professor with: " + "id = " + id + " name = " + newName
                + " Surname = " + newSurname + " Patronymic = " + newPatronymic + " Subject = " + newSubject);

        try {
            Professor professor = new Professor(newName, newSurname, newPatronymic, newSubject);
            professor.setId(id);

            professorService.update(professor);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

        LOGGER.debug("Successfully update professor with new name: " + newName);

        return "redirect:/professors";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam int id) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to remove professor with id = " + id);

        try {
            professorService.remove(id);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully remove professor with id: " + id);

        return "redirect:/professors";
    }
}
