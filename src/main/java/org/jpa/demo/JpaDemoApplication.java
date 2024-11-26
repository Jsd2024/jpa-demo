package org.jpa.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

@SpringBootApplication
public class JpaDemoApplication {
    public static void main(String[] args) {
        // The SpringApplication.run method starts the Spring Boot application
        SpringApplication.run(JpaDemoApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;
    @PostConstruct
    public void saveUserData(){
        String sqlStatements[] = {
                "insert into Users(id, age, name, add) values(1,30,'Donald','Trump')"
                //"insert into employees(first_name, last_name) values('Barack','Obama')"
        };

        Arrays.asList(sqlStatements).forEach(sql -> {
            jdbcTemplate.execute(sql);
        });



//        List<User> employeeList = List.of(
//                User.builder().id(1).age(30).name("Abc").add("St.").build(),
//                User.builder().id(2).age(36).name("iuy").add("St.").build(),
//                User.builder().id(3).age(48).name("hgc").add("St.").build(),
//                User.builder().id(4).age(61).name("uyt").add("St.").build()) ;
//        System.out.print("Hello Employees: "+employeeList);
        //return employeeList;
    }
}
