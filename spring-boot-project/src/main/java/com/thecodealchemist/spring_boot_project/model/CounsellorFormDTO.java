package com.thecodealchemist.spring_boot_project.model;

import java.util.List;

public class CounsellorFormDTO {
    private String specialization; // matches frontend select value
    private String selfDescription; // textarea
    private List<String> timings;   // array of times

    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    public List<String> getTimings() {
        return timings;
    }

    public void setTimings(List<String> timings) {
        this.timings = timings;
    }
}
