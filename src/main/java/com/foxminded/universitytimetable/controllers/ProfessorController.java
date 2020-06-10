package com.foxminded.universitytimetable.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfessorController {
    @GetMapping("/professors")
    public String getProfessorsPage() {
        return "professors";
    }
}
