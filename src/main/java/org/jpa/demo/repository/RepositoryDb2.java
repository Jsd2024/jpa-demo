package org.jpa.demo.repository;

import lombok.extern.slf4j.Slf4j;
import org.jpa.demo.domain.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class RepositoryDb2 {
    @Autowired
    @Qualifier("jdbcTemplate2")
    private final JdbcTemplate jdbcTemplate2;

    @Autowired
    public RepositoryDb2(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate1) {
        this.jdbcTemplate2 = jdbcTemplate1;
    }
    public List<Employee> getAllEmpList() {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        try {
            employeeList = jdbcTemplate2.query(
                    sql,
                    (rs, rowNum) ->
                            new Employee(
                                    rs.getInt("eid"),
                                    rs.getString("name"),
                                    rs.getString("address"),
                                    rs.getInt("age")
                            )
            );
        } catch (Exception e) {
            log.error("Exc:"+e);
            throw new RuntimeException(e);
        }
        return employeeList;
    }

//    public List<Map<String, Object>> getAllUsers() {
//        String sql = "SELECT * FROM users";  // Example query
//        return jdbcTemplate.queryForList(sql);
//    }
//
//    public int saveUser(String name) {
//        String sql = "INSERT INTO users (name) VALUES (?)";
//        return jdbcTemplate.update(sql, name);
//    }
//
//    public int updateUser(Long id, String name) {
//        String sql = "UPDATE users SET name = ? WHERE id = ?";
//        return jdbcTemplate.update(sql, name, id);
//    }
//
//    public int deleteUser(Long id) {
//        String sql = "DELETE FROM users WHERE id = ?";
//        return jdbcTemplate.update(sql, id);
//    }
}
