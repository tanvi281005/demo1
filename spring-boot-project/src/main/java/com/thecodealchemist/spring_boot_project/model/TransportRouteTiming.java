package com.thecodealchemist.spring_boot_project.model;

public class TransportRouteTiming {

    private int routeId;
    private String timings;  // store as string for easy JSON serialization

    public TransportRouteTiming() {}

    public TransportRouteTiming(int routeId, String timings) {
        this.routeId = routeId;
        this.timings = timings;
    }

    // Getters and Setters
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }
}
