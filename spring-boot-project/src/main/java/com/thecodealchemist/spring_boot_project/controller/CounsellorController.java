package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.Counsellor;
import com.thecodealchemist.spring_boot_project.model.CounsellorFormDTO;
import com.thecodealchemist.spring_boot_project.service.CounsellorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/counsellor")
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    

@PostMapping("/register")
public ResponseEntity<String> registerCounsellor(
        @RequestBody CounsellorFormDTO dto,
        HttpSession session) {

    try {
        // Fetch student ID from session
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
            return ResponseEntity.status(401).body("❌ User not logged in");
        }

        // Pass studentId to service
        counsellorService.registerCounsellor(dto, studentId);
        return ResponseEntity.ok("✅ Counsellor registered successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError()
                .body("❌ Registration failed: " + e.getMessage());
    }
}

    // ✅ Fetch counsellor profile + timings + sessions
    @GetMapping("/{counsellorId}")
    public ResponseEntity<?> getCounsellorDetails(@PathVariable int counsellorId) {
        try {
            Map<String, Object> details = counsellorService.getCounsellorDetails(counsellorId);
            return ResponseEntity.ok(details);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching counsellor details.");
        }
    }

    // ✅ Fetch counsellors by category (e.g., Academics, Stress & Anxiety, etc.)
    @GetMapping("/fetch/{category}")
    public ResponseEntity<?> fetchCounsellors(@PathVariable("category") String category) {
        try {
            List<Counsellor> counsellors = counsellorService.getCounsellorsByCategory(category);
            return ResponseEntity.ok(counsellors);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error fetching counsellors.");
        }
    }
}
