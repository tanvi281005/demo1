package com.thecodealchemist.spring_boot_project.model;

public class Services {

    private Integer serviceId;
    private ServiceType serviceName;

    public enum ServiceType {
        TRANSPORT_SERVICES("Transport Services"),
        BUY_SELL("Buy/Sell"),
        MENTORSHIP("Mentorship"),
        ACADEMICS("Academics"),
        FOOD_SERVICES("Food Services");

        private final String displayName;

        ServiceType(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public Services() {}

    public Services(Integer serviceId, ServiceType serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    // Getters and Setters
    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public ServiceType getServiceName() { return serviceName; }
    public void setServiceName(ServiceType serviceName) { this.serviceName = serviceName; }

    @Override
    public String toString() {
        return "Services{" +
                "serviceId=" + serviceId +
                ", serviceName=" + serviceName +
                '}';
    }
}
