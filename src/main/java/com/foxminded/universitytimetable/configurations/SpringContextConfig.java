package com.foxminded.universitytimetable.configurations;

import com.foxminded.universitytimetable.dao.impl.*;
import com.foxminded.universitytimetable.models.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.foxminded.universitytimetable")
public class SpringContextConfig {
    public static AnnotationConfigApplicationContext context
            = new AnnotationConfigApplicationContext(SpringJDBCPostgreSQLConfig.class);

    // Models
    @Bean
    public DayTimetable dayTimetableBean() {
        return new DayTimetable();
    }

    @Bean
    public Group groupBean() {
        return new Group();
    }

    @Bean
    public Lesson lessonBean() {
        return new Lesson();
    }

    @Bean
    public Professor professorBean() {
        return new Professor();
    }

    @Bean
    public Timetable timetableBean() {
        return new Timetable();
    }

    // Impl
    @Bean
    public DayTimetableImpl dayTimetableImplBean() {
        return new DayTimetableImpl();
    }

    @Bean
    public GroupImpl groupImplBean() {
        return new GroupImpl();
    }

    @Bean
    public LessonImpl lessonImplBean() {
        return new LessonImpl();
    }

    @Bean
    public ProfessorImpl professorImplBean() {
        return new ProfessorImpl();
    }

    @Bean
    public StatisticsImpl statisticsImplBean() {
        return new StatisticsImpl();
    }
}
