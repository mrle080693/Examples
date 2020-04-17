package com.foxminded.universitytimetable.configurations;

import com.foxminded.universitytimetable.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.foxminded.universitytimetable")
public class SpringJDBCPostgreSQLConfig {
    @Bean
    public DataSource postgreSQLDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Constants.POSTGRES_DRIVER_CLASS_NAME);
        dataSource.setUrl(Constants.URL);
        dataSource.setUsername(Constants.USERNAME);
        dataSource.setPassword(Constants.PASS);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(postgreSQLDataSource());

        return jdbcTemplate;
    }
}
