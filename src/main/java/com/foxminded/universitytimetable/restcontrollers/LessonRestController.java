package com.foxminded.universitytimetable.restcontrollers;

import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestController
@RequestMapping("/rest_lessons")
public class LessonRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonRestController.class);

    private final LessonService lessonService;

    @Autowired
    public LessonRestController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int add(@RequestParam Lesson lesson) {
        LOGGER.debug("Try to add lesson: " + lesson.toString());
        int id = 0;

        try {
            id = lessonService.add(lesson);
        } catch (ValidationException e) {
            LOGGER.warn(e.getEntityValidationExceptionMessage());
        } catch (HttpClientErrorException e){
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e){
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully add lesson with id = " + id);

        return id;
    }

    @RequestMapping("/get_all")
    public List<Lesson> getAll() {
        LOGGER.debug("Try get all lessons");
        List<Lesson> lessons = null;

        try {
            lessons = lessonService.getAll();
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        } catch (HttpClientErrorException e){
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e){
            LOGGER.error(e.getMessage());
        }

        if (lessons != null) {
            LOGGER.debug("Successfully got " + lessons.size() + " lessons");
        } else {
            LOGGER.debug("Successfully got without lessons");
        }

        return lessons;
    }

    @RequestMapping("/get_by_id")
    public Lesson getById(@RequestParam int id) {
        LOGGER.debug("Try get lesson with id = " + id);
        Lesson lesson = null;

        try {
            lesson = lessonService.getById(id);
        } catch (NotFoundEntityException e) {
            LOGGER.warn("Try to get lesson with not existing id = " + id);
        } catch (HttpClientErrorException e){
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e){
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully got lesson: " + lesson.toString());

        return lesson;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int update(@RequestParam Lesson lesson) {
        LOGGER.debug("Try to update lesson: " + lesson.toString());
        int status = 0;

        try {
            status = lessonService.update(lesson);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        } catch (ValidationException e) {
            LOGGER.warn(e.getEntityValidationExceptionMessage());
        } catch (HttpClientErrorException e){
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e){
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully update lesson: " + lesson.toString());

        return status;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int remove(@RequestParam int lessonId) {
        LOGGER.debug("Try to remove lesson with id = " + lessonId);
        int status = 0;

        try {
            status = lessonService.remove(lessonId);
        } catch (NotFoundEntityException e) {
            LOGGER.warn(e.getEmptyResultExceptionMessage());
        } catch (HttpClientErrorException e){
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e){
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully remove lesson with id: " + lessonId);

        return status;
    }
}
