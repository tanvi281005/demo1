package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.StudentNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentNotificationService {

    @Autowired
    private StudentNotificationRepository notificationRepository;

    // Send a notification to a student
    public void sendNotification(int studentId, String message) {
        notificationRepository.insertNotification(studentId, message);
    }

    // Get all unread notifications for a student
    public List<Map<String, Object>> getUnreadNotifications(int studentId) {
        return notificationRepository.getUnreadNotifications(studentId);
    }

    // Mark a notification as read
    public void markNotificationRead(int notificationId) {
        notificationRepository.markAsRead(notificationId);
    }
}
