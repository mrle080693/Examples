package com.foxminded.universitytimetable.configurations;

import com.foxminded.universitytimetable.dao.impl.*;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.foxminded.universitytimetable")
public class SpringContextConfig {
    public static AnnotationConfigApplicationContext context
            = new AnnotationConfigApplicationContext(SpringJDBCPostgreSQLConfig.class);

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

    @Bean
    public TimetableImpl timetableImplBean() {
        return new TimetableImpl();
    }
}
