package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.Counsellor;
import com.thecodealchemist.spring_boot_project.service.CounsellorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counsellor")
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

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
