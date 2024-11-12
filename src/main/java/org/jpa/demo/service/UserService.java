package org.jpa.demo.service;

import org.jpa.demo.repository.UserRepositoryDb1;
import org.jpa.demo.repository.UserRepositoryDb2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class UserService {

    private final UserRepositoryDb1 userRepositoryDb1;
    private final UserRepositoryDb2 userRepositoryDb2;

    @Autowired
    public UserService(UserRepositoryDb1 userRepositoryDb1, UserRepositoryDb2 userRepositoryDb2) {
        this.userRepositoryDb1 = userRepositoryDb1;
        this.userRepositoryDb2 = userRepositoryDb2;
    }

    public List<Map<String, Object>> getUsersFromDb1() {
        return userRepositoryDb1.getAllUsers();
    }

    public List<Map<String, Object>> getUsersFromDb2() {
        return userRepositoryDb2.getAllUsers();
    }

    public int saveUserToDb1(String name) {
        return userRepositoryDb1.saveUser(name);
    }

    public int saveUserToDb2(String name) {
        return userRepositoryDb2.saveUser(name);
    }

    public int updateUserInDb1(Long id, String name) {
        return userRepositoryDb1.updateUser(id, name);
    }

    public int updateUserInDb2(Long id, String name) {
        return userRepositoryDb2.updateUser(id, name);
    }

    public int deleteUserFromDb1(Long id) {
        return userRepositoryDb1.deleteUser(id);
    }

    public int deleteUserFromDb2(Long id) {
        return userRepositoryDb2.deleteUser(id);
    }
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
