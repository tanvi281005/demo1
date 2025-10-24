package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.dao.CounsellingBookingRepository;
import com.thecodealchemist.spring_boot_project.model.*;
import com.thecodealchemist.spring_boot_project.service.CounsellingBookingService;
import com.thecodealchemist.spring_boot_project.service.StudentNotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/counsellingbook")
public class CounsellingBookingController {

    @Autowired
    private CounsellingBookingService bookingService;

    @Autowired
    private StudentNotificationService studentNotificationService; // for counsellor notifications reuse

    @Autowired
    private CounsellingBookingRepository bookingRepository;

    /**
     * Student books a counselling slot (existing behavior).
     */
    @PostMapping("/book")
    public String bookCounselling(
            @RequestParam Integer counsellorId,
            @RequestParam String slot,
            @RequestParam CounsellingSession.CounsellingMode mode,
            HttpSession session
    ) {
        try {
            Integer studentId = (Integer) session.getAttribute("studentId");
            if (studentId == null) {
                return "Error: No student logged in (session expired)";
            }

            Student student = new Student();
            student.setStudentId(studentId);

            Counsellor counsellor = new Counsellor();
            counsellor.setCounsellorId(counsellorId);

            bookingService.bookCounsellingSession(student, counsellor, slot, mode);

            return "✅ Counselling slot booked successfully! Your request is pending approval.";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Failed to book counselling: " + e.getMessage();
        }
    }

 

@PostMapping("/approve")
public String updateBookingStatus(
        @RequestParam Integer serviceId,
        @RequestParam boolean isApproved,
        HttpSession session
) {
    try {
        Integer counsellorId = (Integer) session.getAttribute("studentId");
        if (counsellorId == null) {
            return "❌ Error: No counsellor logged in (session expired)";
        }

        bookingService.updateBookingStatus(serviceId, counsellorId, isApproved);
        return isApproved
                ? "✅ Request approved successfully!"
                : "❌ Request rejected successfully!";
    } catch (Exception e) {
        e.printStackTrace();
        return "❌ Failed to update booking status: " + e.getMessage();
    }
}
@GetMapping("/pending")
@ResponseBody
public List<Map<String, Object>> getPendingRequests(HttpSession session) {
    Integer counsellorId = (Integer) session.getAttribute("studentId");
    System.out.println("🔍 Session ID: " + session.getId());
    System.out.println("🔍 student_id: " + session.getAttribute("student_id"));
    System.out.println("🔍 studentId: " + session.getAttribute("studentId"));
    if (counsellorId == null) {
        throw new RuntimeException("Counsellor not logged in");
    }
    return bookingRepository.findPendingRequestsForCounsellor(counsellorId);
}


}
