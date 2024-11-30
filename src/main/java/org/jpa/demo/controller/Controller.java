package org.jpa.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.jpa.demo.domain.Employee;
import org.jpa.demo.domain.User;
import org.jpa.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j

@RestController
public class Controller {

    @Autowired
    Service service;

    @RequestMapping("/getUsers")
    public List<User> getUserDataDb1() {
        List<User> userList = new ArrayList<>();

        try {
            userList = service.getUsersListFromDb1();
        } catch (Exception e) {
            log.error("Excep: " + e);
            throw new RuntimeException(e);
        }
        return userList;
    }

    @RequestMapping("/getEmps")
    public List<Employee> getUserDataDb2() {
        List<Employee> empList = new ArrayList<>();

        try {
            List<Map<String, Object>> empsFromDb2 = new ArrayList<>();

            empsFromDb2
                    .stream()
                    .map(map -> map.entrySet())
                    .forEach(System.out::println);
            empList = service.getEmpsFromDb2();
        } catch (Exception e) {
            log.error("Excep: " + e);
            throw new RuntimeException(e);
        }
        return empList;
    }
}
