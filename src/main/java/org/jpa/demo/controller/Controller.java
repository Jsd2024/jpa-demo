package org.jpa.demo.controller;

import org.jpa.demo.domain.Employee;
import org.jpa.demo.domain.User;
import org.jpa.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class Controller {

    @Autowired
    UserService service;
    @RequestMapping("/getUsers")
    public List<User> getUserData(){
        List<Object> users = new ArrayList<>();
        List<Map<String, Object>> usersFromDb1 = service.getUsersFromDb1();

        usersFromDb1
                .stream()
                .map(map -> map.entrySet())
//                        .flatMap((k,v)-> {Map.  })
                .forEach(System.out::println);
////                .map()
//                .map((u) -> {
//                    System.out.print("Hello Employees: "+u);
////                    System.out.print("Hello Employees: "+v);
//
//                    return u.e;
//                })
//                .toList() ;

       users= usersFromDb1.stream()
                .flatMap(map -> map.entrySet().stream())
                .map((k)->
                {
                    System.out.println("key "+k.getKey());

                    System.out.println("value "+k.getValue());

                    return (Object) k.getValue();
                })
               .toList();

       List<User> userList= new ArrayList<>();
               users.stream()
//                       .filter(u-> u instanceof  User)
                       .forEach(u-> {
                           userList.add((User) u);
                       });



//                .forEach( entry -> System.out.println(entry.getKey() + ":" + entry.getValue()) );
//        System.out.print("Hello Employees: "+users);
        return userList;
    }
    @RequestMapping("/")
    public List<Employee> test(){
        List<Employee> employeeList = List.of(
                Employee.builder().age(30).name("Abc").add("St.").build(),
                Employee.builder().age(36).name("iuy").add("St.").build(),
                Employee.builder().age(48).name("hgc").add("St.").build(),
                Employee.builder().age(61).name("uyt").add("St.").build()) ;
        System.out.print("Hello Employees: "+employeeList);
        return employeeList;
    }
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
