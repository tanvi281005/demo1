package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.AcademicResourceRepository;
import com.thecodealchemist.spring_boot_project.model.AcademicResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AcademicResourceService {

    private final AcademicResourceRepository resourceRepository;

    public AcademicResourceService(AcademicResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    // Upload PDF resource
    public void uploadResource(Integer studentId, String course, AcademicResource.ResourceType type, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("PDF file must be provided");
        }

        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        AcademicResource resource = new AcademicResource();
        resource.setStudentId(studentId);  // From session
        resource.setCourse(course);
        resource.setResourceType(type);
        resource.setContent(file.getBytes());

        resourceRepository.save(resource);
    }
}
