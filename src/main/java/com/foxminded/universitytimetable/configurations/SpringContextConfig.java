package com.foxminded.universitytimetable.configurations;

import com.foxminded.universitytimetable.dao.impl.*;
import com.foxminded.universitytimetable.models.*;
import org.springframework.context.annotation.*;

/*
Я подумал что бины которые мы будем использовать не только для jdbcPostgres нужно написать отдельно.
Или ты имеешь ввиду, сменить скоуп моделей?
 */
@Configuration
@ComponentScan("com.foxminded.universitytimetable")
public class SpringContextConfig {
    public static AnnotationConfigApplicationContext context
            = new AnnotationConfigApplicationContext(SpringJDBCPostgreSQLConfig.class);

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

    // Models
    @Bean
    @Scope("prototype")
    public DayTimetable dayTimetableBean() {
        return new DayTimetable();
    }

    @Bean
    @Scope("prototype")
    public Group groupBean() {
        return new Group();
    }

    @Bean
    @Scope("prototype")
    public Lesson lessonBean() {
        return new Lesson();
    }

    @Bean
    @Scope("prototype")
    public Professor professorBean() {
        return new Professor();
    }

    @Bean
    @Scope("prototype")
    public Timetable timetableBean() {
        return new Timetable();
    }
}
