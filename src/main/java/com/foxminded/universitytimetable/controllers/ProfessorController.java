package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Group;
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

    @PostMapping("/add")
    public String save(@RequestParam String newName, @RequestParam String newSurname,
                       @RequestParam String newPatronymic, @RequestParam String newSubject) {
        LOGGER.debug("Try to add professor with: " + " name = " + newName);

        Professor professor = new Professor(newName, newSurname, newPatronymic, newSubject);
        int id = professorService.add(professor);

        LOGGER.debug("Successfully add professor with id = " + id);

        return "redirect:/professors";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getProfessorsViewAndAllProfessors() {
        LOGGER.debug("Try get professors.html with all professors");

        ModelAndView modelAndView = new ModelAndView("professors");
        List<Professor> professors = null;

        try {
            professors = professorService.getAll();
            modelAndView.addObject("professors", professors);

            LOGGER.debug("professors.html successfully got with professors: " + professors.size());
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        }

        if (professors != null) {
            LOGGER.debug("groups.html successfully got with professors: " + professors.size());
        } else {
            LOGGER.debug("groups.html successfully got without professors");
        }

        return modelAndView;
    }

    @GetMapping("/get_all")
    @ResponseBody
    public List<Professor> getAll() {
        LOGGER.debug("Try get all professors");

        List<Professor> professors = null;

        try {
            professors = professorService.getAll();
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        }

        if (professors != null) {
            LOGGER.debug("Successfully got with professors: " + professors.size());
        } else {
            LOGGER.debug("Successfully got without professors");
        }

        return professors;
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
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            LOGGER.warn("Try to get professor with not existing id = " + id);
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
            LOGGER.warn(e.getEmptyResultExceptionMessage());
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
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Successfully remove professor with id: " + id);

        return "redirect:/professors";
    }
}
