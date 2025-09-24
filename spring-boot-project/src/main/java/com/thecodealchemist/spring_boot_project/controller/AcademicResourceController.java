package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.AcademicResource;
import com.thecodealchemist.spring_boot_project.service.AcademicResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resources")
public class AcademicResourceController {

    private final AcademicResourceService resourceService;

    public AcademicResourceController(AcademicResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public static class ResourceRequestDTO {
        public Integer studentId;
        public String course;
        public AcademicResource.ResourceType resourceType;
        public MultipartFile file;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResource(@RequestBody ResourceRequestDTO requestDTO) {
        try {
            resourceService.uploadResource(
                    requestDTO.studentId,
                    requestDTO.course,
                    requestDTO.resourceType,
                    requestDTO.file
            );
            return ResponseEntity.ok("Resource uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
