package com.foxminded.universitytimetable.api;

import com.foxminded.universitytimetable.api.constants.Urls;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewsController {

    @GetMapping(Urls.GET_INDEX_HTML)
    public String getIndex(){
        return "index";
    }

    @GetMapping(Urls.GET_GROUPS_HTML)
    public String getGroups(){
        return "groups";
    }

    @GetMapping(Urls.GET_LESSONS_HTML)
    public String getLessons(){
        return "lessons";
    }

    @GetMapping(Urls.GET_PROFESSORS_HTML)
    public String getProfessors(){
        return "professors";
    }

    @GetMapping(Urls.GET_STATISTICS_HTML)
    public String getStatistics(){
        return "statistics";
    }

    @GetMapping(Urls.GET_TIMETABLE_HTML)
    public String getTimetable(){
        return "index";
    }

    @GetMapping(Urls.GET_ERROR_HTML)
    public String getErrorPage(){
        return "error-page";
    }
}
