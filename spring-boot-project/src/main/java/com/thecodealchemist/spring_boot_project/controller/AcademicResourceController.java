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

    @GetMapping("/fetch")
public ResponseEntity<?> fetchResources(
        @RequestParam String subject,
        @RequestParam AcademicResource.ResourceType type,
        HttpSession session) {

    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return ResponseEntity.status(401).body("You must be logged in.");
    }

    try {
        var resources = resourceService.findResourcesBySubjectAndType(subject, type);
        return ResponseEntity.ok(resources);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error fetching resources: " + e.getMessage());
    }
}

@GetMapping("/download/{id}")
public ResponseEntity<byte[]> downloadResource(@PathVariable int id) {
    try {
        AcademicResource resource = resourceService.getResourceById(id);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=resource_" + id + ".pdf")
                .body(resource.getContent());
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);
    }
}
@PostMapping("/notify-me")
public ResponseEntity<?> notifyMe(
        @RequestParam String subject,
        @RequestParam AcademicResource.ResourceType type,
        HttpSession session) {

    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return ResponseEntity.status(401).body("Unauthorized: Please log in.");
    }

    try {
        resourceService.saveNotificationSubscription(studentId, subject, type);
        return ResponseEntity.ok("You’ll be notified when new material is uploaded!");
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}
@GetMapping("/notifications/fetch")
public ResponseEntity<?> fetchNotifications(HttpSession session) {
    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return ResponseEntity.status(401).body("Unauthorized: Please log in.");
    }

    try {
        var notifications = resourceService.fetchUserNotifications(studentId);
        return ResponseEntity.ok(notifications);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error fetching notifications: " + e.getMessage());
    }
}


}

    

