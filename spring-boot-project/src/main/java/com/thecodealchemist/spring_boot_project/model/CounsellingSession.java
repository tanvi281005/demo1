package com.thecodealchemist.spring_boot_project.model;

import java.sql.Timestamp;

public class CounsellingSession {

    private Integer counsellorId;
    private Integer serviceId;
    private Boolean isApproved;
    private String timeOptions; // Stored as JSON in DB, represented as String in Java
    private CounsellingMode counsellingMode;
    private Timestamp finalTime;

    public enum CounsellingMode {
        Online,
        Offline
    }

    // Getters and Setters
    public Integer getCounsellorId() {
        return counsellorId;
    }

    public void setCounsellorId(Integer counsellorId) {
        this.counsellorId = counsellorId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getTimeOptions() {
        return timeOptions;
    }

    public void setTimeOptions(String timeOptions) {
        this.timeOptions = timeOptions;
    }

    public CounsellingMode getCounsellingMode() {
        return counsellingMode;
    }

    public void setCounsellingMode(CounsellingMode counsellingMode) {
        this.counsellingMode = counsellingMode;
    }

    public Timestamp getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(Timestamp finalTime) {
        this.finalTime = finalTime;
    }
}
