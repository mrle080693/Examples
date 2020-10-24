package com.foxminded.universitytimetable.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*

 */
@Controller("/html")
public class BrowserViewsController {

    @GetMapping("/index")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/groups")
    public String getGroups(){
        return "groups";
    }

    @GetMapping("/lessons")
    public String getLessons(){
        return "lessons";
    }

    @GetMapping("/professor")
    public String getProfessors(){
        return "professors";
    }

    @GetMapping("/statistics")
    public String getStatistics(){
        return "statistics";
    }

    @GetMapping("/timetable")
    public String getTimetable(){
        return "index";
    }

    @GetMapping("/sorry-page")
    public String getSorryPage(){
        return "sorry-page";
    }
}
