package com.foxminded.universitytimetable.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan("com.foxminded.universitytimetable")
public class HibernateConfig {
    @Bean
    public EntityManagerFactory entityManagerFactoryBean(){
        return Persistence.createEntityManagerFactory("mr");
    }
}
