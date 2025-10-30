package com.thecodealchemist.spring_boot_project.model;
import java.sql.Timestamp;

public class Counsellor {
    private Integer counsellorId;
    private Specialization specialization;
    private Integer noOfStudentsCounselled;
    private String selfDescription;
    private Double rating;
    private Timestamp joinedAt;

    public enum Specialization {
        Academics,
        SubstanceAddiction,
        StressAndAnxiety,
        GriefAndLoss,
        PersonalRelationships
    }
    
    public Integer getCounsellorId() {
        return counsellorId;
    }

    public void setCounsellorId(Integer counsellorId) {
        this.counsellorId = counsellorId;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Integer getNoOfStudentsCounselled() {
        return noOfStudentsCounselled;
    }

    public void setNoOfStudentsCounselled(Integer noOfStudentsCounselled) {
        this.noOfStudentsCounselled = noOfStudentsCounselled;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }
}
