package com.foxminded.universitytimetable.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class MyController {
    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/groups")
    public String getGroupsPage() {
        return "groups";
    }

    @GetMapping("/lessons")
    public String getLessonsPage() {
        return "lessons";
    }

    @GetMapping("/professors")
    public String getProfessorsPage() {
        return "professors";
    }

    @GetMapping("/statistics")
    public String getStatisticsPage() {
        return "statistics";
    }

    @GetMapping("/timetable")
    public String getTimetablePage() {
        return "timetable";
    }

}
