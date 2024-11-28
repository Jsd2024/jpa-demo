package org.jpa.demo;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
@EnableJpaRepositories
@Slf4j
@SpringBootApplication
public class JpaDemoApplication {
    public static void main(String[] args) {
        // The SpringApplication.run method starts the Spring Boot application
        SpringApplication.run(JpaDemoApplication.class, args);
    }

    private final JdbcTemplate jdbcTemplate1;
    private final JdbcTemplate jdbcTemplate2;

    public JpaDemoApplication(@Qualifier("jdbcTemplate1") JdbcTemplate jdbcTemplate1,
                              @Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate2) {
        this.jdbcTemplate1 = jdbcTemplate1;
        this.jdbcTemplate2 = jdbcTemplate2;
    }

    @PostConstruct
    public void saveUserData() {

        try {
            String sqlStatements[] = {
                    //"CREATE TABLE USERS ( id INT PRIMARY KEY, age INT, name VARCHAR(255), address VARCHAR(255) )",
                    "insert into Users(id, age, name, address) values(1,38,'Donald','Trump')",
                    //"CREATE TABLE EMPLOYEE ( id INT PRIMARY KEY, age INT, name VARCHAR(255), address VARCHAR(255) )",
                    "insert into Employee(eid, age, name, address) values(1,30,'Barack','Obama')"
            };

//        Arrays.asList(sqlStatements).forEach(sql -> {
//
//            jdbcTemplate1.execute(sql);
//        });

            for (int i = 0; i < sqlStatements.length; i++) {
                if (i == 0 ) {//|| i==1
                    jdbcTemplate1.execute(sqlStatements[i]);
                }
                if (i > 0) {
                    jdbcTemplate2.execute(sqlStatements[i]);
                }
            }
        } catch (Exception e) {
            log.error("Ex >> "+e);
            throw new RuntimeException(e);
        }


    }


}
