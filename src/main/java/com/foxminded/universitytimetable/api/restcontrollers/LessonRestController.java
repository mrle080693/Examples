package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.services.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/lessons")
public class LessonRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonRestController.class);

    private final LessonService lessonService;

    @Autowired
    public LessonRestController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Lesson add(@RequestParam Lesson lesson) {
        LOGGER.debug("Try to add lesson: " + lesson);
        Lesson returnedLesson = new Lesson();

        try {
            returnedLesson = lessonService.add(lesson);
        } catch (ValidationException e) {
            LOGGER.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (HttpClientErrorException e) {
            LOGGER.warn(e.getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.debug("Successfully add lesson with id = " + returnedLesson);

        return returnedLesson;
    }

    @RequestMapping
    public List<Lesson> getAll() {
        LOGGER.debug("Try get all lessons");
        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = lessonService.getAll();
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

        LOGGER.debug("Successfully got " + lessons.size() + " lessons");

        return lessons;
    }

    @RequestMapping("/{id}")
    public Lesson getById(@PathVariable int id) {
        LOGGER.debug("Try get lesson with id = " + id);
        Lesson lesson = new Lesson();

        try {
            lesson = lessonService.getById(id);
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

        LOGGER.debug("Successfully got lesson: " + lesson);

        return lesson;
    }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public Lesson update(@RequestParam Lesson lesson) {
        LOGGER.debug("Try to update lesson: " + lesson);
        Lesson returnedLesson = new Lesson();

        try {
            returnedLesson = lessonService.update(lesson);
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

        LOGGER.debug("Successfully update lesson: " + lesson);

        return returnedLesson;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int remove(@RequestParam int lessonId) {
        LOGGER.debug("Try to remove lesson with id = " + lessonId);
        int status = 0;

        try {
            status = lessonService.remove(lessonId);
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

        LOGGER.debug("Successfully remove lesson with id: " + lessonId);

        return status;
    }
}
