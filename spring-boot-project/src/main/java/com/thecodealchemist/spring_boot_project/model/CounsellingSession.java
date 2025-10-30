package com.thecodealchemist.spring_boot_project.model;

import java.sql.Time;

public class CounsellingSession {

    private Integer counsellorId;
    private Integer serviceId;
    private Boolean isApproved;
    private CounsellingMode counsellingMode;
    private Time timeapproved;

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

   

    public CounsellingMode getCounsellingMode() {
        return counsellingMode;
    }

    public void setCounsellingMode(CounsellingMode counsellingMode) {
        this.counsellingMode = counsellingMode;
    }

    public Time gettimeapproved() {
        return timeapproved;
    }

    public void settimeapproved(Time finalTime) {
        this.timeapproved = finalTime;
    }
}
