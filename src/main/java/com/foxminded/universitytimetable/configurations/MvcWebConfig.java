package com.foxminded.universitytimetable.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/groups").setViewName("groups");
        registry.addViewController("/lessons").setViewName("lessons");
        registry.addViewController("/professors").setViewName("professors");
        registry.addViewController("/statistics").setViewName("statistics");
        registry.addViewController("/timetable").setViewName("timetable");
    }
}
