package org.jpa.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryDb1 {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryDb1(@Qualifier("jdbcTemplate1") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getAllUsers() {
        String sql = "SELECT * FROM users";  // Example query
        return jdbcTemplate.queryForList(sql);
    }

//    public int saveUser(String name) {
//        String sql = "INSERT INTO users (name) VALUES (?)";
//        return jdbcTemplate.update(sql, name);
//    }

    public int updateUser(Long id, String name) {
        String sql = "UPDATE users SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, name, id);
    }

    public int deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
