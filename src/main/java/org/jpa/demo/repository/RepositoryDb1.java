package org.jpa.demo.repository;

import lombok.extern.slf4j.Slf4j;
import org.jpa.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
@Repository
public class RepositoryDb1 {
    @Autowired
    @Qualifier("jdbcTemplate1")
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RepositoryDb1(@Qualifier("jdbcTemplate1") JdbcTemplate jdbcTemplate) {
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

    public List<User> getAllUsersList() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            users = jdbcTemplate.query(
                    sql,
                    (rs, rowNum) ->
                            new User(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("address"),
                                    rs.getInt("age")
                            )
            );
        } catch (Exception e) {
            log.error("Exc:"+e);
            throw new RuntimeException(e);
        }
//        users = //jdbcTemplate.queryForObject(sql, List.class);
        return users;
    }
}
