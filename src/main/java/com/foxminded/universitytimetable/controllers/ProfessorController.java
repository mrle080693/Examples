package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.services.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/professors")
public class ProfessorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorController.class);

    @Autowired
    private ProfessorService professorService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAll() {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try get professors.html with all professors");

        ModelAndView modelAndView = new ModelAndView("professors");
        List<Professor> professors = null;

        try {
            professors = professorService.getAll();
            modelAndView.addObject("professors", professors);

            if (LOGGER.isDebugEnabled())
                LOGGER.debug("professors.html successfully got with professors: " + professors.size());
        } catch (NotFoundEntityException e) {
            // Do nothing
        }

        if (professors != null) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("groups.html successfully got with professors: " + professors.size());
        } else {
            LOGGER.debug("groups.html successfully got without professors");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable("id") int id) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try get professors.html with professor with id = " + id);

        ModelAndView modelAndView = new ModelAndView("professors");

        try {
            Professor professor = professorService.getById(id);
            modelAndView.addObject("professor", professor);
        } catch (NotFoundEntityException e) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            LOGGER.warn("Try to get professor with not existing id = " + id);
        }

        LOGGER.debug("professors.html successfully got with professor with id = : " + id);

        return modelAndView;
    }

    @PostMapping("/save")
    public String save(@RequestParam(value = "id", defaultValue = "0") int id, @RequestParam String newName,
                       @RequestParam String newSurname, @RequestParam String newPatronymic, @RequestParam String newSubject) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try save professor with: " + "id = " + id + " name = " + newName
                + " Surname = " + newSurname + " Patronymic = " + newPatronymic + " Subject = " + newSubject);

        try {
            Professor professor = new Professor(newName, newSurname, newPatronymic, newSubject);
            if (id != 0) {
                try {
                    professor.setId(id);
                    professorService.update(professor);
                } catch (ValidationException | NotFoundEntityException e) {
                    professor.setId(0);
                    professorService.add(professor);
                }
            } else {
                professorService.add(professor);
            }
        } catch (ValidationException e) {
            // Do nothing but only till task 15
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully add professor with id = " + id);

        return "redirect:/professors";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam int id) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Try to remove professor with id = " + id);

        try {
            professorService.remove(id);
        } catch (NotFoundEntityException e) {
            // Do nothing but only till task 15
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully remove professor with id: " + id);

        return "redirect:/professors";
    }
}
