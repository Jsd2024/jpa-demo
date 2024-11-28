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
    public List<User> getUserDataDb1(){
        List<User> userList= new ArrayList<>();

        try {
//        List<Map<String, Object>> usersFromDb1 = service.getUsersFromDb1();

//        usersFromDb1
//                .stream()
//                .map(map -> map.entrySet())
//                .forEach(System.out::println);
       userList=service.getUsersListFromDb1();
        } catch (Exception e) {
            log.error("Excep: "+e);
            throw new RuntimeException(e);
        }
        return userList;
    }
    @RequestMapping("/getEmps")
    public List<Employee> getUserDataDb2(){
        List<Employee> empList= new ArrayList<>();

        try {
            List<Map<String, Object>> empsFromDb2 =new ArrayList<>();

            empsFromDb2
                    .stream()
                    .map(map -> map.entrySet())
                    .forEach(System.out::println);
            empList=service.getEmpsFromDb2();
        } catch (Exception e) {
            log.error("Excep: "+e);
            throw new RuntimeException(e);
        }
        return empList;
    }

//    @RequestMapping("/")
//    public List<Employee> test(){
//        List<Employee> employeeList = List.of(
//                Employee.builder().age(30).name("Abc").add("St.").build(),
//                Employee.builder().age(36).name("iuy").add("St.").build(),
//                Employee.builder().age(48).name("hgc").add("St.").build(),
//                Employee.builder().age(61).name("uyt").add("St.").build()) ;
//        System.out.print("Hello Employees: "+employeeList);
//        return employeeList;
//    }
//    @RequestMapping("/users")
//    public List<User> saveUserData(){
//        List<User> employeeList = List.of(
//                User.builder().age(30).name("Abc").add("St.").build(),
//                User.builder().age(36).name("iuy").add("St.").build(),
//                User.builder().age(48).name("hgc").add("St.").build(),
//                User.builder().age(61).name("uyt").add("St.").build()) ;
//        System.out.print("Hello Employees: "+employeeList);
//        return employeeList;
//    }
}
