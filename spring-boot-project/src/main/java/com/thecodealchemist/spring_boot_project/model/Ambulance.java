package com.thecodealchemist.spring_boot_project.model;

public class Ambulance {

    private Integer serviceId; // Primary key
    private Services service;  // PK & FK

    private String fromPos;
    private String toPos;
    private String patientCondition;
    private String ambulanceNo;
    private Boolean nurseRequired;
    private String necessity;
    public Ambulance() {}

    public Ambulance(Integer serviceId, Services service, String fromPos, String toPos,
                     String patientCondition, String ambulanceNo, Boolean nurseRequired,
                     String necessity) {
        this.serviceId = serviceId;
        this.service = service;
        this.fromPos = fromPos;
        this.toPos = toPos;
        this.patientCondition = patientCondition;
        this.ambulanceNo = ambulanceNo;
        this.nurseRequired = nurseRequired;
        this.necessity = necessity;
    }

    // Getters and Setters
    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public Services getService() { return service; }
    public void setService(Services service) { this.service = service; }

    public String getFromPos() { return fromPos; }
    public void setFromPos(String fromPos) { this.fromPos = fromPos; }

    public String getToPos() { return toPos; }
    public void setToPos(String toPos) { this.toPos = toPos; }

    public String getPatientCondition() { return patientCondition; }
    public void setPatientCondition(String patientCondition) { this.patientCondition = patientCondition; }

    public String getAmbulanceNo() { return ambulanceNo; }
    public void setAmbulanceNo(String ambulanceNo) { this.ambulanceNo = ambulanceNo; }

    public Boolean getNurseRequired() { return nurseRequired; }
    public void setNurseRequired(Boolean nurseRequired) { this.nurseRequired = nurseRequired; }

    public String getNecessity() { return necessity; }
    public void setNecessity(String necessity) { this.necessity = necessity; }

    @Override
    public String toString() {
        return "Ambulance{" +
                "serviceId=" + serviceId +
                ", service=" + service +
                ", fromPos='" + fromPos + '\'' +
                ", toPos='" + toPos + '\'' +
                ", patientCondition='" + patientCondition + '\'' +
                ", ambulanceNo='" + ambulanceNo + '\'' +
                ", nurseRequired=" + nurseRequired +
                ", necessity='" + necessity + '\'' +
                '}';
    }
}
