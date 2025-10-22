package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.*;
import com.thecodealchemist.spring_boot_project.service.CounsellingBookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/counsellingbook")
public class CounsellingBookingController {

    @Autowired
    private CounsellingBookingService bookingService;

    @PostMapping("/book")
    public String bookCounselling(
            @RequestParam Integer counsellorId,
            @RequestParam String slot,
            @RequestParam CounsellingSession.CounsellingMode mode,
            HttpSession session
    ) {
        try {
            // 🔹 Fetch student ID from session
            Integer studentId = (Integer) session.getAttribute("studentId");
            if (studentId == null) {
                return "Error: No student logged in (session expired)";
            }

            // Create model objects
            Student student = new Student();
            student.setStudentId(studentId);

            Counsellor counsellor = new Counsellor();
            counsellor.setCounsellorId(counsellorId);

            // Delegate to service layer
            bookingService.bookCounsellingSession(student, counsellor, slot, mode);

            return "✅ Counselling slot booked successfully! Your request is pending approval.";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Failed to book counselling: " + e.getMessage();
        }
    }
}
