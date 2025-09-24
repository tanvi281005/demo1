package com.thecodealchemist.spring_boot_project.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Request {

    private Integer requestId;
    private Student student;
    private Services service;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime finishedAt;
    private LocalTime duration;
    private Status status;

    public enum Status {
        Pending,
        Completed,
        Ongoing,
        Rejected
    }

    // Constructors
    public Request() {}

    public Request(Integer requestId, Student student, Services service,
                   LocalDateTime createdAt, LocalDateTime finishedAt,
                   LocalTime duration, Status status) {
        this.requestId = requestId;
        this.student = student;
        this.service = service;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.duration = duration;
        this.status = status;
    }

    // Getters and Setters
    public Integer getRequestId() { return requestId; }
    public void setRequestId(Integer requestId) { this.requestId = requestId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Services getService() { return service; }
    public void setService(Services service) { this.service = service; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getFinishedAt() { return finishedAt; }
    public void setFinishedAt(LocalDateTime finishedAt) { this.finishedAt = finishedAt; }

    public LocalTime getDuration() { return duration; }
    public void setDuration(LocalTime duration) { this.duration = duration; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", student=" + student +
                ", service=" + service +
                ", createdAt=" + createdAt +
                ", finishedAt=" + finishedAt +
                ", duration=" + duration +
                ", status=" + status +
                '}';
    }
}
