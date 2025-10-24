package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.service.StudentNotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/student/notification")
public class StudentNotificationController {

    @Autowired
    private StudentNotificationService notificationService;

    // ✅ Fetch unread notifications
    @GetMapping("/unread")
    public List<Map<String, Object>> getUnreadNotifications(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) return List.of();
        return notificationService.getUnreadNotifications(studentId);
    }

    // ✅ Mark a notification as read
    @PostMapping("/mark-read")
    public void markNotificationRead(@RequestParam Integer notificationId) {
        notificationService.markNotificationRead(notificationId);
    }
}
