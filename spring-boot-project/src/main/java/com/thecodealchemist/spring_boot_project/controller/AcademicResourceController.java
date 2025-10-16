package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.AcademicResource;
import com.thecodealchemist.spring_boot_project.service.AcademicResourceService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/resources")
public class AcademicResourceController {

    private final AcademicResourceService resourceService;

    public AcademicResourceController(AcademicResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResource(
            @RequestParam String course,
            @RequestParam String subjectCode, 
            @RequestParam AcademicResource.ResourceType resourceType,
            @RequestParam MultipartFile file,
            HttpSession session) {

        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
            return ResponseEntity.status(401).body("Unauthorized: Login required");
        }

        try {
            resourceService.uploadResource(studentId, course,subjectCode, resourceType, file);
            return ResponseEntity.ok("Resource uploaded successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/fetch-subjects")
    public ResponseEntity<?> fetchsubjects(HttpSession session) {
     Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return ResponseEntity.status(401).body("You must be logged in.");
    }
    List<String> subjects = resourceService.fetchuniquesubjects();
    return ResponseEntity.ok(subjects != null ? subjects : List.of());
    }
    
}
