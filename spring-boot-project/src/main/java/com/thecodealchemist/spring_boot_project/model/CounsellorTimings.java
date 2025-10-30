package com.thecodealchemist.spring_boot_project.model;

import java.time.LocalTime;

public class CounsellorTimings {

    private Integer counsellorId;
    private LocalTime timing;

    // Getters and Setters
    public Integer getCounsellorId() {
        return counsellorId;
    }

    public void setCounsellorId(Integer counsellorId) {
        this.counsellorId = counsellorId;
    }

    public LocalTime getTiming() {
        return timing;
    }

    public void setTiming(LocalTime timing) {
        this.timing = timing;
    }
}
