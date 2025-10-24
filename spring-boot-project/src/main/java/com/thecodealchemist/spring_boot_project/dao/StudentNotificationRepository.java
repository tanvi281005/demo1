package com.thecodealchemist.spring_boot_project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StudentNotificationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Insert a new notification
    public void insertNotification(int studentId, String message) {
        String sql = "INSERT INTO student_notification (student_id, message) VALUES (?, ?)";
        jdbcTemplate.update(sql, studentId, message);
    }

    // Fetch unread notifications for a student
    public List<Map<String, Object>> getUnreadNotifications(int studentId) {
        String sql = "SELECT * FROM student_notification WHERE student_id = ? AND is_read = FALSE";
        return jdbcTemplate.queryForList(sql, studentId);
    }

    // Mark a notification as read
    public void markAsRead(int notificationId) {
        String sql = "UPDATE student_notification SET is_read = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, notificationId);
    }
}
