package com.foxminded.universitytimetable.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LessonController {
    @GetMapping("/lessons")
    public String getLessonsPage() {
        return "lessons";
    }
}
