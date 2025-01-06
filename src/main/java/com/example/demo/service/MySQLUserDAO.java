package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class MySQLUserDAO implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySQLUserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name")
        ));
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name")
        ));
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO user (email, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getName());
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE user SET email = ?, name = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getName(), user.getId());
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
