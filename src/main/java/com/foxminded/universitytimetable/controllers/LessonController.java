package com.foxminded.universitytimetable.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lessons")
public class LessonController {
    @GetMapping
    public String getLessonsPage() {
        return "lessons";
    }

}
