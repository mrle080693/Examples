package com.foxminded.universitytimetable.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.foxminded.universitytimetable")
@PropertySource("classpath:application.properties")
public class SpringJDBCConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringJDBCConfig.class);


    @Value("${spring.datasource.driver}")
    private String postgresDriverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("postgreSQLDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        return jdbcTemplate;
    }

    @Bean
    public DataSource postgreSQLDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresDriverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    /*
    @Bean
    public DataSource postgreSQLDataSourceFromJndi() throws NamingException {
        DataSource dataSource = null;
        JndiTemplate jndi = new JndiTemplate();

        try {
            dataSource = jndi.lookup("java:comp/env/jdbc/MyLocalDB", DataSource.class);
        } catch (NamingException e) {
            LOGGER.error("Try to get DataSource for PostgreSQL from JNDI was failed");
            throw e;
        }

        return dataSource;
    }
*/
}
