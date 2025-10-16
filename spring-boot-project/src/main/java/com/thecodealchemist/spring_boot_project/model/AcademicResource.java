package com.thecodealchemist.spring_boot_project.model;

import java.time.LocalDateTime;

public class AcademicResource {

    private Integer resourceId;
    private Integer studentId; 
    private String subjectcode;
    private String course;
    private ResourceType resourceType;
    private LocalDateTime uploadDate = LocalDateTime.now();
    private byte[] content;

    public enum ResourceType {
        PYQ, Assignment, Tutorials, Books, Notes
    }

    // Getters and setters
    public Integer getResourceId() { return resourceId; }
    public void setResourceId(Integer resourceId) { this.resourceId = resourceId; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public String getSubjectCode() { return subjectcode; }
    public void setSubjectCode(String subjectcode) { this.subjectcode = subjectcode; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public ResourceType getResourceType() { return resourceType; }
    public void setResourceType(ResourceType resourceType) { this.resourceType = resourceType; }

    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }

    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }
}

