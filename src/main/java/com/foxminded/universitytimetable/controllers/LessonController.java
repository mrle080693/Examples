package com.foxminded.universitytimetable.controllers;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/lessons")
public class LessonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonController.class);

    @Autowired
    private LessonService lessonService;

    @PostMapping("/add")
    public String add(@RequestParam Date date, @RequestParam int lessonNumber, @RequestParam int groupId,
                      @RequestParam int professorId, @RequestParam String building, @RequestParam String classroom) {

        LOGGER.debug("Try to add lesson with: " + "date = " + date
                + " lesson number = " + lessonNumber + " group id = " + groupId + " professor id = " + professorId
                + " building = " + building + " classroom = " + classroom);

        int id = 0;

        try {
            Lesson lesson = new Lesson(date, lessonNumber, groupId, professorId, building, classroom);
            id = lessonService.add(lesson);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        } catch (ValidationException e) {
            LOGGER.warn(e.getValidationExceptionMessage());
        }

        LOGGER.debug("Successfully add lesson with id = " + id);

        return "redirect:/lessons";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAll() {
        LOGGER.debug("Try get lessons.html with all lessons");

        ModelAndView modelAndView = new ModelAndView("lessons");
        List<Lesson> lessons = null;

        try {
            lessons = lessonService.getAll();
            modelAndView.addObject("lessons", lessons);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        }

        if (lessons != null) {
            LOGGER.debug("lessons.html successfully got with lessons: " + lessons.size());
        } else {
            LOGGER.debug("lessons.html successfully got without lessons");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable("id") int id) {
        LOGGER.debug("Try get lessons.html with lesson with id = " + id);

        ModelAndView modelAndView = new ModelAndView("lessons");

        try {
            Lesson lesson = lessonService.getById(id);
            modelAndView.addObject("id", lesson.getId());
            modelAndView.addObject("date", lesson.getDate());
            modelAndView.addObject("lessonNumber", lesson.getLessonNumber());
            modelAndView.addObject("groupId", lesson.getGroupId());
            modelAndView.addObject("professorId", lesson.getProfessorId());
            modelAndView.addObject("building", lesson.getBuilding());
            modelAndView.addObject("classroom", lesson.getClassroom());
        } catch (NotFoundEntityException e) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            LOGGER.warn("Try to get lesson with not existing id = " + id);
        }

        LOGGER.debug("lessons.html successfully got with lesson with id = : " + id);

        return modelAndView;
    }

    @PostMapping("/update")
    public String update(@RequestParam(value = "id", defaultValue = "0") int id, @RequestParam Date date,
                         @RequestParam int lessonNumber, @RequestParam int groupId, @RequestParam int professorId,
                         @RequestParam String building, @RequestParam String classroom) {

        LOGGER.debug("Try to update lesson with: id = " + id + " date = " + date
                + " lesson number = " + lessonNumber + " group id = " + groupId + " professor id = " + professorId
                + " building = " + building + " classroom = " + classroom);

        try {
            Lesson lesson = new Lesson(date, lessonNumber, groupId, professorId, building, classroom);
            lesson.setId(id);

            lessonService.update(lesson);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        } catch (ValidationException e) {
            LOGGER.warn(e.getValidationExceptionMessage());
        }

        LOGGER.debug("Successfully update lesson with id: " + id);

        return "redirect:/lessons";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam int id) {
        LOGGER.debug("Try to remove lesson with id = " + id);

        try {
            lessonService.remove(id);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        }

        LOGGER.debug("Successfully remove lesson with id: " + id);

        return "redirect:/lessons";
    }
}
