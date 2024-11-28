package org.jpa.demo.service;

import jakarta.persistence.EntityManagerFactory;
import org.jpa.demo.domain.Employee;
import org.jpa.demo.domain.User;
import org.jpa.demo.repository.RepositoryDb1;
import org.jpa.demo.repository.RepositoryDb2;
import org.springframework.beans.factory.annotation.*;

import java.util.List;
import java.util.Map;
@org.springframework.stereotype.Service
public class Service {

    private final RepositoryDb1 repositoryDb1;
    private final RepositoryDb2 repositoryDb2;

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManagerFactory entityDb2ManagerFactory;

    @Autowired
    public Service(RepositoryDb1 repositoryDb1
            , RepositoryDb2 repositoryDb2, EntityManagerFactory entityManagerFactory, EntityManagerFactory entityDb2ManagerFactory) {
        this.repositoryDb1 = repositoryDb1;
        this.repositoryDb2 = repositoryDb2;
        this.entityManagerFactory = entityManagerFactory;
        this.entityDb2ManagerFactory = entityDb2ManagerFactory;
    }




    public List<Map<String, Object>> getUsersFromDb1() {
        return repositoryDb1.getAllUsers();
    }

    public List<User> getUsersListFromDb1() {
        return repositoryDb1.getAllUsersList();
    }

    public List<Employee> getEmpsFromDb2() {
        return repositoryDb2.getAllEmpList();
    }

//    public int saveUserToDb1(String name) {
//        return userRepositoryDb1.saveUser(name);
//    }

//    public int saveUserToDb2(String name) {
//        return repositoryDb2.saveUser(name);
//    }

//    public int updateUserInDb1(Long id, String name) {
//        return userRepositoryDb1.updateUser(id, name);
//    }

//    public int updateUserInDb2(Long id, String name) {
//        return repositoryDb2.updateUser(id, name);
//    }

//    public int deleteUserFromDb1(Long id) {
//        return userRepositoryDb1.deleteUser(id);
//    }

//    public int deleteUserFromDb2(Long id) {
//        return repositoryDb2.deleteUser(id);
//    }
}

//777777
//@Service
//public class DatabaseService {
//
//    @Autowired
//    @Qualifier("jdbcTemplate1")
//    private JdbcTemplate jdbcTemplate1;
//
//    @Autowired
//    @Qualifier("jdbcTemplate2")
//    private JdbcTemplate jdbcTemplate2;
//
//    public void queryDatabase1() {
//        getData("SELECT * FROM table1", jdbcTemplate1);
//    }
//
//    private void getData(String query, JdbcTemplate jdbcTemplate1) {
//        String sql = query;
//        List<Map<String, Object>> result = jdbcTemplate1.queryForList(sql);
//        result.forEach(row -> System.out.println(row));
//    }
//
//    public void queryDatabase2() {
//        getData("SELECT * FROM table2", jdbcTemplate2);
//    }
//}
