package com.thecodealchemist.spring_boot_project.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class NotificationRepository {

    private final JdbcTemplate jdbcTemplate;

    public NotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveSubscription(int userId, String subjectCode, String type) {
        String sql = "INSERT INTO ResourceNotificationSubscription (user_id, subject_code, resource_type) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, subjectCode, type);
    }

    public List<Map<String, Object>> findSubscribers(String subjectCode, String type) {
        String sql = "SELECT * FROM ResourceNotificationSubscription WHERE subject_code = ? AND resource_type = ? AND notified = FALSE";
        return jdbcTemplate.queryForList(sql, subjectCode, type);
    }

    public void markAsNotified(String subjectCode, String type) {
        String sql = "UPDATE ResourceNotificationSubscription SET notified = TRUE WHERE subject_code = ? AND resource_type = ?";
        jdbcTemplate.update(sql, subjectCode, type);
    }
}
